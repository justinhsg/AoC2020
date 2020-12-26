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
    numbers = list(map(int, infile.read().split("\n")))
    preamble = 25 if len(sys.argv)==1 else 5
    
part1 = None
part2 = None

sums = dict()

for i,n_i in enumerate(numbers[:preamble]):
    
    for j, n_j in enumerate(numbers[i+1:preamble]):
        if(n_i+n_j not in sums):
            sums[n_i+n_j] = 0
        sums[n_i+n_j]+=1

for k in range(preamble, len(numbers)):
    n_k = numbers[k]
    
    if(n_k not in sums):
        part1 = n_k
        break
    n_r = numbers[k-preamble]
    
    for i in range(k-preamble+1, k):
        n_a = numbers[i]
        
        if(n_a + n_k not in sums):
            sums[n_a+n_k] = 0
        sums[n_a+n_k] += 1
        sums[n_a+n_r] -= 1
        if(sums[n_a+n_r]==0):
            del sums[n_a+n_r]
    

start = 0
end = 1
cur_sum = numbers[start]
while(start != len(numbers)):
    #print(cur_sum, numbers[start:end])
    if(cur_sum < part1 and end==len(numbers)):
        part2 = None
        break
    elif(cur_sum < part1):
        cur_sum += numbers[end]
        end += 1
    elif(cur_sum > part1):
        cur_sum -= numbers[start]
        start += 1
    elif(start-end == 1):
        start += 1
    else:
        subarr = numbers[start:end]
        part2 = min(subarr)+max(subarr)
        break

print(part1)
print(part2)
    