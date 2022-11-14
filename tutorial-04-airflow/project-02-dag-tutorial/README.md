# DAG Tutorial


## tutorial


### Jhon's workflow
1. jhon -> Launch Library: Request 5 of Upcoming Rocket Launch data
2. Launch Library -> Local: Save data
3. Local -> Internet: Request Rocket Image
4. Internet -> Local: Save Rocket Image
5. Local -> System Alert: Alert


### Jhon's workflow -> DAG
1. download_launches: jhon -> Launch Library -> Local
2. get_pictures: Local -> Internet -> Local
3. notify: Local -> System Alert


위 DAG를 작성하기 전에, dags 폴더 생성 필요. 

```mkdir dags/```


### Study by writing DAG Code
./dags/download_rocket_launches.py 참고

1. DAG는 모든 workflow의 entrypoint
   1. Operator는 개념적으로 Task의 템플릿이라고 보면 됨 (각 operator는 하나의 task를 수행)
   2. 모든 Operator는 DAG 객체를 참조
   3. 이를 통해 Airflow는 어떤 task가 어떤 DAG에 속하는지 확인
2. ```schedule_interval = None``` : DAG가 자동으로 실행되지 않음을 의미
   1. Airflow Web에서 수동 실행 가능
3. 각 Operator는 하나의 task를 수행하고, 여러개의 Operator가 Airflow의 workflow 또는 DAG를 구성
   1. 서로 독립적으로 실행이 가능
   2. 순서를 정의하여 실행이 가능 => Dependency(의존성)
4. BashOperator 작성: 발사 예정 로켓 정보를 bash command를 통해 받아오기 위해 사용.
   1. ```task_id = "download_launches"``` : Task 이름 설정
   2. ```bash_command = "curl -o /tmp/launches.json '...'"``` : 실행할 bash command
   3. ```dag = dag``` : DAG 변수에 대한 참조
5. PythonOperator 작성: 로켓 정보를 토대로 이미지 다운로드하는 파이썬 함수 실행을 위해 사용
   1. ```def _get_pictures():``` : 실행할 파이썬 함수
      1. ```print(...)``` : Airflow Log에 저장하기 위해 stdout으로 출력
   2. ```get_pictures = PythonOperator( ```: Python 함수 호출을 위해 PythonOpeartor 구체화
      1. ```task_id = "get_pictures"``` : Task 이름 설정
      2. ```python_callable = _get_pictures``` : 실행할 파이썬 함수 지정
      3. ```dag = dag``` : DAG 변수에 대한 참조
6.    오른쪽 시프트 연산자(binary right shift operator) ">>" 는 태스크 간의 의존성을 정의함.
   1. Python에서의 >>는 비트 연산자이나, Airflow에서는 이를 재정의하였음.


### Execute DAG
> 파이썬 환경에서도 Airflow를 실행할 수는 있으나, 여기선 Docker Container를 통해 Airflow를 실행한다.

도커 컨테이너 실행 후, docker_start.sh 실행
```
docker run -it -p 8081:8080 \    # host 8081 port 개방
   -v /Users/Jiwon/Crowdworks/tutorial/tutorial-04-airflow/project-02-dag-tutorial/download_rocket_launches.py:/opt/airflow/dags/download_rocket_launches.py \    # 컨테이너에 DAG 파일 mount
   --entrypoint=/bin/bash \
   --name airflow \
   apache/airflow:2.0.0-python3.8 \        # Airflow 도커 이미지 (책에서는 대문자 Python이지만 소문자로 해야 오류 안남.)
   -c '( \
      airflow db init && \       # 컨테이너에서 메타스토어 초기화
      airflow users create \     # 사용자 만들기
         --username admin \
         --password admin \
         --firstname jiwon \
         --lastname choe \
         --role Admin \
         --email jiwon0929@crowdworks.kr \
   ); \
   airflow webserver & \         # Webserver 시작
   airflow scheduler \           # Scheduler 시작
   '
```

1. localhost:8081 접속
2. admin/admin 로그인
3. DAGS에서 download_rocket_launches 클릭
4. 좌측 상단의 토글 누른 후 우측 상단에 재생 버튼 클릭
5. 그래프 뷰를 보면 각 태스크들의 실행 상태 확인 가능
6. DAG가 완료되면 notify 태스크를 클릭 후 로그 클릭
7. 다운받은 이미지 개수 확인 가능.
8. docker container에서 ```docker exec -it airflow /bin/bash```
9. /tmp/images 에서 다운로드 받은 이미지 확인 가능.


### Schedule Setting
1. dag에서 ```schedule_interval="daily"``` 로 수정 

### Diff between Task and Operator

1. Operator: 단일 작업을 수행하는 역할
   1. BashOperator: Bash Script 실행하는데 사용
   2. PythonOperator: Python 함수를 실행하는데 사용
   3. SimpleHTTPOperator: HTTP 엔드포인트 호출하는데 사용
   4. 등..
2. Airflow 문서나 책에서는 Operator와 Task를 동일하게 봄(사용자 관점에서는 모두 같은 의미)
   1. **Task는** 작업의 올바른 실행을 보장하기 위한 **_Operator의 'Wrapper or Manager'_** 로 볼 수 있음.
   2. Task는 Operator의 상태를 관리하고 사용자에게 상태 변경(시작 또는 완료)을 표시하는 Airflow의 내장 컴포넌트.

DAG[ _Task[Operator]_ + _Task[Opeartor]_ + _Task[Opeartor]_ ]


### 요약
1. Airflow의 워크플로우는 DAG로 표시한다.
2. Operator는 단일 Task를 나타낸다.
3. Airflow는 일반적인 작업과 특정 작업 모두에 대한 Operator의 집합을 포함한다.
4. Airflow UI 는 DAG 구조를 확인하기 위한 그래프를 뷰와 시간 경과에 따른 DAG 상태를 보기 위한 트리 뷰를 제공한다. (2.0.0 버젼 기준)
5. DAG 안에 있는 Task는 어디에 위치하든지 재시작할 수 있다.



**출처: Data Pipeline with Apache Airflow (제이펍)**