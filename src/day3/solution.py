import sys
import os
from functools import reduce
with open(os.path.join(sys.path[0], 'input' if len(sys.argv)==1 else 'sample'), "r") as infile:
    map = infile.read().split("\n")


part1 = None
part2 = None

w = len(map[0])
h = len(map)
cur_pos = 0

pos = [0 for _ in range(5)]
trees = [0 for _ in range(5)]
dc = [1,3,5,7,1]
pos_1 = 0
pos_3 = 0
pos_5 = 0
pos_7 = 0
pos_21 = 0
trees_1 = 0
trees_3 = 0
trees_5 = 0
trees_7 = 0
trees_21 = 0

n_trees = 0
for i in range(h):
    for p in range(5):
        if(p != 4):
            if(map[i][pos[p]] == '#'):
                trees[p] += 1
            pos[p] = (pos[p]+dc[p])%w
        if(p == 4 and i%2 == 0):
            if(map[i][pos[p]] == '#'):
                trees[p] += 1
            pos[p] = (pos[p]+dc[p])%w
    #print(pos)
part1 = trees[1]
part2 = reduce(lambda a, b: a*b, trees, 1)
print(part1)
print(part2)
