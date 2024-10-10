/* 모든 plus_info와 close_info에 대해 이벤트 리스너 설정 */
document.querySelectorAll(".plus_info").forEach(button => {
	button.addEventListener("click", function() {
		// 현재 m_list 내에서 list_info 찾기
		const list_info = this.closest(".m_list").querySelector(".list_info");
		// 현재 row 찾기
		const list = this.closest(".row");

		// 클릭 시 효과
		list.classList.add("active");

		// 리스트 보여주기
		list_info.style.display = "block";
		list_info.style.opacity = "0";
		list_info.style.transform = "translateY(20px)";

		// 요청 애니메이션 프레임
		requestAnimationFrame(() => {
			// 두 번 호출하여 부드러운 애니메이션 효과 추가
			requestAnimationFrame(() => {
				// 불투명도 설정
				list_info.style.opacity = "1";
				// 원래 위치로 이동
				list_info.style.transform = "translateY(0)";
			});
		});
	});
});

document.querySelectorAll(".close_info").forEach(button => {
	button.addEventListener("click", function() {
		const list_info = this.closest(".list_info");
		const list = list_info.closest(".m_list").querySelector(".row");

		list.classList.remove("active");

		list_info.style.opacity = "0";
		list_info.style.transform = "translateY(20px)";
		setTimeout(() => {
			list_info.style.display = "none";
		}, 500); // 애니메이션 시간과 맞추기
	});
});

/* 글쓰기 이동시 아이디 확인 */
function check_id() {
	const loginUser = $(".loginUser").val();
	console.log(loginUser);
	if (!loginUser) {
		alert("로그인 후 이용해주세요!");
		location.replace("/");
	} else {
		location.replace("/mboard/m_write");
	}
}

/* 더보기 구현 */
$(function() {
	// 처음 7개씩 list 요소를 보여줌
	$(".m_list").slice(0, 7).show();

	$(".more_list_btn").click(function() {
		$(".m_list:hidden").slice(0, 7).fadeIn(400);

		if ($(".m_list:hidden").length == 0) {
			$(".more_list_btn").hide();
		}
	});
});

/* 위로가기 버튼 구현 */
function up() {
	window.scrollTo({
		top: 0,
		behavior: "smooth"
	});
}

/* ------------------------------------------------------------------------------------- */
/* 참가/참여취소 버튼에 따라 user(스케줄 칼럼), board(member 칼럼) 테이블 업데이트
+ 이달의모임, mySchedule, list의 memberbox 동적 화면구현 파트 */

// 참가목록 받아오기 파트! (단지 받아와서 보여주기)--------------------------------------------------------
// 1) 참가자 목록 서버에서 가져오기
function getmembers(mboardnum, callback) {
	// ★ 멤버 배열 받아오는 Ajax
	$.ajax({
		method: "GET",
		// 보드넘버 넘겨주면서
		url: `/mboard/get_members?boardnum=${mboardnum}`,
		success: function(response) {
			// arUser 배열에 담음
			const arUser = JSON.parse(response);
			// 콜백을 호출해야만 받아온 arUser를 가지고 있는 getmembers 함수를 사용 할 수 있음
			callback(arUser);
		},
		error: function(xhr, status, error) {
			console.error("getmembers(참가자 목록 서버에서 가져오기) Error:", error);
		}
	});
}

// 2) 모든 게시판 참가자 목록을 초기화 및 보여주기
function member_view() {
	// 모든 listInfo 다 찾고 each 메소드로 각 listinfo에 대해 반복작업
	$(".list_info").each(function() {
		// 현재 listInfo를 가져오기
		const listInfo = $(this);
		// id 가져오기 == 보드넘버 받아오기
		const mboardnum = listInfo.attr("id");

		// 1) 참가자 목록 받아온 함수 호출
		getmembers(mboardnum, function(arUser) {
			// **에러 대비 - 혹시 있을 빈 문자열 제거 후 멤버 배열에 담아주기
			let members = arUser.filter(user => user.trim() !== "");

			// 멤버 추가할 list_info_4를 찾아!(listInfo 안의 멤버박스)
			const membersBox = listInfo.find(".list_info_4");
			// 새로운 참가자 목록을 다시 삽입하기 위해 비워주고 아래에서 다시 삽입(==초기화)
			membersBox.empty();
			if (members.length === 0) {
				// 참가자가 아무도 없으면(배열에 아무도 없음) 메세지 표시!
				membersBox.append("<div>참가 인원이 없습니다</div>");
			} else {
				// 참가자가 있는 경우(배열에 멤버들이 들어감) 이제 각각의 참여자들을 div로 묶어서 넣어주기
				members.forEach(user => {
					const newMember = $("<div></div>")
						.addClass("m_list_members") // 만들어둔 속성 추가
						.text(user); // 안에 받아온 아이디 추가
					// 새로운 멤버는 list_info_4 의 다음 요소로 추가
					membersBox.append(newMember);
				});
			}
		});
	});
}

// 3) 페이지 로드될때마다 참가자 목록 불러오기
$(document).ready(function() {
	member_view();
});

// 4) 참가하기 : 참가목록에 추가하기 파트 (Ajax 2개 + 추가 5개 실행)--------------------------------------------------
// 		4-1) html에 멤버 추가
// 		4-2) board 테이블 멤버 추가(DB)
// 		4-3) user 테이블 날짜 추가(DB)
// 		4-4) 이달의 모임 : 멤버수 증가
// 		4-5) my schedule 위젯 : 내 스케줄 추가
function addUser(element) {
	const loginUser = $(".loginUser").val();
	const listInfo = $(element).closest(".m_list").find(".list_info");
	// listInfo의 아이디 == 해당 boardnum
	const mboardnum = listInfo.attr("id");
	// listInfo의 제목
	const mboardtitle = listInfo.find(".list_title").text().trim();
	// listInfo의 아이디
	const writer = listInfo.find(".userid").text().trim();
	const flag = 'ameeting';
	let path = '/mboard/m_get?mboardnum=';
	path += mboardnum;
	console.log(path);

	// *유효성 - 비로그인 걸러내기
	if (!loginUser) {
		alert("로그인 후 이용해주세요!");
		location.replace("/");
		return;
	}

	// 1) 참가자 목록 서버에서 받아온 함수 호출 > 새 참가자 추가해야하니까
	getmembers(mboardnum, function(arUser) {
		let members = arUser.filter(user => user.trim() !== ""); // 혹시 있을 공백 제거

		// *유효성 - 멤버배열에 로그인유저가 있다면
		if (members.includes(loginUser)) {
			alert("이미 참여하셨습니다!");
			return;
		}

		// 최대 인원수 찾고 숫자로 바꿔주기
		const m_num = parseInt(listInfo.find('.m_num').text(), 10);
		// *유효성 - 인원수 초과
		if (members.length >= m_num) {
			alert("인원수를 초과하였습니다!");
			return;
		}

		// ★ 유저스케줄 받아오는 Ajax(유효성 검사 위해)
		$.ajax({
			url: `/user/get_schedule?userid=${loginUser}`,
			method: "GET",
			success: function(response) {
				// *유효성 - 최대 스케줄 수 3개 지정
				// 역슬래시 제거하고 각 날짜들을 배열로 변환
				const currentSchedule = response.split("\\").filter(Boolean);
				if (currentSchedule.length >= 3) {
					alert("최대 가능 스케줄은 3개입니다. 최대 스케줄을 초과하였습니다!");
					return;
				}

				// *유효성 - 모임 날짜 중복 안되게 하기
				// 모임 날짜 가져와서 배열에 담겨있는 날짜와 비교
				const list_setdate = listInfo.find(".m_date").text().split(": ")[1].trim();
				if (currentSchedule.includes(list_setdate)) {
					alert("회원님께서는 해당 날짜에 스케줄이 등록되어있습니다! 스케줄 조정 후 참여해주세요!");
					return;
				}

				// 배열에 참가자 추가해주기(2번 과정과 동일)
				members.push(loginUser); // 로그인 사용자 추가
				const membersBox = listInfo.find('.list_info_4');
				// 이전 멤버 리스트 비우고
				membersBox.empty();
				// 4-1) html에 멤버 추가
				members.forEach(user => {
					// 새로운 멤버 포함해서 배열 하나씩 추가
					$('<div></div>').addClass("m_list_members").text(user).appendTo(membersBox);
				});

				// 4-2) board 테이블 멤버 추가(DB)
				// ★ 서버에 멤버 추가해주는 Ajax
				const member = members.join("\\");
				$.ajax({
					url: '/mboard/put_member',
					method: "POST",
					contentType: 'application/json',
					data: JSON.stringify({
						member: member, boardnum: mboardnum
						, userid: writer, contentpath: path, boardtitle: mboardtitle, flag: flag
					}),
					success: function() {
						// 멤버 넣은 후 처리하기!!
						// 4-3) user 테이블 : 날짜 추가(DB)
						updateUserSchedule(mboardnum);
						// 4-4) 이달의 모임 : 멤버수 증가
						updateMemberCount(mboardnum);
						// 4-5) my schedule 위젯 : 내 스케줄 추가
						updateMySchedule();
						// 참가자 목록 업데이트
						member_view(mboardnum);
					},
					error: function(xhr) {
						console.error("요청 중 오류 발생:", xhr.statusText);
					}
				});
			},
			error: function(xhr) {
				console.error("스케줄 확인 요청 중 오류 발생:", xhr.statusText);
			}
		});
	});
}

// 5) 참여취소 : 참가목록에서 제거하기 파트 (Ajax 1개 + 추가 5개 실행)--------------------------------------------------
// 5-1) html에서 멤버 제거
// 5-2) board 테이블 멤버 제거(DB)
// 5-3) user 테이블 날짜 제거(DB)
// 5-4) 이달의 모임 : 멤버수 감소
// 5-5) my schedule 위젯 : 내 스케줄 제거
function delUser(element) {
	const loginUser = $(".loginUser").val();
	const listInfo = $(element).closest(".m_list").find(".list_info");
	const mboardnum = listInfo.attr("id");
	const mboardtitle = listInfo.find(".list_title").text().trim();
	const writer = listInfo.find(".userid").text().trim();
	const flag = 'dmeeting';
	let path = '/mboard/m_get?mboardnum=';
	path += mboardnum;

	// *유효성 - 비로그인 걸러내기
	if (!loginUser) {
		alert("로그인 후 이용해주세요!");
		location.replace("/");
		return;
	}
	
	if(writer==loginUser){
		alert("본인 모임에는 참가를 취소하실 수 없습니다!");
		return;
	}

	// 1) 참가자 목록 서버에서 받아온 함수 호출 > 유저 제거하고 남은 참가자들 추가해야하니까
	getmembers(mboardnum, function(arUser) {
		let members = arUser.filter(user => user.trim() !== "");

		// *유효성 - 로그인유저가 members 배열에 포함되었는지?
		if (!members.includes(loginUser)) {
			alert("이 모임에 참여하지 않은 상태입니다!");
			return;
		}

		// 로그인유저가 members 배열에 포함되었다면 > 참가자 목록에서 로그인 유저를 제거
		// 로그인유저가 아닌 멤버들만 새로운 배열로 만들어 members에 포함시킴
		// 참여 취소를 누름 == 누른사람(로그인 유저)만 빼고
		members = members.filter(user => user !== loginUser);
		const membersBox = listInfo.find(".list_info_4");
		// 기존 참가자 목록 지우고
		membersBox.empty();
		// 5-1) html에서 멤버 제거
		// 새로 members에 추가된 (삭제하기 누른 사람 빼고) 사람들을 for문돌며 추가
		members.forEach(user => {
			$("<div>").addClass("m_list_members").text(user).appendTo(membersBox);
		});

		// 5-2) board 테이블 멤버 제거(DB)
		// ★ 서버에 새로운 참가자들 보내서 업데이트하는 Ajax
		const member = members.join("\\");
		$.ajax({
			method: "POST",
			url: '/mboard/put_member',
			contentType: 'application/json',
			data: JSON.stringify({
				member: member, boardnum: mboardnum
				, userid: writer, contentpath: path, boardtitle: mboardtitle, flag: flag
			}),
			success: function(response) {
				// 5-3) user 테이블 날짜 제거(DB)
				removeUserSchedule(mboardnum);
				// 5-4) 이달의 모임 : 멤버수 감소
				updateMemberCount(mboardnum);
				// 5-5) my schedule 위젯 : 내 스케줄 제거
				updateMySchedule();
				// 참가자 목록 업데이트
				member_view(mboardnum);
			},
			error: function(xhr) {
				console.error("오류:", xhr.statusText);
			}
		});
	});
}

// 4-3) 참여하기를 누를때마다 user 테이블의 스케줄 칼럼에 날짜 추가 (Ajax 2개)(DB)
function updateUserSchedule(mboardnum) {
	const loginUser = $(".loginUser").val();

	// list_info의 '모임 날짜 : ' 제거, 날짜만 남게
	const setdate = $(`.list_info[id="${mboardnum}"] .m_date`).text().replace('모임 날짜 : ', '').trim();

	// ★ 로그인 유저에 맞는 스케줄 가져오는 Ajax
	$.ajax({
		method: "GET",
		url: `/user/get_schedule?userid=${loginUser}`,
		success: function(currentSchedule) {
			// 기존 스케줄과 합쳐서 data 만들기
			const newSchedule = currentSchedule ? `${currentSchedule}\\${setdate}` : setdate;
			const data = JSON.stringify({ userid: loginUser, schedule: newSchedule });

			// ★ 스케줄 업데이트 요청하는 Ajax
			$.ajax({
				method: "POST",
				url: '/user/update_schedule',
				contentType: 'application/json',
				data: data,
				success: function(response) {
					console.log("스케줄 업데이트 성공 : ", response);
				},
				error: function(xhr) {
					console.error("스케줄 업데이트 오류 : ", xhr.statusText);
				}
			});
		},
		error: function(xhr) {
			console.error("스케줄 가져오기 오류 : ", xhr.statusText);
		}
	});

}

// 5-3) 참가취소를 누를때마다 user 테이블의 스케줄 칼럼에서 날짜 제거 (Ajax 2개)(DB) - 위와 비슷
function removeUserSchedule(mboardnum) {
	const loginUser = $(".loginUser").val();
	const setdate = $(`.list_info[id="${mboardnum}"] .m_date`).text().replace('모임 날짜 : ', '').trim();

	// ★ 로그인 유저에 맞는 스케줄 가져오는 Ajax
	$.ajax({
		method: "GET",
		url: `/user/get_schedule?userid=${loginUser}`,
		success: function(currentSchedule) {
			// 현재 스케줄에서 삭제할 날짜 제외한 새로운 스케줄 생성하고 data로 보내기
			// 스케줄에 현재 날짜가 있다면 제외하고 \\ 붙여서 묶기
			const updatedSchedule = currentSchedule.split("\\").filter(schedule => schedule !== setdate).join("\\");
			const data = JSON.stringify({ userid: loginUser, schedule: updatedSchedule });

			// ★ 스케줄 업데이트 요청하는 Ajax
			$.ajax({
				method: "POST",
				url: '/user/update_schedule',
				contentType: 'application/json',
				data: data,
				success: function(response) {
				},
				error: function(xhr) {
					console.error("스케줄 삭제 오류 : ", xhr.statusText);
				}
			});
		},
		error: function(xhr) {
			console.error("스케줄 가져오기 오류 : ", xhr.statusText);
		}
	});
}

// 4-4, 5-4) 참여하기/참가취소를 누를때마다 이달의 모임 멤버수도 변경해주기 (Ajax 1개)
function updateMemberCount(mboardnum) {
	// ★ 멤버 수 받아오는 Ajax
	$.ajax({
		method: "GET",
		url: `/mboard/get_members?boardnum=${mboardnum}`,
		success: function(response) {
			// 서버에서 받아온 멤버 목록을 배열에 넣어서 개수로 세어주기
			const membersCounts = JSON.parse(response);
			const memberCount = membersCounts.length;

			// 여기부터는 숨겨놓은 모든 이달의모임 보드번호를 받아와서 현재 참여/참여취소한 모임의 보드넘버와 비교한 후
			// 해당하는 이달의모임 멤버수 변수를 변경하는 과정

			// .mo_list_boardnum 클래스가 있는 모든 p 요소를 찾아 반복문 돌림(모든 이달의 모임 보드넘버)
			$(".mo_list_boardnum").each(function() {
				const $mo_list_boardnum = $(this);

				// p 요소의 텍스트(보드넘버)가 현재 바뀌어야 할 mboardnum과 일치하는 경우
				// 가져온 보드넘버(문자타입)와 mboardnum(숫자타입) 비교
				if ($mo_list_boardnum.text().trim() === mboardnum.toString()) {
					// 멤버 수를 표시하는 p 요소를 찾음 (현재 보드넘버 p의 다음 형제 요소)
					const $memberCountDisplay = $mo_list_boardnum.next();
					if ($memberCountDisplay.length) {
						// 존재한다면 멤버 수 업데이트
						$memberCountDisplay.text(memberCount);
					}
				}
			});
		},
		error: function(xhr) {
			console.error("멤버 수 업데이트 오류 : ", xhr.statusText);
		}
	});
}

// 4-5, 5-5) 내 일정 위젯 실시간 변경
function updateMySchedule() {
	const loginUser = $(".loginUser").val();
	// 서버에서 사용자 스케줄 가져오기
	$.ajax({
		method: "GET",
		url: `/mboard/get_my_schedule?userid=${loginUser}`,
		success: function(response) {
			// 받아온 boardDTO 타입의 리스트들
			const mySchedules = response;
			// 추가할 부분
			const $scheduleList = $(".schedulebox .schedule_list");

			// 일정 시간 후에 화면 업데이트
			setTimeout(() => {
				// 기존 내용을 비우고
				$scheduleList.empty();
				if (mySchedules.length > 0) {
					// 모든 스케줄 순회하면서
					mySchedules.forEach(m_board => {
						const $scheduleItem = $(`
	                            <div class="schedule_item">
	                                <div class="s_date">${m_board.setdate}</div>
	                                <div class="d-day_box">D-<span class="d-day">${m_board.dday}</span></div>							
	                                <div class="s_title">${m_board.boardtitle}</div>
	                                <div class="del_btn" onclick="delUser_scheduleBox('${m_board.boardnum}')">삭제하기</div>
	                            </div>
	                        `);
						// 하나씩 스케줄 항목 추가
						$scheduleList.append($scheduleItem);
					});
				} else {
					$scheduleList.html('<div class="no_join_div">참여한 스케줄이 없습니다.</div>');
				}
			}, 250); // 0.2초
		},
		error: function(xhr) {
			console.error("스케줄 가져오기 오류 : ", xhr.statusText);
		}
	});
}

// +) 일정 위젯에서 삭제하기 눌러서 바로 삭제하기 (delUser와 비슷하지만 boardnum 정해져있음)
function delUser_scheduleBox(mboardnum) {
	const loginUser = $(".loginUser").val();
	const $listInfo = $(`.list_info[id="${mboardnum}"]`);
	const mboardtitle = $listInfo.find(".list_title").text().trim();
	const writer = $listInfo.find(".userid").text().trim();
	const flag = 'dmeeting';
	let path = '/mboard/m_get?mboardnum=';
	path += mboardnum;

	// *유효성 - 비로그인 걸러내기
	if (!loginUser) {
		alert("로그인 후 이용해주세요!");
		location.replace("/");
		return;
	}
	
	if(writer==loginUser){
		alert("본인 모임에는 참가를 취소하실 수 없습니다!");
		return;
	}

	getmembers(mboardnum, function(arUser) {
		let members = arUser.filter(user => user.trim() !== "");

		// *유효성 - 로그인유저가 members 배열에 포함되었는지?
		if (!members.includes(loginUser)) {
			alert("이 모임에 참여하지 않은 상태입니다!");
			return;
		}

		members = members.filter(user => user !== loginUser);
		const $membersBox = $listInfo.find('.list_info_4');
		$membersBox.empty();
		members.forEach(user => {
			const $newMember = $('<div class="m_list_members"></div>').text(user);
			$membersBox.append($newMember);
		});

		const member = members.join("\\");
		$.ajax({
			method: "POST",
			url: '/mboard/put_member',
			contentType: 'application/json',
			data: JSON.stringify({
				member: member, boardnum: mboardnum
				, userid: writer, contentpath: path, boardtitle: mboardtitle, flag: flag
			}),
			success: function(response) {
				removeUserSchedule(mboardnum);
				updateMemberCount(mboardnum);
				updateMySchedule();
				member_view(mboardnum);
			},
			error: function(xhr) {
				console.error('오류:', xhr.statusText);
			}
		});
	});
}

$(document).ready(function() {
	// 페이지가 로드될 때 스케줄 업데이트
	updateMySchedule();
});

/* ------------------------------------------------------------------------------------- */
/* 댓글 구현 */

// 댓글 추가
function add_reply(button) {
	// 현재 list의 boardnum 가져오기
	const boardnum = $(button).closest(".list_info").attr("id");
	const replycontent = $(button).closest(".reply_write_box").find("textarea").val();
	const userid = $(".loginUser").val();
	const ctuserid = $(".userid").html();
	const boardtitle = $(".list_title").html();
	let path = window.location.pathname;
	path += '?mboardnum=';
	path += boardnum;

	// * 유효성 - 비로그인 걸러내기
	if (!userid) {
		alert("로그인 후 이용해주세요!");
		location.replace('/');
		return;
	}

	// ★ 댓글 DB로 보내는 Ajax
	$.ajax({
		method: "POST",
		url: "/mboard/put_reply",
		contentType: "application/json",
		data: JSON.stringify({
			boardnum: boardnum,
			replycontent: replycontent,
			replyuserid: userid,
			ctuserid: ctuserid,
			contentpath: path,
			boardtitle: boardtitle
		}),
		success: function(response) {
			alert("댓글 작성 완료!");
			// 페이지 새로고침
			location.reload();
		},
		error: function(xhr, status, error) {
			console.error("오류 발생:", xhr.statusText, error);
		}
	});
}

let replynum;

// 댓글 수정
function modify_reply(button) {
	// 댓글 번호 가져오기
	const reply_list = $(button).closest(".reply_list");
	replynum = reply_list.find(".replynum").text();

	// 댓글 수정환경 구축
	const reply_content = reply_list.find(".reply_con").text();
	const reply_con = reply_list.find(".reply_con");
	reply_con.empty();
	reply_con.append(`<textarea id="modify_replycontent"
					>${reply_content}</textarea>`);

	const reply_btn = reply_list.find(".reply_btn");
	reply_btn.empty();
	reply_btn.append(`<div class="go_modify_reply" onclick="go_modify_reply(${replynum})">수정완료</div>`);
	reply_btn.append(`<div class="stop_modify_reply" onclick="stop_modify_reply()">수정취소</div>`);

}

// 댓글 수정 취소
function stop_modify_reply() {
	location.reload();
}

// 댓글 수정 완료	
function go_modify_reply(replynum) {
	const replycontent = $("#modify_replycontent").val();
	console.log(replycontent);

	// ★ 수정된 댓글 DB로 보내는 Ajax
	$.ajax({
		method: "POST",
		url: "/mboard/modify_reply",
		contentType: "application/json",
		data: JSON.stringify({
			replynum: replynum,
			replycontent: replycontent,
		}),
		success: function(response) {
			alert("댓글 수정 완료!");
			// 페이지 새로고침
			location.reload();
		},
		error: function(xhr, status, error) {
			console.error("오류 발생:", xhr.statusText, error);
		}
	});
}

// 댓글 삭제
function delete_reply(button) {
	// 댓글 정보를 가져오기
	const reply_list = $(button).closest(".reply_list");
	const replynum = reply_list.find(".replynum").text();

	if (confirm("정말로 댓글을 삭제하시겠습니까?")) {
		// ★ 댓글 DB에서 삭제 요청보내는 Ajax
		$.ajax({
			method: "POST",
			url: "/mboard/delete_reply",
			contentType: "application/json",
			data: JSON.stringify({
				replynum: replynum
			}),
			success: function(response) {
				alert("댓글 삭제 완료!")
				location.reload();
			},
			error: function(xhr, status, error) {
				console.error("오류 발생:", xhr.statusText, error);
			}
		});
	}
}

/* 검색하기 구현 */
function handle_search_btn() {
	const form = document.search_form;
	const type = $("#type").val();
	const keyword = $("#keyword").val();
	// 비어 있는지 체크
	if (!type || !keyword) {
		alert("검색 조건과 키워드를 입력하세요.");
		return;
	}

	form.submit();
}