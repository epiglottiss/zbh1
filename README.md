# fastlms

### Issue 1. Login History 기능 추가

 commit [9596991](https://github.com/epiglottiss/zbh1/commit/9596991b0453216acc053ba4586b4c6e9ebe86ac)

- Login History Entity, Repository, Service, Controller 추가
- Login History는 회원 당 한 번만 가장 최근 로그인 시점이 기록됨
- WebSecurity에 의해 로그인 성공 시 DB에 로그인 기록 저장 (UserAuthenticationSuccessHandler.java)
- Login History에 기록하는 정보
    - 사용자 이메일
    - 로그인 시간
    - 접속한 ip
    - 접속한 user agent
- 관리자 회원 관리 페이지에서 회원의 마지막 로그인일자 표시하도록 template 수정

---

### Issue 2. Login History 내역 표시 기능 추가
commit [f500636](https://github.com/epiglottiss/zbh1/commit/f50063651c7a8540d965ef51f360f95baa21ffb6)

- 관리자 회원 관리 - 회원 상세 정보 페이지에 로그인 접속 목록 표시 추가 
    - 회원ID로 Login History 검색한 목록을 회원 상세정보 페이지로  전달
- Login History 컨셉 수정
    - 기존 : 회원 당 가장 최근의 로그인 접속 이력만 저장
    - 변경 : 회원의 로그인 기록 전체 저장
- 컨셉 수정에 따라, 한 회원의 로그인 기록 목록 전달하는 API 추가 
- 가장 최근 로그인 기록은 회원의 로그인 기록 중에서 검색하도록 수정
    
---

### Issue 3. 배너 관리 기능 추가
- 미구현 
- 예상 완료일 (1월 27일)

---
### Issue 4. 메인 화면 배너 표시 기능 추가
- 미구현 
- 예상 완료일 (1월 28일)
