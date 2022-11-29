# Task Instance Study

## 1. Task State
---

![task_state_flow](../../resources/task_state_flow.png)

1. up_for_retry : The task failed, but has retry attempts left and will be rescheduled.
   1. 해당 태스크의 실행은 실패했으나, 재시도 횟수가 남아있어 다시 예약된 상태