import threading
import time

class Worker(threading.Thread):
    def __init__(self, name):
        super().__init__()
        self.name = name
    
    def run(self):
        print("sub thread start ", threading.currentThread().getName())
        if self.name == "1":
            time.sleep(3)
        else:
            time.sleep(1)
        print("sub thread end ", threading.currentThread().getName())


print("main thread start")
# for i in range(5):
#     name = "thread {}".format(i)
#     t = Worker(name)
#     t.daemon = True
#     t.start()

t1 = Worker("1")
t1.start()

t2 = Worker("2")
t2.start()

# join: wait until thread is done..
# join이 없는 스레드는 메인 스레드가 기다려주지 않음.
t1.join()
t2.join()

print("main thread post job")
print("main thread end")