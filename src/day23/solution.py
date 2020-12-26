import sys
import os
import re
from collections import deque
day_number = sys.path[0].split('\\')[-1]
if len(sys.argv)==1:
    path_to_source = os.path.join("\\".join(sys.path[0].split("\\")[:-2]), f"input\\{day_number}")
else:
    path_to_source = os.path.join("\\".join(sys.path[0].split("\\")[:-2]), f"sample\\{day_number}")
with open(path_to_source, "r") as infile:
    vals = list(map(int, infile.read()))

max_val = max(vals)

next = [None for _ in range(max_val+1)]
for i in range(len(vals)):
    next[vals[i]] = vals[(i+1)%len(vals)]


cur = vals[0]


def do_turn(cur):
    three_start = next[cur]
    three_mid = next[next[cur]]
    three_end = next[next[next[cur]]]
    three_after = next[three_end]
    #print(f"pickup: {three_start} {three_mid} {three_end}")
    next[cur] = three_after
    
    dest = cur-1 if cur > 1 else max_val
    extracted_nos = set([three_start, three_mid, three_end])
    
    while(dest in extracted_nos):
        dest = dest-1 if dest > 1 else max_val
    #print(f"destination: {dest}")
    after_dest = next[dest]
    next[dest] = three_start
    next[three_end] = after_dest
    
    return next[cur]
    

for _ in range(100):
    cur = do_turn(cur)
    
    visited = set([1])
    t_v = 1
    t = ""

    
    
visited = set([1])

part1 = ""
t_v = 1
while(next[t_v] not in visited):
    t_v = next[t_v]
    part1 += str(t_v)
    visited.add(t_v)



max_val = 1000000

next = [i+1 for i in range(max_val+1)]
for i in range(len(vals)-1):
    next[vals[i]] = vals[i+1]
next[vals[-1]] = len(vals)+1
next[-1] = vals[0]

cur = vals[0]
for _ in range(10000000):
    cur = do_turn(cur)

part2 = next[1] * next[next[1]]
print(part1)
print(part2)
    

