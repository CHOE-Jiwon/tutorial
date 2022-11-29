# Google Cloud BigQuery Operators

[공식 문서 참고](https://airflow.apache.org/docs/apache-airflow-providers-google/stable/operators/cloud/bigquery.html#howto-operator-bigqueryinsertjoboperator)

# 1. 준비 사항
---
1. Select or create a Cloud Platform project using the [Cloud Console](https://console.cloud.google.com/project)
2. Enable billing for your project, as described in Google Cloud documentation.
3. Enable the API, as described in the Cloud Console documentation
4. Install API libraries via **pip**
   1. ```pip install 'apache-airflow[google]'```
   2. docker 사용자는 docker-compose.yaml파일의 ```x-aiorflow-common.environment._PIP_ADDITIONAL_REQUIRMENTS``` 에 ```apache-airflow-providers-google``` 추가
5. Setup a Google Cloud Connection -> 아래서 설명


### Setup a Google Cloud Connection

구글 클라우드 연결을 위해 필요한 인증 방법은 두 가지가 있다.

1. Application Default Credentials 사용 -> 패스하겠다.
2. JSON format으로 된 key file을 특정하여 service account 사용
   1. ```Keyfile Path``` 사용
   2. ```Keyfile JSON``` 사용
   3. ```Keyfile secret name``` 사용

여기서는 Keyfile Path를 가지고 service account를 사용하여 연결하는 방법으로 가겠다.

1. Airflow -> Admin -> Connection 에서 새로운 커넥션을 추가하자.
   1. Connection Id: ```google_cloud_default``` (다른 이름으로 해도 상관은 없지만 이게 기본값이다.)
   2. Connection Type: Google Cloud (만약 커넥션 타입에 구글 클라우드가 없다면 PIP 설정 확인한다.)
   3. Description: 적고싶은 설명 적고
   4. Keyfile Path: ```{your-key-file-path}``` 을 입력하자.
   5. ...
   6. Project Id: 사용할 Google Cloud Project 를 입력하자.
2. Operator의 인자 값 중 ```gcp_conn_id``` 를 추가하자.
   1. 만약 이 인자를 추가하지 않는다면, 기본적으로 ```google_cloud_default``` 라는 connection을 찾아서 사용한다.
   2. 만약 커넥션을 추가할 때 다른 Id를 사용했다면, 인자값으로 ```gcp_conn_id = {your_conn_id}``` 을 주면 된다.
3. Operator의 인자 값 중 ```project_id``` 가 있는데, 이는 필수 값은 아니다.
   1. 앞서 작성했던 ```gcp_conn_id``` 를 통해 서비스 계정을 찾게 되는데, 해당 서비스 계정에 이미 프로젝트 정보가 들어가 있기 때문이다...?
   2. 아니면 connection에 이미 project_id가 있어서...?

