# 테스트 개발 주도 시작하기
## TDD란?
* 테스트 코드를 먼저 작성하고 작성한 테스트를 통과시키기 위해 구현하는 방식입니다.
---
### 흐름
1. 테스트 pseudo code 작성
2. 테스트를 위한 테스트 코드 작성 컴파일 오류를 없애기 위한 코드 작성(메서드, 클래스...)
3. 테스트
4. 리팩토링
5. 구현코드 추가

### TDD 사이클
1. 레드(Red) : 실패
2. 그린(Green) : 성공
3. 리팩터(Refactor) : 리팩토

### 테스트 순서
1. 테스트 코드를 작성할때 쉬운 케이스 부터 시작해야한다.
2. 예외상황을 먼저 테스트 해야한다.
---
* @BeforEach : 테스트가 동작 전 세팅하는데 역활을 보통 많이 합니다.

* 테스트는 언제 동일한 결과를 보장해야합니다.
* 실행 순서에 영향을

### 테스트 코드 구성 요소 : 상황(given), 실행(when), 결과(then)
* 언제나 상황 혹은 결과가 존재해야 하는건 아납니다.

### 외부 상태가 테스트 결과에 영향을 주면 언된다.
* 테스트는 언제 실행해도 항상 정상적으로 동작해야 합니다.
  <br>외부 상태에 따라 테스트의 성공 여부가 바뀌지 않으려면 테스트 실행 전에 외부를 원하는 상태로 만들거나 테스트
  <br>실행 후에 외부 상태를 원래대로 되돌려 놓아야 합니다.


## 대역
### 외부 요인으로 인한 테스트에 어려움으로 인해 대역을 많이 사용합니다.
| 종류        | 설명                                                                             |
|-----------|--------------------------------------------------------------------------------|
| 스텁 (Stub) | 구현을 단순한 것으로 대체한다. 테스트에 맞게 단순히 원하는 동작을 수행합니다.|
|가짜 (Fake)| 실제 동작하는 구현을 제공합니다. DB 대신 메모리를 이용해 Repository 같은것을 구현한것이 가짜에 해당합니다.|
|스파이(Spy)| 호출된 내역을 기록합니다. 기록한 내용은 테스트 결과를 검증할때 사용하며 스텁이기도 합니다.|
|모의(Mock)| 기대한 대로 상호작용한지 행위를 검증합니다. 기대한대로 동작하지 않는다면 익셉션을 발생시킬수 있습니다. 모의 객체는 스텁이자 스파이도 된다. |


---
## Mockito
* Mockito 대역을 사용할때 자주 사용되는 객체 생성, 검증, 스텁등 지원하는 프레임워크이다.
  * Mockito를 이용하여 항상 값을 값을 리턴하거나 Exception을 발생시킬수 있다.
  * ArgumentMatchers() 클래스를 사용하여 임의값을 항상 리턴하게도 할 수 있다.

### 매칭 처리
| 메서드 명                                         | 설명                    |
|-----------------------------------------------|-----------------------|
| anyInt(), anyShort(), anyLong(), anyBoolean() | 기본 데이터 타입에 대한 임의 값 일치 |
| anyString()                                   | 문자열에 대한 임의값 일치        |
| any()                                         |임의 타입에 대한 일치|
| anyList() ...                                 |콜렉션에 대한 일치|
|matchers()|정규표현식을 이용한 String 값 일치|
|eq()| 특정값 일치 여부|
```java
// 항상 "NUM"이 리턴 됨
    @Test
    void mockTest() {
        GameNumGen genMock = mock(GameNumGen.class);
        // given을 통해 특정 값을 리턴하도록 설정 가능
        given(genMock.generate(any())).willReturn("NUM");

        String num = genMock.generate(GameLevel.EASY);
        assertEquals("123", num);
    }
```

### 행위 검증
* should 후에 동작
* 모의 객체가 기대한 대로 불렀는지 검증하는 코드

|메서드 명|설명|
|-------|---|
|only()|한 번만 호출|
|times(int)| 지정한 횟수만큼 호출|
|never()|호출하지 않음|
|atLeast(int)|적어도 지정한 횟수만큼 호출|
|atLeastOnce()|atLeast(1)과 동일|
|atMost(int)|최대 지정한 횟수만큼 호출|

```java
// 항상 "NUM"이 리턴 됨
    @Test
    void mockTest() {
        GameNumGen genMock = mock(GameNumGen.class);
        Game game= new Game(genMock);
        game.init(GameLevel.EASY);
        
        then(genMock).should().generate(GameLevel.EASY);
        // then(genMock).should(only()).generate(GameLevel.EASY);
    }
```

### 인자 캡쳐
* 객체를 쉽게 검증하기 위해 사용
* 모의 객체의 메서드를 호출할때 전달한 인자가 정확한지 테스트
```java
@Test
void whenRegisterThenMail() {
    userRegister("id", "pw", "email@email.com");

    ArgumentCaptor<String> captor= ArgumentCaptor.forClass(String.class);
    then(mockEmailNotifier)
        .should().sendRegisterEmail(captor.capture());
    
    String realEmail = captor.getValue();
    assertEqual("email@email.com", realEmail);
}
```

### thirdParty등 외부 API를 연동해서 사용등을 통한 경우 대역을 사용하여 테스트를 할 수 있다.
1. 제어하기 힘든 외부 상황을 별도 타입으로 분리
2. 테스트 코드는 별도로 분리한 타입의 대역을 생성
3. 생성한 대역을 테스트의 대상의 생성자 등을 이용해서 전달
4. 대역을 이용한 상황 구성

* WireMockServer를 HTTP서버를 흉내내 외부 api에 대역을 사용 할 수 있다.
1. 실행전 WireMockServer를 시작 -> 실제 HTTP 서버가 뜬다.
2. WireMockServer 동작을 기술한다.
3. HTTP 연동하는 수행 테스트를 실행한다.
4. @AfterEach 싱행 후 WireMockServer를 중지한다.

### 테스트 코드와 유지보수
1. 변수나 필드를 사용해 기대값 표현하지 않기
* 기대값을 표기 할때 변수나 필드를 사용한 경우 오히려 가독성이 떨어질수 있다. 오히려 실제 값을 사용하는게 가독성에 도움이 된다.
2. 두 개 이상을 검증하지 않기
3. 정확하게 일치하는 값으로 몸의 객체 설정하지 않기
4. 과도하게 구현 검증하지 않기
5. 셋업을 이용해서 중복된 상황을 설정하지 않기
  1. @BeforEach 같은 메서드를 통해 셋업을 할 경우 코드가 긴 경우 로직을 번갈아 봐야 하는 어려움이 있으며 셋업에 코드가 변경되면
     <br>테스트가 실패하게 되므로 테스트 별로 설정을 하는게 좋다.
6. 통합테스트에서 데이터 공유 주의하기
7. 통합 테스트의 상황 설정을 위한 보조 클래스 사용하기
8. 실행 환경이 다르다고 실패하지 않기
9. 실행 시점이 다르다고 실패하지 않기
10. 랜덤하게 실패하지 않기
11. 필요하지 않은 값은 설정하지 않기
12. 단위 테스트를 위한 객체 생성 보조 클래스
13. 조건부로 검증하지 않기
14. 통합 테스트는 필요하지 않은 범위까지 연동하지 않기
