import sys
import os
day_number = sys.path[0].split('\\')[-1]
if len(sys.argv)==1:
    path_to_source = os.path.join("\\".join(sys.path[0].split("\\")[:-2]), f"input\\{day_number}")
else:
    path_to_source = os.path.join("\\".join(sys.path[0].split("\\")[:-2]), f"sample\\{day_number}")
with open(path_to_source, "r") as infile:
    lines = infile.read().split("\n")

part1 = 0
part2 = 0
for line in lines:
    [req, pw] = line.split(": ")
    [min, max, char] = re.split("-| ", req)
    min = int(min)
    max = int(max)
    if(min <= pw.count(char) and pw.count(char) <= max):
        part1 += 1
    if(len(pw) < min):
        continue
    if(len(pw) < max and pw[min-1] == char):
        part2 += 1
    if((pw[min-1] == char) ^ (pw[max-1] == char)):
        part2 += 1
print(part1)
print(part2)
            
    

