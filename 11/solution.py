
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
to_check = set()

for r in range(n_row):
    for c in range(n_col):
        if(seats[r][c] == 'L'):
            to_check.add((r,c))
            

it = 0
while(len(to_check)!=0):
    new_to_check = set()
    to_flip = set()
    for (r,c) in to_check:
        assert(seats[r][c] != '.')
        n_occupied = 0
        for d in range(8):
            new_r = r+d_row[d]
            new_c = c+d_col[d]
            if(0<= new_r < n_row and 0<=new_c<n_col):
                if(seats[new_r][new_c] == '#'):
                    n_occupied += 1
        if (seats[r][c] == 'L' and n_occupied == 0):
            to_flip.add((r,c))
    
        elif(seats[r][c] == '#' and n_occupied >= 4):
            to_flip.add((r,c))
    for (r,c) in to_flip:
        assert(seats[r][c] != '.')
        seats[r][c] = '#' if seats[r][c] == 'L' else 'L'    
        for d in range(8):
            new_r = r+d_row[d]
            new_c = c+d_col[d]
            if(0<= new_r < n_row and 0<=new_c<n_col):
                if(seats[new_r][new_c] != '.'):
                    new_to_check.add((new_r, new_c))
    to_check = new_to_check
    #print(f"Iteration {it}")
    #for r in seats:
#        print("".join(r))
    #print()
    it += 1
part1 = 0
for r in seats:
    for seat in r:
        if(seat == '#'):
            part1 += 1


with open(os.path.join(sys.path[0], 'input' if len(sys.argv)==1 else 'sample'), "r") as infile:
    seats = list(map(lambda x: list(x), infile.read().split("\n")))
      
for r in range(n_row):
    for c in range(n_col):
        if(seats[r][c] == 'L'):
            to_check.add((r,c))    
while(len(to_check)!=0):
    new_to_check = set()
    to_flip = set()
    for (r,c) in to_check:
        assert(seats[r][c] != '.')
        n_occupied = 0
        for d in range(8):
            
            steps = 1
            while(True):
                new_r = r+d_row[d]*steps
                new_c = c+d_col[d]*steps
                
                
                if(not (0<= new_r < n_row and 0<=new_c<n_col)):
                    break
                if(seats[new_r][new_c] == '#'):
                    n_occupied+=1
                    break
                elif(seats[new_r][new_c] == 'L'):
                    break
                else:
                    steps += 1
        if (seats[r][c] == 'L' and n_occupied == 0):
            to_flip.add((r,c))
    
        elif(seats[r][c] == '#' and n_occupied >= 5):
            to_flip.add((r,c))
    for (r,c) in to_flip:
        assert(seats[r][c] != '.')
        seats[r][c] = '#' if seats[r][c] == 'L' else 'L'    
        for d in range(8):
            steps = 1
            while(True):
                new_r = r+d_row[d]*steps
                new_c = c+d_col[d]*steps
                if(not (0<= new_r < n_row and 0<=new_c<n_col)):
                    break
                elif seats[new_r][new_c] != '.':
                    new_to_check.add((new_r, new_c))
                    break
                steps += 1
    to_check = new_to_check
    
part2 = 0
for r in seats:
    for seat in r:
        if(seat == '#'):
            part2 += 1

print(part1)
print(part2)
        