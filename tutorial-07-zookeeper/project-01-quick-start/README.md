#  Zookeeper Download

```bash
# apache-zookeeper tar 파일 다운로드
curl https://dlcdn.apache.org/zookeeper/zookeeper-3.7.1/apache-zookeeper-3.7.1-bin.tar.gz -o

# untar
tar xvzf apache-zookeeper-3.7.1-bin.tar.gz

# $ZK_HOME 설정
echo "export ZK_HOME=$(pwd)" >> ~/.bashrc && source ~/.bashrc

# Java Version 확인 (1.8 버젼 굿)
java -version
```


### Mac 에서 zookeeper가 뜨지 않는 경우

내 경우 포트 문제로 인해 zookeeper가 뜨지 않았음.

zookeeper.config 파일에서 prometheus 사용을 위한 7000번 포트가 필요한데,
Mac의 Airplay가 해당 포트를 이미 사용중에 있음. 

1. Airplay 종료 또는
2. 포트 번호 변경

을 통해 해결이 가능. 