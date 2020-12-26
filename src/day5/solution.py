import sys
import os
import re
day_number = sys.path[0].split('\\')[-1]
if len(sys.argv)==1:
    path_to_source = os.path.join("\\".join(sys.path[0].split("\\")[:-2]), f"input\\{day_number}")
else:
    path_to_source = os.path.join("\\".join(sys.path[0].split("\\")[:-2]), f"sample\\{day_number}")
with open(path_to_source, "r") as infile:
    data = infile.read().split("\n")
    
part1 = 0
part2 = 0

seat_ids = []
seats = [False for _ in range(2**10-1)]
for datum in data:
    id = 0
    for c in datum:
        id *= 2
        if(c == 'B' or c == 'R'):
            id += 1
    part1 = max(part1, id)
    seats[id] = True

while(not seats[part2]):
    part2 += 1
while(seats[part2]):
    part2+=1

print(part1)
print(part2)
