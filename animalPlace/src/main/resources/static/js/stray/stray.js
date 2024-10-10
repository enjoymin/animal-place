const animal = [];
const All =[];
const All_Dog = [];
const All_Cat = [];
const All_Etc = [];
const Seoul = [];
const Seoul_Dog = [];
const Seoul_Cat = [];
const Seoul_Etc = [];
const Busan = [];
const Busan_Dog = [];
const Busan_Cat = [];
const Busan_Etc = [];
const Daegu = [];
const Daegu_Dog = [];
const Daegu_Cat = [];
const Daegu_Etc = [];
const Incheon = [];
const Incheon_Dog = [];
const Incheon_Cat = [];
const Incheon_Etc = [];
const Gwangju = [];
const Gwangju_Dog = [];
const Gwangju_Cat = [];
const Gwangju_Etc = [];
const Daejeon = [];
const Daejeon_Dog = [];
const Daejeon_Cat = [];
const Daejeon_Etc = [];
const Ulsan = [];
const Ulsan_Dog = [];
const Ulsan_Cat = [];
const Ulsan_Etc = [];
const Gyeonggi = [];
const Gyeonggi_Dog = [];
const Gyeonggi_Cat = [];
const Gyeonggi_Etc = [];
const Gangwon = [];
const Gangwon_Dog = [];
const Gangwon_Cat = [];
const Gangwon_Etc = [];
const Chungbuk = [];
const Chungbuk_Dog = [];
const Chungbuk_Cat = [];
const Chungbuk_Etc = [];
const Chungnam = [];
const Chungnam_Dog = [];
const Chungnam_Cat = [];
const Chungnam_Etc = [];
const Jeonbuk = [];
const Jeonbuk_Dog = [];
const Jeonbuk_Cat = [];
const Jeonbuk_Etc = [];
const Jeonnam = [];
const Jeonnam_Dog = [];
const Jeonnam_Cat = [];
const Jeonnam_Etc = [];
const Gyeongbuk = [];
const Gyeongbuk_Dog = [];
const Gyeongbuk_Cat = [];
const Gyeongbuk_Etc = [];
const Gyeongnam = [];
const Gyeongnam_Dog = [];
const Gyeongnam_Cat = [];
const Gyeongnam_Etc = [];
const Jeju = [];
const Jeju_Dog = [];
const Jeju_Cat = [];
const Jeju_Etc = [];
const Sejong = [];
const Sejong_Dog = [];
const Sejong_Cat = [];
const Sejong_Etc = [];
let region = 'All';
let breed = '';
let pageNo = 1;
$(window).on('load', function() {
	$.ajax({
		url:'/stray/get?pageNo='+pageNo,
		method:'GET',
		success:function(data){
			const items = data.response.body.items.item;
			let str = "";
			
			for(const item of items){
				const stray = {
					age	: item.age,
					careAddr : item.careAddr,
					careNm : item.careNm,
					careTel : item.careTel,
					chargeNm : item.chargeNm,
					colorCd : item.colorCd,
					desertionNo : item.desertionNo,
					filename : item.filename,
					happenDt : item.happenDt,
					happenPlace : item.happenPlace,
					kindCd : item.kindCd,
					neuterYn : item.neuterYn,
					noticeEdt : item.noticeEdt,
					noticeNo : item.noticeNo,
					noticeSdt : item.noticeSdt,
					officetel : item.officetel,
					orgNm : item.orgNm,
					popfile : item.popfile,
					processState : item.processState,
					sexCd : item.sexCd,
					specialMark : item.specialMark,
					weight : item.weight
				};
				const year = item.happenDt.slice(0, 4);
				const month = item.happenDt.slice(4, 6);
				const day = item.happenDt.slice(6, 8);

				stray.happenDt = `${year}년 ${month}월 ${day}일`;

				if(stray.sexCd == 'M'){
					stray.sexCd = '수컷'
				}
				if(stray.sexCd == 'F'){
					stray.sexCd = '암컷'
				}
				if(stray.sexCd == 'Q'){
					stray.sexCd = '알수없음'
				}
				if(stray.neuterYn == 'Y'){
					stray.neuterYn = 'O'
				}
				if(stray.neuterYn == 'N'){
					stray.neuterYn = 'X'
				}
				if(stray.neuterYn == 'U'){
					stray.neuterYn = '알수없음'
				}
				if(item.processState == "보호중"){
					animal.push(stray);
				}
			}
			pageNo++;
			$.ajax({
				url:'/stray/get?pageNo='+pageNo,
				method:'GET',
				success:function(data){
					const items = data.response.body.items.item;
					let str = "";
					
					for(const item of items){
						const stray = {
							age	: item.age,
							careAddr : item.careAddr,
							careNm : item.careNm,
							careTel : item.careTel,
							chargeNm : item.chargeNm,
							colorCd : item.colorCd,
							desertionNo : item.desertionNo,
							filename : item.filename,
							happenDt : item.happenDt,
							happenPlace : item.happenPlace,
							kindCd : item.kindCd,
							neuterYn : item.neuterYn,
							noticeEdt : item.noticeEdt,
							noticeNo : item.noticeNo,
							noticeSdt : item.noticeSdt,
							officetel : item.officetel,
							orgNm : item.orgNm,
							popfile : item.popfile,
							processState : item.processState,
							sexCd : item.sexCd,
							specialMark : item.specialMark,
							weight : item.weight
						};
						const year = item.happenDt.slice(0, 4);
						const month = item.happenDt.slice(4, 6);
						const day = item.happenDt.slice(6, 8);
						
						stray.happenDt = `${year}년 ${month}월 ${day}일`;
						
						if(stray.sexCd == 'M'){
							stray.sexCd = '수컷'
						}
						if(stray.sexCd == 'F'){
							stray.sexCd = '암컷'
						}
						if(stray.sexCd == 'Q'){
							stray.sexCd = '알수없음'
						}
						if(stray.neuterYn == 'Y'){
							stray.neuterYn = 'O'
						}
						if(stray.neuterYn == 'N'){
							stray.neuterYn = 'X'
						}
						if(stray.neuterYn == 'U'){
							stray.neuterYn = '알수없음'
						}
						if(item.processState == "보호중"){
							animal.push(stray);
						}
					}
					pageNo++;
					$.ajax({
						url:'/stray/get?pageNo='+pageNo,
						method:'GET',
						success:function(data){
							const items = data.response.body.items.item;
							let str = "";
							
							for(const item of items){
								const stray = {
									age	: item.age,
									careAddr : item.careAddr,
									careNm : item.careNm,
									careTel : item.careTel,
									chargeNm : item.chargeNm,
									colorCd : item.colorCd,
									desertionNo : item.desertionNo,
									filename : item.filename,
									happenDt : item.happenDt,
									happenPlace : item.happenPlace,
									kindCd : item.kindCd,
									neuterYn : item.neuterYn,
									noticeEdt : item.noticeEdt,
									noticeNo : item.noticeNo,
									noticeSdt : item.noticeSdt,
									officetel : item.officetel,
									orgNm : item.orgNm,
									popfile : item.popfile,
									processState : item.processState,
									sexCd : item.sexCd,
									specialMark : item.specialMark,
									weight : item.weight
								};
								const year = item.happenDt.slice(0, 4);
								const month = item.happenDt.slice(4, 6);
								const day = item.happenDt.slice(6, 8);
								
								stray.happenDt = `${year}년 ${month}월 ${day}일`;
								
								if(stray.sexCd == 'M'){
									stray.sexCd = '수컷'
								}
								if(stray.sexCd == 'F'){
									stray.sexCd = '암컷'
								}
								if(stray.sexCd == 'Q'){
									stray.sexCd = '알수없음'
								}
								if(stray.neuterYn == 'Y'){
									stray.neuterYn = 'O'
								}
								if(stray.neuterYn == 'N'){
									stray.neuterYn = 'X'
								}
								if(stray.neuterYn == 'U'){
									stray.neuterYn = '알수없음'
								}
								if(item.processState == "보호중"){
									animal.push(stray);
								}
								
							}
							if(pageNo == 3){
								window.scrollTo(0, 0);
								console.log("성공");
								console.log(animal);
								//페이징 구축
								paginatio(animal, region, breed);
								//지역별로 나누어서 저장 작업	
								regionDivide();				
							}
						},
						error:function(data){
							console.log(data);
							console.log("실패");
							window.alert("데이터를 가져오는데 실패하였습니다.\n새로고침을 해주시기 바랍니다.");
						}
					})				
				},
				error:function(data){
					console.log(data);
					console.log("실패");
					window.alert("데이터를 가져오는데 실패하였습니다.\n새로고침을 해주시기 바랍니다.");
				}
			})
		},
		error:function(data){
			console.log(data);
			console.log("실패");
			window.alert("데이터를 가져오는데 실패하였습니다.\n새로고침을 해주시기 바랍니다.");
		}
	})
});

		
function paginatio(status, region, breed){
	const totalPosts = status.length;
    const postsPerPage = 2;
    const totalPages = Math.ceil(totalPosts / postsPerPage);
    const pageLimit = 10; // 한 번에 보여줄 페이지 버튼 수
    let currentPage = 1;
			
	function renderPosts() {
	    const result = document.getElementById("result");
	    const start = (currentPage - 1) * postsPerPage;
	    const end = Math.min(start + postsPerPage, totalPosts);
	    
	    // 초기 HTML 문자열
	    let html = '';

	    // 첫 번째 포스트 렌더링
	    if (start < totalPosts) {
	        html += `<tr class="info">
	<td>
		<div class="overlay">
			<span>이미지 로딩중</span>
		</div>
        <img src="${status[start].popfile}" class="strayImg">
    </td>
    <td>
        <span>유기동물 정보</span>
        <div class="info_box">						
            <div class="strayInfo1">
                <div>품종</div>
                <div>${status[start].kindCd}</div>
                <div>나이</div>
                <div>${status[start].age}</div>
                <div>색상</div>
                <div>${status[start].colorCd}</div>
                <div>체중</div>
                <div>${status[start].weight}</div>
                <div>성별</div>
                <div>${status[start].sexCd}</div>
                <div>중성화<br>여부</div>
                <div>${status[start].neuterYn}</div>
                <div>상태</div>
                <div>${status[start].processState}</div>
                <div>특징</div>
                <div>${status[start].specialMark}</div>
            </div>
        </div>
		<div class="modal">
			<div class="modal_popup">
				<div class="info_box">						
				    <div class="strayInfo1">
						<div>발견일</div>
						<div>${status[start].happenDt}</div>
						<div>발견<br>장소</div>
						<div>${status[start].happenPlace}</div>
						<div>보호소<br>이름</div>
						<div>${status[start].careNm}</div>
						<div>보호소<br>전화번호</div>
						<div>${status[start].careTel}</div>
						<div>보호<br>장소</div>
						<div>${status[start].careAddr}</div>
						<div>관할<br>기관</div>
						<div>${status[start].orgNm}</div>
						<div>담당자</div>
						<div>${status[start].chargeNm}</div>
						<div>담당자<br>연락처</div>
						<div>${status[start].officetel}</div>
				    </div>
					<button type="button" class="close_btn" onclick="closeModal1()">닫기</button>
				</div>
			</div>
		</div>
		<button type="button" class="modal_btn" onclick="openModal1()">추가정보 보기</button>
    </td>
</tr>`;
	    }

	    // 두 번째 포스트 렌더링: 현재 페이지가 마지막 페이지가 아닐 때만
	    if (end - 1 < totalPosts && start + 1 < totalPosts) {
	        html += `<tr class="info">
    <td>
		<div class="overlay">
			<span>이미지 로딩중</span>
		</div>
        <img src="${status[end - 1].popfile}" class="strayImg">
    </td>
    <td>
        <span>유기동물 정보</span>
        <div class="info_box">						
            <div class="strayInfo1">
                <div>품종</div>
                <div>${status[end - 1].kindCd}</div>
                <div>나이</div>
                <div>${status[end - 1].age}</div>
                <div>색상</div>
                <div>${status[end - 1].colorCd}</div>
                <div>체중</div>
                <div>${status[end - 1].weight}</div>
                <div>성별</div>
                <div>${status[end - 1].sexCd}</div>
                <div>중성화<br>여부</div>
                <div>${status[end - 1].neuterYn}</div>
                <div>상태</div>
                <div>${status[end - 1].processState}</div>
                <div>특징</div>
                <div>${status[end - 1].specialMark}</div>
            </div>
        </div>
		<div class="modal">
			<div class="modal_popup">
				<div class="info_box">						
				    <div class="strayInfo1">
						<div>발견일</div>
						<div>${status[end - 1].happenDt}</div>
						<div>발견<br>장소</div>
						<div>${status[end - 1].happenPlace}</div>
						<div>보호소<br>이름</div>
						<div>${status[end - 1].careNm}</div>
						<div>보호소<br>전화번호</div>
						<div>${status[end - 1].careTel}</div>
						<div>보호<br>장소</div>
						<div>${status[end - 1].careAddr}</div>
						<div>관할<br>기관</div>
						<div>${status[end - 1].orgNm}</div>
						<div>담당자</div>
						<div>${status[end - 1].chargeNm}</div>
						<div>담당자<br>연락처</div>
						<div>${status[end - 1].officetel}</div>
				    </div>
					<button type="button" class="close_btn" onclick="closeModal2()">닫기</button>
				</div>
			</div>
		</div>
		<button type="button" class="modal_btn" onclick="openModal2()">추가정보 보기</button>
    </td>
</tr>`;
	    }
		
		loadingRemove();
	    // 결과를 HTML에 추가
	    result.innerHTML = html;
	}

	function renderPagination(region, breed) {
	    const paginationUl = document.getElementById('pagination');
	    paginationUl.innerHTML = '';

	    const startPage = Math.floor((currentPage - 1) / pageLimit) * pageLimit + 1;
	    const endPage = Math.min(startPage + pageLimit - 1, totalPages);

	    // 처음으로 버튼: 현재 페이지 그룹이 11 이상일 때만 표시
		if(currentPage > 10){
		    const firstLi = document.createElement('li');
			firstLi.classList.add('first');
		    firstLi.innerText = '◀◀';
		    firstLi.onclick = () => changePage(1, region, breed);
	    	paginationUl.appendChild(firstLi);
			
	        const prevLi = document.createElement('li');
			prevLi.classList.add('prev');
	        prevLi.innerText = '◁';
	        prevLi.onclick = () => changePage(startPage - 1, region, breed);
	        paginationUl.appendChild(prevLi);
		}

	    // 페이지 버튼 생성
	    for (let i = startPage; i <= endPage; i++) {
	        const pageLi = document.createElement('li');
	        pageLi.innerText = i;
	        pageLi.className = (i === currentPage) ? 'active' : '';
	        pageLi.onclick = () => changePage(i, region, breed);
	        paginationUl.appendChild(pageLi);
	    }

	    // 다음 버튼: 다음 페이지 그룹의 첫 페이지로 이동
	    if (endPage < totalPages) {
	        const nextLi = document.createElement('li');
			nextLi.classList.add('next');
	        nextLi.innerText = '▷';
	        nextLi.onclick = () => changePage(endPage + 1, region, breed);
	        paginationUl.appendChild(nextLi);

		    // 마지막으로 버튼
			const lastLi = document.createElement('li');
			lastLi.classList.add('last');
		    lastLi.innerText = '▶▶';
		    lastLi.onclick = () => changePage(totalPages, region, breed);
		    paginationUl.appendChild(lastLi);
	    }
	}

	function changePage(page, region, breed) {
	    if (page < 1) page = 1;
	    if (page > totalPages) page = totalPages;
	    currentPage = page;
		
		document.getElementById('region').value = region;
		document.getElementById('breed').value = breed;
		
	    renderPosts();
	    renderPagination(region, breed);
		window.scrollTo(0, 0);

	}
	
	
	
	// 초기 렌더링
	renderPosts();
	renderPagination(region, breed);
	nottingContainer();
	selectContainer();

}
	
function loadingRemove(){
	let loading = document.getElementById("loading");
	loading.style.display = 'none';
}

function nottingContainer(){
	const result = document.getElementById('result');
	const notting = document.getElementById('notting');

	if (result.innerHTML.trim() === '') {
	    notting.style.display = 'flex';
	} 
	else {
	    notting.style.display = 'none';
	}
}

function selectContainer(){
	const select = document.getElementById('select');
	
	select.style.display = 'flex';
}

$(".changeSelect").click(function(e){
		//e(이벤트)의 기본 작동 막기
		//e.preventDefault();
		
		const type = document.getElementsByClassName("type");
		const value = type[0].value + type[1].value;
		region = type[0].value;
		breed = type[1].value;
		
		if(value == 'All'){
			paginatio(All, region, breed);
		}
		if(value == 'All_Dog'){
			paginatio(All_Dog, region, breed);
		}
		if(value == 'All_Cat'){
			paginatio(All_Cat, region, breed);
		}
		if(value == 'All_Etc'){
			paginatio(All_Etc, region, breed);
		}
		if(value == 'Seoul'){
			paginatio(Seoul, region, breed);
		}
		if(value == 'Seoul_Dog'){
			paginatio(Seoul_Dog, region, breed);
		}
		if(value == 'Seoul_Cat'){
			paginatio(Seoul_Cat, region, breed);
		}
		if(value == 'Seoul_Etc'){
			paginatio(Seoul_Etc, region, breed);
		}
		if(value == 'Busan'){
			paginatio(Busan, region, breed);
		}
		if(value == 'Busan_Dog'){
			paginatio(Busan_Dog, region, breed);
		}
		if(value == 'Busan_Cat'){
			paginatio(Busan_Cat, region, breed);
		}
		if(value == 'Busan_Etc'){
			paginatio(Busan_Etc, region, breed);
		}
		if(value == 'Daegu'){
			paginatio(Daegu, region, breed);
		}
		if(value == 'Daegu_Dog'){
			paginatio(Daegu_Dog, region, breed);
		}
		if(value == 'Daegu_Cat'){
			paginatio(Daegu_Cat, region, breed);
		}
		if(value == 'Daegu_Etc'){
			paginatio(Daegu_Etc, region, breed);
		}
		if(value == 'Incheon'){
			paginatio(Incheon, region, breed);
		}
		if(value == 'Incheon_Dog'){
			paginatio(Incheon_Dog, region, breed);
		}
		if(value == 'Incheon_Cat'){
			paginatio(Incheon_Cat, region, breed);
		}
		if(value == 'Incheon_Etc'){
			paginatio(Incheon_Etc, region, breed);
		}
		if(value == 'Gwangju'){
			paginatio(Gwangju, region, breed);
		}
		if(value == 'Gwangju_Dog'){
			paginatio(Gwangju_Dog, region, breed);
		}
		if(value == 'Gwangju_Cat'){
			paginatio(Gwangju_Cat, region, breed);
		}
		if(value == 'Gwangju_Etc'){
			paginatio(Gwangju_Etc, region, breed);
		}
		if(value == 'Daejeon'){
			paginatio(Daejeon, region, breed);
		}
		if(value == 'Daejeon_Dog'){
			paginatio(Daejeon_Dog, region, breed);
		}
		if(value == 'Daejeon_Cat'){
			paginatio(Daejeon_Cat, region, breed);
		}
		if(value == 'Daejeon_Etc'){
			paginatio(Daejeon_Etc, region, breed);
		}
		if(value == 'Ulsan'){
			paginatio(Ulsan, region, breed);
		}
		if(value == 'Ulsan_Dog'){
			paginatio(Ulsan_Dog, region, breed);
		}
		if(value == 'Ulsan_Cat'){
			paginatio(Ulsan_Cat, region, breed);
		}
		if(value == 'Ulsan_Etc'){
			paginatio(Ulsan_Etc, region, breed);
		}
		if(value == 'Gyeonggi'){
			paginatio(Gyeonggi, region, breed);
		}
		if(value == 'Gyeonggi_Dog'){
			paginatio(Gyeonggi_Dog, region, breed);
		}
		if(value == 'Gyeonggi_Cat'){
			paginatio(Gyeonggi_Cat, region, breed);
		}
		if(value == 'Gyeonggi_Etc'){
			paginatio(Gyeonggi_Etc, region, breed);
		}
		if(value == 'Gangwon'){
			paginatio(Gangwon, region, breed);
		}
		if(value == 'Gangwon_Dog'){
			paginatio(Gangwon_Dog, region, breed);
		}
		if(value == 'Gangwon_Cat'){
			paginatio(Gangwon_Cat, region, breed);
		}
		if(value == 'Gangwon_Etc'){
			paginatio(Gangwon_Etc, region, breed);
		}
		if(value == 'Chungbuk'){
			paginatio(Chungbuk, region, breed);
		}
		if(value == 'Chungbuk_Dog'){
			paginatio(Chungbuk_Dog, region, breed);
		}
		if(value == 'Chungbuk_Cat'){
			paginatio(Chungbuk_Cat, region, breed);
		}
		if(value == 'Chungbuk_Etc'){
			paginatio(Chungbuk_Etc, region, breed);
		}
		if(value == 'Chungnam'){
			paginatio(Chungnam, region, breed);
		}
		if(value == 'Chungnam_Dog'){
			paginatio(Chungnam_Dog, region, breed);
		}
		if(value == 'Chungnam_Cat'){
			paginatio(Chungnam_Cat, region, breed);
		}
		if(value == 'Chungnam_Etc'){
			paginatio(Chungnam_Etc, region, breed);
		}
		if(value == 'Jeonbuk'){
			paginatio(Jeonbuk, region, breed);
		}
		if(value == 'Jeonbuk_Dog'){
			paginatio(Jeonbuk_Dog, region, breed);
		}
		if(value == 'Jeonbuk_Cat'){
			paginatio(Jeonbuk_Cat, region, breed);
		}
		if(value == 'Jeonbuk_Etc'){
			paginatio(Jeonbuk_Etc, region, breed);
		}
		if(value == 'Jeonnam'){   
			paginatio(Jeonnam, region, breed);
		}                
		if(value == 'Jeonnam_Dog'){
			paginatio(Jeonnam_Dog, region, breed);
		}
		if(value == 'Jeonnam_Cat'){
			paginatio(Jeonnam_Cat, region, breed);
		}
		if(value == 'Jeonnam_Etc'){
			paginatio(Jeonnam_Etc, region, breed);
		}   
		if(value == 'Gyeongbuk'){   		
			paginatio(Gyeongbuk, region, breed);
		}
		if(value == 'Gyeongbuk_Dog'){
			paginatio(Gyeongbuk_Dog, region, breed);
		}
		if(value == 'Gyeongbuk_Cat'){
			paginatio(Gyeongbuk_Cat, region, breed);
		}
		if(value == 'Gyeongbuk_Etc'){
			paginatio(Jeonnam_Etc, region, breed);
		} 
		if(value == 'Gyeongnam'){   
			paginatio(Gyeongnam, region, breed);
		}                
		if(value == 'Gyeongnam_Dog'){
			paginatio(Gyeongnam_Dog, region, breed);
		}
		if(value == 'Gyeongnam_Cat'){
			paginatio(Gyeongnam_Cat, region, breed);
		}
		if(value == 'Gyeongnam_Etc'){
			paginatio(Gyeongnam_Etc, region, breed);
		} 
		if(value == 'Jeju'){   
			paginatio(Jeju, region, breed); 
		}                
		if(value == 'Jeju_Dog'){
			paginatio(Jeju_Dog, region, breed);
		}
		if(value == 'Jeju_Cat'){
			paginatio(Jeju_Cat, region, breed);
		}
		if(value == 'Jeju_Etc'){
			paginatio(Jeju_Etc, region, breed);
		} 
		if(value == 'Sejong'){   
			paginatio(Sejong, region, breed); 
		}                                
		if(value == 'Sejong_Dog'){
			paginatio(Sejong_Dog, region, breed);
		}
		if(value == 'Sejong_Cat'){
			paginatio(Sejong_Cat, region, breed);
		}
		if(value == 'Sejong_Etc'){
			paginatio(Sejong_Etc, region, breed);
		} 
		
	})
	
function regionDivide(){
	for(let i = 0; i < animal.length; i++){
		All.push(animal[i]);
		if(animal[i].kindCd.substring(0,3) == '[개]'){
			All_Dog.push(animal[i]);
		}
		if(animal[i].kindCd.substring(0,5) == '[고양이]'){
			All_Cat.push(animal[i]);
		}
		if(animal[i].kindCd.substring(0,6) == '[기타축종]'){
			All_Etc.push(animal[i]);
		}
		if(animal[i].careAddr.substring(0,4) == '서울특별'){
			Seoul.push(animal[i]);
			if(animal[i].kindCd.substring(0,3) == '[개]'){
				Seoul_Dog.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,5) == '[고양이]'){
				Seoul_Cat.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,6) == '[기타축종]'){
				Seoul_Etc.push(animal[i]);
			}
		}
		if(animal[i].careAddr.substring(0,4) == '부산광역'){
			Busan.push(animal[i]);
			if(animal[i].kindCd.substring(0,3) == '[개]'){
				Busan_Dog.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,5) == '[고양이]'){
				Busan_Cat.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,6) == '[기타축종]'){
				Busan_Etc.push(animal[i]);
			}
		}
		if(animal[i].careAddr.substring(0,4) == '대구광역'){
			Daegu.push(animal[i]);
			if(animal[i].kindCd.substring(0,3) == '[개]'){
				Daegu_Dog.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,5) == '[고양이]'){
				Daegu_Cat.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,6) == '[기타축종]'){
				Daegu_Etc.push(animal[i]);
			}
		}
		if(animal[i].careAddr.substring(0,4) == '인천광역'){
			Incheon.push(animal[i]);
			if(animal[i].kindCd.substring(0,3) == '[개]'){
				Incheon_Dog.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,5) == '[고양이]'){
				Incheon_Cat.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,6) == '[기타축종]'){
				Incheon_Etc.push(animal[i]);
			}
		}
		if(animal[i].careAddr.substring(0,4) == '광주광역'){
			Gwangju.push(animal[i]);
			if(animal[i].kindCd.substring(0,3) == '[개]'){
				Gwangju_Dog.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,5) == '[고양이]'){
				Gwangju_Cat.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,6) == '[기타축종]'){
				Gwangju_Etc.push(animal[i]);
			}
		}
		if(animal[i].careAddr.substring(0,4) == '대전광역'){
			Daejeon.push(animal[i]);
			if(animal[i].kindCd.substring(0,3) == '[개]'){
				Daejeon_Dog.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,5) == '[고양이]'){
				Daejeon_Cat.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,6) == '[기타축종]'){
				Daejeon_Etc.push(animal[i]);
			}
		}
		if(animal[i].careAddr.substring(0,4) == '울산광역'){
			Ulsan.push(animal[i]);
			if(animal[i].kindCd.substring(0,3) == '[개]'){
				Ulsan_Dog.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,5) == '[고양이]'){
				Ulsan_Cat.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,6) == '[기타축종]'){
				Ulsan_Etc.push(animal[i]);
			}
		}
		if(animal[i].careAddr.substring(0,4) == '경기도 '){
			Gyeonggi.push(animal[i]);
			if(animal[i].kindCd.substring(0,3) == '[개]'){
				Gyeonggi_Dog.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,5) == '[고양이]'){
				Gyeonggi_Cat.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,6) == '[기타축종]'){
				Gyeonggi_Etc.push(animal[i]);
			}
		}
		if(animal[i].careAddr.substring(0,4) == '강원도 '){
			Gangwon.push(animal[i]);
			if(animal[i].kindCd.substring(0,3) == '[개]'){
				Gangwon_Dog.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,5) == '[고양이]'){
				Gangwon_Cat.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,6) == '[기타축종]'){
				Gangwon_Etc.push(animal[i]);
			}
		}
		if(animal[i].careAddr.substring(0,4) == '충청북도'){
			Chungbuk.push(animal[i]);
			if(animal[i].kindCd.substring(0,3) == '[개]'){
				Chungbuk_Dog.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,5) == '[고양이]'){
				Chungbuk_Cat.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,6) == '[기타축종]'){
				Chungbuk_Etc.push(animal[i]);
			}
		}
		if(animal[i].careAddr.substring(0,4) == '충청남도'){
			Chungnam.push(animal[i]);
			if(animal[i].kindCd.substring(0,3) == '[개]'){
				Chungnam_Dog.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,5) == '[고양이]'){
				Chungnam_Cat.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,6) == '[기타축종]'){
				Chungnam_Etc.push(animal[i]);
			}
		}
		if(animal[i].careAddr.substring(0,4) == '전라북도'){
			Jeonbuk.push(animal[i]);
			if(animal[i].kindCd.substring(0,3) == '[개]'){
				Jeonbuk_Dog.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,5) == '[고양이]'){
				Jeonbuk_Cat.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,6) == '[기타축종]'){
				Jeonbuk_Etc.push(animal[i]);
			}
		}
		if(animal[i].careAddr.substring(0,4) == '전라남도'){
			Jeonnam.push(animal[i]);
			if(animal[i].kindCd.substring(0,3) == '[개]'){
				Jeonnam_Dog.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,5) == '[고양이]'){
				Jeonnam_Cat.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,6) == '[기타축종]'){
				Jeonnam_Etc.push(animal[i]);
			}
		}
		if(animal[i].careAddr.substring(0,4) == '경상북도'){
			Gyeongbuk.push(animal[i]);
			if(animal[i].kindCd.substring(0,3) == '[개]'){
				Gyeongbuk_Dog.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,5) == '[고양이]'){
				Gyeongbuk_Cat.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,6) == '[기타축종]'){
				Gyeongbuk_Etc.push(animal[i]);
			}
		}
		if(animal[i].careAddr.substring(0,4) == '경상남도'){
			Gyeongnam.push(animal[i]);
			if(animal[i].kindCd.substring(0,3) == '[개]'){
				Gyeongnam_Dog.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,5) == '[고양이]'){
				Gyeongnam_Cat.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,6) == '[기타축종]'){
				Gyeongnam_Etc.push(animal[i]);
			}
		}
		if(animal[i].careAddr.substring(0,4) == '제주특별'){
			Jeju.push(animal[i]);
			if(animal[i].kindCd.substring(0,3) == '[개]'){
				Jeju_Dog.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,5) == '[고양이]'){
				Jeju_Cat.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,6) == '[기타축종]'){
				Jeju_Etc.push(animal[i]);
			}
		}
		if(animal[i].careAddr.substring(0,4) == '세종특별'){
			Sejong.push(animal[i]);
			if(animal[i].kindCd.substring(0,3) == '[개]'){
				Sejong_Dog.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,5) == '[고양이]'){
				Sejong_Cat.push(animal[i]);
			}
			if(animal[i].kindCd.substring(0,6) == '[기타축종]'){
				Sejong_Etc.push(animal[i]);
			}
		}
	}
}

const modal = document.getElementsByClassName('modal');
const open = document.getElementsByClassName('modal_btn');
const close = document.getElementsByClassName('close_btn');

function openModal1(){
    //display 속성을 block로 변경
    modal[0].style.display = 'block';
	open[0].style.display = 'none';
}
function closeModal1(){
   //display 속성을 none으로 변경
    modal[0].style.display = 'none';
	open[0].style.display = 'block';
}

function openModal2(){
    //display 속성을 block로 변경
    modal[1].style.display = 'block';
	open[1].style.display = 'none';
}
function closeModal2(){
   //display 속성을 none으로 변경
    modal[1].style.display = 'none';
	open[1].style.display = 'block';
}
