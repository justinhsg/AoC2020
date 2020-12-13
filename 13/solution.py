import sys
import os
import re
from collections import deque

with open(os.path.join(sys.path[0], 'input' if len(sys.argv)==1 else 'sample'), "r") as infile:
    lines = infile.read().split("\n")

depart = int(lines[0])
nos = []
offset = []
for i, nums in enumerate(lines[1].split(",")):
    if(nums != 'x'):
        nos.append(int(nums))
        offset.append(i)

best = 1e100
for n in nos:
    delay = (-depart%n)
    if(delay < best):
        best = delay
        part1 = delay*n
        
acc_offset = 0
prods = nos[0]
for i in range(1, len(nos)):
    next_offset = offset[i]
    next_id = nos[i]
    '''
    -(acc_offset + n*prods) = next_offset mod next_id
    -(n*prods) = next_offset+acc_offset mod next_id
    n*(-prods) = next_offset+acc_offset mod next_id
    n = (next_offset+acc_offset)*(-prods)^(next_id-2) mod next_id
    '''
    n = (next_offset + acc_offset)*(-prods)**(next_id-2) % next_id
    acc_offset = acc_offset + n*prods
    prods *= next_id
part2=acc_offset    

print(part1)
print(part2)
        