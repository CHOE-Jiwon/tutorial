# Profiling
---
> 병목 지점을 찾기 위해 하는 행위
> 측정 가능한 자원은 모두 프로파일할 수 있음.

### Profiling의 목표
1. 시스템의 어느 부분이 느린지, 어디서 RAM을 많이 쓰는지, 디스크 I/O나 네트워크 I/O를 과도하게 발생시키는 부분이 어디인지를 확인


### 시간을 측정하는 간단한 방법
1. print time.time()
2. Decorator
3. timeit

### Unix time 명령어를 이용한 간단한 시간 측정
```bash
$ /usr/bin/time -p {execute command}
$ /usr/bin/time --verbose {execute command}
```

`--verbose` 옵션은 mac에서 안먹힘

> `Major (requirint I/O) page faults` : 운영체제가 RAM에서 필요한 데이터를 찾을 수 없기 때문에 디스크에서 페이지를 불러왔는지 여부.
> 속도를 느리게 하는 원인


* real: 경과 시간을 기록
* user: CPU가 커널 함수 외 작업을 처리하느라 소비한 시간을 기록
* sys: 커널 함수를 수행하는 데 소비한 시간을 기록

## 한 줄씩 프로파일링 (line_profiler)
