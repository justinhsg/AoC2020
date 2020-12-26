import sys
import os

day_number = sys.path[0].split('\\')[-1]
if len(sys.argv)==1:
    path_to_source = os.path.join("\\".join(sys.path[0].split("\\")[:-2]), f"input\\{day_number}")
else:
    path_to_source = os.path.join("\\".join(sys.path[0].split("\\")[:-2]), f"sample\\{day_number}")
with open(path_to_source, "r") as infile:
    lines = infile.read().split("\n")

depart = int(lines[0])

ids = []
delays  = []
for i, nums in enumerate(lines[1].split(",")):
    if(nums != 'x'):
        ids.append(int(nums))
        delays.append(i)

best = 1e100
for id in ids:
    delay = (-depart%id)
    if(delay < best):
        best = delay
        part1 = delay*id


t = [0 for _ in range(len(ids))]
prod = [id for id in ids]
for i in range(1, len(ids)):
    prod[i] *= prod[i-1]
    
for i in range(len(ids)-1):
    inv_prod = prod[i]**(ids[i+1]-2) % ids[i+1]
    f = ( (-t[i]-delays[i+1]) * inv_prod ) % ids[i+1]
    t[i+1] = t[i] + f*prod[i]
    
    
part2= t[-1]

print(part1)
print(part2)
        