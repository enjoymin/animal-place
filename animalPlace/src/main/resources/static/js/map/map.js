window.onload = function() {
    setTimeout(function() {
        window.scrollTo(0, 0); // 페이지가 로드된 후 잠시 대기하고 맨 위로 스크롤
    }, 100);
    document.getElementById('keyword').value = '';
	setCurrentLocation(); // GPS 또는 우편번호를 이용한 현재 위치 설정
/*	setupScrollListener();*/
};

var markers = []; // 마커를 저장할 배열
var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

// 지도를 생성합니다    
var map = new kakao.maps.Map(mapContainer, mapOption); 

map.addOverlayMapTypeId(kakao.maps.MapTypeId.TERRAIN); 

var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

// 장소 검색 객체를 생성
var ps = new kakao.maps.services.Places();  
var infowindow = new kakao.maps.InfoWindow({zIndex:1});
var pagination; // 전역 pagination 객체

// 현재 위치 설정 함수
function setCurrentLocation() {
    const isLoggedIn = sessionStorage.getItem('isLoggedIn') === 'true';

    if (isLoggedIn) {
        // 우편번호 API를 통해 주소 정보 가져오기
        $.get('/api/getUserZipcode', function(data) {
            const { zipcode } = data;

            // 우편번호를 사용하여 좌표를 얻기 위한 API 호출
            $.get(`/api/getCoordinatesByZipcode?zipcode=${zipcode}`, function(locationData) {
                const { lat, lng } = locationData;

                if (lat && lng) {
                    const currentPosition = new kakao.maps.LatLng(lat, lng);
                    map.setCenter(currentPosition); // 사용자의 위치로 지도의 중심 변경
                    addMarker(currentPosition, 0, { place_name: '현재 위치' }); // 현재 위치에 마커 추가
                    alert(`로그인한 사용자님의 위치로 설정되었습니다: ${data.address}`); // 사용자에게 위치 알림
                } else {
                    setDefaultLocation(); // 좌표가 없으면 기본 위치 설정
                }
            }).fail(function() {
                alert('위치 정보를 가져오는 데 실패했습니다. 기본 위치로 설정합니다.');
                setDefaultLocation(); // API 호출 실패 시 기본 위치 설정
            });
        }).fail(function() {
            alert('주소 정보를 가져오는 데 실패했습니다. 기본 위치로 설정합니다.');
            setDefaultLocation(); // 주소 정보 실패 시 기본 위치 설정
        });
    } else {
        // GPS를 사용하여 현재 위치 설정
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(
                function(position) {
                    const lat = position.coords.latitude;
                    const lng = position.coords.longitude;
                    const currentPosition = new kakao.maps.LatLng(lat, lng);
                    
                    map.setCenter(currentPosition); // 사용자의 위치로 지도의 중심 변경
                    addMarker(currentPosition, 0, { place_name: '현재 위치' }); // 현재 위치에 마커 추가
                    alert(`현재 위치로 설정되었습니다: ${lat}, ${lng}`); // 위치 알림
                },
                function(error) {
                    console.error(error);
                    alert('위치 정보를 가져오는 데 실패했습니다. 기본 위치로 설정합니다.');
                    setDefaultLocation(); // 실패 시 기본 위치로 설정
                }
            );
        } else {
            alert('이 브라우저는 Geolocation을 지원하지 않습니다. 기본 위치로 설정합니다.');
            setDefaultLocation(); // Geolocation 지원 안 할 경우 기본 위치로 설정
        }
    }
}

// 기본 위치 설정 함수
function setDefaultLocation() {
    map.setCenter(new kakao.maps.LatLng(37.566826, 126.9786567)); // 서울 기본 위치
}
//이벤트 리스너 성능개선
let debounceTimer;

/*function setupScrollListener() {
    window.addEventListener('scroll', function() {
        clearTimeout(debounceTimer);
        debounceTimer = setTimeout(() => {
            const scrollTop = window.scrollY;
            const documentHeight = document.documentElement.scrollHeight;
            const viewportHeight = window.innerHeight;

            if (scrollTop + viewportHeight >= documentHeight - 100 && !isLoading) {
                // 로딩 및 페이지 요청 로직
            }
        }, 100); // 100ms 후에 실행
    });
}*/


// 키워드로 장소를 검색합니다
searchPlaces();
/*setCurrentLocation(); // 현재 위치 설정 호출*/

// 키워드 검색을 요청하는 함수입니다
function searchPlaces() {
    var keyword = document.getElementById('keyword').value;
    if (keyword === '') {
        return; // 아무 작업도 하지 않고 종료
    }
    if (!keyword.replace(/^\s+|\s+$/g && !keyword.trim())) {
        alert('키워드를 입력해주세요!');
        return false;
    }
	
	document.querySelector('.image-container').style.display = 'block';

    // 주변 검색 요청
    const currentLocation = map.getCenter(); // 현재 지도 중심 좌표
    ps.keywordSearch(keyword, placesSearchCB, {
        location: currentLocation // 현재 위치를 기준으로 검색
    });
}

// 장소검색이 완료됐을 때 호출되는 콜백함수입니다
function placesSearchCB(data, status, paginationData) {
    if (status === kakao.maps.services.Status.OK) {
        pagination = paginationData; // pagination 객체 저장
   
		console.log(pagination);
		if(pagination){
			displayPagination(pagination);
		}
		     
        if (data.length === 0) {
            // 주변 검색 결과가 없을 경우 전국 검색 요청
            ps.keywordSearch(document.getElementById('keyword').value, placesSearchCB);
        } else {
            displayPlaces(data);
            displayPagination(pagination);
           /* setupScrollListener();*/ // 스크롤 이벤트 리스너 설정
        }
    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        alert('검색 결과가 존재하지 않습니다.');
        return;
    } else if (status === kakao.maps.services.Status.ERROR) {
        alert('검색 결과 중 오류가 발생했습니다.');
        return;
    }
}

var fixedMarker = null; // 현재 고정된 마커를 저장할 변수

function displayPlaces(places) {
    var listEl = document.getElementById('placesList'),
        menuEl = document.getElementById('menu_wrap'),
        fragment = document.createDocumentFragment(), 
        bounds = new kakao.maps.LatLngBounds(); 

	fixedMarker = null;
		
    removeAllChildNods(listEl);
    removeMarker();

    for (var i = 0; i < places.length; i++) {
        var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x);
        var marker = addMarker(placePosition, i, places[i]);
        var itemEl = getListItem(i, places[i]);
        fragment.appendChild(itemEl);

        (function(marker, place) {
            itemEl.onclick = function() {
                displayInfowindow(marker, place);
                const position = marker.getPosition();
                
                // 마커 위치로 이동
                map.panTo(position);

                // 마커 고정 및 마우스 오버 이벤트 비활성화
                if (!fixedMarker) {
                    fixedMarker = marker; // 현재 마커를 고정
                    // 마커에 대한 마우스 오버 및 아웃 이벤트 제거
                    kakao.maps.event.removeListener(marker, 'mouseover');
                    kakao.maps.event.removeListener(marker, 'mouseout');
                }

                setTimeout(function() {
                    if (map.getLevel() > 1) {
                        map.setLevel(1); 
                    }
                }, 500);
            };

            // 마우스 오버 시 인포윈도우 표시
            itemEl.onmouseover = function() {
                if (!fixedMarker) { // 고정되지 않은 경우에만 이벤트 발생
                    displayInfowindow(marker, place);
                }
            };

            // 마우스 아웃 시 인포윈도우 닫기
            itemEl.onmouseout = function() {
                if (!fixedMarker) { // 고정되지 않은 경우에만 이벤트 발생
                    infowindow.close();
                }
            };
        })(marker, places[i]);

        bounds.extend(placePosition);
    }

    listEl.appendChild(fragment);
    menuEl.scrollTop = 0;
    map.setBounds(bounds);
    
    menuEl.style.display = 'block'; // 리스트를 보여줍니다
    listEl.style.display = 'block'; // 리스트도 보여줍니다
}

// 스크롤 이벤트 리스너 설정
/*let isLoading = false; // 로딩 중인지 확인하는 플래그

function setupScrollListener() {
    const listContainer = document.getElementById('menu_wrap'); // 맵 리스트의 컨테이너 ID로 변경

    listContainer.addEventListener('scroll', function() {
        const scrollTop = listContainer.scrollTop; // 컨테이너의 스크롤 위치
        const documentHeight = listContainer.scrollHeight; // 컨테이너의 전체 높이
        const viewportHeight = listContainer.clientHeight; // 컨테이너의 뷰포트 높이

        console.log('Scroll position:', scrollTop, 'Document height:', documentHeight);

        // 스크롤이 페이지 하단에 가까워졌을 때
        if (scrollTop + viewportHeight >= documentHeight - 100 && !isLoading) {
            // 더 이상 페이지가 없지 않은 경우에만 다음 페이지 요청
            if (!pagination.is_end && pagination.current < pagination.last) {
                isLoading = true; // 로딩 시작
                console.log("Loading more content..."); // 로딩 시작 로그
                // 다음 페이지 요청
                ps.keywordSearch(document.getElementById('keyword').value, placesSearchCB, { page: pagination.current + 1 });

                // 로딩이 끝나는 시점에 isLoading을 false로 설정
                setTimeout(() => {
                    isLoading = false; // 로딩 끝
                    console.log("Loading complete."); // 로딩 완료 로그
                }, 2000); // 2초 후에 다시 요청 가능
            }
        }
    });
}

// 한 번만 호출
setupScrollListener();*/


// 인포윈도우 표시 함수
function displayInfowindow(marker, place) {
    var content = `
        <div class="place_map">
            <h4>${place.place_name}</h4>
            <p>주소: ${place.road_address_name || place.address_name}</p>
            <p>전화번호: ${place.phone || '정보 없음'}</p>
        </div>
    `;
    
    infowindow.setContent(content);
    infowindow.open(map, marker);
	
	setTimeout(function() {
	    const absoluteDivs = document.querySelectorAll('div[style*="position: absolute"]'); // position:absolute가 있는 모든 div 선택

	    absoluteDivs.forEach(absoluteDiv => {
	        // 특정 스타일 속성이 일치하는 div를 찾아 수정
	        if (absoluteDiv.style.cursor === 'default' && 
	            absoluteDiv.style.background === 'rgb(255, 255, 255)' && 
	            absoluteDiv.style.border === '1px solid rgb(118, 129, 168)') {
	                
	            absoluteDiv.style.backgroundColor = '#E9FFC2'; 
	            absoluteDiv.style.borderRadius = '23%'; 
	            absoluteDiv.style.setProperty('border', 'none', 'important'); // border를 none으로 설정
	        }
	    });
	}, 0);
	// 인포윈도우 클릭 이벤트 추가
	const placeMapDiv = document.querySelector('.place_map');
	placeMapDiv.onclick = function() {
		const iframe = document.getElementById('iframeElement'); // iframe의 ID
		iframe.src = place.place_url; // 웹사이트 URL 설정
		const search_detail = document.getElementById('search_detail');
		search_detail.scrollIntoView({ behavior: 'smooth' }); // 스크롤 애니메이션
	};
}

//게시판 바로가기 클릭 이벤트


// 마커 추가 함수
function addMarker(position, idx, place) {
    var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png',
        imageSize = new kakao.maps.Size(36, 37),
        imgOptions = {
            spriteSize: new kakao.maps.Size(36, 691),
            spriteOrigin: new kakao.maps.Point(0, (idx * 46) + 10),
            offset: new kakao.maps.Point(13, 37)
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
        marker = new kakao.maps.Marker({
            position: position,
            image: markerImage
        });

    marker.setMap(map);
    markers.push(marker);
	

    // 클릭 이벤트 추가
    kakao.maps.event.addListener(marker, 'click', function() {
		map.panTo(position);
        const iframe = document.getElementById('iframeElement'); // iframe의 ID
        iframe.src = place.place_url; // 웹사이트 URL 설정
        const search_detail = document.getElementById('search_detail');
        search_detail.scrollIntoView({ behavior: 'smooth' }); // 스크롤 애니메이션
    });

    // 마우스 오버 및 아웃 이벤트 추가
    kakao.maps.event.addListener(marker, 'mouseover', function() {
        displayInfowindow(marker, place);
    });

    kakao.maps.event.addListener(marker, 'mouseout', function() {
        infowindow.close();
    });

    return marker;
}

// 검색 결과 목록의 자식 Element 제거 함수
function removeAllChildNods(el) {
    while (el.firstChild) {
        el.removeChild(el.firstChild);
    }
}

// 지도 위의 모든 마커 제거 함수
function removeMarker() {
    markers.forEach(function(marker) {
        marker.setMap(null);
    });
    markers = [];
}

// 검색결과 항목을 Element로 반환하는 함수
function getListItem(index, places) {
    var el = document.createElement('li'),
        itemStr = '<span class="markerbg marker_' + (index + 1) + '"></span>' +
                    '<div class="info">' +
                    '   <h5>' + places.place_name + '</h5>';

    if (places.road_address_name) {
        itemStr += '    <span>' + places.road_address_name + '</span>' +
                    '   <span class="jibun gray">' + places.address_name + '</span>';
    } else {
        itemStr += '    <span>' + places.address_name + '</span>';
    }

    itemStr += '  <span class="tel">' + (places.phone || '정보 없음') + '</span>' +
                '</div>';           

    el.innerHTML = itemStr;
    el.className = 'item';

    return el;
}

// 페이지 번호 표시 함수
function displayPagination(pagination) {
    var paginationEl = document.getElementById('pagination'),
        fragment = document.createDocumentFragment();

    removeAllChildNods(paginationEl);

    for (var i = 1; i <= pagination.last; i++) {
        var el = document.createElement('a');
        el.href = "#";
        el.innerHTML = i;

        if (i === pagination.current) {
            el.className = 'on';
        } else {
            el.onclick = (function(page) {
                return function(e) {
					e.preventDefault();
                    ps.keywordSearch(document.getElementById('keyword').value, placesSearchCB, { page: page });
                };
            })(i);
        }
        fragment.appendChild(el);
    }

    paginationEl.appendChild(fragment);
	console.log("페이지 번호:", paginationEl.innerHTML); // 추가: HTML 내용 확인
}
let isTopHalf = true; // 초기 상태: 위쪽 반이 보임
// 토글 버튼 클릭 이벤트
document.getElementById('toggleButton').onclick = function() {
    var listEl = document.getElementById('menu_wrap');

    // 리스트의 숨김/표시 처리
    if (listEl.classList.contains('hidden')) {
        listEl.classList.remove('hidden'); // 리스트 나타내기
    } else {
        listEl.classList.add('hidden'); // 리스트 숨기기
    }

    // 이미지 위치 토글 처리
    const toggle = document.getElementById('toggleButton');
    if (isTopHalf) {
        toggle.style.top = '-100%'; // 아래쪽 반 보이기
    } else {
        toggle.style.top = '0'; // 위쪽 반 보이기
    }
    
    isTopHalf = !isTopHalf; // 상태 반전
};