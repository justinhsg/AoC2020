import sys
import os
import re
with open(os.path.join(sys.path[0], 'input' if len(sys.argv)==1 else 'sample'), "r") as infile:
    sections = infile.read().split("\n\n")


rules = sections[0].split('\n')
ticket = list(map(int, sections[1].split('\n')[1].split(",")))
other_tickets = sections[2].split('\n')[1:]

rule_bounds = dict()
for r in rules:
    r_match = re.match("^([a-z ]+): ([0-9]+)-([0-9]+) (?:or ([0-9]+)-([0-9]+))*$", r)
    rule_name = r_match.group(1)
    n_bounds = (len(r_match.groups())-1)//2
    rule_bounds[rule_name] = []
    for b in range(n_bounds):
        rule_bounds[rule_name].append( (int(r_match.group(2+2*b)), int(r_match.group(3+2*b))) )
part1 = 0

valid_tickets = []
for t in other_tickets:
    values = list(map(int, t.split(",")))
    is_valid = True
    for v in values:
        fits = False
        for rule in rule_bounds:
            for (lb, ub) in rule_bounds[rule]:
                if(lb <= v <= ub):
                    fits = True
                    break
            if(fits):
                break
        if(not fits):
            part1 += v
            is_valid = False
    if(is_valid):
        valid_tickets.append(values)
        assert(len(values) == len(rule_bounds))

candidates = [None for _ in range(len(valid_tickets[0]))]

for pos in range(len(valid_tickets[0])):
    pos_candidates = set(rule_bounds.keys())
    for cand in rule_bounds:
        
        for vt in valid_tickets:
            v = vt[pos]
            fits = False
            for(lb, ub) in rule_bounds[cand]:
                
                if(lb <= v <= ub):
                    fits = True
                    break
            if(not fits):
                pos_candidates.remove(cand)
                break
        
    candidates[pos] = pos_candidates
    
confirmed = [None for _ in range(len(valid_tickets[0]))]
n_confirmed = 0
while(n_confirmed < len(valid_tickets[0])):
    to_remove = set()
    for (pos, cands) in enumerate(candidates):
        if(len(cands) == 1):
            conf = list(cands)[0]
            
            to_remove.add(conf)
            confirmed[pos] = conf
    n_confirmed += len(to_remove)
    for (pos, cands) in enumerate(candidates):
        candidates[pos] = candidates[pos]-to_remove
part2 = 1
for (i, name) in enumerate(confirmed):
    if re.match("^departure", name):
        part2 *= ticket[i]
        
        

    
    
print(part1)
print(part2)