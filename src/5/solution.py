import sys
import os
import re
with open(os.path.join(sys.path[0], 'input' if len(sys.argv)==1 else 'sample'), "r") as infile:
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
