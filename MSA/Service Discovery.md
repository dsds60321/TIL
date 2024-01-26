# Service Discovery
## eureka server
1. Spring Cloud Netflix Eureka
   1. key와 value 형태로 서버를 등록한다.
   2. 클라이언트는 로드 밸런스 혹은 api gatewway가 servicediscovery는 클라이언트 서버가 갈 위치를 응답해주어 클라이언트는 자신이 찾아가야할 서버에 요청을 보내게 된다.
![유레카 서버 동작 화면.png](img%2F%EC%9C%A0%EB%A0%88%EC%B9%B4%20%EC%84%9C%EB%B2%84%20%EB%8F%99%EC%9E%91%20%ED%99%94%EB%A9%B4.png)
* @EnableEurekaServer : Service Discovery 서버로 실행하게다는 어노테이션 입니다.
```java
@SpringBootApplication
@EnableEurekaServer
public class EcoomerceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcoomerceApplication.class, args);
    }
}
```
* register-with-eureka, fetch-registry :  Service Discovery 서버에 등록하는 설정 discovery 서버는 자신은 등록할 필요가 없기에 false 설정한다. default(true)
```yaml
eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```

* 실행 화면 
* 유레카 대시보드 : Instance currently에 instance란 유레카서버에 등록된 서버를 의미합니다.
![유레카 대시보드.png](img%2F%EC%9C%A0%EB%A0%88%EC%B9%B4%20%EB%8C%80%EC%8B%9C%EB%B3%B4%EB%93%9C.png)
---
## eureka client
* client가 될 서버 즉, 서비스를 실행할 서버는 service discovery를 등록해준다
```groovy
implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-client'
```
* @EnableDiscoveryClient : 어노테이션은 마이크로서비스가 서비스 디스커버리 엔진에 등록되는 것을 활성화하는 역할을 합니다.
* yml 또는 properties 파일에 해당 서비스 디스커버리 서버의 주소를 설정해야 합니다.
```java
@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}

```
* fetch-registry= eureka 서버로부터 인스턴스들의 정보를 주기적으로 가져올지에 대한 설정,
  * true -> 갱신 된 정보를 받겠다는 설정
* service-url: defaultZone : 디스커버리 서버의 주소 등록
```yaml
eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://127.0.0.1:8761/eureka
```
* 클라이언트 등록시 유레카 서버 대시보드에 등록이 된다 up 상태면 동작중인 상태이다.
![유레카 클라이언트 서버 등록 대시보드.png](img%2F%EC%9C%A0%EB%A0%88%EC%B9%B4%20%ED%81%B4%EB%9D%BC%EC%9D%B4%EC%96%B8%ED%8A%B8%20%EC%84%9C%EB%B2%84%20%EB%93%B1%EB%A1%9D%20%EB%8C%80%EC%8B%9C%EB%B3%B4%EB%93%9C.png)
* 여러 서비스를 다른 포트로 올리려고 할때 매번 포트를 바꾸고 실행하는건 불편하기에 랜덤포트를 사용한다.
  * port : 0 random port를 사용하겟다는 의미
* randomPort를 사용하면 서버가 여러개 띄어줘 잇어도 유레카 서버 대시보드에 0번 포트로만 잡히기에 1개에 인스턴스만 띄어져잇는걸로 보인다.
* 아래와 같은 방법으로 인스턴스 아이디를 랜덤으로 부여해 여러개에 인스턴스가 나오게 할 수 있다.
```yaml
eureka:
  instance:
    instance-id: ${spring.cloud.client.hostname}:${spring.application.instance_id:${random.value}}
```
~~~yaml
server:
  port: 0
~~~