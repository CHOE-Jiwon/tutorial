import pstats

p = pstats.Stats("./profiles.stats")
p.sort_stats("cumulative")

# python -m cProfile ... 했을 때랑 동일한 결과 출력
# p.print_stats()

# 함수 호출자의 정보를 출력
# p.print_callers()

# 함수 호출 목록 출력
p.print_callees()