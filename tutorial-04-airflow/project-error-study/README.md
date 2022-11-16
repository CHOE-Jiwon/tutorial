# Error Study

## 1. Worker & Operator
---
### 1. 문제 상황
1. 기존 사내에서 튜토리얼을 진행했던 깃 레파지토리의 docker-compose.yaml을 가지고 슬랙 테스트용 댁을 생성
2. 해당 댁에는 PythonOperator로 작동하는 태스크가 존재
3. 해당 댁을 트리거 시키면 오류 발생
   1. ```airflow-worker_1     | [2022-11-16 05:39:47,940: ERROR/ForkPoolWorker-15] [71ef414c-9696-4b2e-9a92-d85420351dbe] Failed to execute task Dag 'slack_test_dag' could not be found; either it does not exist or it failed to parse..```
   2. Worker에서 'slack_test_dag'을 찾지 못했다고 나옴.

### 2. 확인 사항
1. 기존에 존재하던 sample_dag은 작동하는지
   1. -> 제대로 작동함
2. 코드에 오류가 있는지
   1. -> 없음.
3. docker-compose.yaml에 문제가 있는지
   1. airflow-worker 서비스에서 volumes 쪽 코드가 이상
   2. ```
        volumes:
          ../de-etl-batch:/home/airflow/de-etl-batch
      ```

### 3. 해결
1. 내 로컬에 존재하지 않는 디렉토리와 도커가 마운트되고 있었음.
2. 위 마운트 코드를 지우고 다시 컨테이너를 띄운 후 실행하니 오류가 나지 않았.

### 4. 의문점
1. Operator 중 EmptyOperator를 사용하던 sample_dag은 정상적으로 동작을 했었음.
   1. 아마 EmptyOperator는 따로 Worker에서 실행되지 않고 Scheduler에서 바로 실행시키는 것이 아닐까?
2. 위 volumes 코드는 무엇 때문에 존재했던 것일까?
   1. 융성님이 개발하면서 넣었던 것이라고 함.