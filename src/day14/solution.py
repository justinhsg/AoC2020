import sys
import os
import re
day_number = sys.path[0].split('\\')[-1]
if len(sys.argv)==1:
    path_to_source = os.path.join("\\".join(sys.path[0].split("\\")[:-2]), f"input\\{day_number}")
else:
    path_to_source = os.path.join("\\".join(sys.path[0].split("\\")[:-2]), f"sample\\{day_number}")
with open(path_to_source, "r") as infile:
    lines = infile.read().split("\n")

part1, part2 = None, None
mask_ones = 0 #or
mask_zeros = 0 #nor

memory = dict()
for line in lines:
    
    if(line[:4] == "mask"):
        mask_ones = 0 #and
        mask_zeros = 0 #nor
        bits = line[7:]
        for bit in bits:
            mask_ones <<= 1
            mask_zeros <<= 1
            if(bit == '1'):
                mask_ones += 1
            elif(bit =='0'):
                mask_zeros += 1
    else:
        m = re.match('^mem\[([0-9]+)\] = ([0-9]+)$', line)
        loc = int(m.group(1))
        val = int(m.group(2))
        if(loc not in memory):
            memory[loc] = 0
        memory[loc] = ((val | mask_ones)& ~mask_zeros)
part1 = 0
for loc in memory:
    part1 += memory[loc]
    

memory = dict()
mask_ones = 0
mask_xs = 0
floats = [0]
for line in lines:
    if(line[:4] == "mask"):
        mask_ones = 0
        mask_xs = 0
        floats = [0]
        bits = line[7:]
        for bit in bits:
            mask_ones <<=1
            mask_xs <<=1
            floats = [f<<1 for f in floats]
            if(bit == '1'):
                mask_ones += 1
            elif(bit == 'X'):
                mask_xs += 1
                floats = [f+1 for f in floats]+floats
                
    else:
        m = re.match('^mem\[([0-9]+)\] = ([0-9]+)$', line)
        loc = int(m.group(1))
        val = int(m.group(2))
        loc = ((loc|mask_ones)&~mask_xs)
        for f in floats:
            new_loc = loc | f
            memory[new_loc] = val
part2 = 0
for loc in memory:
    part2 += memory[loc]
print(part1)
print(part2)
        