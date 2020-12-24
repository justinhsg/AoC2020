import sys
import os
import re
from collections import deque
with open(os.path.join(sys.path[0], 'input' if len(sys.argv)==1 else 'sample'), "r") as infile:
    cross = list(map(list, infile.read().split("\n")))

in_size = len(cross)

cube = [ 
            [ 
                ["." for c in range(in_size+6*2)] if r < 6 or r >= in_size+6 else \
                ["." if (c < 6 or c >= in_size+6) else cross[r-6][c-6] for c in range(in_size+6*2)] 
            for r in range(in_size+6*2)] if l==6 else
            [
                ["." for _ in range(in_size+6*2)]
            for _ in range(in_size+6*2)]
        for l in range(1+6*2)]

def get_neighbour(l,r,c):
    n = 0
    for nl in [l-1, l, l+1]:
        for nr in [r-1, r, r+1]:
            for nc in [c-1, c, c+1]:
                if(0 <= nl < 1+6*2 and 0 <= nr < in_size+6*2 and 0 <= nc < in_size+6*2):
                    if(cube[nl][nr][nc] == '#'):
                        n += 1
    if(cube[l][r][c] == '#'):
        n-=1
    return n
        
to_check = set()
for nl in range(6-1, 6+1+1):
    for nr in range(6-1, 6+in_size+1):
        for nc in range(6-1, 6+in_size+1):
            to_check.add((nl,nr,nc))

n_cycle = 0
while(n_cycle != 6):
    n_cycle += 1
    to_flip = set()
    for (l,r,c) in to_check:
        neighbours = get_neighbour(l,r,c)
        if(cube[l][r][c] == '#' and not(2<= neighbours <= 3)):
            to_flip.add((l,r,c))
        elif(cube[l][r][c] == '.' and neighbours == 3):
            to_flip.add((l,r,c))
    to_check.clear()
    for (l,r,c) in to_flip:
        cube[l][r][c] = '#' if cube[l][r][c] == '.' else '.'
        for nl in [l-1, l, l+1]:
            for nr in [r-1, r, r+1]:
                for nc in [c-1, c, c+1]:
                    if(0 <= nl < in_size+6*2 and 0 <= nr < in_size+6*2 and 0 <= nc < in_size+6*2 and not(nl==nr==nc==0)):
                        to_check.add((nl, nr, nc))

part1 = 0
for l in cube:
    for r in l:
        for c in r:
            if c == '#':
                part1 += 1

hypercube = [
                [ 
                    [ 
                        ["." for c in range(in_size+6*2)] if r < 6 or r >= in_size+6 else \
                        ["." if (c < 6 or c >= in_size+6) else cross[r-6][c-6] for c in range(in_size+6*2)] 
                    for r in range(in_size+6*2)] if l==6 else
                    [
                        ["." for _ in range(in_size+6*2)]
                    for _ in range(in_size+6*2)]
                for l in range(1+6*2)] if t == 6 else
                [
                    [
                        ["." for _ in range(in_size+6*2)]
                    for _ in range(in_size+6*2)]
                for _ in range (1+6*2)]
            for t in range(1+6*2)]
            
def get_neighbour_4d(t, l,r,c):
    n = 0
    for nt in [t-1, t, t+1]:
        for nl in [l-1, l, l+1]:
            for nr in [r-1, r, r+1]:
                for nc in [c-1, c, c+1]:
                    if(0<= nt < 1+6*2 and 0 <= nl < 1+6*2 and 0 <= nr < in_size+6*2 and 0 <= nc < in_size+6*2):
                        if(hypercube[nt][nl][nr][nc] == '#'):
                            n += 1
    if(hypercube[t][l][r][c] == '#'):
        n-=1
    return n

to_check = set()
for nt in range(6-1, 6+1+1):
    for nl in range(6-1, 6+1+1):
        for nr in range(6-1, 6+in_size+1):
            for nc in range(6-1, 6+in_size+1):
                to_check.add((nt, nl,nr,nc))

n_cycle = 0
while(n_cycle != 6):
    n_cycle += 1
    to_flip = set()
    for (t, l,r,c) in to_check:
        neighbours = get_neighbour_4d(t, l,r,c)
        if(hypercube[t][l][r][c] == '#' and not(2<= neighbours <= 3)):
            to_flip.add((t,l,r,c))
        elif(hypercube[t][l][r][c] == '.' and neighbours == 3):
            to_flip.add((t,l,r,c))

    to_check.clear()
    for (t,l,r,c) in to_flip:
        hypercube[t][l][r][c] = '#' if hypercube[t][l][r][c] == '.' else '.'
        for nt in [t-1, t, t+1]:
            for nl in [l-1, l, l+1]:
                for nr in [r-1, r, r+1]:
                    for nc in [c-1, c, c+1]:
                        if(0 <= nt <= 1+6*2 and 0 <= nl < 1+6*2 and 0 <= nr < in_size+6*2 and 0 <= nc < in_size+6*2 and not(nt==nl==nr==nc==0)):
                            to_check.add((nt, nl, nr, nc))

part2 = 0
for t in hypercube:
    for l in t:
        for r in l:
            for c in r:
                if c == '#':
                    part2 += 1
                    
print(part1)
print(part2)