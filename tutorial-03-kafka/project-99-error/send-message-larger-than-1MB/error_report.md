# 2023-02-03 Kafka Connector Error

## 1. 에러 발생

---
![slack_image](./스크린샷%202023-02-03%20오후%201.24.46.png)

Grafana Alert 알림 메세지 옴.

```
{"state":"FAILED","trace":"org.apache.kafka.connect.errors.ConnectException: Unrecoverable exception from producer send callback
    at org.apache.kafka.connect.runtime.WorkerSourceTask.maybeThrowProducerSendException(WorkerSourceTask.java:312)
    at org.apache.kafka.connect.runtime.WorkerSourceTask.prepareToSendRecord(WorkerSourceTask.java:126)
    at org.apache.kafka.connect.runtime.AbstractWorkerSourceTask.sendRecords(AbstractWorkerSourceTask.java:395)
    at org.apache.kafka.connect.runtime.AbstractWorkerSourceTask.execute(AbstractWorkerSourceTask.java:354)
    at org.apache.kafka.connect.runtime.WorkerTask.doRun(WorkerTask.java:189)
    at org.apache.kafka.connect.runtime.WorkerTask.run(WorkerTask.java:244)
    at org.apache.kafka.connect.runtime.AbstractWorkerSourceTask.run(AbstractWorkerSourceTask.java:72)
    at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)
    at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
    at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
    at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
    at java.base/java.lang.Thread.run(Thread.java:829)
Caused by: org.apache.kafka.common.errors.RecordTooLargeException: The message is 1125872 bytes when serialized which is larger than 1048576, which is the value of the max.request.size configuration.
","worker_id":"kafka-connect-2:8083","generation":79}
```

<br/>

## 상황 파악

---

### 1. 카프카 커넥트 상태 확인
```bash
$ sudo docker ps | grep kafka-conenct-2
> 49a4ac173ec1        confluentinc/cp-kafka-connect-base:7.3.0   "bash -c 'echo \"In..."   9 days ago          Up 9 days (healthy)   0.0.0.0:8083->8083/tcp, 9092/tcp, 0.0.0.0:9091->7072/tcp   kafka-connect-2
```

> Kafka-Connect는 정상적으로 떠있는 상태.

<br/>

### 2. Debezium 커넥터 상태 확인
```bash
$ curl localhost:8083/connectors/cdc-source/status
> {"name":"cdc-source","connector":{"state":"RUNNING","worker_id":"kafka-connect-2:8083"},"tasks":[{"id":0,"state":"FAILED","worker_id":"kafka-connect-2:8083","trace":"org.apache.kafka.connect.errors.ConnectException: Unrecoverable exception from producer send callback\n\tat org.apache.kafka.connect.runtime.WorkerSourceTask.maybeThrowProducerSendException(WorkerSourceTask.java:312)\n\tat org.apache.kafka.connect.runtime.WorkerSourceTask.prepareToSendRecord(WorkerSourceTask.java:126)\n\tat org.apache.kafka.connect.runtime.AbstractWorkerSourceTask.sendRecords(AbstractWorkerSourceTask.java:395)\n\tat org.apache.kafka.connect.runtime.AbstractWorkerSourceTask.execute(AbstractWorkerSourceTask.java:354)\n\tat org.apache.kafka.connect.runtime.WorkerTask.doRun(WorkerTask.java:189)\n\tat org.apache.kafka.connect.runtime.WorkerTask.run(WorkerTask.java:244)\n\tat org.apache.kafka.connect.runtime.AbstractWorkerSourceTask.run(AbstractWorkerSourceTask.java:72)\n\tat java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)\n\tat java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)\n\tat java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)\n\tat java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)\n\tat java.base/java.lang.Thread.run(Thread.java:829)\nCaused by: org.apache.kafka.common.errors.RecordTooLargeException: The message is 1125872 bytes when serialized which is larger than 1048576, which is the value of the max.request.size configuration.\n"}],"type":"source"}
``` 

> 문제는 `cdc-source` 커넥터에서 발생한 것. (`cdc-ads-source` 는 정상 작동하는 것으로 보여짐.)
> connector의 state는 `RUNNING`이지만, task의 state는 `FAILED`
> 
> 즉, Debezium-Connector인 cdc-source의 task가 동작하지 않고 있는 상황

<br/>


### 3. Kafka Topic Message 확인

cdc-source 커넥터가 바라보고 있는 테이블들에 대해 CDC Log가 쌓이고 있는지 확인. 

<br/>

kafdrop 에서 `XXXXXX` 토픽에 쌓이는 메세지들을 확인해보았는데, 2023-02-03 02:05:01 (+09:00) 이후로 쌓이는 메세지가 없음. 
나머지 테이블들도 마찬가지. 

<br/>

> 즉, cdc-source의 Task가 동작을 하지 않고 있어서 그와 연관된 테이블들의 CDC 로그가 수집되고 있지 않는 상황

<br/>

### 4. docker logs 확인

```bash
$ sudo docker logs kafka-connect-2
...
[2023-02-03 02:05:02,285] ERROR [cdc-source|task-0] WorkerSourceTask{id=cdc-source-0} failed to send record to AAAA.BBBB.CCCCC:  (org.apache.kafka.connect.runtime.AbstractWorkerSourceTask:406)
org.apache.kafka.common.errors.RecordTooLargeException: The message is 1125872 bytes when serialized which is larger than 1048576, which is the value of the max.request.size configuration.
[2023-02-03 02:05:07,328] ERROR [cdc-source|task-0] WorkerSourceTask{id=cdc-source-0} Task threw an uncaught and unrecoverable exception. Task is being killed and will not recover until manually restarted (org.apache.kafka.connect.runtime.WorkerTask:196)
...
```

> 즉, XXXXX 테이블에서 record를 send하지 못하였고, 그 원인은 `max.request.size`인 1MB 이상 메세지를 보내려고 한 것.

<br/>
<br/>

## 해결

---
<br/>

### 0. 참고

[[Kafka 운영]1MB 이상의 메세지를 보낼 때 고려해볼만 한 설정들](https://always-kimkim.tistory.com/entry/kafka-operations-settings-concerned-when-the-message-has-more-than-1-mb)

1. 카프카는 기본적으로 1MB 이상의 큰 메세지를 고려하고 만들어진 메세지 시스템이 아니다.
2. 1MB 이상의 메세지 전달이 가능하도록 파이프라인을 구성하기 위해서는 아래 설정을 바꿔야 한다.
   1. 프로듀서 설정: 프로듀서가 브로커에게 한 번 요청할 때 보낼 수 있는 최대 크기
   2. 토픽 설정: 레코드 배치(단일 요청)의 최대 크기


### 1. Kafka Producer의 max.request.size 수정

[Debezium Faq](https://debezium.io/documentation/faq/)
![](스크린샷%202023-02-03%20오후%202.48.32.png)


```bash
# Kafka connect 내리기
$ sudo docker stop kafka-connect-2

# producer.max.request.size 수정
(docker-compose.yaml)
...
CONNECT_PRODUCER_MAX_REQUEST_SIZE=2097152
...

# Kafka connect 다시 실행
$ sudo docker-compose up -d

# Kafka connect log 확인
$ sudo docker logs -f kafka-connect-2
```

(참고 문서에서는 max.request.size를 설정하라고 되어 있는데, 해당 값을 바꿔도 아무런 변화가 없었다.)

Docker를 다시 올리고 나서 2~3분(더 걸릴 수도 있음) 뒤에 Connector가 작동을 시작한다. 

<br/>

***docker에 제대로 반영이 되었나 확인***
```bash
$ sudo docker exec -it kafka-connect-2 /bin/bash
$ cd /etc/kafka-connect
$ cat kafka-connect.properties
> ...
producer.max.request.size=2097152
...
```

위 설정파일을 보면 반영은 제대로 되어 있음.

오후 05:07에 배포가 되었고, 오후 05:11분에 아래와 같은 에러가 발생. 

*에러메세지가 바뀜*
```
org.apache.kafka.connect.errors.ConnectException: Unrecoverable exception from producer send callback
    at org.apache.kafka.connect.runtime.WorkerSourceTask.maybeThrowProducerSendException(WorkerSourceTask.java:312)
    at org.apache.kafka.connect.runtime.WorkerSourceTask.prepareToSendRecord(WorkerSourceTask.java:126)
    at org.apache.kafka.connect.runtime.AbstractWorkerSourceTask.sendRecords(AbstractWorkerSourceTask.java:395)
    at org.apache.kafka.connect.runtime.AbstractWorkerSourceTask.execute(AbstractWorkerSourceTask.java:354)
    at org.apache.kafka.connect.runtime.WorkerTask.doRun(WorkerTask.java:189)
    at org.apache.kafka.connect.runtime.WorkerTask.run(WorkerTask.java:244)
    at org.apache.kafka.connect.runtime.AbstractWorkerSourceTask.run(AbstractWorkerSourceTask.java:72)
    at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)
    at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
    at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
    at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
    at java.base/java.lang.Thread.run(Thread.java:829)
Caused by: org.apache.kafka.common.errors.RecordTooLargeException: The request included a message larger than the max message size the server will accept.
```

> Producer에서 메세지를 보내지 못하는 것은 동일하나, 그 이유가 바뀜. 
> -> 서버(카프카 브로커)에서 받을 수 있는 메세지의 용량보다 메세지가 큼. 

<br/>

---
### 잠깐!
해당 에러를 마주쳤을 시점에 CDC 데이터가 쌓이고 있었음. 

이제와서 생각해보면, 저 시점에 쌓이던 데이터들은 1MB가 넘지 않는 데이터여서 문제가 없었던 것 같다. 

그리고 나서 다시 1MB 이상의 메세지를 보내려고 하니 다시 에러가 발생하고 CDC 데이터가 쌓이지 않은 것 같다.

---

<br/>

### 2. Kafka Broker Topic의 message.max.bytes 변경

문제가 발생한 토픽은 XXXXXXX 이므로, `AAAA.BBBB.CCCCC` 토픽에 대해 설정 값을 바꾼다.

```bash
kafka-configs.sh --bootstrap-server kafka:19092 --entity-type topics --entity-name AAAA.BBBB.CCCCC --alter --add-config message.max.bytes=2097152
```

<br/>

### 3. CDC 데이터 잘 쌓이는지 확인

1. 1 MB 이상의 데이터 임의로 밀어넣기 (dev.BBBB.CCCCC 에서 테스트.)
   1. 이상 X
2. 운영 환경 CDC 데이터 잘 쌓이나 확인
   1. 이상 X

<br/>

## 기억합시다.

---

### ts_ms와 source.ts_ms

```sql
select
SUBSTR(CAST(DATETIME(TIMESTAMP_MILLIS(CAST(jsonPayload.ts_ms AS INT64)),'Asia/Seoul') AS STRING), 0, 13),
COUNT(*)
from QQQQQQ.WWWWWW
WHERE DATE(timestamp) >= "2023-02-02"
GROUP BY 1
ORDER BY 1;
```

데이터를 복구한 다음, ODS_CDC_TABLE의 로그들을 시간단위로 집계하여 봤더니 중간 데이터가 빵꾸나있음... 

그러나 위 쿼리에서는 ts_ms를 바라보고 있기 때문에 데이터가 빵꾸났다고 판단해서는 안됨.

[Debezium transaction metadata](https://debezium.io/documentation/reference/stable/connectors/mysql.html#mysql-transaction-metadata)
1. ts_ms: Debezium이 해당 트랜잭션을 처리한 시간
2. source.ts_ms: DB에서 변경이 이루어진 시간

이에 따라 SQL을 변경하여 다시 결과를 살펴보면, 데이터는 빵꾸난 것이 아님을 알 수 있다.
```sql
select
SUBSTR(CAST(DATETIME(TIMESTAMP_MILLIS(CAST(jsonPayload.source.ts_ms AS INT64)),'Asia/Seoul') AS STRING), 0, 13),
COUNT(*)
from QQQQQQ.WWWWWW
WHERE DATE(timestamp) >= "2023-02-02"
GROUP BY 1
ORDER BY 1;
```