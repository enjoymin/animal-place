<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

const menu = document.getElementById("menubar");
const menuButton = document.querySelector(".menu");
menuButton.addEventListener("click", function(e) {
	e.preventDefault(); // 기본 동작 방지

	// 현재 right 속성을 체크하고 토글
	if (menu.style.right === "-400px") {
		menu.style.right = "0px"; // 메뉴 열기
	} else {
		menu.style.right = "-400px"; // 메뉴 닫기
	}
});