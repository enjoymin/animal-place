document.addEventListener("DOMContentLoaded", function() {
	const defaultAnimalButton = document.querySelector('.animal-btn2[data-animal="강아지"]');
	if (defaultAnimalButton) {
		defaultAnimalButton.click(); // 클릭 이벤트 트리거
	}

	// Hover 효과 추가
	document.querySelectorAll('.left-item').forEach(function(item) {
		item.addEventListener('mouseenter', function() {
			item.classList.add('hover');
			item.querySelector('img').classList.add('blur-background');
		});

		item.addEventListener('mouseleave', function() {
			item.classList.remove('hover');
			item.querySelector('img').classList.remove('blur-background');
		});
	});

	document.querySelectorAll('.animal-btn').forEach(function(button) {
		button.addEventListener('click', function() {
			document.querySelectorAll('.animal-btn').forEach(btn => btn.classList.remove('active'));
			this.classList.add('active');
		});
	});

	const animalButtons = document.querySelectorAll('.animal-btn2');
	const animalImage = document.getElementById('animal-image');
	const showAnimal = document.querySelector('.show-animal');
	let selectedAnimal = '강아지';
	animalButtons.forEach(button => {
		button.addEventListener('click', function() {
			selectedAnimal = this.getAttribute('data-animal');
			animalImage.src = `/images/disease/${selectedAnimal}1.png`;

			switch (selectedAnimal) {
				case '강아지':
					showAnimal.className = 'show-animal dog';
					break;
				case '고양이':
					showAnimal.className = 'show-animal cat';
					break;
				case '새':
					showAnimal.className = 'show-animal bird';
					break;
				case '햄스터':
					showAnimal.className = 'show-animal hamster';
					break;
				case '토끼':
					showAnimal.className = 'show-animal rabbit';
					break;
			}
		});
	});

	document.querySelectorAll('.body-part-btn').forEach(button => {
		button.addEventListener('click', function() {
			const bodyPart = this.getAttribute('data-body-part');
			fetchDiseases(selectedAnimal, bodyPart);
		});
	});
});

function fetchDiseases(animal, bodyPart) {
console.log(animal, bodyPart);
	$.ajax({
		url: `/board/disease/${animal}/${bodyPart}`,
		method: 'GET',
		success: function(data) {
			displayDiseases(data);
		},
		error: function(error) {
			console.error("Error fetching diseases:", error);
			
		}
	});
}


function displayDiseases(diseases) {
	const diseaseList = document.querySelector('.disease-list');
	diseaseList.innerHTML = ''; // 기존 리스트 초기화

	
	diseases.forEach(disease => {
		const diseaseItem = `
            <div class="disease-item">
                <div class="left-item">
                    <img src="${disease.imageUrl}" alt="${disease.name}">
                    <div class="overlay">${disease.name}</div>
                </div>
                <div class="right-item">
                    <h2>${disease.name}</h2>
                    <fieldset>
                        <div class="item-title">
                            <div>
                                <h3>증상</h3>
                            </div>
                            <div class="item-symptoms">
                                <p>${disease.symptoms}</p>
								
                            </div>
                        </div>
                        <div class="item-btn">
                            <button onclick="showModal('${disease.name}', '${disease.solution}', '${disease.imageUrl}')">자세히 보기</button>
                        </div>
						</div>
                    </fieldset>
                </div>
            </div>
        `;
		diseaseList.insertAdjacentHTML('beforeend', diseaseItem);
	});
}

function showModal(name, solution, imageUrl) {
	const modal = document.querySelector('.detail-infor');
	const modalBackground = document.getElementById('modalBackground'); // 모달 배경 추가
	modal.querySelector('.detail-name h3').innerText = name;
	modal.querySelector('.detail-solution p').innerText = solution; // 해결책 추가
	modal.querySelector('.detail-img img').src = imageUrl;
	modal.style.display = 'block';
	modalBackground.style.display = 'block';

}


function closeModal() {
	const modal = document.querySelector('.detail-infor');
	const modalBackground = document.getElementById('modalBackground'); // 모달 배경 추가
	modal.style.display = 'none'; // 모달을 숨김
	modalBackground.style.display = 'none'; // 배경도 숨김
}
// animal-btn2 클릭 시 active 클래스 추가
document.querySelectorAll('.animal-btn2').forEach(button => {
  button.addEventListener('click', function() {
    // 이미 활성화된 버튼에서 active 클래스 제거
    document.querySelectorAll('.animal-btn2').forEach(btn => btn.classList.remove('active'));
    
    // 클릭된 버튼에 active 클래스 추가
    this.classList.add('active');
  });
});

