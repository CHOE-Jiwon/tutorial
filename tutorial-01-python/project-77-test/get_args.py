import sys

system_args = sys.argv

print(system_args)
print(system_args[1:])
print(type(system_args))

import textwrap

long_string = """Thiss is fine if your use case can accept
    extraneous leading spaces."""

print(long_string)

long_string = textwrap.dedent("""\
    This is also fine, because testwrap.dedent()
    will collapse common leading spaces in each line.
""")

print(long_string)
print("Y")

my_query = """\
SELECT
    *
FROM asdlfn
WHERE 1=1
    AND asdfas
    AND vbpai\
"""

print(my_query)
print("H")

MYSQL_DAILY_ROW_COUNT_QUERY= 'SELECT COUNT(1) as cnt ' \
                                   'FROM table ' \
                                   'WHERE condition_column ' \
                                   'BETWEEN "batch_date 00:00:00" AND "batch_date 23:59:59"'\
                                   'additional_condition;'

print(MYSQL_DAILY_ROW_COUNT_QUERY)