# ⏱ 타임딜 플랫폼 대여 서비스 ⏱

- **터지지 않는 타임딜 플랫폼 : DDIB**
- **기업에게 서버를 대여해주는 플랫폼 : BIDD**

## 👨‍👩‍👧‍👦 프로젝트 기간 및 팀원

- 팀명 : 쳇바퀴
- 개발 기간 : 2024.04.08 ~ 2024.05.20 (6주)

<table>
  <tr>
    <td align="center" width="33%">
      <img src="https://avatars.githubusercontent.com/kn9012" width="150px;" alt="김유나"/>
    </td>
    <td align="center" width="33%">
      <img src="https://avatars.githubusercontent.com/kimyusan" width="150px;" alt="김유산"/>
    </td>
   <td align="center" width="33%">
      <img src="https://avatars.githubusercontent.com/gnoesnooj" width="150px;" alt="예준성 (팀장)"/>
    </td>
  </tr>
  <tr>    
    <td align="center">
      <a href="https://github.com/kn9012">
        <div>김유나</div>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/kimyusan">
        <div>김유산</div>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/gnoesnooj">
        <div>예준성 (팀장)</div>
      </a>
    </td>
  </tr>
  <tr>    
    <td align="center">
     <b>BackEnd</b>
    </td>
    <td align="center">
      <b>BackEnd, Infra</b>
    </td>
    <td align="center">
      <b>BackEnd</b>
    </td>
  </tr>
   <tr>    
    <td>
     <b>[회원 서버 개발]</b><br>- Auth : Oauth2를 이용한 카카오 소셜로그인 구현<br>- Spring Security + JWT를 활용하여 토큰 검증을 통한 인증 및 인가<br>- 자주 접근하는 토큰 저장 위해 Redis 사용<br><br><b>[API Gateway & Eureka 서버 개발]</b><br>- 1차 개발 시 API Gateway 활용하여 인증/인가 처리 및 라우팅 처리<br>
    </td>
    <td>
      <b>[Kubernetes 설계]</b><br>- SVC를 통한 MSA 설계 및 구축<br>- 인그라스 컨트롤러 설정<br>- Helm을 이용한 상태관리<br><br><b>[Prometheus + Grafana]</b><br>- 메트릭 데이터를 수집해 시스템 모니터링 구축<br>
    </td>
    <td>
      <b>[상품 서버 개발]</b><br>- Spring Batch를 통한 상품 상태 관리 시스템 구현<br>- 스케줄링을 통해 알림 시스템 구현<br><br><b>[EFK 로깅 시스템]</b><br>- Logback 과 EFK 를 활용한 로깅 시스템 구축<br><br><b>[Tx 추적 시스템]</b><br>- Micrometer와 Sleuth 활용한 분산 트랜잭션 추적 시스템 구축<br>
    </td>
  </tr>
 
  <tr>
    <td align="center">
      <img src="https://avatars.githubusercontent.com/tpwls101" width="150px;" alt="유세진"/>
    </td>
    <td align="center">
      <img src="https://avatars.githubusercontent.com/mkwwd" width="150px;" alt="조자영"/>
    </td>
   <td align="center">
      <img src="https://avatars.githubusercontent.com/tneserp" width="150px;" alt="한재현"/>
    </td>
  </tr>
  <tr>    
    <td align="center">
      <a href="https://github.com/tpwls101">
        <div>유세진</div>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/mkwwd">
        <div>조자영</div>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/tneserp">
        <div>한재현</div>
      </a>
    </td>
  </tr>
  <tr>    
    <td align="center">
     <b>BackEnd 리더</b>
    </td>
    <td align="center">
      <b>FrontEnd 리더</b>
    </td>
    <td align="center">
      <b>BackEnd</b>
    </td>
  </tr>
   <tr>    
    <td>
     <b>[결제 서버 개발]</b><br>- 카카오페이 결제 및 주문 관련 API 구현<br>- 카카오페이 결제 시스템 비동기 처리 및 테스트 코드를 통한 성능 향상 확인<br>- Redisson을 활용한 재고 동시성 제어 구현 및 테스트 코드를 통한 적용 전후 테스트<br><br><b>[부하테스트]</b><br>- JMeter를 사용하여 테스트 시나리오 작성 및 부하테스트 진행<br>
    </td>
    <td>
      <b>[프론트엔드 설계 및 개발]</b><br>- Next.js를 이용하여 SSL방식 사용<br>- Recoil을 사용하여 Client에서의 데이터를 관리<br>- react-query를 사용하여 Server에서의 데이터를 관리하고 데이터의 변경이 없으면 호출없이 데이터를 사용하도록 구현<br>
    </td>
    <td>
      <b>[대기열 시스템 개발]</b><br>- Spring Webflux를 이용한 대기열 서버 구축<br>- Reactive Redis를 통한 사용자
선착순 입장 보장<br><br><b>[부하테스트]</b><br>- Artillery, Jmeter를 사용하여 부하테스트 진행<br>
    </td>
  </tr>
</table>

<br>

## ✏ 개요

 주문 폭주로 인한 서버 다운을 경험하신 적이 있나요? 만반의 준비를 했음에도 불구하고 대용량 트래픽을 견디지 못해 고객님들께 사과의 말씀을 전하신 적이 있나요?

 할인 이벤트를 위한 서버 유지비가 부담되는 중소기업들을 위해 타임딜 이벤트 대여 플랫폼인 DDIB과 BIDD를 개발했습니다.

 우리나라 중소기업의 비율은 무려 99%에 달하며, 중소기업의 온라인 매출 판매액은 약 80%에 달합니다. 온라인 시장이 커져가는 만큼, 기업들은 고가의 연예인 광고비에 힘을 쓰는 대신 네고왕처럼 온라인상에서 할인 이벤트를 진행함으로써 기업을 홍보하고 있으며 이는 소비자들에게 큰 호응을 얻고 있습니다. 기업은 수수료 없이 서버 대여비만 지불함으로써 대용량 트래픽에도 견딜 수 있는 환경을 구축할 수 있고, 소비자 입장에서는 같은 상품일지라도 더욱 저렴하게 구매할 수 있다는 장점이 있습니다. 지금 바로 BIDD에서 서버를 대여하고, 터지지 않는 타임딜 플랫폼인 DDIB을 만나러 가볼까요?

<br>

## 🖥️ 개발 환경

### **💻 IDE**

- IntelliJ IDEA `2024.1`
- Visual Studio Code



### **🔧** BackEnd

- Oracle JDK `17`
- Spring Boot `3.2.4`
- Spring Cloud Netflix Eureka `4.1.1`
- Spring Cloud Gateway `4.1.2`
- Spring Cloud Openfeign `4.1.1`
- Spring Data JPA `3.2.4`
- Spring Webflux `3.2.4`
- Spring Batch
- Spring Security `6.2.3`
- OAuth2 `9.43.3`
- JWT `0.12.3`
- Logback
- Micrometer
- Swagger

### **🎨** FrontEnd

- Next.js
- React.js
- Typescript
- SCSS
- Zustand
- React Query
- node.js `10.5.0`

### 🗑 Database

- MySQL `8.3.0`
- Redis
    - Redisson `3.29.0`
    - Reactive Redis `3.2.4`
- AWS S3 `2.2.6`

### **🚀** Infra

- AWS EC2
- Google Cloud Platform
- MobaXterm `24.1`
- Kubernetes `1.28.9`
- Nginx `1.18.0 (Ubuntu)`
- Jenkins `2.456`
- Docker `26.1.0`
- Docker-Compose `1.28.2`
- Zipkin

### 🏁 Test

- JMeter `5.6.3`
- Artilery

### 👀 Monitoring

- Prometheus
- Grafana

### 🎈 Logging

- Elasticsearch
- Fluentd
- Kibana

### **🤝** Communication Tools

- Git
- Jira
- Notion
- Mattermost

<br>

## ⚒ 서비스 기능 소개

### BIDD (기업)

**1️⃣ 기업 신청**

기업 회원은 상품을 등록하기 위해 기업 신청을 할 수 있다.

![기업신청](https://github.com/gnoesnooj/ddib/assets/91658692/b5fdff5e-0109-475c-af87-013d480df80e)


**2️⃣ 상품 등록**

기업 회원은 타임딜 이벤트 진행을 원하는 날짜 및 시간대를 선택해 상품을 등록할 수 있다.

![상품_등록](https://github.com/gnoesnooj/ddib/assets/91658692/5f6685eb-6dcc-4bbe-b40a-c62618e9f132)


**3️⃣ 판매내역 조회**

기업회원은 등록한 상품에 대한 판매내역을 조회할 수 있다.

![판매내역_조회](https://github.com/gnoesnooj/ddib/assets/91658692/8385b73b-dc75-4292-9759-beda679ed861)


### **DDIB (소비자)**

**1️⃣ 타임딜 목록 조회**

사용자는 오늘의 타임딜과 주간 타임딜, 카테고리별 타임딜 이벤트를 조회할 수 있다.

![image](https://github.com/gnoesnooj/ddib/assets/91658692/ab13c684-8f0a-4cc5-b864-4e6925236bd7)


**2️⃣ 검색**

사용자는 원하는 타임딜 이벤트를 검색할 수 있다.

![상품_검색](https://github.com/gnoesnooj/ddib/assets/91658692/e726681e-aa94-4adf-b1b6-e8158a5501d3)


**3️⃣ 상품 상세 조회**

사용자는 상품에 대한 상세 정보 및 해당 기업에 대한 정보를 조회할 수 있다.

![image](https://github.com/gnoesnooj/ddib/assets/91658692/29f8add1-4feb-44a5-b011-f605706217fe)


**4️⃣ 상품 좋아요/취소**

상품 개별 좋아요를 누르면 해당 상품에 대한 이벤트 시작 12시간 전, 1시간 전에 알림을 받을 수 있다.

![상품_좋아요와_취소](https://github.com/gnoesnooj/ddib/assets/91658692/8e12c811-f3a1-4894-8d60-0353f256a004)


**5️⃣ 대기열 입장**

입장한 순서대로 대기열에서 순번을 가지게 되고, 새로고침 시 마지막 순번으로 들어가게 된다.

![대기열](https://github.com/gnoesnooj/ddib/assets/91658692/ec3040ca-ea20-49e3-8a71-0f7261576066)


**6️⃣ 주문/결제하기**

사용자는 주문하고자 하는 상품과 수량, 가격을 확인하고 배송지 정보를 입력한다. QR 코드를 통해 카카오페이로 결제를 진행한다.

![주문결제](https://github.com/gnoesnooj/ddib/assets/91658692/b08be317-b144-45ef-a1c4-f4441e2e03bd)


**7️⃣ 마이페이지 - 주문내역 조회**

사용자는 본인이 주문한 모든 주문내역에 대한 정보를 조회할 수 있다.

![주문내역_조회](https://github.com/gnoesnooj/ddib/assets/91658692/b3c39a8f-265e-46ab-bbbc-d619319f76ff)


**8️⃣ 마이페이지 - 환불**

사용자는 구매를 취소하여 환불을 받을 수 있다.

![환불](https://github.com/gnoesnooj/ddib/assets/91658692/b0ebbb4c-6fdb-4257-850a-98a33d56e036)


**9️⃣ 마이페이지 - 회원 정보 조회**

사용자는 마이페이지에서 기본 배송지로 사용할 주소를 저장할 수 있다.

![회원_정보_조회](https://github.com/gnoesnooj/ddib/assets/91658692/d854e931-5f09-40b6-b0ed-872f77a7b1e4)


**🔟 마이페이지 - 키워드 알림 신청/취소**

사용자는 키워드별로 알림을 신청해 해당 카테고리의 상품이 등록되면 알림을 받을 수 있다.

![키워드_알림_신청_및_취소](https://github.com/gnoesnooj/ddib/assets/91658692/771d3dc2-5c3c-4465-8df5-e1f96cb4348c)


**1️⃣1️⃣ 마이페이지 - 위시리스트**

사용자는 좋아요를 누른 상품 목록을 조회할 수 있다.
![image](https://github.com/gnoesnooj/ddib/assets/91658692/789dcdd6-cb0f-4a2b-b153-dc9e1bb9e410)



<br>

## 🔥 주요 기술 소개

### 1. MSA (MicroService Architecture)

저희 팀은 프로젝트 주제에 적합한 즉, 대용량 트래픽을 감당할 수 있는 아키텍처에 대해 고민하게 되었습니다. 그 결과, 기존의 Monolithic 구조 대신 MSA 구조를 선정하게 되었습니다.

기존 하나의 서버로 운영되던 환경에서는 대용량 트래픽 발생에 따라 생길 수 있는 모든 문제가 SPOF 문제로 직결되었고, 이는 MSA 적용을 통해 해결할 수 있었습니다.

<br>

### 2. Spring Webflux와 Redis를 활용한 대기열 시스템

대기열 구축을 위해 Spring Webflux 와 Redis를 활용하였습니다.

Spring Webflux는 Reactive 프로그래밍 모델로서, 논 블로킹(Non-blocking) 및 비동기(Async)로 대량의 요청을 동시에 처리해야 할 때 유리합니다.

Redis의 인-메모리 성능뿐만 아니라 Zset을 사용하여 우선순위를 쉽게 관리하였으며 Reactive에 맞추어 제공되는 redis-reative 라이브러리를 사용했습니다. K-V-S는 다음과 같이 사용했습니다.

- `key` : queue (대기열)
- `value` : userId (유저 아이디)
- `score` : time (입장시간)

다음은 Spring Webflux와 Redis를 사용한 대기열 시스템의 시퀀스 다이어그램입니다.

![image](https://github.com/gnoesnooj/ddib/assets/91658692/031f643b-5669-4eea-a700-fdf156762de5)

<br>

### 3. 비동기 처리

결제 시스템에 비동기 처리를 적용함으로써 성능 향상을 확인할 수 있었습니다. 사용자가 100명, 200명, 500명, 1000명일 때 각각 동기, 비동기 방식으로 응답시간을 테스트 해 본 결과 **성능이 약 6배 향상**된 것을 확인했습니다.

아래는 사용자가 1000명일 때 동기, 비동기 방식에 따른 응답시간입니다.

- 동기 방식
    
    ![동기방식](https://github.com/gnoesnooj/ddib/assets/91658692/e590bc5b-37eb-4628-8c05-216812f1dd9c)

    
    ⇒ 10개의 표본 평균 값 : `82.872초`
    
- 비동기 방식
    
    ![비동기방식](https://github.com/gnoesnooj/ddib/assets/91658692/24d7ef50-d165-463d-942f-77d704e98b94)

    
    ⇒ 10개의 표본 평균 값 : `13.681초`
    
<br>

### 4. Redisson을 활용한 재고 동시성 제어

동시에 여러 사용자가 상품을 구매할 경우, 재고량을 초과해도 상품이 구매되는 문제가 발생할 수 있습니다. 이를 방지하기 위해 동시성 제어를 구현했습니다.

사용 기술 스택으로는 Redis의 Redisson 구현체를 사용했습니다. MySQL은 적당한 수준의 트래픽은 감당할 수 있지만, 대용량 트래픽을 목표로 프로젝트를 진행하고 있기 때문에 초기 구축 비용과 관리 비용이 발생하더라도 더 우수한 성능을 제공하는 Redis를 사용하기로 결정했습니다.

또한, Lettue를 사용하면 스핀 락 방식으로 계속해서 락 획득을 시도하지만, Redisson을 사용하면 `pub-sub` 방식으로 Redis에 부하를 줄일 수 있기에 대용량 트래픽에 적합하다 생각해 사용하였습니다. 다음은 pub-sub 방식에 의한 락 획득 방식입니다.

![레디슨](https://github.com/gnoesnooj/ddib/assets/91658692/f67418d9-9d01-48d7-815a-30fbfea9ed14)


<br>

### 5. Spring Batch

상품 상태 관리를 위해 Spring Batch를 활용하였습니다. 해당 상품의 타임딜이 종료되는 시간에 맞춰 배치 작업이 실행되고, 종료된 상품에 맞게 상태를 업데이트합니다.

또한 Spring Batch는 각 상품을 위시리스트에 담은 회원들을 위해 해당 상품의 타임딜 오픈 24시간, 1시간 전에 알림을 전송하는 기능에 사용됩니다. 이를 통해 원하는 상품의 타임딜을 놓치지 않을 수 있도록 해줍니다.

<br>

### 6. Logging System - EFK

![efk](https://github.com/gnoesnooj/ddib/assets/91658692/918590a4-3275-4728-971b-7d767c7ad16d)


MSA 기반에서는 모든 로그를 각각의 서버에 접속하여 확인해야 하는 어려움이 있기에, 이를 한 곳에서 확인할 수 있도록 EFK 로깅 시스템을 구축했습니다.

Docker Compose를 통해 Elastic Search, Fluentd, Kibana를 구동하고, Logback 설정을 통해 Fluentd로 로그를 수집하며, Elastic Search에 저장한 후 Kibana를 통해 로그를 시각화합니다.

<br>

### 7. Tracing Tx - Micrometer & Zipkin

MSA 환경에서 하나의 서버가 다른 서버를 호출하는 경우가 많기에 하나의 요청이 어떤 트랜잭션의 흐름을 가지고 실행되는지 확인하기 어려운 경우가 많습니다. 따라서 Tx 추적을 위해 Micrometer와 Zipkin을 통해 로그 수집 및 트랜잭션 추적을 구현하였습니다.

로그를 수집한다는 점에서 EFK 와 성격이 유사하지만, EFK는 모든 Logging Level에 대한 수집이 가능하고 Micrometer와 Zipkin은 하나의 Rest API 요청이 발생했을 때 실행되는 모든 메소드와 트랜잭션에 대한 추적을 가능하게 합니다. 따라서 성격이 비슷하지만 사용되는 목적에서 좀 더 각자만의 분명한 이유가 있습니다.

<br>

### 8. Kubernetes

컨테이너들을 효율적으로 관리하기 위해 쿠버네티스를 도입했습니다. 먼저 대용량 트래픽으로 인한 서버 부하가 오지 않도록 로드 밸런싱을 구축하였습니다. 또한 HorizontalPodAutoscaler를 사용하여 CPU 사용률을 확인해 일정량 이상이 되면 오토 스케일링이 되도록 하였습니다. 쿠버네티스는 아래와 같은 장점이 있습니다.

1. 자동화된 배포 및 확장
    - HPA를 사용하여 애플리케이션 배포와 확장을 자동화함으로써 관리 부담을 줄일 수 있습니다.
    - 필요에 따라 자동으로 파드를 추가하거나 제거할 수 있습니다.
2. 서비스 복원력
    - 장애가 발생한 파드를 자동으로 재시작하거나 원하는 상태로 자동 복구할 수 있습니다.
    - 지속적인 애플리케이션 가용성을 보장합니다.
3. 효율적인 리소스 관리
    - 리소스 요청 및 제한을 설정하여 클러스터 내에서 효율적인 리소스 분배와 사용을 보장할 수 있습니다.
    - 클러스터 내 모든 애플리케이션에 대해 리소스 사용을 최적화합니다.

<br>

### 9. Jmeter를 사용한 부하테스트

대용량 트래픽을 유발하고 그에 따른 성능을 테스트하기 위해 JMeter를 사용했습니다. 예시로 API에 10초 동안 10,000개의 요청(1초에 1,000개)을 보내 테스트합니다. 대기열에 부하를 걸어 아래와 같이 요청이 들어왔음을 확인할 수 있습니다. Nginx에서 초당 1,000개의 요청만 받을 수 있기 때문에 초당 1,000개 이상의 요청을 보내면 에러율이 생길 수 있습니다.

- 요청에 대한 Summary Report 확인
    
    ![jmeter](https://github.com/gnoesnooj/ddib/assets/91658692/e56c4105-85fb-43a1-8cc0-c83ec52a59eb)

    
    ⇒ 10,000개의 요청에 대한 에러율 : 0.00%
    
    ⇒ 초당 처리율 : 907.9/sec
    
- 대기열에 부하를 걸었을 때의 화면 확인
    
    ![대기열](https://github.com/gnoesnooj/ddib/assets/91658692/c8551e50-3e70-4852-aacc-4e4c3d682167)

    
<br>

### 10. Prometheus와 Grafana를 이용한 자원 관리

Prometheus로 메트릭 데이터를 수집해 Grafana로 시각화합니다. Grafana로 파드의 CPU 메모리 사용량이나 생성된 파드들을 확인할 수 있습니다.

![bandicam_2024-05-20_04-09-41-551](https://github.com/gnoesnooj/ddib/assets/91658692/eff8757c-8ada-4adc-8b49-d9bd7d570a8b)


<br>

## 💡 프로젝트 진행

### Git

형상 관리 시스템으로, 프로젝트의 소스 코드 버전 관리를 위해 Git을 사용했습니다.

문제가 발생했을 때, 소스 코드의 변경 사항을 추적하여 쉽게 해결할 수 있었습니다. 또한, 다양한 브랜치를 생성하여 병합 및 테스트를 수행할 수 있으며, 이를 통해 다양한 기능을 동시에 개발하고 안정성을 유지할 수 있었습니다.

### Jira

Agile 방법론을 기반으로 한 프로젝트 관리 도구로서 Jira를 이용했습니다. 

매주 월요일 오전 회의에서 한 주 동안 진행되어야 할 주 단위 계획을 짜고, 스프린트를 만들어 등록했고, 이는 각 작업에 대한 할당, 우선 순위 지정, 작업 상태 업데이트 등을 통해 프로젝트의 진행 상황을 파악하기 용이했습니다.

### Notion

효율적인 문서화 도구로서 Notion을 이용했습니다.

필요한 기술 스택에 대한 자료 링크, 참고자료, 요구사항 정의서, 아이디어 등을 모아 관리했습니다. 그리고 항상 모든 회의 및 피드백은 기록으로 남겨두어서 잘 반영할 수 있도록 하였습니다. 깃과 지라의 컨벤션 규칙, 점검 사항 등도 Notion에 기록하여 모두가 항시 확인할 수 있도록 관리했습니다.

### Scrum Meeting

매일 아침 9시에 어제 했던 일, 오늘 할 일, 이슈를 각자 정리해서 5~10분 동안 서로 공유하는 시간을 가졌습니다. 이를 통해 모든 팀원들이 프로젝트의 전반적인 상태를 이해함으로써 필요한 조치를 취할 수 있었고, 자신의 작업을 공유하고 문제점이나 진전 사항을 토론할 수 있었습니다.

<br>

## 🔍 ERD

![erd](https://github.com/gnoesnooj/ddib/assets/91658692/8ba3473f-ba2f-463f-8499-f0e5753dcf29)


<br>

## 📉 시스템 아키텍처

### 1️⃣차 개발

1차로 MSA 구조에 Spring Cloud Gateway와 Eureka를 활용하여 개발하였습니다.

![아키텍처](https://github.com/gnoesnooj/ddib/assets/91658692/496fc44f-d241-41bf-9438-7066db9dfd3a)


### 2️⃣차 개발

추후 쿠버네티스를 도입하여 2차 개발을 진행하였습니다.

![k8s아키](https://github.com/gnoesnooj/ddib/assets/91658692/8fd2c09b-f0cd-4c3b-ac55-69635547373c)

