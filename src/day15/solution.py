import sys
import os
import re
import time
day_number = sys.path[0].split('\\')[-1]
if len(sys.argv)==1:
    path_to_source = os.path.join("\\".join(sys.path[0].split("\\")[:-2]), f"input\\{day_number}")
else:
    path_to_source = os.path.join("\\".join(sys.path[0].split("\\")[:-2]), f"sample\\{day_number}")
with open(path_to_source, "r") as infile:
    nums = list(map(int, infile.read().split(",")))

#last_called = dict()
last_called = [0 for _ in range(30000000)]
last_n = 0
cur_i = 0
for n in nums:
    last_called[last_n] = cur_i
    cur_i += 1
    last_n = n
while(cur_i < 2020):
    n = 0 if last_called[last_n]==0 else cur_i-last_called[last_n]
    last_called[last_n] = cur_i
    cur_i += 1
    last_n = n
    
part1 = last_n
while(cur_i< 30000000):
    n = 0 if last_called[last_n]==0 else cur_i-last_called[last_n]
    last_called[last_n] = cur_i
    cur_i += 1
    last_n = n
part2 = last_n  

print(part1)
print(part2)
        
