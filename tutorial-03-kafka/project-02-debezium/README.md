# [Debezium connector for MySQL](https://debezium.io/documentation/reference/1.9/connectors/mysql.html)

### MySQL connector configuration example
```
{
    "name": "inventory-connector", 
    "config": {
        "connector.class": "io.debezium.connector.mysql.MySqlConnector", 
        "database.hostname": "192.168.99.100", 
        "database.port": "3306", 
        "database.user": "debezium-user", 
        "database.password": "debezium-user-pw", 
        "database.server.id": "184054", 
        "topic.prefix": "fullfillment", 
        "database.include.list": "inventory", 
        "schema.history.internal.kafka.bootstrap.servers": "kafka:9092", 
        "schema.history.internal.kafka.topic": "schemahistory.fullfillment", 
        "include.schema.changes": "true" 
    }
}
```

1. `name`: 카프카 커넥트 서비스에 등록될 커넥터의 이름
2. `connector.class`: 커넥터 클래스 명 (고정값)
3. `database.hostname`: MySQL 서버 주소
4. `database.port`: 고정값
5. `database.user`: MySQL 서버 로그인 아이디
6. `database.password`: MySQL 서버 로그인 패스워드
7. `database.server.id`: 커넥터의 고유 ID
8. `topic.prefix`: MySQL 서버 또는 클러스터의 topic prefix (debezium 1.9 버젼에서는 `database.server.name` 으로 나온다!)
   1. Debezium이 CDC하는 특정 MySQL DB 서버/클러스터에 대한 네임스페이스를 제공하는 Topic Prefix.
   2. 커넥터에서 내보낸 이벤트를 수신하는 모든 Kafka 토픽 이름의 prefix로 사용되므로, 다른 모든 커넥터에 구분되어 고유해야합니다.
9.  `database.include.list`: 특정 서버에 의해 호스팅되는 데이터베이스 리스트 (내가 CDC 하려는 데이터 베이스)
10. `schema.history.internal.kafka.bootstrap.servers`: 커넥터가 카프카 클러스터와의 연결을 설정하기 위해 사용하는 host/port의 Pair 리스트
    1.  커텍터가 이전에 저장했던 데이터베이스 스키마 기록을 검색하는 데 사용
    2.  원본 데이터베이스에서 읽은 각 DDL 문을 작성하는 데 사용
    3.  각 pair는 kafka connect 프로세스에서 사용하는 동일한 Kafka 클러스터를 가리켜야 함.
11. `schema.history.internal.kafka.topic`: 커넥터가 DB Schema의 히스토리를 저장하게되는 카프카 토픽 name
12. `include.schema.changes`: 

### About `snapshot.mode`
** [참고](https://debezium.io/documentation/reference/1.9/connectors/mysql.html#mysql-property-snapshot-mode)

`snapshot.mode` 는 커넥터의 스냅샷 모드를 설정할 수 있는 property이다. 이 속성 값은 커넥터가 시작되고 나서 초기 데이터베이서의 상태를 어떻게 읽을 것인지를 정의한다.

1. `initial` (default): 커넥터는 시작할 때 전체 데이터를 읽어온다 (op = 'r'). 이후에 발생하는 모든 데이터 변화를 추적한다.
2. `initial_only` : 
3. `never` : 
4. `schema_only` : 
5. `schema_only_recovery` : 
6. `incremental` : 