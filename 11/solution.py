
import sys
import os
import re
from collections import deque

with open(os.path.join(sys.path[0], 'input' if len(sys.argv)==1 else 'sample'), "r") as infile:
    seats = list(map(lambda x: list(x), infile.read().split("\n")))
    

part1 = None
part2 = None

d_row = [-1, -1, -1, 0, 0, 1, 1, 1]
d_col = [-1, 0, 1, -1, 1, -1, 0, 1]

n_row = len(seats)
n_col = len(seats[0])

part1_adj = dict()
part2_adj = dict()

part1_occupied = dict()
part2_occupied = dict()

for r in range(n_row):
    for c in range(n_col):
        if(seats[r][c] == 'L'):
            part1_adj[(r,c)] = []
            part2_adj[(r,c)] = []
            for d in range(8):
                new_r = r+d_row[d]
                new_c = c+d_col[d]
                if(0<= new_r < n_row and 0<=new_c<n_col):
                    if(seats[new_r][new_c] != '.'):
                        part1_adj[(r,c)].append((new_r, new_c))
                
                steps = 1
                while(0<= r+d_row[d]*steps < n_row and 0<= c+d_col[d]*steps < n_col):
                    if(seats[r+d_row[d]*steps][c+d_col[d]*steps] != '.'):
                        part2_adj[(r,c)].append((r+d_row[d]*steps, c+d_col[d]*steps))
                        break
                    else:
                        steps += 1
            
            part1_occupied[(r,c)] = False
            part2_occupied[(r,c)] = False
            
            
to_check = part1_occupied.keys()
while(len(to_check)!=0):
    new_to_check = set()
    to_flip = set()
    for (r,c) in to_check:
        n_occupied = 0
        for new_rc in part1_adj[(r,c)]:
            if(part1_occupied[new_rc]):
                n_occupied += 1
        if (not part1_occupied[(r,c)] and n_occupied == 0):
            to_flip.add((r,c))
        elif(part1_occupied[(r,c)] and n_occupied >= 4):
            to_flip.add((r,c))
    for cur_rc in to_flip:
        part1_occupied[cur_rc] = not part1_occupied[cur_rc]
        for new_rc in part1_adj[cur_rc]:
            new_to_check.add(new_rc)
    to_check = new_to_check

part1 = sum(part1_occupied.values())

to_check = part2_occupied.keys()
while(len(to_check)!=0):
    new_to_check = set()
    to_flip = set()
    for (r,c) in to_check:
        n_occupied = 0
        for new_rc in part2_adj[(r,c)]:
            if(part2_occupied[new_rc]):
                n_occupied += 1
        if (not part2_occupied[(r,c)] and n_occupied == 0):
            to_flip.add((r,c))
        elif(part2_occupied[(r,c)] and n_occupied >= 5):
            to_flip.add((r,c))
    for cur_rc in to_flip:
        part2_occupied[cur_rc] = not part2_occupied[cur_rc]
        for new_rc in part2_adj[cur_rc]:
            new_to_check.add(new_rc)
    to_check = new_to_check


    t_seats=  [['.' for _ in range(n_col)] for _ in range(n_row)]
    

part2 = sum(part2_occupied.values())

print(part1)
print(part2)
        