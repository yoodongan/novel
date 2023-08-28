# 웹소설 서비스 REST API
## ERD 설계 
![](https://i.imgur.com/UxbY30n.png)
## REST API 설계

➡️ [API 명세](https://hushed-bite-bb4.notion.site/10674566d0754dd08666da7a2b8ff188?v=e6318d3b059f48e1a7c732ff7ac42d7f)

## 진행 기간
1차 : 2023.04.06 ~ 2023.05.02      
2차[리팩토링] : 2023.08.23 ~

## 아키텍처
![](https://i.imgur.com/YZ3vdfu.png)

## 시퀀스 다이어그램 (리팩토링 후)
### Spring Security, JWT를 사용한 회원가입 및 로그인
![](https://i.imgur.com/BHFWzgk.png)
### 베스트 선호 작품 목록(캐싱) 외 소설 도메인 관련 기능
![](https://i.imgur.com/hzVxPQi.png)
### 동시성 문제, MyChapter(보유 중인 소설의 Chapter) 관련 기능
![](https://i.imgur.com/7flLnLK.png)


## 프로젝트에서 다룬 내용
프로젝트를 진행하며 고민하고 해결해 본 내용들입니다.     
모든 개발 과정은 다음 링크에서 확인하실 수 있습니다. ➡️ [개발 과정 링크](https://velog.io/@daryu519/series/%EC%9B%B9%EC%86%8C%EC%84%A4-%EC%84%9C%EB%B9%84%EC%8A%A4-%EA%B0%9C%EC%9D%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8)
### 1. 동시성 문제 해결
> 특정 유저가 동일한 포인트 충전 요청을 N 번 시도했을 때, 한 번만 충전되어야 합니다.

위 문제를 해결하기 위해 다음과 같은 사항들을 고려해봤습니다.
- PESSIMISTIC_WRITE (비관적 락)
- Redis의 분산 락
### 2. 캐싱을 통해 조회 성능 향상
> 전체 소설 중 선호도가 가장 높은 순서대로 '베스트 소설'이 조회되어야 합니다.   
> 베스트 소설 목록은 1시간 간격으로 갱신됩니다.

베스트 소설 목록은 모든 유저가 공유하는 내용이고, 크게 바뀌지 않기 때문에 '캐싱'을 고려했습니다.       

### 3. N+1 문제 해결을 위한 페치 조인 적용
> 해당 회원이 ‘선호’하는 소설 목록을 보여줘야 합니다.    
> 응답은 소설의 마지막 화, 회원이 가장 최근에 읽은 회차를 포함해야 합니다.

강의를 통해 들었던 N+1 문제를 직접 마주치고 해결해봤습니다.     
추가적으로 주어진 요구사항(소설의 마지막 화, 최근 읽은 회차)에 대해 어떻게 API에 포함시킬지 고민해봤습니다.

## 기술 스택
- Java 17
- Spring Data JPA
- Spring Security 
- JWT
- MySQL
- Redis
- Spock

## 기여
- Swagger 을 활용한 REST API 명세, ERD Cloud 를 활용한 DB 설계
- Spock 테스트 프레임워크를 활용한 BDD 개발
- Spring Security와 JWT를 사용한 인증-인가 구현
  - JwtProvider클래스에 accessToken 발급, 검증 기능, claim(본문) 얻는 기능 추가
  - Filter를 사용해 요청 당 한 번만 인가가 수행되도록 구현
- 모든 회원들이 사용할 수 있는 베스트 소설 목록 조회 기능을 Redis 를 활용해 성능 개선 (약 37.5배 빠른 속도로 조회)
  - RedisTemplate을 사용해 Dto 직렬화,
    RedisCacheManager에 <String, 조회 결과 Dto> 형태로 캐싱
- 페치 조인을 사용해 조회 성능 개선 (소설과 관련된 N개의 쿼리가 추가적으로 나오는 상황을 단방 쿼리로 변경)
- 동일한 유저가 포인트 충전을 N번 발생시켜도 1번만 충전되도록 동시성 문제 해결
  (Redis의 분산 락인 Redisson 라이브러리 사용)
  - 대용량 데이터 상황을 가정해 락 획득 시 Redis요청에 대한 부하를 줄이기 위해 Redisson 사용
  - Spock 테스트로 중복 충전 동시에 2개 보내 단일 UPDATE 쿼리 문 확인
- 리팩토링 - 도메인 모델 패턴 적용 및 Service에서 ResponseDto를 내려주는 방식으로 변경
 

## 리팩토링
- 메서드 통일 및 도메인 모델 패턴 적용해 객체지향적 설계로 변경
- DB 구조 변경(MyChapter 테이블 추가, Transactions 테이블 추가)
  - Member와 Chapter의 사이를 MyChapter 라는 중간 테이블로 연결
  - 현재 읽고 있는 페이지, 읽음 여부, 결제 여부 등을 저장하기 위해 MyChapter라는 테이블이 필요하다고 판단
  - 포인트 충전, Chapter 거래 내역 등을 저장할 Transactions 테이블 추가
  - Preference(선호 작품) 테이블의 '선호도 점수'를 Novel 테이블로 옮김
  - Novel 테이블에 비즈니스 로직을 구체화 할 필드 4개 추가
- 복잡한 비즈니스 로직 수정
  - 소설의 선호도 점수를 1~5점 매기는 방식에서, '선호 작품 등록'를 수행하면, 소설의 선호도가 1 증가하도록 구현 
  - 총 조회수, 총 챕터수, 선호도 점수 는 자동으로 계산되어 넣어지도록 구현
- 회원 작가 등록 후 소설 등록 시 '권한 체크', Swagger 로 테스트
- 구매한 Chapter를 읽는 readChapter()에서 발생하는 N+1문제 해결을 위해 fetch join 사용 (위에서 설명한 N:1 관계의 페치조인과 동일)
- RedissonClient를 사용해 포인트 충전과 소장권 구매 동시성 문제 해결
- Member, Novel, Chapter 도메인 관련 시퀀스 다이어그램 작성 (readme 참고)

## 아쉬운 점
> 추후 보안할 내용들, 프로젝트를 진행하면서 아쉬웠던 점을 정리했습니다.
- 대용량 데이터 처리에 대한 부분은 시간 관계 상 다루지 못했습니다.
- 실제로 어떻게 작동할지와 관련한 프로파일링 측정 부분을 다루지 못했습니다.