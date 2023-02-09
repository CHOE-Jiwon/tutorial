# Leader Election Login

---

분산시스템을 이루어야 하는 어플리케이션에서의 상황

## When start application

1. Create my node to znode of namespace for leader election as ephmeral child node
    with larger number value than existing children have.
2. elect()


## When elect
1. get children in znode of namespace for leader election.
2. sort children by value
3. get first node in children
4. if first node is my node, I'm a leader else I'm a follower
5. watch znode of namespace for leader election on mode - PERSITENT_RECURSIVE


## When watch triggered
1. if event is NodeDeleted, elect() <br/>
    else do nothing


## When stop application
1. my node is removed from znode of namespace for leader election <br/>
    as child node because my node was ephemeral node.


---
참고 

** 패스트캠퍼스 온라인 강의 - 한 번에 끝내는 데이터 엔지니어링 초격차 패키지 Online
