import sys
import os
import re
from collections import deque
with open(os.path.join(sys.path[0], 'input' if len(sys.argv)==1 else 'sample'), "r") as infile:
    lines = infile.read().split("\n")

pub_key_1 = int(lines[0])
pub_key_2 = int(lines[1])

loop_1 = 0
val_1 = 1
while(val_1 != pub_key_1):
    val_1 = val_1 * 7 % 20201227
    loop_1 += 1


loop_2 = 0
val_2 = 1
while(val_2 != pub_key_2):
    val_2 = (val_2*7) % 20201227
    loop_2+=1

part1 = 1
for _ in range(loop_1):
    part1 = (part1*pub_key_2) % 20201227
print(part1)
#dirs = [0, 0, 0]