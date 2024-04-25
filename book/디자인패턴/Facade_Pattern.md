# Facade Pattern
* 어떤 기능을 처리하기 위해 복잡한 메서드 사용을 감춰서 단순화 시켜주는 패턴
  * 하나의 동작에 많은 작은 동작이 필요하다면 파사드 패턴을 이용해볼 여지가 있다.
### 장점
1. 외부에서 사용하기 용이
2. 중간 매개체 파사드가 존재하므로 의존성 감소


```java
@RequiredArgsConstructor
@Service
public class SmartHomeFacade {
  private Lights lights;
  private Aircon aircon;
  private Security security;
  private Televeision televeision;


  public void leaveHome() {
    lights.turnOffAll();
    aircon.off();
    security.arm();
  }

  public void arriveHome() {
    lights.turnOnEntrance();
    aircon.on();
    security.disarm();
  }
}
```