import sys
import os

day_number = sys.path[0].split('\\')[-1]
if len(sys.argv)==1:
    path_to_source = os.path.join("\\".join(sys.path[0].split("\\")[:-2]), f"input\\{day_number}")
else:
    path_to_source = os.path.join("\\".join(sys.path[0].split("\\")[:-2]), f"sample\\{day_number}")
with open(path_to_source, "r") as infile:
    numbers = list(map(int, infile.read().split("\n")))


part1 = None
part2 = None
    
unique = set(numbers)
if(len(unique) == len(numbers)):
    for n in unique:
        if (2020-n) in unique:
            part1 = n*(2020-n)
            break
    part2_found = False
    for n in unique:
        for m in unique:
            if(n != m and 2020-n-m in unique and 2020-n-m != n and 2020-n-m != m):
                    part2 = n*m*(2020-n-m)                    
                    break
        if(part2 is not None):
            break
print(part1)
print(part2)
