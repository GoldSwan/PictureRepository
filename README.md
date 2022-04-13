# Picture Repository(사진 저장소 웹 플랫폼)

- 내가 원하는 사진을 언제 어디서든 저장하고 공유할 수 있는 플랫폼이 있으면 좋겠다고 생각하여 개발하게 된 프로젝트입니다.

## 프로젝트 목표

- 이미지 포스팅, 공유 소설 네트워크인 핀터레스트를 모티브로 하여 사진저장소 플랫폼 [Picture Repository] 구현
- 반응형 웹서비스 구현
- 비동기 통신 웹서비스 구현

## **TO DO LIST**

자세한 TO DO LIST는 [여기](/docs/TO-DO-LIST.md)를 참조하세요.

## 데모(Demo)

[로그인 화면]

![login-demo-1.gif](/docs/images/login-demo-1.gif)
 - 로그인을 성공할 경우 [홈 화면]으로 이동합니다.
 - 로그인을 실패할 경우 에러메세지가 발생합니다. 로그인을 실패해도 이전 입력값이 사라지지 않습니다.
 - 회원가입 버튼을 누르면 [회원가입 화면]으로 이동합니다.

[회원가입 화면], [회원가입 완료 화면]

![join-demo-1.gif](/docs/images/join-demo-1.gif)

[회원가입 화면]

 - 아이디, 이메일, 비밀번호를 입력하면 유효성을 체크하여 유효하지 않을 경우 에러메시지를 출력합니다.
 - 회원가입 버튼을 눌러도 동일하게 유효성을 체크합니다.
 - 회원가입이 성공적으로 이루어지면 [화원가입 완료 화면]으로 이동합니다.
- 로그인 화면 이동 버튼을 누르면 [로그인 화면]으로 이동합니다.

[회원가입 완료 화면]

 - [로그인 화면]으로 이동하여 로그인을 진행할 수 있습니다.

[사진 업로드 화면]

![upload-demo-1.gif](/docs/images/upload-demo-1.gif)
 - 사진, 내용을 게시판에 업로드할 수 있습니다.
 - 태그를 추가할 수 있습니다.

[사용자 사진 조회 화면]

![search-demo-1.gif](/docs/images/search-demo-1.gif)
 - 사진이 첨부된 게시판을 제목, 내용, 태그로 검색할 수 있습니다.
 - 페이징 처리 기능이 구현되었습니다. (하단 네비게이션 바)

## 기능개선 / 성능개선 / 버그수정 REPORT

[기능개선]

- [x]  부트스트랩을 활용하여 사진 업로드 화면 UI 개선
- [x]  다중으로 파일이 업로드 되도록 개선
- [x]  상수 값을 .propertes 파일로 따로 관리할 수 있도록 개선
- [x]  모바일 환경에서도 홈화면 header부분 UI가 최적화 될 수 있도록 개선 - 반응형 네비게이션 바로 변경
- [ ]  사진 렌더링 기능 구현 - 스크롤을 내리면 아래 공간에 사진이 추가되어 계속 보여지도록 구현
- [ ]  검색 시 제목, 제목+내용, 태그를 콤보 박스로 선택 후 검색할 수 있도록 개선
- [x]  HOME 화면 상단 네비게이션 UI 개선
- [ ]  HOME 화면 상단 네비게이션 기능 개선- [나의 갤러리 화면], [게시글 관리 화면], [사용자 정보 관리 화면] 으로 이동하는 버튼 추가
- [ ]  [사진 업로드 화면] 기능 개선 - 태그 입력 후 엔터키를 눌렀을 때 form 제출되는 것이 아닌 태그만 추가되도록 개선

[성능개선]

- [ ]  검색 성능 개선 - 페이징 처리, 인덱스 재구성을 통한 성능 최적화

[버그수정]

- [x]  파일 업로드 후 이동한 페이지에서 새로고침하면 다시 업로드 되는 현상 발견 > redirect 방식으로 이동하여 해결
- [x]  javascript에서 빈 값의 EL 표현식 에러 개선 > 할당 변수에 ' 작은 따옴표를 양쪽에 달아 해결

## HTTP API Docs

|Method|URL|설명|
|:---|:---|:---|
|GET|/bulletinboards|게시글 전체 조회|
|GET|/bulletinboards/{bulletinId}|특정 게시글 조회|
|GET|/bulletinboards/{searchtype}|타입별(제목, 제목+내용, 태그) 게시글 검색 조회|
|POST|/bulletinboards/newbulletinboard|게시글 생성|
|GET|/bulletinboards/newbulletinboard/{bulletinId}|게시글 수정 화면 조회|
|POST|/bulletinboards/newbulletinboard/{bulletinId}|게시글 수정|
|DELETE|/bulletinboards/newbulletinboard/{bulletinId}|게시글 삭제|

## **사용 기술**

- [Front-End]
    - CSS3
    - HTML5
    - Javascript ES6
    - jQuery 2.2.1
    - Bootstrap 4.4.1

- [Back-End]
    - Java 1.8
    - Spring Framework 4.3.12
    - MySQL 5.7.19

## **도입 예정 기술**

- REST API
- AWS

## **개발 환경**

- OpenJDK 1.8
- Tomcat 8.5
- Maven
- Chrome 브라우저 최적화
