# XCom
---
1. XCom이란 태스크 간에 작은 데이터를 공유할 수 있게 도와주는 기능이다.
2. xcom_push 를 이용해 {key:value} 를 등록할 수 있다.
   1. 이 때 해당 데이터는 태스크에 종속되어있는가?
   2. task에 종속되었다기보단, ```context["task_instance"]``` 에 종속된 것 같음.
   3. xcom_pull 할 때 task_id를 적지 않아도 값들을 가져올 수 있음.
3. xcom_pull 을 이용해 key값을 파라미터로 전달하면 해당 key에 매핑된 value를 가져올 수 있다.
   1. 만약 파라미터로 key값을 안주고 task_ids를 주었을 때는?
   2. PythonOperator의 callable 함수에서 return값을 주면, 해당 return 값은 태스크의 XCom으로 들어감
