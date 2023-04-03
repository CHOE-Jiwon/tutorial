# subPackage/A_subPackage/a.py
# import inspect

# Check whether `os` library in Local Scope
print('os' in locals())
print(locals().keys())
# Check whether `os` library in Enclosing Scope
# print('os' in inspect.currentframe().f_back.f_locals)
# Check whether `os` library in Global Scope
print('os' in globals())
print(globals().keys())
# Check whether `os` library in Built-in Scope
print('os' in dir(__builtins__))
print('os' in vars())
print(vars().keys())