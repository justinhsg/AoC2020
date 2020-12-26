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
    lines = infile.read().split("\n")
    
part1 = None
part2 = None

ops = list(map(lambda x: x.split(" ")[0], lines))
vals = list(map(lambda x: int(x.split(" ")[1]), lines))

pc = 0
acc = 0
visited = set()
visited.add(pc)
while(pc >= 0 and pc < len(ops)):
    op = ops[pc]
    val = vals[pc]
    if(op == "acc"):
        acc += val
        pc += 1
    elif(op == "nop"):
        pc += 1
    elif(op == "jmp"):
        pc += val
    if(pc in visited):
        break
    else:
        visited.add(pc)
part1 = acc

for change_pc in range(len(ops)):
    if(ops[change_pc] == "acc"):
        continue
    acc = 0
    OOB = True
    pc = 0
    visited.clear()
    visited.add(pc)
    while(pc >= 0 and pc < len(ops)):
        op = ops[pc]
        val = vals[pc]
        if(op == "acc"):
            acc += val
            pc += 1
        elif(pc!= change_pc and op == "nop" or pc==change_pc and op=="jmp"):
            pc += 1
        elif(pc!= change_pc and op == "jmp" or pc==change_pc and op=="nop"):
            pc += val
        if(pc in visited):
            OOB = False
            break
        else:
            visited.add(pc)
    if(OOB):
        part2 = acc
        break
print(part1)
print(part2)
    