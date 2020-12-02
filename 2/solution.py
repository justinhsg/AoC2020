import sys
import os
with open(os.path.join(sys.path[0], 'input' if len(sys.argv)==1 else 'sample'), "r") as infile:
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
            
    

