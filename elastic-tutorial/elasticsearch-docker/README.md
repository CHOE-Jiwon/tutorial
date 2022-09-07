## 1. Docker Elasticsearch Image 준비 
---
로컬에 직접 엘라스틱서치를 설치하지 않고, 도커를 사용하여 튜토리얼을 진행할 것이다.

.
### 1. Pulling the Image

기본적으로 로컬에 Docker가 설치되어 있어야 한다.

```bash
$ docker pull docker.elastic.co/elasticsearch/elasticsearch:7.6.2
```

.

### 2. Starting a single node cluster with Docker

가져온 이미지를 가지고 컨테이너를 실행시켜보자.

```bash
$ docker run -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.6.2
```

엘라스틱서치 클러스터를 개발할 때나 테스트할 때는 single-node discovery 옵션을 준다.

문서에 따르면, 해당 모드로 컨테이너를 띄울 경우, 노드는 자기 자신을 마스터로 선출(elect)하고 다른 노드가 있는 클러스터에 참여하지 않는다. [2]

.

### 3. 동작 테스트

http://localhost:9200/ 으로 접속해보거나, 아래와 같이 명령어를 날려보자. (해당 명령어는 MAC에서 사용한 명령어이다.)
```bash
$ curl -X GET 'http://localhost:9200/'

...결과...
{
  "name" : "78fe422d8d5b",
  "cluster_name" : "docker-cluster",
  "cluster_uuid" : "CVv1_C_JTTC8g6XEljt40A",
  "version" : {
    "number" : "7.6.2",
    "build_flavor" : "default",
    "build_type" : "docker",
    "build_hash" : "ef48eb35cf30adf4db14086e8aabd07ef6fb113f",
    "build_date" : "2020-03-26T06:34:37.794943Z",
    "build_snapshot" : false,
    "lucene_version" : "8.4.0",
    "minimum_wire_compatibility_version" : "6.8.0",
    "minimum_index_compatibility_version" : "6.0.0-beta1"
  },
  "tagline" : "You Know, for Search"
}
```

.

.

## 2. Tutorial
---
엘라스틱서치 설치 및 동작 테스트가 끝났으니, 기본적인 기능들을 사용해보도록 하자.

.

### 1. Elasticsearch Index 생성
Indexing이란 엘라스틱서치에 데이터를 추가하는 과정이다. 엘라스틱서치에 추가되는 데이터들은 **_Apache Lucene_** 의 indexes라는 곳에 저장되기 때문에 Indexing이라 불린다.
(엘라스틱서치는 데이터를 저장하고 검색할 때 Apache Lucene을 사용한다.)

기본적으로 엘라스틱서치 대부분의 기능은 REST API처럼 동작하기 때문에 데이터를 추가하기 위해 `POST`나 `PUT` API를 사용할 수 있다.


특정 id를 가진 data를 추가하거나 추가할 data의 id를 알 때는 `PUT` 을 사용하고, data에 대한 id를 생성하고 싶을 때는 `POST`를 사용하면 된다.


이를 확인하기 위해 아래 명령어를 쳐보자.


**1. 데이터에 대해 id를 생성하는 POST 명령어**
```bash
$ curl -X POST 'localhost:9200/logs/my_app' -H 'Content-Type: application/json' -d '{
  "timestamp": "2022-09-07 14:45:52",
  "message": "Hello World POST",
  "user_id": 4,
  "admin": false
}'

...결과...
{"_index":"logs","_type":"my_app","_id":"jgF9FoMBJGoCDMReL-v2","_version":1,"result":"created","_shards":{"total":2,"successful":1,"failed":0},"_seq_no":0,"_primary_term":1}%
```

**2. id를 알고 있는 데이터를 추가하는 명령어**
```bash
$ curl -X PUT 'localhost:9200/app/users/4' -H 'Content-Type: application/json' -d '{
  "id": 4,
  "username": "jiwon",
  "last_login": "2022-09-07 14:49:22"
}'

...결과...
{"_index":"app","_type":"users","_id":"4","_version":1,"result":"created","_shards":{"total":2,"successful":1,"failed":0},"_seq_no":0,"_primary_term":1}
```


위 명령어를 사용하면서 인덱스를 따로 설정하지 않았지만, Beats shippers (Filebeat, Metricbeat, ...) 또는 Logstash를 사용하여 Elasticsearch에 데이터가 밀어넣어질 경우, 알아서 적절한 인덱스가 생성된다.


**3. 존재하는 인덱스의 리스트를 조회하는 명령어**
```bash
$ curl -X GET 'localhost:9200/_cat/indices?v&pretty'

...결과...
health status index uuid                   pri rep docs.count docs.deleted store.size pri.store.size
yellow open   app   dXJq2cI_QPOlxwsfg_wYNQ   1   1          1            0      4.4kb          4.4kb
yellow open   logs  w9q2vRPlTc-ejj3rmBwKGg   1   1          1            0      4.8kb          4.8kb
```

참고로 `v` 와 `pretty` 옵션은 출력을 잘 파악하기 위한 수단이다. 필수값이 아니다.
.

.

## 2. Elasticsearch Querying
---


우리가 엘라스틱서치를 사용하는 가장 큰 이유 중 하나가 바로, 대용량 데이터를 역색인을 활용하여 빠르게 찾고 분석할 수 있다는 것이다.

이번에는 데이터를 검색하는 쿼리를 날려보도록 하자.

### 1. id를 사용하여 원하는 데이터 검색
```bash
$ curl -X GET 'localhost:9200/app/users/4?pretty'

...결과...
{
  "_index" : "app",
  "_type" : "users",
  "_id" : "4",
  "_version" : 1,
  "_seq_no" : 0,
  "_primary_term" : 1,
  "found" : true,
  "_source" : {
    "id" : 4,
    "username" : "jiwon",
    "last_login" : "2022-09-07 14:49:22"
  }
}
```

_로 시작하는 필드들은 결과의 메타 필드들이다. `_source` 필드는 인덱싱된 데이터 원본이다.

API의 엔드포인트로 `_search` 를 사용해서도 검색이 가능하다.

.

### 2. _search를 사용하여 검색 [5]
``` bash
$ curl -X GET 'localhost:9200/_search?q=POST&pretty'

...결과...
{
  "took" : 2,
  "timed_out" : false,
  "_shards" : {
    "total" : 2,
    "successful" : 2,
    "skipped" : 0,
    "failed" : 0
  },
  "hits" : {
    "total" : {
      "value" : 1,
      "relation" : "eq"
    },
    "max_score" : 0.2876821,
    "hits" : [
      {
        "_index" : "logs",
        "_type" : "my_app",
        "_id" : "jgF9FoMBJGoCDMReL-v2",
        "_score" : 0.2876821,
        "_source" : {
          "timestamp" : "2022-09-07 14:45:52",
          "message" : "Hello World POST",
          "user_id" : 4,
          "admin" : false
        }
      }
    ]
  }
}
```

결과에 여러 필드들이 있는데, 이는 검색에 대한 내용 및 검색 결과에 대한 내용을 담고 있다.

자세한 내용은 [5] 문서를 참고하면 된다.

.

위에서 해본 검색은 흔히 **_URI Search_** 라고 하며, 엘라스틱서치에서 가장 간단하게 쿼리를 날릴 수 있는 방법이다.

index를 특정지은 후 id를 사용하여 검색하거나, _search를 사용하여 내가 찾고 싶은 단어만 설정하면 그 단어를 가진 데이터들이 출력되는 것을 확인할 수 있었다. (예시에서는 POST에 대한 검색 결과로 하나의 document만 나왔으나, message에 "시리얼은 역시 POST"라는 내용을 넣고 데이터를 밀어 넣는다면, 해당 document도 나올 것이다.)

.

Lucene query들을 활용하면 더 구체적인 쿼리 사용이 가능하다.
1. `username:jiwon` : username 필드가 "jiwon" 인 document들을 찾는다.
2. `jiwon*` : "jiwon"으로 시작하는 단어를 포함한 document들을 찾는다. "jiwon1"도 나올 수 있고, "jiwonGood" 도 나올 수 있다.
3. `jiwon?` : 위와 비슷하나, "jiwon" 용어 자체를 포함한 document는 제외된다.

.

.

## 2. Elasticsearch Query DSL

위에서 URI 검색을 진행해보았다. 이는 매우 간단한 검색이었으니, 이제는 Query DSL을 통해 request body search라는 좀 더 고급진 검색을 해보자.

> Elasticsearch에서는 Query를 정의하기 위해 JSON기반의 full Query DSL(Domain Specific Language) 기능을 제공한다. Query DSL을 2가지 절로 이루어진, 쿼리의 AST(Abstract Syntax Tree)라고 생각하자.
> - Leaf query clauses: `match`, `term`, `range` 쿼리와 같이 특정 value나 특정 field를 찾는데 사용된다.
> - Compound query clauses: 다른 leaf 또는 compound 쿼리들을 감싸고, 여러개의 쿼리를 논리적 방식(logical fashion)으로 결합하거나 동작을 변경(alter their behaviour)하는데 사용된다.
> .
> 쿼리절은 query context나 filter context에서 어떻게 사용되는지에 따라 다르게 동작한다.

.

### 1. Elasticsearch Query Types
엘라스틱서치에는 수많은 종류의 검색 쿼리가 존재한다. 이 쿼리들을 조합하거나 다른 옵션으로 매치하거나 해서 원하는 결과를 얻을 수 있다.

다음은 엘라스틱에 포함된 쿼리 타입들이다.

1. Geo queries
2. "More like this" queries
3. Scripted queries
4. Full text queries
5. Shape queries
6. Span queries
7. Term-level queries
8. Specialized queries


일반적으로 DSL의 쿼리절 filter context에서 document를 필터와 매치하여 "Y/N" 으로 필터링하는 것은 빠르지만, query context는 문서와 얼마나 일치하는지를 나타내는 `relevance score` 와 같은 계산도 가능하다. filter는 relevance score를 사용하지 않는다. 
_(이 relevance는 결과로 보여줄 document들의 순서와 inclusion을 결정한다.)_


**1. query context를 사용한 DSL 쿼리**
```bash
$ curl -X GET 'localhost:9200/logs/_search?pretty' -H 'Content-Type: application/json' -d '{
  "query": {
    "match_phrase": {
      "message": "Hello World POST"
    }
  }
}'

...결과...
{
  "took" : 11,
  "timed_out" : false,
  "_shards" : {
    "total" : 1,
    "successful" : 1,
    "skipped" : 0,
    "failed" : 0
  },
  "hits" : {
    "total" : {
      "value" : 1,
      "relation" : "eq"
    },
    "max_score" : 0.8630463,
    "hits" : [
      {
        "_index" : "logs",
        "_type" : "my_app",
        "_id" : "jgF9FoMBJGoCDMReL-v2",
        "_score" : 0.8630463,
        "_source" : {
          "timestamp" : "2022-09-07 14:45:52",
          "message" : "Hello World POST",
          "user_id" : 4,
          "admin" : false
        }
      }
    ]
  }
}
```

.

.

.

## 3. Removing Elasticsearch Data
이제까지 데이터를 추가하고, 조회해보았다. 마지막으로는 데이터 삭제에 대해 간단하게 해볼 것인데, 조회, 추가와 같이 매우 간단한 작업이다.

다른 점이 있다면, HTTP Method가 DELETE인 점이다!

**1. 데이터 삭제**
```bash
$ curl -X DELETE 'localhost:9200/app/users/4?pretty'

...결과...
{
  "_index" : "app",
  "_type" : "users",
  "_id" : "4",
  "_version" : 2,
  "result" : "deleted",
  "_shards" : {
    "total" : 2,
    "successful" : 1,
    "failed" : 0
  },
  "_seq_no" : 1,
  "_primary_term" : 1
}
```

`_shards.successful` 을 보면 삭제가 잘 된것 같으니 한번 조회를 해보자.

```bash
$ curl -X GET 'localhost:9200/app/users/4'

...결과...
{"_index":"app","_type":"users","_id":"4","found":false}
```

없다! 잘 삭제된 것 같지만 불안하니까 다른 조회 방법으로 다시 조회해본다.

```bash
$ curl -X GET 'localhost:9200/_search?q=username:jiwon'

...결과...
{"took":2,"timed_out":false,"_shards":{"total":2,"successful":2,"skipped":0,"failed":0},"hits":{"total":{"value":0,"relation":"eq"},"max_score":null,"hits":[]}}
```

이번에도 데이터가 없는 것을 확인할 수 있었다. 이로써 데이터가 잘 삭제된 것을 확신할 수 있다.

.

**2. 인덱스 삭제**

```bash
$ curl -X DELETE 'localhost:9200/app?pretty'

...결과...
{
  "acknowledged" : true
}
```

ack가 true로 뜬 것을 보니 인덱스 삭제 행위가 잘 이루어진 것 같지만 다시 한 번 확인해보자.

```bash
$ curl -X GET 'localhost:9200/cat/indicies?v&pretty'

...결과...
health status index uuid                   pri rep docs.count docs.deleted store.size pri.store.size
yellow open   logs  w9q2vRPlTc-ejj3rmBwKGg   1   1          1            0      4.8kb          4.8kb
```

app이라는 index name을 가진 인덱스가 없는 것을 확인할 수 있다.

이로써 인덱스 삭제까지 완료하였다.

.

.

여기까지 도커를 사용하여 엘라스틱서치를 설치하고, 엘라스틱서치에서 CURD 처럼 간단한 명령들을 경험해보았다.

.

.

.



## 참고
[1] [Elasticsearch Guide - Install Elasticsearch with Docker](https://www.elastic.co/guide/en/elasticsearch/reference/7.6/docker.html)

[2] [Elasticsearch on DEV - Single-node discovery](https://www.elastic.co/guide/en/elasticsearch/reference/7.6/bootstrap-checks.html#single-node-discovery)

[3] [Elasticsearch-tutorial](https://logz.io/blog/elasticsearch-tutorial/)

[4] [cat Indicies](https://www.elastic.co/guide/en/elasticsearch/reference/7.6/cat-indices.html#cat-indices)

[5] [search API](https://www.elastic.co/guide/en/elasticsearch/reference/7.6/search-search.html)

[6] [Elasticsearch - QueryDSL](https://www.elastic.co/guide/en/elasticsearch/reference/7.6/query-dsl.html)