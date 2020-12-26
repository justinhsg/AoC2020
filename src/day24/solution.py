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



#dirs = [0, 0, 0]

flipped = set()
for line in lines:
    dir = 0+0j
    i = 0
    while(i < len(line)):
        c = line[i]
        i+=1
        if(c == 'n' or c == 's'):
            c += line[i]
            i+=1
        if(c == 'e'):
            dir += 1
        elif(c == 'w'):
            dir -= 1
        elif(c == 'se'):
            dir += 0.5-0.5j
        elif(c == 'nw'):
            dir += -0.5+0.5j
        elif(c == 'sw'):
            dir += -0.5-0.5j
        elif(c== 'ne'):
            dir += 0.5+0.5j
    if(dir in flipped):
        flipped.remove(dir)
        
    else:
        flipped.add(dir)
        
part1 = len(flipped)

to_check = set()
dplane = [0.5+0.5j, 1, 0.5-0.5j, -0.5-0.5j, -1, -0.5+0.5j]


def n_black(tile):
    n_b = 0
    for dp in dplane:
        if (dp+tile) in flipped:
            n_b += 1
    return n_b

for tile in flipped:
    to_check.add(tile)
    for dp in dplane:
        to_check.add(dp+tile)
        
        
for day in range(100):
    #print(f"Before day {day}")
    #print(flipped)
    to_flip = set()
    for check_tile in to_check:
        n_b = n_black(check_tile)
        if(check_tile in flipped and (n_b== 0 or n_b > 2)):
            to_flip.add(check_tile)
        elif(check_tile not in flipped and n_b == 2):
            to_flip.add(check_tile)
    
    to_check.clear()
    #print("to flip")
    #print(to_flip)
    for flip_tile in to_flip:
        if flip_tile in flipped:
            flipped.remove(flip_tile)
            #print(f"flipping {flip_tile} to white")
        else:
            #print(f"flipping {flip_tile} to black")
            flipped.add(flip_tile)
        to_check.add(flip_tile)
        for dp in dplane:
            to_check.add(dp+flip_tile)
    #print(f"day {day}: {len(flipped)}")

part2 = len(flipped)
print(part1)
print(part2)