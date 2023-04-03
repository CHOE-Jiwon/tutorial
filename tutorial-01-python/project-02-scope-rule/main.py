import os

from subPackage.A_subPackage import a
# from subPackage.A_subPackage import b
# from subPackage.A_subPackage import c

# from subPackage.B_subPackage import d
# from subPackage.B_subPackage import e
# from subPackage.B_subPackage import f



msg = "This is 'msg' var in 'main.py' module"

print("IN main.py\n\n")
print("locals()")
print(locals().keys())

print("----------------")

print("globals()")
print(globals().keys())