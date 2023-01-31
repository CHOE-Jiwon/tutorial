# PythonOperator

[공식 문서 참고](https://airflow.apache.org/docs/apache-airflow/2.3.3/_api/airflow/operators/python/index.html)


## Option

---

### 1. `provide_context` option 
 
> ChatGPT:  The provide_context option of the PythonOperator in Airflow is a boolean argument that controls whether the 
> context (i.e., the run information) of the task instance is passed to the task's Python callable as a dictionary.
>
> 즉, Task Instance의 context 정보를 callable 함수로 넘길지 말지 정하는 옵션

- `'provide_context': True` : callable 함수는 `kwargs` 를 인자로 넘겨받음.
- `kwargs`: 해당 인자값에는 task instance의 context(`execution_date`, `dag_run` 과 같은 메타데이터 정보) 가 포함되어 있음.

예시
```python
from airflow import DAG
from airflow.operators.python_operator import PythonOperator
from datetime import datetime, timedelta

default_args = {
    'owner': 'airflow',
    'start_date': datetime(2022, 1, 1),
    'depends_on_past': False,
    'retries': 1,
    'retry_delay': timedelta(minutes=5),
}

dag = DAG(
    'my_dag',
    default_args=default_args,
    schedule_interval=timedelta(hours=1),
)

def print_task_context(**kwargs):
    task_instance = kwargs['task_instance']
    execution_date = task_instance.execution_date
    print(f'Task instance context: execution_date={execution_date}')

task = PythonOperator(
    task_id='print_task_context',
    python_callable=print_task_context,
    provide_context=True,
    dag=dag,
)
```