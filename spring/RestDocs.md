# REST Docs 사용법
* 테스트 코드를 바탕으로 API 문서화를 할 수 있는 REST Docs를 사용해보자
  [참고](https://docs.spring.io/spring-restdocs/docs/current/reference/htmlsingle/#getting-started)

## 요구사항
1. 자바 17
2. 스프링 프레임워크 6

## 프로젝트 빌드 구성
```groovy
plugins {
	id "org.asciidoctor.jvm.convert" version "3.3.2"
}

configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
	asciidoctorExt (1)
}

dependencies {
	asciidoctorExt 'org.springframework.restdocs:spring-restdocs-asciidoctor' (2)
	testImplementation 'org.springframework.restdocs:spring-restdocs-mockmvc'
}

ext { (3)
    set('snippetsDir', file("build/generated-snippets"))
}

test { 
	outputs.dir snippetsDir (4)
    useJUnitPlatform()
}

asciidoctor(5) {
    (6)
    configurations 'asciidoctorExt'

    (7)
    inputs.dir snippetsDir

    (8)
    sources{
        include("**/index.adoc","**/common/*.adoc")
    }

    (9)
    baseDirFollowsSourceFile()

    (10)
    dependsOn test
}

tasks.register('createDocument', Copy) {
    dependsOn asciidoctor
    (11)
    from file('build/docs/asciidoc')
    into file('src/main/resources/static')
}

build {
    dependsOn createDocument (12)
}

``` 

1. dependencies에 적용한 기능 추가
2. dependencies추가
3. snippetsDir 변수 등록
4. 스니펫 조각 출력될 dir 설정
5. asciidoctor 작업 구성
7. snippetsDir 입력 등록
8. Asciidoctor 파일의 경로를 지정
9. Asciidoctor 파일의 기본 경로 설정
10. 빌드시 테스트 후 asciidoctor 진행 후 설정
11. build/docs/asciidoc 생성된 HTML src/main/resources/static/docs 디렉토리에 복사
12. createDocument 후 build


## 테스트 설정
* RestDocs 설정
