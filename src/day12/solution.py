
import sys
import os
import re
from collections import deque

with open(os.path.join(sys.path[0], 'input' if len(sys.argv)==1 else 'sample'), "r") as infile:
    directions = infile.read().split("\n")
    

#N E S W
d = [1j, 1, -1j, -1]

face = 1
pos1 = 0

wp = 10 + 1j
pos2 = 0

for dir in directions:
    code = dir[0]
    val = int(dir[1:])
    if(code == 'N'):
        pos1 += val*1j
        wp += val*1j
    elif(code == 'E'):
        pos1 += val
        wp += val
    elif(code == 'S'):
        pos1 -= val*1j
        wp -= val*1j
    elif(code == 'W'):
        pos1 -= val
        wp -= val
    elif(code == 'R'):
        face = (face+(val//90))%4
        wp *= (-1j)**(val//90)
    elif(code == 'L'):
        face = (face-(val//90))%4
        wp *= (1j)**(val//90)
    elif(code == 'F'):
        pos1 += val*d[face]
        pos2 += val*wp
        
part1 = int(abs(pos1.real) + abs(pos1.imag))
part2 = int(abs(pos2.real) + abs(pos2.imag))

print(part1)
print(part2)
        