import json
import pathlib

import airflow
import requests
import requests.exceptions as requests_exceptions

from airflow import DAG
from airflow.operators.bash import BashOperator
from airflow.operators.python import PythonOperator

# 객체의 인스턴스 생성(구체화)
# 모든 workflow의 entrypoint
dag = DAG(
    dag_id = "download_rocket_launches",            # DAG Name
    start_date = airflow.utils.dates.days_ago(14),  # DAG Start Date
    schedule_interval = None,                       # DAG Execution Interval
)

# BashOperator를 이용하는 task 생성
# curl로 URL 결과값 다운로드
download_launches = BashOperator(
    task_id = "download_launches",                  # task_name
    bash_command = "curl -o /tmp/launches.json -L 'https://ll.thespacedevs.com/2.0.0/launch/upcoming'",
    dag = dag,
)

# Json Parsing + Download all rocket image
def _get_pictures():
    pathlib.Path("/tmp/images").mkdir(parents=True, exist_ok=True)

    with open("/tmp/launches.json") as f:
        launches = json.load(f)

        image_urls = [launch["image"] for launch in launches["results"]]

        for image_url in image_urls:
            try:
                response = requests.get(image_url)
                image_filename = image_url.split("/")[-1]
                target_file = f"/tmp/images/{image_filename}"

                with open(target_file, "wb") as f:
                    f.write(response.content)
                
                print(f"Download {image_url} to {target_file}")
            except requests_exceptions.MissingSchema:
                print(f"{image_url} appears to be an invalid URL.")
            except requests_exceptions.ConnectionError:
                print(f"Could not connect to {image_url}.")

# PythonOperator를 이용하는 task 생성
# _get_pictures()로 이미지 다운로드
get_pictures = PythonOperator(
    task_id = "get_pictures",
    python_callable = _get_pictures,
    dag = dag,
)

# BashOperator를 이용하는 task 생성
notify = BashOperator(
    task_id = "notify",
    bash_command = 'echo "There are now $(ls /tmp/images | wc -l) images."',
    dag = dag,
)

# task 실행 순서 설정
download_launches >> get_pictures >> notify