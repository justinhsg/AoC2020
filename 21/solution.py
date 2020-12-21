import sys
import os
import re
from collections import deque
with open(os.path.join(sys.path[0], 'input' if len(sys.argv)==1 else 'sample'), "r") as infile:
    lines = infile.read().split("\n")

all_allergens = set()
all_ingredients = set()

allergen_pos = dict()

ingred_list = []

for line in lines:
    m = re.match("^((?:[a-z]+ )+)\(contains ([a-z]+(?:, [a-z]+)*)\)$", line)
    ingreds =m.group(1).split(" ")[:-1]
    allergens = m.group(2).split(", ")
    
    for a in allergens:
        if a not in allergen_pos:
            allergen_pos[a]  = []
        allergen_pos[a].append(set(ingreds))
    
    all_allergens   |= set(allergens)
    all_ingredients |= set(ingreds)
    ingred_list.append(set(ingreds))

n_confirmed = 0
to_process = set()
for a in allergen_pos:
    possibles = all_ingredients.copy()
    for ing_set in allergen_pos[a]:
        possibles &= ing_set
    
    allergen_pos[a] = possibles
    
    to_process.add(a)
        
while(n_confirmed != len(all_allergens)):

    to_remove_a = set()
    to_remove_i = set()
    for a in to_process:
        if(len(allergen_pos[a]) == 1):
            to_remove_a.add(a)
            to_remove_i |= allergen_pos[a]
            n_confirmed += 1
    for a in to_remove_a:
        to_process.remove(a)
    for a in to_process:
        allergen_pos[a] = allergen_pos[a] - to_remove_i
        

has_allergens = set()

allergen_list = []
for a in allergen_pos:
    has_allergens |= allergen_pos[a]
    allergen_list.append((allergen_pos[a].pop(), a))


part1 = 0
for s in ingred_list:
    part1 += len(s-has_allergens)


sorted_canon = sorted(allergen_list, key=lambda x:x[1])
part2 = ",".join(map(lambda x:x[0], sorted_canon))
print(part1)
print(part2)
