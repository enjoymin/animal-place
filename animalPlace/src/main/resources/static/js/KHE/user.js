
function findAddr() {
	new daum.Postcode({
		oncomplete: function(data) {
			// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

			// 각 주소의 노출 규칙에 따라 주소를 조합한다.
			// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
			var addr = ''; // 주소 변수
			var extraAddr = ''; // 참고항목 변수

			//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
			if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
				addr = data.roadAddress;
			} else { // 사용자가 지번 주소를 선택했을 경우(J)
				addr = data.jibunAddress;
			}

			// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
			if (data.userSelectedType === 'R') {
				// 법정동명이 있을 경우 추가한다. (법정리는 제외)
				// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
				if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
					extraAddr += data.bname;
				}
				// 건물명이 있고, 공동주택일 경우 추가한다.
				if (data.buildingName !== '' && data.apartment === 'Y') {
					extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
				}
				// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
				if (extraAddr !== '') {
					extraAddr = ' (' + extraAddr + ')';
				}
				// 조합된 참고항목을 해당 필드에 넣는다.
				document.getElementById("addretc").value = extraAddr;

			} else {
				document.getElementById("addretc").value = '';
			}

			// 우편번호와 주소 정보를 해당 필드에 넣는다.
			document.getElementById('zipcode').value = data.zonecode;
			document.getElementById("addr").value = addr;
			// 커서를 상세주소 필드로 이동한다.
			document.getElementById("addrdetail").focus();
		}
	}).open();
}


$("#zipcode").click(function() {
	findAddr();
})
$("#userid").blur(function() {
	if (loginUser != "" && loginUser != null) {
		return;
	}
	checkId();
})
$("#userpw").blur(function() {
	checkPw();
})
$("#userpw_re").blur(function() {
	checkPw_re();
})
let idwrn = $("#idwarning");
let pwwrn = $("#pwwarning");
let idflag = 0;
let pwflag = 0;

if (loginUser != "" && loginUser != null) {
	idflag = 1;
}
$("#useremail").keydown(function(e) {
	if (e.keyCode === 9) {
		findAddr();
	}
});
function checkId() {
	const userid = $("#userid").val();
	const reg = /^(?!.*[가-힣])(?=.*?[a-zA-Z]).{5,}$/
	if (userid.length < 5 || userid.length > 12) {
		$("#idwarning2").html("");
		idwrn.html("아이디는 5~12자로 만들어 주세요")
		idflag = 0;
		return;
	}
	if (!reg.test(userid)) {
		console.log(userid);
		$("#idwarning2").html("");
		idwrn.html("아이디 조건에 부합하지 않습니다!")
		idflag = 0;
		return;
	}
	$.getJSON(
		`/user/checkId?userid=` + userid,
		function(data) {
			if (data) {
				idwrn.html("");
				$("#idwarning2").html("사용가능한 아이디 입니다.")
				idflag = 1;
			}
			else {
				$("#idwarning2").html("");
				idwrn.html("중복된 아이디입니다 다른 아이디를 입력해 주세요");
				idflag = 0;
			}
		}
	)
}
function checkPw() {
	const userpw = $("#userpw").val();
	const reg = /^(?!.*[가-힣])(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[~!?-@#$%^&*]).{4,}$/
	if (userpw == "") {
		$("#pwwarning2").html("");
		pwwrn.html("비밀번호를 입력해 주세요!");
		return 0;
	}
	if (userpw.length < 8) {
		$("#pwwarning2").html("");
		pwwrn.html("비밀번호의 길이는 8자 이상으로 해주세요!");
		pwflag = 0;
		return 0;
	}
	else {
		pwwrn.html("");
		if (!reg.test(userpw)) {
			$("#pwwarning2").html("");
			pwwrn.html("비밀번호는 영어 대문자, 소문자, 숫자, 특수문자를 조합해서 만들어주세요");
			pwflag = 0;
			return 0;
		}
		else {
			pwwrn.html("");
			return 1;
		}
	}
}
function checkPw_re() {
	if (!checkPw()) {
		return;
	}
	const userpw = $("#userpw").val();
	const userpw_re = $("#userpw_re").val();
	if (userpw != userpw_re) {
		$("#pwwarning2").html("");
		pwwrn.html("비밀번호가 일치하지 않습니다!");
		pwflag = 0;
		return;
	}
	else {
		pwwrn.html("");
		$("#pwwarning2").html("비밀번호 확인 완료!");
		pwflag = 1;
	}
}
const arHobby = [];
function addHobby(){
	const joinForm = document.joinForm;
	const hobby_list = document.getElementsByClassName("hobby_list")[0];
	const hobby = joinForm.hobby;

	if(hobby.value == ""){
		alert("취미를 입력해 주세요!");
		hobby.focus();
		return;
	}
	if(arHobby.indexOf(hobby.value) != -1){
		alert("중복된 취미입니다!");
		hobby.focus();
		hobby.value = "";
		return;
	}
	if(arHobby.length == 5){
		alert("취미는 5개 이하로 입력해주세요!")
		return;
	}
	//span 태그 노드 생성
	const inputHobby = document.createElement("span");
	//span 태그 노드 클래스 속성 값으로 userhobby
	inputHobby.classList = "userhobby";
	//span 태그 노드 name 속성 값으로 userhobby
	inputHobby.name = "userhobby";
	//span 태그 노드 내부 내용으로 입력한 취미 문자열 설정
	inputHobby.innerHTML = hobby.value;
	//취미 목록 배열에 입력한 취미 문자열 추가
	arHobby.push(hobby.value);
	
	//a태그 노드 생성
	const xBox = document.createElement("a");
	//a 태그 노드 클래스 속성 값으로 xBox
	xBox.classList = "xBox";
	//만들어진 a태그를 위에 만든 span 태그의 자식으로 추가
	inputHobby.appendChild(xBox);
	
	inputHobby.addEventListener("click",deleteHobby)
	
	hobby_list.appendChild(inputHobby);
	
	hobby.value = "";
	hobby.focus();
}
function hobbyKeyup(){
	if(window.event.keyCode == 13){
		addHobby();
	}
}
function deleteHobby(e){
	//e.target : 클릭된 대상(1. span태그 클릭 / 2. a태그 클릭)
	let deleteNode = null;
	if(e.target.classList == "xBox"){
		deleteNode = e.target.parentNode;
	}
	else{
		deleteNode = e.target;
	}
	
	let txt = deleteNode.innerText;
	for(let i in arHobby){
		if(arHobby[i] == txt){
			arHobby.splice(i,1);
			break;
		}
	}
	deleteNode.remove();
}
function sendit() {
	checkPw_re();
	if (idflag != 1) {
		alert("아이디를 확인해주세요!");
		$("#userid").focus();
		return;
	}
	if (pwflag != 1) {
		alert("비밀번호를 확인해 주세요!")
		$("#userpw").focus();
		return;
	}
	if ($("#zipcode").val() == "") {
		alert("주소를 입력해 주세요!");
		return;
	}
	const joinForm = document.joinForm;
	joinForm.submit();
}
