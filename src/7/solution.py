import sys
import os
import re
from collections import deque

with open(os.path.join(sys.path[0], 'input' if len(sys.argv)==1 else 'sample'), "r") as infile:
    lines = infile.read().split("\n")
    
part1 = 0
part2 = 0

contains = dict()
contained_by = dict()

for line in lines:
    words = line.split(" ")
    parent_colour = "".join(words[:2])

    if(len(words)!=7):
        child_words = words[4:]
        n_children = len(child_words)//4
        children = []
        for i in range(n_children):
            child_colour = "".join(child_words[4*i+1:4*i+3])
            child_amt = int(child_words[4*i])
            children.append((child_colour, child_amt))
            
            if(child_colour not in contained_by):
                contained_by[child_colour] = []
            contained_by[child_colour].append(parent_colour)
            
        contains[parent_colour] = children
        
visited_colours = set()
to_visit = deque()
to_visit.append("shinygold")
visited_colours.add("shinygold")

while(len(to_visit) != 0):
    cur_colour = to_visit.popleft()
    if(cur_colour not in contained_by):
        continue
    for next_colour in contained_by[cur_colour]:
        if(next_colour not in visited_colours):
            visited_colours.add(next_colour)
            to_visit.append(next_colour)

n_bags_mem = dict()

def n_bags(colour):
    if(colour not in n_bags_mem):
        n_bags_mem[colour] = 1
        if colour in contains:
            for c_colour, c_num in contains[colour]:
                n_bags_mem[colour] += c_num * n_bags(c_colour)
    return n_bags_mem[colour]

part1 = len(visited_colours)-1
part2 = n_bags("shinygold")-1
print(part1)
print(part2)
    