create database project;
use project;

create database project;

use project;

################################# PBOARD START
create table p_board(
	boardnum bigint primary key auto_increment,
	boardtitle varchar(300) not null,
	boardcontents varchar(300) not null,
	regdate datetime default now(), #등록시간
	updatedate datetime default now(), #수정시간
	userid varchar(300),
    
	boardflag bool default false #댓글 알림을 위한 컬럼
);
##################### drop table p_board;


create table p_reply(
	replynum bigint primary key auto_increment,
	replycontent text not null,
	regdate datetime default now(),
	updatedate datetime default now(),
	replyuserid varchar(300),
	boardnum bigint
);

#p_pic -> p_file로 수정
create table p_file(
  systemname varchar(3000) not null,
  orgname varchar(3000) not null,
  boardnum bigint
);

create table p_likelist(
	userid varchar(300),
	pboardnum bigint
);

######################## PBOARD END

###################### USER START

create table user(
	userid varchar(300) primary key,
    userpw varchar(300),
    username varchar(300),
    userphone varchar(300),
    useremail varchar(300),
    zipcode varchar(300),
    addr varchar(1000),
    addrdetail varchar(2000) not null,
    addretc varchar(300),
    userpet varchar(300),
    schedule varchar(300)
);
drop table user;
insert into  user(userid,userpw,username,addrdetail) values("apple","1234","김사과","중국산");

insert into  user(userid,userpw,username,addrdetail) values("banana","1234","반하나","중국산");

create table myphoto(
	systemname varchar(1000),
    userid varchar(300)
);

create table alarm(
	alarmnum int primary key auto_increment,
    userid varchar(300) not null,
    boardtitle varchar(1000) not null,
    contentpath varchar(3000) not null,
    reply boolean default false,
    ameeting boolean default false,
    dmeeting boolean default false,
    plike boolean default false
);
select * from alarm;
drop table alarm;

############################ USER END

############################# MAP START

create table posts(
boardnum bigint auto_increment primary key,
boardtitle varchar(300) not null,
boardcontent text not null,
place_data text not null,
regdate datetime default now(),
updatedate datetime default now() on update now(),
readcount int default 0,
userid varchar(300)
);

create table map_search_history(
	id serial primary key,
    keyword varchar(300) not null,
    search_count int default 1,
    search_time timestamp default current_timestamp
);
drop table map_search_history;
select * from map_search_history;
delete from map_search_history where id = 10;

################################## MAP END

################################## DISEASE START
CREATE TABLE disease (
    id INT AUTO_INCREMENT PRIMARY KEY,
    animal_name varchar(300), 
    body_part varchar(300),
    name VARCHAR(100) NOT NULL,
    symptoms TEXT,
    image_url VARCHAR(300),
    solution TEXT
);
################################## DISEASE END


################################### mboard start
create table m_board(
boardnum int auto_increment primary key,
    boardtitle varchar(300),
    userid varchar(300),
    constraint userid foreign key(userid) references user(userid),
    setdate date,
    dDay int,
    place varchar(3000),
    mnum int,
    boardcontent varchar(3000),
    boarddatetime datetime default now(),
    boardflag boolean default false,
	readcount int default 0,
    member varchar(3000)
);
select * from m_board;
drop table m_board;


create table m_reply(
replynum bigint auto_increment primary key,
    replycontent varchar(3000),
    replyuserid varchar(300),
    replydatetime datetime default now(),
    boardnum int,
    constraint boardnum foreign key(boardnum) references m_board(boardnum)
);

drop table m_reply;
################################## mboard end

############################# 규진님 완성되면 추가

create table adoption(
   adoptionnum bigint auto_increment primary key,
    title varchar(300),
    contents varchar(3000),
    breed varchar(100),
    type varchar(100),
    region varchar(100),
    gender varchar(100),
    age varchar(100),
    cost varchar(100),
    regdate datetime default now(),
    adoptionOk varchar(100),
    userid varchar(300)
);

create table adfile(
   systemname varchar(3000),
	orgname varchar(3000),
    adoptionnum bigint
);

###############################


#따로 넣어줘야하는거

#-----강아지 데이터 
INSERT INTO disease (animal_name, body_part, name, symptoms, image_url, solution) VALUES
("강아지", "머리", '외이도염', '귀를 긁거나 흔드는 행동, 악취, 귀에 분비물, 통증', '/images/disease/강아지 외이도염.jpg', '수의사 진료를 통해 정확한 진단을 받고, 필요 시 항생제 처방. 귀 청소 시 전용 클리너 사용하고, 정기적으로 귀 상태를 점검하여 재발 방지.'),
("강아지", "머리", '치주염', '입 냄새, 잇몸 붓기, 식욕 감소, 씹는 데 어려움', '/images/disease/강아지 치주염.jpg', '정기적인 치아 관리와 스케일링을 통해 구강 위생을 유지. 잇몸 마사지와 치아 청소용 제품 사용, 필요시 수의사와 상담하여 적절한 치료를 받는다.'),
("강아지", "머리", '안구염', '눈물이 많이 나거나 충혈, 눈 주위의 부기, 비늘 같은 것', '/images/disease/강아지 안구염.jpg', '안약 처방 및 눈 세척을 통해 염증 완화, 수의사 진료 시 추가 검사를 통해 원인 파악. 환경에서의 자극 요소 제거 및 주기적인 눈 건강 점검.'),
("강아지", "머리", '피부염', '가려움증, 발진, 염증, 털 빠짐', '/images/disease/강아지 피부염.jpg', '항히스타민제 사용 및 수의사 진료 후 피부 검사 시행. 주기적인 목욕과 피부 보호제를 사용하여 상태 개선 및 관리.'),
("강아지", "목", '갑상선 기능 저하증', '체중 변화, 탈모, 무기력, 피부 건조', '/images/disease/강아지 갑상선 기능 저하증.jpg', '호르몬 치료와 정기적인 혈액 검사를 통해 호르몬 수치 모니터링. 식이 요법 조절 및 정기적인 수의사 검진을 통해 건강 상태 유지.'),
("강아지", "목", '경부 종양', '부풀어 오른 목, 통증, 삼키기 어려움', '/images/disease/강아지 경부 종양.jpg', '수술적 제거 후 방사선 치료가 필요할 수 있으며, 수의사와 상담하여 적절한 후속 조치 계획. 정기적인 검진을 통해 상태 모니터링 및 재발 방지.'),
("강아지", "목", '기관지염', '기침, 호흡 곤란, 목소리 변화, 흉부 압박', '/images/disease/강아지 기관지염.jpg', '약물 치료로 기침 완화 및 호흡기 관리, 수분 공급과 환경 관리로 증상 완화. 필요 시 수의사와 상담하여 추가 치료 계획 수립.'),
("강아지", "목", '식도염', '구토, 식욕 감소, 삼키기 어려움, 침흘림', '/images/disease/강아지 식도염.jpg', '수의사 진료 후 약물 치료 및 식이 요법 조절, 부드러운 사료 제공으로 소화 부담 최소화. 정기적인 건강 검진을 통해 장기적인 관리.'),
("강아지", "몸통", '비만', '체중 증가, 활동 감소, 숨쉬기 힘듦', '/images/disease/강아지 비만.jpg', '식단 조절 및 규칙적인 운동을 통해 체중 관리, 수의사와 상담하여 적절한 식단 계획 수립. 장기적으로 건강한 체중 유지 및 생활 습관 개선.'),
("강아지", "몸통", '심장병', '기침, 숨쉬기 힘듦, 무기력, 식욕 감소', '/images/disease/강아지 심장병.jpg', '약물 치료와 저염식이 필요하며, 수의사와 상담하여 주기적인 심장 검사 및 모니터링을 통해 상태 관리. 운동량 조절과 환경 변화로 스트레스 감소.'),
("강아지", "몸통", '호흡기 감염', '기침, 재채기, 콧물, 호흡 곤란', '/images/disease/강아지 호흡기 감염.jpg', '수의사 진료 후 약물 치료와 충분한 수분 섭취. 깨끗한 환경 유지 및 감염 예방을 위한 백신 접종.'),
("강아지", "몸통", '장 질환', '설사, 구토, 식욕 감소, 복통', '/images/disease/강아지 장 질환.jpg', '식이 요법 및 약물 치료로 장 건강 회복, 수의사와 상담하여 식사 조절 및 필요시 추가 검사 실시. 수분 보충 및 영양 관리.'),
("강아지", "다리", '슬개골 탈구', '다리를 절거나 불편해하는 모습, 걷기 어려움, 통증', '/images/disease/강아지 슬개골 탈구.jpg', '수술 또는 물리치료로 통증 완화 및 기능 회복. 정기적인 관찰과 필요시 체중 관리로 추가 부담 감소.'),
("강아지", "다리", '관절염', '움직임이 느려짐, 통증, 경직, 활동 감소', '/images/disease/강아지 관절염.jpg', '통증 완화제 사용 및 물리치료, 관절 보조제 사용으로 관리. 정기적인 수의사 상담을 통해 상태 모니터링 및 치료 조정.'),
("강아지", "다리", '발톱 문제', '발톱이 부러지거나 패인 경우, 통증, 걷기 어려움', '/images/disease/강아지 발톱 문제.jpg', '정기적인 발톱 관리 및 필요한 경우 수의사 진료. 적절한 자르기 및 발톱 보호로 추가 문제 예방.'),
("강아지", "다리", '근육 경련', '불규칙한 움직임, 통증, 경직, 과도한 피로', '/images/disease/강아지 근육 경련.jpg', '수의사 진료 및 치료, 적절한 스트레칭과 운동 관리로 근육 회복. 스트레스 관리 및 환경 개선으로 예방.'),
("강아지", "털", '벼룩 감염', '가려움증, 털 빠짐, 피부 염증, 발진', '/images/disease/강아지 벼룩 감염.jpg', '벼룩 치료제 사용 및 환경 청소와 관리. 주기적인 예방 접종과 체크로 재감염 방지.'),
("강아지", "털", '피부염', '발진, 가려움증, 염증, 피부 자극', '/images/disease/강아지 피부염.jpg', '약물 치료 및 환경 관리, 정기적인 목욕과 보호제 사용으로 관리. 수의사 상담을 통해 필요시 추가 치료 실시.'),
("강아지", "털", '탈모증', '부위별 털 빠짐, 피부 변화, 가려움', '/images/disease/강아지 탈모증.jpg', '원인에 따른 치료와 관리로 증상 완화, 수의사 진료를 통해 추가적인 원인 파악 및 치료법 상담.'),
("강아지", "털", '곰팡이 감염', '털 빠짐, 발진, 가려움증, 피부 붉어짐', '/images/disease/강아지 곰팡이 감염.jpg', '항진균제 사용 및 피부 관리로 증상 완화. 환경 청결 유지와 예방 조치를 통해 재발 방지.');


#---고양이
INSERT INTO disease (animal_name, body_part, name, symptoms, image_url, solution) VALUES
("고양이", "머리", '치주염', '입 냄새, 식욕 감소, 잇몸 붓기, 치아 변색', '/images/disease/고양이 치주염.jpg', '정기적인 치아 관리와 스케일링이 필요하며, 구강 세정제 사용과 수의사 상담을 통해 건강한 구강 상태 유지. 치아 건강을 위해 특별히 개발된 사료 사용.'),
("고양이", "머리", '귀 진드기', '귀를 긁는 행동, 악취, 귀에 분비물, 염증', '/images/disease/고양이 귀 진드기.jpg', '귀 청소 후 약물 사용을 통해 진드기를 제거하고, 주기적인 귀 상태 점검으로 재발 방지. 환경 청소를 통해 진드기 감염 예방.'),
("고양이", "머리", '결막염', '눈물, 충혈, 눈 주위의 부기, 눈에 고름', '/images/disease/고양이 결막염.jpg', '안약 처방과 함께 수의사 진료를 통해 정확한 원인 파악 및 치료. 자극적인 환경 제거와 정기적인 눈 상태 점검으로 예방.'),
("고양이", "머리", '진균성 두피염', '탈모, 가려움증, 피부 발진, 비늘 같은 것', '/images/disease/고양이 진균성 두피염.jpg', '항진균제 사용 및 영향을 받은 부위를 청결하게 유지하여 증상 완화. 주기적인 샤워와 환경 소독으로 재발 방지.'),
("고양이", "목", '갑상선 기능 항진증', '체중 변화, 탈모, 과다한 식욕, 불안', '/images/disease/고양이 갑상선 기능 항진증.jpg', '호르몬 치료와 정기적인 혈액 검사를 통해 호르몬 수치 모니터링. 수의사와 상담하여 적절한 식이 요법을 조정.'),
("고양이", "목", '기관지염', '기침, 호흡 곤란, 재채기, 가래', '/images/disease/고양이 기관지염.jpg', '약물 치료와 함께 환경 관리를 통해 증상 완화. 자극적인 요소를 피하고, 주기적인 건강 검진을 통해 장기적인 관리.'),
("고양이", "목", '목 종양', '부풀어 오른 목, 삼키기 어려움, 통증', '/images/disease/disease/고양이 목 종양.jpg', '수술적 제거 후 방사선 치료가 필요할 수 있으며, 수의사와 상담하여 적절한 후속 조치 계획. 정기적인 검진으로 상태 모니터링.'),
("고양이", "목", '식도 질환', '구토, 식욕 감소, 침흘림, 삼키기 어려움', '/images/disease/고양이 식도 질환.jpg', '수의사 진료 후 약물 치료 및 식이 요법 조절. 부드러운 사료 제공으로 소화 부담 최소화. 정기적인 검진으로 건강 유지.'),
("고양이", "몸통", '비만', '체중 증가, 활동 감소, 숨쉬기 힘듦, 에너지 저하', '/images/disease/고양이 비만.jpg', '식단 조절과 규칙적인 운동을 통해 체중 관리. 수의사와 상담하여 적절한 체중 감량 계획 수립. 장기적인 건강 유지.'),
("고양이", "몸통", '심장병', '기침, 숨쉬기 힘듦, 무기력, 식욕 감소', '/images/disease/고양이 심장병.jpg', '약물 치료와 저염식이 필요하며, 수의사와 상담하여 주기적인 심장 검사 및 모니터링을 통해 상태 관리. 스트레스 최소화와 규칙적인 운동이 필요.'),
("고양이", "몸통", '호흡기 감염', '기침, 재채기, 콧물, 호흡 곤란', '/images/disease/고양이 호흡기 감염.jpg', '수의사 진료 및 약물 치료로 증상 완화. 환경 청결 유지와 정기적인 예방 접종으로 감염 예방. 충분한 수분 섭취가 중요.'),
("고양이", "몸통", '장 질환', '설사, 구토, 식욕 감소, 복통', '/images/disease/고양이 장 질환.jpg', '식이 요법 및 약물 치료로 장 건강 회복, 수의사와 상담하여 맞춤형 식사 계획을 수립. 수분 보충과 영양 관리가 필요.'),
("고양이", "다리", '슬개골 탈구', '다리를 절거나 불편해하는 모습, 통증', '/images/disease/고양이 슬개골 탈구.jpg', '수술 또는 물리치료로 통증 완화 및 기능 회복. 정기적인 관찰과 필요시 체중 관리로 추가 부담 감소.'),
("고양이", "다리", '관절염', '움직임이 느려짐, 통증, 경직, 활동 감소', '/images/disease/고양이 관절염.jpg', '통증 완화제 사용 및 물리치료, 관절 보조제를 통해 증상 관리. 수의사 상담을 통해 장기적인 치료 계획 수립.'),
("고양이", "다리", '발톱 문제', '발톱이 부러지거나 패인 경우, 통증, 걷기 어려움', '/images/disease/고양이 발톱 문제.jpg', '정기적인 발톱 관리 및 필요한 경우 수의사 진료. 적절한 자르기와 발톱 보호로 추가 문제 예방.'),
("고양이", "다리", '근육 경련', '불규칙한 움직임, 통증, 과도한 피로', '/images/disease/고양이 근육경련.jpg', '수의사 진료 및 치료, 적절한 스트레칭과 운동 관리로 근육 회복. 스트레스 관리와 환경 개선으로 예방.'),
("고양이", "털", '벼룩 감염', '가려움증, 털 빠짐, 피부 염증, 발진', '/images/disease/고양이 벼룩 감염.jpg', '벼룩 치료제 사용 및 환경 청소로 감염 방지. 정기적인 예방 접종과 체크로 재감염 방지.'),
("고양이", "털", '피부염', '발진, 가려움증, 염증, 피부 자극', '/images/disease/고양이 피부염.jpg', '약물 치료 및 환경 관리로 증상 완화, 정기적인 목욕과 피부 보호제를 사용하여 피부 건강 유지.'),
("고양이", "털", '탈모증', '부위별 털 빠짐, 피부 변화, 가려움', '/images/disease/고양이 탈모증.jpg', '원인에 따른 치료와 관리로 증상 완화, 수의사 진료를 통해 추가적인 원인 파악 및 치료법 상담.'),
("고양이", "털", '곰팡이 감염', '털 빠짐, 발진, 가려움증, 피부 붉어짐', '/images/disease/고양이 곰팡이 감염.jpg', '항진균제 사용 및 피부 관리로 증상 완화. 환경 청결 유지와 예방 조치를 통해 재발 방지.');

#---새
INSERT INTO disease (animal_name, body_part, name, symptoms, image_url, solution) VALUES
("새", "머리", '비행 모양병', '깃털이 부풀어 오르고 비정상적인 행동을 보이며, 대개 무기력함과 식욕 감소가 동반됨.', '/images/disease/새 비행 모양병.jpg', '적절한 사육 환경 조성, 영양 보충, 수의사의 진료를 통해 증상 관리.'),
("새", "머리", '안구 감염', '눈 주위가 붉어지고 부풀어 오르며, 눈물이 과도하게 나오고 눈을 자주 깜빡임.', '/images/disease/새 안구 감염.jpg', '수의사 진료 후 적절한 항생제 사용 및 눈 세척.'),
("새", "머리", '호흡기 감염', '기침, 호흡 곤란, 고음으로 짖는 소리, 무기력함.', '/images/disease/새 호흡기 감염.jpg', '약물 치료, 따뜻하고 통풍이 잘 되는 환경 제공.'),
("새", "머리", '깃털 진드기 감염', '깃털이 부풀어 오르고, 심한 가려움증과 털 빠짐.', '/images/disease/새 깃털 진드기 감염.jpg', '진드기 치료제 사용, 정기적인 샤워와 청결 유지.'),
("새", "목", '간 질환', '식욕 감소, 비만, 구토, 발작 증상도 나타날 수 있음.', '/images/disease/새 간 질환.jpg', '수의사 진료 및 약물 치료, 건강한 식단 제공.'),
("새", "목", '장 질환', '설사, 구토, 체중 감소, 식욕 변화가 보임.', '/images/disease/새 장 질환.jpg', '식이 요법 및 약물 치료, 수분 공급을 잊지 않도록 함.'),
("새", "목", '비타민 A 결핍', '깃털 문제, 무기력, 피부 건조증이 발생할 수 있음.', '/images/disease/새 비타민 부족.jpg', '영양 보충제 사용, 신선한 채소와 과일 제공.'),
("새", "목", '근육 질병', '운동 능력 감소, 다리의 비정상적인 움직임.', '/images/disease/새 근육 질병.jpg', '수의사 진료, 물리 치료 및 적절한 운동 계획 수립.'),
("새", "몸통", '관절염', '움직임이 느려지고 통증이 느껴지며, 발을 들고 앉거나 걸을 때 불편함.', '/images/disease/새 관절염.jpg', '통증 완화제 사용, 체중 관리 및 운동 제한.'),
("새", "몸통", '발톱 문제', '발톱이 부러지거나 패인 경우, 걷기 어려움.', '/images/disease/새 발톱 문제.jpg', '정기적인 발톱 관리, 필요 시 수의사에게 검진 요청.'),
("새", "몸통", '근육 경련', '불규칙한 움직임, 통증을 호소함.', '/images/disease/새 근육 경련.jpg', '수의사 진료, 필요 시 진통제 및 근육 이완제 사용.'),
("새", "몸통", '발바닥 염증', '다리 부풀어 오르고 통증, 걸을 때 불편함을 느낌.', '/images/disease/새 발바닥 염증.jpg', '수의사 진료, 염증 완화제 및 적절한 치료 시행.'),
("새", "털", '깃털 문제', '깃털이 부풀어 오르거나 손상됨, 비정상적인 털 빠짐.', '/images/disease/새 깃털 문제.jpg', '영양 보충 및 환경 개선, 정기적인 샤워 및 관찰.'),
("새", "털", '비행 문제', '비행을 하지 못하거나 날기 힘들어함, 날개를 제대로 펴지 못함.', '/images/disease/새 비행문제.jpg', '수의사 진료, 근력 훈련 및 비행 연습 제공.'),
("새", "털", '날개 부상', '날개를 다쳤거나 부풀어 오름, 비행 시 통증.', '/images/disease/새 날개 부상.jpg', '수의사 진료 및 치료, 필요 시 부상 부위에 대한 물리 치료.'),
("새", "털", '부풀음증', '날개가 비정상적으로 부풀어 오르고, 가벼운 통증 호소.', '/images/disease/새 부풀음증.jpg', '수의사 진료 및 치료, 적절한 사육 환경 유지.'),
("새", "다리", '피부 감염', '피부가 붉어지고 부풀어 오름, 비듬과 가려움증이 동반됨.', '/images/disease/새 피부 감염.jpg', '수의사 진료 후 적절한 항생제나 항진균제를 처방받음, 청결한 환경 유지.'),
("새", "다리", '비듬', '피부에서 비듬이 떨어짐, 가려움증이 심함.', '/images/disease/새 비듬.jpg', '수의사에게서 비듬 전용 샴푸나 치료제를 사용하고 영양이 풍부한 사료를 제공.'),
("새", "다리", '알레르기성 피부염', '가려움증, 피부 발진, 염증이 심해짐.', '/images/disease/새 알레르기성 피부염.jpg', '알레르기 유발 요인을 파악하고 피하며, 필요 시 항히스타민제를 사용하고, 수의사 상담.'),
("새", "다리", '진드기 감염', '피부에 발진, 심한 가려움증, 털 빠짐이 발생함.', '/images/disease/새 진드기.jpg', '진드기 제거제를 사용하여 감염된 부위를 치료하고 정기적으로 예방 약을 사용.'); 

#--토끼
INSERT INTO disease (animal_name, body_part, name, symptoms, image_url, solution) VALUES
("토끼", "머리", '치주염', '입에서 악취가 나고, 잇몸이 붉어지며, 식욕이 감소하는 경우가 많음.', '/images/disease/토끼 치주염.jpg', '정기적인 치아 관리 및 수의사 상담, 필요시 치아 청소.'),
("토끼", "머리", '안구 감염', '눈물이 과도하게 나고, 눈 주위가 충혈되며, 눈을 자주 깜빡임.', '/images/disease/토끼 안구 감염.jpg', '안약 처방, 수의사 진료 후 적절한 치료 시행.'),
("토끼", "머리", '귀 진드기 감염', '귀를 자주 긁거나 털을 빠트리며, 귀에서 불쾌한 냄새가 나는 경우.', '/images/disease/토끼 귀 진드기 감염.jpg', '귀 청소 후, 진드기 제거제를 사용하고, 필요시 수의사 진료.'),
("토끼", "머리", '피부 감염', '가려움증, 발진, 피부의 염증이 나타나고 털이 빠질 수 있음.', '/images/disease/토끼 피부 감염.jpg', '항생제 사용 및 피부 청결 유지, 수의사 진료 필수.'),
("토끼", "목", '소화기 장애', '설사나 변비가 반복되며, 식사 후 구토가 발생할 수 있음.', '/images/disease/토끼 소화기 장애.jpg', '식이 요법으로 섬유질을 늘리고, 수의사 상담 후 치료.'),
("토끼", "목", '비만증', '체중 증가, 활동량 감소, 배가 불룩해짐.', '/images/disease/토끼 비만.jpg', '운동을 늘리고, 식단 조절 및 수의사와 상담하여 계획 수립.'),
("토끼", "목", '신장 질환', '소변량의 변화, 갈증이 심해지고 체중 감소가 발생할 수 있음.', '/images/disease/토끼 신장 질환.jpg', '수의사 진료 후 약물 치료 및 식이 요법 진행.'),
("토끼", "목", '호흡기 감염', '기침, 재채기, 호흡이 힘들어 보이며, 발열이 동반될 수 있음.', '/images/disease/토끼 호흡기 감염.jpg', '수의사 진료 및 약물 치료, 환경 관리 중요.'),
("토끼", "몸통", '발바닥 염증', '발바닥이 부풀어 오르고, 통증으로 인해 보행이 불편함.', '/images/disease/토끼 발바닥 염증.jpg', '정기적인 발 관리 및 수의사 방문, 필요한 경우 치료.'),
("토끼", "몸통", '슬개골 탈구', '다리를 절거나 불편해하는 모습, 뒷다리를 들어올리는 경향이 보임.', '/images/disease/토끼 슬개골 탈구.jpg', '수술이나 물리치료 필요, 수의사 진료 필수.'),
("토끼", "몸통", '관절염', '움직임이 느려지고, 특정 자세를 취하기 어려움, 통증 호소.', '/images/disease/토끼 관절염.jpg', '통증 완화제를 사용하고, 체중 관리 및 운동 조절.'),
("토끼", "몸통", '골절', '다리를 사용하지 못하고 통증을 느끼며, 부종이 나타남.', '/images/disease/토끼 골절.jpg', '수의사 진료 및 치료, 필요한 경우 수술적 처치.'),
("토끼", "다리", '벼룩 감염', '가려움증이 심하고 털이 빠짐, 피부 자극이 나타날 수 있음.', '/images/disease/토끼 벼룩 감염.jpg', '벼룩 치료제를 사용하고, 환경 청결 유지.'),
("토끼", "다리", '피부염', '발진이 생기고 가려움증이 동반되며, 피부가 붉어질 수 있음.', '/images/disease/토끼 피부염.jpg', '약물 치료 및 환경 관리, 수의사 상담 필요.'),
("토끼", "다리", '탈모증', '부위별로 털이 빠지며, 가려움증이나 염증이 발생할 수 있음.', '/images/disease/토끼 탈모증.jpg', '원인에 따라 치료를 진행하며, 수의사 진료가 필요함.'),
("토끼", "다리", '곰팡이 감염', '털이 빠지거나 발진이 생기며, 가려움증이 심해질 수 있음.', '/images/disease/토끼 곰팡이 감염.jpg', '항진균제 사용과 함께 환경 관리 및 수의사 진료 필요.'),
("토끼", "털", '털 빠짐 증후군', '특정 부위에서 털이 빠지며, 피부가 드러남, 가려움증 동반 가능.', '/images/disease/토끼 털 빠짐 증후군.jpg', '수의사 진료를 통해 원인 파악 후 영양 상태 개선 및 스트레스 요인 감소.'),
("토끼", "털", '피부염', '발진, 피부가 붉어지고, 가려움증이 동반되며, 특정 부위에 상처가 생길 수 있음.', '/images/disease/토끼 피부염.jpg', '감염 부위 청결 유지 및 수의사 진료, 필요시 약물 치료 진행.'),
("토끼", "털", '진드기 감염', '가려움증, 털 빠짐, 피부 발진이 발생하며, 불편함을 호소함.', '/images/disease/토끼 진드기 감염.jpg', '진드기 제거제를 사용하여 감염된 부위를 치료하고, 정기적인 예방 조치를 통해 재발 방지.'),
("토끼", "털", '비듬', '털에 비듬이 생기고 피부 가려움증이 심해지며, 털이 빠질 수 있음.', '/images/disease/토끼 비듬.jpg', '수의사에게 비듬 전용 샴푸 처방받기, 건강한 식단 유지 및 청결한 환경 유지.');

#--햄스터
INSERT INTO disease (animal_name, body_part, name, symptoms, image_url, solution) VALUES
("햄스터", "머리", '피부염', '가려움증, 발진, 피부가 붉어짐, 물어뜯는 행동', '/images/disease/햄스터 피부염.jpg', '수의사 진료 및 약물 치료. 환경 청결 유지.'),
("햄스터", "머리", '안구 감염', '눈 주위 부풀어 오름, 분비물, 눈물 증가', '/images/disease/햄스터 안구 감염.jpg', '수의사 진료. 감염 부위 청결 유지. 필요시 안약 사용.'),
("햄스터", "머리", '호흡기 감염', '기침, 재채기, 숨을 쉴 때 소음, 식욕 감소', '/images/disease/햄스터 호흡기 감염.jpg', '수의사 진료 및 약물 치료. 따뜻한 환경 유지.'),
("햄스터", "머리", '치아 문제', '식욕 감소, 턱 문제, 씹기 어려움, 침 흘림', '/images/disease/햄스터 치아 문제.jpg', '치아 정비 및 정기적인 체크업. 식이 요법 조정.'),
("햄스터", "목", '소화기 문제', '설사, 변비, 식욕 변화, 복부 팽만감', '/images/disease/햄스터 소화기 문제.jpg', '식이 요법. 수의사 진료. 건강한 사료 제공.'),
("햄스터", "목", '비만', '체중 증가, 활동 감소, 호흡 문제', '/images/disease/햄스터 비만.jpg', '운동 및 식단 조절. 저칼로리 간식 제공. 정기적인 운동 시간 계획.'),
("햄스터", "목", '근육 질병', '운동 능력 감소, 힘이 없는 모습, 짧은 숨', '/images/disease/햄스터 근육질병.jpg', '수의사 진료 및 치료. 적절한 운동 제공.'),
("햄스터", "목", '신경 질환', '비정상적인 행동, 경련, 불안정한 걷기', '/images/disease/햄스터 신경 질환.jpg', '수의사 진료. 환경 변화 및 스트레스 관리.'),
("햄스터", "몸통", '슬개골 탈구', '다리를 절거나 불편해하는 모습, 다리 사용에 어려움', '/images/disease/햄스터 슬개골 탈구.jpg', '수술 또는 물리치료. 부드러운 침대 제공.'),
("햄스터", "몸통", '관절염', '움직임이 느려짐, 통증, 뚜렷한 불편함', '/images/disease/햄스터 관절염.jpg', '통증 완화제 및 물리치료. 적절한 운동 및 영양 공급.'),
("햄스터", "몸통", '발톱 문제', '발톱이 부러지거나 패인 경우, 통증', '/images/disease/햄스터 발톱 문제.jpg', '정기적인 발톱 관리. 필요시 수의사 방문.'),
("햄스터", "몸통", '골절', '다리 사용 불가, 통증, 부종', '/images/disease/햄스터 골절.jpg', '수의사 진료 및 치료. 안정된 환경 제공.'),
("햄스터", "다리", '벼룩 감염', '가려움증, 털 빠짐, 피부 발진', '/images/disease/햄스터 벼룩 감염.jpg', '벼룩 치료제 사용. 청결한 환경 유지. 정기적인 예방 조치.'),
("햄스터", "다리", '피부염', '발진, 가려움증, 염증, 피부가 붉어짐', '/images/disease/햄스터 다리.jpg', '약물 치료 및 환경 관리. 피부 상태 모니터링.'),
("햄스터", "다리", '탈모증', '부위별 털 빠짐, 피부가 노출됨, 가려움증', '/images/disease/햄스터 탈모증.jpg', '원인에 따른 치료. 스트레스 관리. 수의사 상담.'),
("햄스터", "다리", '곰팡이 감염', '털 빠짐, 발진, 피부 벗겨짐', '/images/disease/햄스터 곰팡이 감염.jpg', '항진균제 사용. 환경 청결 유지.'),
("햄스터", "털", '털 빠짐', '특정 부위에서 털이 빠짐, 피부가 노출됨', '/images/disease/햄스터 털 빠짐.jpg', '수의사 진료를 통해 원인 파악. 영양 상태 개선. 스트레스 요인 줄이기.'),
("햄스터", "털", '비듬', '털에서 비듬이 떨어짐, 가려움증, 피부가 건조해짐', '/images/disease/햄스터 비듬.jpg', '비듬 전용 샴푸 사용. 적절한 영양 섭취로 피부 건강 증진.'),
("햄스터", "털", '진드기 감염', '가려움증, 털 빠짐, 피부 발진', '/images/disease/햄스터 진드기 감염.jpg', '진드기 제거제를 사용하여 감염된 부위를 치료. 정기적인 예방 조치를 통해 재발 방지.'),
("햄스터", "털", '털 엉킴', '털이 엉키고 뭉쳐짐, 피부 자극', '/images/disease/햄스터 털 엉킴.jpg', '정기적인 털 관리와 빗질로 엉킴 방지. 필요한 경우 털을 정리해 주는 것이 좋음.');