
import sys
import os
import re
from collections import deque

with open(os.path.join(sys.path[0], 'input' if len(sys.argv)==1 else 'sample'), "r") as infile:
    numbers = list(map(int, infile.read().split("\n")))
    
    

part1 = None
part2 = None

diffs = [0 for _ in range(4)]

n_sort = sorted(numbers)
n_one = 1
n_three = 1

for (i, n_i) in enumerate(n_sort[:-1]):
    diff = n_sort[i+1]-n_i
    if(diff == 1):
        n_one += 1
    elif(diff == 3):
        n_three += 1
part1 = n_one*n_three
ways = [0 for _ in range(len(numbers))]

for (i, n_i) in enumerate(n_sort):
    if(n_i <= 3):
        ways[i] += 1
    for j in range(max(i-3, 0), i):
        if(n_i - n_sort[j] <= 3 ):
            ways[i] += ways[j]
part2 = ways[len(numbers)-1]

print(part1)
print(part2)
        