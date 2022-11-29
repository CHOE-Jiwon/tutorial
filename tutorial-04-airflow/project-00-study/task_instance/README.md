# Task Instance Study

## 1. Task State
---

![task_state_flow](../../resources/task_state_flow.png)

[Airflow State 101 참고](https://towardsdatascience.com/airflow-state-101-2be3846d4634)

1. none : 해당 태스크가 실행을 대기하는 큐에 없는 상태 (의존성이 충족되지 않은 상태임)
   1. DAG Graph에는 no_status라고 보임.
2. scheduled : 스케쥴러가 해당 태스크의 의존성을 만족하여 실행 예약이 된 상태
3. queued : 태스크가 Executor에 할당되고, Worker를 기다리고 있는 상태
4. running : Worker에서 태스크가 실행중인 상태
   1. Worker 일수도 있고, 로컬 또는 synchronous executor 일수도 있음
5. success : 태스크의 실행이 에러 없이 끝마친 상태
6. shutdown : 태스크가 실행중인 상태에서 외부의 요청으로 인해 종료된 상태
   1. 참고로 2.3.3 버젼의 graph를 보면 shutdown 상태값은 보이지 않음.
7. restarting : 태스크가 실행중인 상태에서 외부의 요청으로 인해 다시 실행된 상태
   1. 기존 실행과 다른 점은, 기존 실행은 에러 없이 끝나면 Success로 넘어가지만
   2. restarting 은 up_for_retry로 넘어감.
   3. 참고로 2.3.3 버젼의 graph를 보면 restarting 상태값은 보이지 않음.
8. failed : 태스크가 실행중에 에러가 발생하여 실행 실패한 상태
9.  skipped : 태스크가 branching, LatestOnly 또는 이와 비슷한 사유로 인해 실행되지 않고 순서를 넘어간 상태
    1. 스케쥴러가 해당 태스크를 그냥 넘김.
    2. 이에 따라 해당 태스크의 실행 자체가 무시됨.
10. upstream_failed : 해당 태스크가 정상적으로 실행되기 위해 Trigger Rule이 상위 태스크 정상적으로 끝나야 된다고 하는데, 상위 태스크(upstream task)가 실패하여 해당 태스크도 실패한 상태
    1. 얘는 실행조차 가보지 못한 실패 상태
11. up_for_retry : 해당 태스크의 실행은 실패했으나, 재시도 횟수가 남아있어 다시 예약된 상태
    1. [stackoverflow 참고](https://stackoverflow.com/questions/53744198/apache-airflow-tasks-are-stuck-in-a-up-for-retry-state)
    2. Scheduler는 재시도 interval을 기다렸다가 다음 Schduelr의 heartbeat에 도달하면 트리거
12. up_for_reschedule: 해당 태스크를 실행하려고 보니 센서였더라.. 그래서 rescheduled된 상태
    1.  센서가 뭔데? _(확인필요)_
    2.  왜 센서는 rescheduled로 가는데?
    3.  데드락과 같은 상태를 피하기 위해 나온 상태값이라네?
13. sensing : 해당 태스크는 Smart Sensor 더라.
    1.  참고로 2.3.3 버젼의 graph를 보면 sensing 상태값은 보이지 않음.
    2.  2.4.0 부터는 해당 feature가 사라짐.
    3.  [Smart Sensor 참고](https://airflow.apache.org/docs/apache-airflow/2.3.3/concepts/smart-sensors.html)
14. deferred : 트리거에 의해 태스크의 실행이 연기된 상태
    1.  연기됐다는건, 정확히 어떻게 동작하는것인가? _(확인필요)_
    2.  [Deferrable Operators & Triggers 참고](https://airflow.apache.org/docs/apache-airflow/2.3.3/concepts/deferring.html)
    3.  DAG의 첫 태스크일 경우 원활한 실행을 위해 실행가능한 워커가 나올 때 까지 실행을 지연시키는 것으로 보임...
15. removed : 태스크 실행이 시작된 이후 DAG로부터 작업이 사라진 상태
    1.  [stackoverflow 참고](https://stackoverflow.com/questions/53654302/tasks-are-moved-to-removed-state-in-airflow-when-they-are-queued-and-not-restore)
    2. ```airflow.models.DagRun:verify_integrity``` 에 의해 태스크가 removed 상태로 바뀌게 됨.
    3. 위 함수가 데이터베이스 내에서 연관된 DAG에 더이상 존재하지 않는 task_id를 발견할 때 적용됨.
    4. 참고로 2.3.3 버젼의 graph를 보면 Removed 상태값은 보이지 않음.