# Docker Quick Start in Docker
- https://airflow.apache.org/docs/apache-airflow/2.3.3/start/docker.html

## Quick start
1. 사전 준비
   1. Docker 설치
   2. Docker Compose 설치
   3. Docker Resource 조정 (메모리 최소 4GB)
2. docker-compose.yaml 다운로드
   1. ```curl -LfO 'https://airflow.apache.org/docs/apache-airflow/2.3.3/docker-compose.yaml'```
3. 환경 설정
   1. Airflow user 설정
      1. .env 파일에 ```AIRFLOW_UID=50000``` 추가
   2. database 초기화
      1. docker-compose up airflow-init
4. Airflow 실행
   1. docker-compose up -d
5. Airflow env 접근
   1. CLI를 통한 접근
      1. `airflow-*` 라는 서비스에만 접근 가능.
      2. ```docker-compose run airflow-worker airflow info```
      3. 제일 끝 인자를 info 말고 다른걸로 해도 됨. ```dokcer-compose run airflow-worker airflow -h```
      4. 또는..
      5. ```curl -LfO 'https://airflow.apache.org/docs/apache-airflow/2.3.3/airflow.sh'``` 다운받고 실행시키는 방법도 존재.
   2. UI를 통한 접근
      1. docker-compose.yaml에서 설정했던 webserver의 포트로 접속 (http://localhost:8081)
      2. ID:PW = airflow:airflow
   3. REST API를 통한 접근
      1. ```curl -X GET --user "airflow:airflow" "http://localhost:8081/api/v1/pools"```
      2. ```curl -X GET --user "airflow:airflow" "http://localhost:8081/api/v1/dags"```
6. Docker cleaning up
   1. volume과 DB 데이터, 다운로드받은 이미지까지 모두 삭제
   2. ```docker-compose down --volumes --rmi all```

## Study

### Airflow Minimal Architecture
Client -> Airflow Webserver -> Airflow Database <--> Airflow Scheduler -> Airflow DAGS

1. airflow 사용을 위해 도커에 할당 메모리는 최소 4GB여야 한다. 이상적인 메모리는 8GB
2. docker-compose를 띄울 때 yaml 파일에서 webServer port가 이미 사용중인 포트인지 아닌지 확인.
3. web UI로 airflow 접근은 보기 간편, REST API로 airflow 접근은 속도가 빠른듯..?
4. AIRFLOW_UID: Airflow 컨테이너를 실행할 사용자의 UID (default: 50000)
5. ```docker-compose up -d airflow-init``` 으로 postgres, redis를 띄우고 airflow 초기화 실행
   1. (이거 굳이 안해도 docker-compose.yaml돌리면 걍 됨)
6. 기본 설정 건드리지 않고 내 로컬 dags 폴더 아래 샘플 dag.py 를 넣으니까 web UI에서 확인됨.
   1. x-airflow-common.volumes 설정으로 내 local dags와 docker의 Dags가 연결됨.
7. local의 ./scheduler/YYYY-MM-DD/ 에 보면 dag 파일에 대한 로그가 존재.
8. Airflow-Scheduler는 무엇인가?
   1. DAG 파일을 구문 분석하고 추출된 정보를 저장
   2. 실행할 준비가 된 태스크를 결정하고 이를 대기 상태로 전환
   3. 대기 상태에서 태스크 가져오기 및 실행