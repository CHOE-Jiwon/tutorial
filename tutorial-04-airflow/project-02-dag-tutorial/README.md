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
4. 오른쪽 시프트 연산자(binary right shift operator) ">>" 는 태스크 간의 의존성을 정의함.


**출처: Data Pipeline with Apache Airflow (제이펍)**