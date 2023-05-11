from functools import wraps
import time

def timefn(fn):
    @wraps(fn)
    def measure_time(*args, **kwargs):
        t1 = time.time()
        result = fn(*args, **kwargs)
        t2 = time.time()
        print(f"@timefn: {fn.__name__} took {t2 - t1} seconds")
        
        return result
    return measure_time

# @timefn
def calculate_z_serial_purepython(maxiter, zs, cs):
    """ 줄리아 갱신 규칙을 사용해서 output 리스트 계산하기"""
    output = [0] * len(zs)

    for i in range(len(zs)):
        n = 0

        z = zs[i]
        c = cs[i]

        while abs(z) < 2 and n < maxiter:
            z = z * z + c
            n += 1
        
        output[i] = n

    return output

x1, x2, y1, y2 = -1.8, 1.8, -1.8, 1.8
c_real, c_imag = -0.62772, -.42193

def calc_pure_python(desired_width, max_iterations):
    """복소 좌표(zs)와 복소 인자(cs) 리스트를 만들고,
    줄리아 집합을 생성한다."""

    x_step = (x2-x1) / desired_width
    y_step = (y1-y2) / desired_width

    x = []
    y = []

    ycoord = y2
    while ycoord > y1:
        y.append(ycoord)
        ycoord += y_step
    
    xcoord = x1
    while xcoord < x2:
        x.append(xcoord)
        xcoord += x_step
    
    zs = []
    cs = []

    for ycoord in y:
        for xcoord in x:
            zs.append(complex(xcoord, ycoord))
            cs.append(complex(c_real, c_imag))
    
    print("Length of x: ", len(x))
    print("Total elements: ", len(zs))

    start_time = time.time()

    output = calculate_z_serial_purepython(max_iterations, zs, cs)
    
    end_time = time.time()

    secs = end_time - start_time
    print(calculate_z_serial_purepython.__name__ + " took", secs, "seconds")

    assert sum(output) == 33219980
    

if __name__ == "__main__":
    calc_pure_python(desired_width=1000, max_iterations=300)