### 1. Pulling the Image
```bash
$ docker pull docker.elastic.co/elasticsearch/elasticsearch:7.6.2
```

### 2. Starting a single node cluster with Docker
```bash
$ docker run -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.6.2
```

엘라스틱서치 클러스터를 개발할 때나 테스트할 때는 single-node discovery 옵션을 준다.

### 3. GET 명령어 날려보기
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

### 참고
1. [Elasticsearch Guide - Install Elasticsearch with Docker](https://www.elastic.co/guide/en/elasticsearch/reference/7.6/docker.html)
2. 