docker run -it -p 8081:8080 \
    -v /Users/Jiwon/Crowdworks/tutorial/tutorial-04-airflow/project-02-dag-tutorial/dags/download_rocket_launches.py:/opt/airflow/dags/download_rocket_launches.py \
    --entrypoint=/bin/bash \
    --name airflow \
    apache/airflow:2.0.0-python3.8 \
    -c '( \
        airflow db init && \
        airflow users create \
            --username admin \
            --password admin \
            --firstname jiwon \
            --lastname choe \
            --role Admin \
            --email jiwon0929@crowdworks.kr \
    ); \
    airflow webserver & \
    airflow scheduler \
    '