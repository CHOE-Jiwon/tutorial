# Operators Default Arguments

### 공식 문서 참고
***
* [Default Arguments](https://airflow.apache.org/docs/apache-airflow/1.10.1/tutorial.html#default-arguments)
* [BaseOperator](https://airflow.apache.org/docs/apache-airflow/1.10.1/code.html#airflow.models.BaseOperator)


## Default Arguments란
***
Airflow DAG을 개발할 때 주로 default_args를 선언하고 DAG의 인자로 넣게 되는데, 이 ```default_args``` 에 대해 살펴보자.

처음에는 default_args 가 DAG에 적용이 되는건줄 알았는데, 다시 보니 operator에 적용이 되는 것이다.  
공식문서에 나온 설명은 다음과 같다.  

> We’re about to create a DAG and some tasks, and we have the choice to explicitly pass a set of arguments to each task’s constructor (which would become redundant), or (better!) we can define a dictionary of default parameters that we can use when creating tasks.  
> 
> DAG와 Tasks를 만들려고 하며, 각 작업의 생성자(Constructor)에 arguments set 을 명시적으로 전달하거나 (이는 중복될 수 있다.) 또는 tasks를 생성할 때 사용하는 default parametrs를 정의할 수 있다 (더 나는 방법이라고 문서에서 설명중).  
  
  
***즉, default_args는 DAG에 적용된다기 보다는, DAG 내에 있는 각 task들에 적용이 되는 것이다.***  

코드로 보는 예시를 보자.

```python
from datetime import datetime, timedelta

default_args = {
    'onwer': 'airflow',
    'depends_on_past': False,
    'start_date': datetime(2015, 6, 1),
    'email': ['airflow@example.com'],
    'email_on_failure': False,
    'email_on_retry': False,
    'retries': 1,
    'retry_delay': timedelta(minutes = 5),
    # 'queue': 'bash_queue',
    # 'pool': 'backfill',
    # 'priority_weight': 10,
    # 'end_date': datetime(2016, 1, 1),
}
```

더 자세한 정보를 알기 위해서는 ```airflow.models.BaseOperator``` 문서를 보면 된다.

참고로, 상용 환경과 개발 환경에 따라 서로 다른 default_args를 적용할 수도 있다.

## airflow.models.BaseOperator
***
#### 매우 중요: 모든 Operators 들은 BaseOperator를 상속받으며, 이를 통해 많은 기능들을 얻게 된다.  

```python
class airflow.models.BaseOperator(
    task_id,
    owner='Airflow',
    email=None,
    email_on_retry=True,
    email_on_failure=True,
    retries=0,
    retry_delay=datetime.timedelta(0, 300),
    retry_exponential_backoff=False,
    max_retry_delay=None,
    start_date=None,
    end_date=None,
    schedule_interval=None,
    depends_on_past=False,
    wait_for_downstream=False,
    dag=None,
    params=None,
    default_args=None,
    adhoc=False,
    priority_weight=1,
    weight_rule='downstream',
    queue='default',
    pool=None,
    sla=None,
    execution_timeout=None,
    on_failure_callback=None,
    on_success_callback=None,
    on_retry_callback=None,
    trigger_rule='all_success',
    resources=None,
    run_as_user=None,
    task_concurrency=None,
    executor_config=None,
    inlets=None,
    outlets=None,
    *args,
    **kwargs)
```

이 파라미터들을 전부 볼 필요는 없지만, 현재 회사에서 사용중이거나 자주 사용하는 파라미터 위주로 살펴볼 예정이다.

1. task_id (string) : 태스크에 부여되는 고유하며 의미를 지닌 ID 이다.
2. onwer (string) : 태스크의 소유자를 나타내며, unix username을 쓰는 것을 추천한다.
3. retries (int) : 태스크가 실패 상태로 넘어가기 전에 수행해야 하는 재시도 횟수이다. (바로 실패로 넘어가지 않고 재시도가 끝나면 실패 상태로 넘어감)
4. retry_delay (timedelta) : 재시도 간의 대기 시간이다.
5. start_date (dateimte) : 첫번째 태스크 인스턴스의 실행 시간을 정의한다.
   1. 이를 설정할 때에는 `schedule_interval` 와 단위를 비슷하게 맞추는 것이 좋다. (예를 들어 스케쥴링 주기가 시간 단위라면, start_date를 특정 시간에 맞춘다. 2022-11-11 11:11:11 이렇게 맞추는건 별로.)
   2. 에어플로우는 단순히 최근 `execution_date` 에 `schedule_interval` 을 더해서 다음 실행 시각을 정한다.
   3. A 태스크에 의존성이 존재하는 B 태스크가 있을 때, 이 두 태스크의 `start_date` 이 `execution_date`와 일치하지 않는 방식으로 오프셋 되는 경우, A 태스크는 실행 조건을 만족하지 못한다. ??????????? 먼말이고???
6. end_date (datetime) : 해당 인자가 주어지면, 스케쥴러가 `end_date` 이후로는 실행하지 않음.
7. dag (DAG) : task가 붙을 DAG
8. on_failure_callback (callable) : 태스크가 실패했을 때 호출되는 함수. context dictionary가 해당 함수의 인자로 전달된다. Context는 태스크 인스턴스 오브젝트와 관련된 references를 포함하고 있다.
9. on_retry_callback (callable) : 태스크가 retry 될 때 호출되는 함수. on_failure_callback와 비슷하다.
10. on_success_callback (callable) : 태스크가 성공했을 때 호출되는 함수. on_failure_callback와 비슷하다.
11. trigger_rule (str) : 해당 태스크를 트리거하기 위해 어떤 의존성을 적용할지 정의한다.
    1.  Option: ```{ all_success | all_failed | all_done | one_success | one_failed | dummy}```
    2.  Deafult: ```all_success```
    3.  str 을 사용해도 되고, static class인 ```airflow.utils.TriggerRule``` 을 사용해도 된다.


#### execution_date란?
* [참고1](https://dydwnsekd.tistory.com/108)
* [참고2](https://towardsdatascience.com/airflow-schedule-interval-101-bbdda31cc463)

1. execution_date : the start date and time when you expect a DAG to be triggered.
2. start_date : the data and time when a DAG has been triggered, and this time usually refers to the wall clock.

라고는 설명이 되어 있지만, 쉽게 설명하자면...

1. start_date : 이 DAG은 이 날짜 이후부터 실행될거야 (DAG에 걸어놓은 시작 datetime)
2. execution_date : DAG이 schedule_interval에 따라 실행되는 datetime (```execution_date = start_date + schedule_interval```) 라고 생각하면 편함. airflow 최신 버젼에서는 Logical time 라고도 함.
