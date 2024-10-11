window.onload = function() {
    setTimeout(function() {
        window.scrollTo(0, 0);
    }, 100);
    document.getElementById('keyword').value = '';
    setCurrentLocation();
    loadRankings(); // 초기 로드 시 순위 가져오기
};

var markers = [];
var mapContainer = document.getElementById('map'),
    mapOption = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567),
        level: 3
    };

var map = new kakao.maps.Map(mapContainer, mapOption);
var ps = new kakao.maps.services.Places();
var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
var pagination;

function setCurrentLocation() {
    const isLoggedIn = sessionStorage.getItem('isLoggedIn') === 'true';

    if (isLoggedIn) {
        $.get('/api/getUserZipcode', function(data) {
            const { zipcode } = data;

            $.get(`/api/getCoordinatesByZipcode?zipcode=${zipcode}`, function(locationData) {
                const { lat, lng } = locationData;

                if (lat && lng) {
                    const currentPosition = new kakao.maps.LatLng(lat, lng);
                    map.setCenter(currentPosition);
                    addMarker(currentPosition, 0, { place_name: '현재 위치' });
                    alert(`로그인한 사용자님의 위치로 설정되었습니다: ${data.address}`);
                } else {
                    setDefaultLocation();
                }
            }).fail(function() {
                alert('위치 정보를 가져오는 데 실패했습니다. 기본 위치로 설정합니다.');
                setDefaultLocation();
            });
        }).fail(function() {
            alert('주소 정보를 가져오는 데 실패했습니다. 기본 위치로 설정합니다.');
            setDefaultLocation();
        });
    } else {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(
                function(position) {
                    const lat = position.coords.latitude;
                    const lng = position.coords.longitude;
                    const currentPosition = new kakao.maps.LatLng(lat, lng);
                    
                    map.setCenter(currentPosition);
                    addMarker(currentPosition, 0, { place_name: '현재 위치' });
                    alert(`현재 위치로 설정되었습니다: ${lat}, ${lng}`);
                },
                function(error) {
                    console.error(error);
                    alert('위치 정보를 가져오는 데 실패했습니다. 기본 위치로 설정합니다.');
                    setDefaultLocation();
                }
            );
        } else {
            alert('이 브라우저는 Geolocation을 지원하지 않습니다. 기본 위치로 설정합니다.');
            setDefaultLocation();
        }
    }
}

function setDefaultLocation() {
    map.setCenter(new kakao.maps.LatLng(37.566826, 126.9786567));
}

async function searchPlaces() {
    var keyword = document.getElementById('keyword').value;
    if (keyword === '') {
        return;
    }

    document.querySelector('.image-container').style.display = 'block';
    const currentLocation = map.getCenter();
    ps.keywordSearch(keyword, placesSearchCB, { location: currentLocation });

    await recordSearch(keyword); // 검색 기록 저장
}

async function recordSearch(keyword) {
    if (!keyword.trim()) {
        alert('키워드를 입력해주세요!');
        return;
    }
    try {
        const response = await fetch('/search/record', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ keyword }),
        });

        if (!response.ok) {
            throw new Error('서버 오류: ' + response.status);
        }

        await loadRankings(); // 순위 업데이트
    } catch (error) {
        console.error('검색 기록 저장 실패:', error);
    }
}

let previousRankings = [];

async function loadRankings() {
    try {
        const response = await fetch('/search/rankings');
        const data = await response.json();
        
        console.log('API Response:', data); // API 응답 확인

        const rankings = Array.isArray(data) ? data : data.rankings;

        const rankingsList = document.getElementById('rankingsList');
        rankingsList.innerHTML = ''; // 기존 목록 초기화

        rankings.forEach((rank, index) => {
            const li = document.createElement('li');
            li.classList.add('rank-item');

            // 초기 상태 설정
            li.style.transform = 'translateY(10px)';
            li.style.opacity = 0;

            let change = '';
            const prevRank = previousRankings.find(r => r.keyword === rank.keyword);
            const prevCount = prevRank ? prevRank.searchCount : 0;

            li.style.color = 'black'; // 기본 색상

            if (rank.searchCount > prevCount) {
                change = ` (▲)`;
                li.style.color = 'red'; // 상승 시 빨간색
            } else if (rank.searchCount < prevCount) {
                change = ` (▼)`;
                li.style.color = 'blue'; // 하락 시 파란색
            } else {
                change = ' (-)'; // 변화 없음
            }

            li.textContent = `${index + 1}. "${rank.keyword}"${change}`;
            rankingsList.appendChild(li);

            // 애니메이션 효과
            setTimeout(() => {
                li.style.opacity = 1;
                li.style.transform = 'translateY(0)';
            }, index * 100);
        });

        // 이전 순위 업데이트
        previousRankings = rankings;
    } catch (error) {
        console.error('순위 로드 실패:', error);
    }
}

// 장소검색이 완료됐을 때 호출되는 콜백함수입니다
function placesSearchCB(data, status, paginationData) {
    if (status === kakao.maps.services.Status.OK) {
        pagination = paginationData;

        if (data.length === 0) {
            ps.keywordSearch(document.getElementById('keyword').value, placesSearchCB);
        } else {
            displayPlaces(data);
            displayPagination(pagination);
        }
    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        alert('검색 결과가 존재하지 않습니다.');
    } else if (status === kakao.maps.services.Status.ERROR) {
        alert('검색 결과 중 오류가 발생했습니다.');
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

// 인포윈도우 표시 함수
function displayInfowindow(marker, place) {
    var content = `
        <div style="cursor: default; position: absolute; background: rgb(255, 255, 255); border: none; z-index: 1; display: block; width: 280px; height: 72px; margin-top: -40px; margin-left: -65px;">
            <div style="position: absolute; width: 11px; height: 9px; background: url('http://t1.daumcdn.net/localimg/localimages/07/mapjsapi/triangle.png') 0% 0% / 11px 9px no-repeat; left: 134px; top: 72px;"></div>
            <div style="position: absolute; left: 0px; top: 0px;">
                <div class="place_map" style="cursor: pointer;"> <!-- 클릭 가능한 영역으로 설정 -->
                    <h4>${place.place_name}</h4>
                    <p>주소: ${place.road_address_name || place.address_name}</p>
                    <p>전화번호: ${place.phone || '정보 없음'}</p>
                </div>
            </div>
        </div>
    `;

    infowindow.setContent(content);
    infowindow.open(map, marker);
    
    // 인포윈도우 클릭 이벤트 추가
    const placeMapDiv = document.querySelector('.place_map');
    if (placeMapDiv) {
        placeMapDiv.onclick = function() {
            const iframe = document.getElementById('iframeElement');
            iframe.src = place.place_url; // 웹사이트 URL 설정
            const search_detail = document.getElementById('search_detail');
            search_detail.scrollIntoView({ behavior: 'smooth' }); // 스크롤 애니메이션
        };
    }
}

// 마커 클릭 이벤트
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

    // 마커 클릭 이벤트
    kakao.maps.event.addListener(marker, 'click', function() {
        displayInfowindow(marker, place);
        const iframe = document.getElementById('iframeElement'); // iframe의 ID
        iframe.src = place.place_url; // 웹사이트 URL 설정
        const search_detail = document.getElementById('search_detail');
        search_detail.scrollIntoView({ behavior: 'smooth' }); // 스크롤 애니메이션
    });

    // 마우스 오버 이벤트
    kakao.maps.event.addListener(marker, 'mouseover', function() {
        displayInfowindow(marker, place);
    });

    // 마우스 아웃 이벤트
    kakao.maps.event.addListener(marker, 'mouseout', function() {
        infowindow.close();
    });

    return marker;
}

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

    // 클릭 이벤트
    kakao.maps.event.addListener(marker, 'click', function() {
        displayInfowindow(marker, place);
    });

    // 마우스 오버 이벤트
    kakao.maps.event.addListener(marker, 'mouseover', function() {
        displayInfowindow(marker, place);
    });

    // 마우스 아웃 이벤트
    kakao.maps.event.addListener(marker, 'mouseout', function() {
        infowindow.close();
    });

    return marker;
}

function removeAllChildNods(el) {
    while (el.firstChild) {
        el.removeChild(el.firstChild);
    }
}

function removeMarker() {
    markers.forEach(function(marker) {
        marker.setMap(null);
    });
    markers = [];
}

function getListItem(index, place) {
    var el = document.createElement('li'),
        itemStr = `<span class="markerbg marker_${index + 1}"></span>
                    <div class="info">
                        <h5>${place.place_name}</h5>	
                        <span>${place.road_address_name || place.address_name}</span>
                        <span class="tel">${place.phone || ''}</span>
                    </div>`;

    el.innerHTML = itemStr;
    el.className = 'item';

    return el;
}

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
            el.onclick = (function(i) {
                return function() {
                    pagination.gotoPage(i);
                };
            })(i);
        }

        fragment.appendChild(el);
    }
	

    paginationEl.appendChild(fragment);
}
let isTopHalf = true; // 상태 초기화

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
