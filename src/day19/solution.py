import sys
import os
import re
from collections import deque
with open(os.path.join(sys.path[0], 'input' if len(sys.argv)==1 else 'sample'), "r") as infile:
    sections = infile.read().split("\n\n")

rules = sections[0].split("\n")

max_rule = 0
for rule in rules:
      m = re.match("^([0-9]+): ([0-9|\"ab ]+)$", rule)
    max_rule = max(max_rule, int(m.group(1)))
rule_arr = [None for _ in range(max_rule+1)]

rule_gen = [None for _ in range(max_rule + 1)]

for rule in rules:
    m = re.match("^([0-9]+): ([0-9|\"ab ]+)$", rule)
    rule_id = int(m.group(1))
    body = m.group(2)
    m2 = re.match("^\"(a|b)\"$", body)
    if(m2):
        rule_arr[rule_id] = m2.group(1)
        rule_gen[rule_id] = m2.group(1)
    else:
        rule_arr[rule_id] = list(map(lambda x: tuple(map(int, x.split(" "))), body.split(" | ")))


strings = sections[1].split("\n")



#rule_arr[8] = [(42,), (42, 8)]
#rule_arr[11] = [(42, 31), (42, 11, 31)]

def gen(r):
    if(rule_arr[r] == 'a' or rule_arr[r] == 'b'):
        rule_gen[r] = [rule_arr[r]]
        return rule_gen[r]
    if(rule_gen[r] is not None ):
        return rule_gen[r]
    else:
        rule_gen[r] = []
        for rule_tup in rule_arr[r]:
            gens = ['']
            for next_r in rule_tup:
                t_gen = []
                next_gen = gen(next_r)
                for g1 in gens:
                    for g2 in next_gen:
                        t_gen.append(g1+g2)
                gens = t_gen
            rule_gen[r] = rule_gen[r] + t_gen
        return rule_gen[r]

list_31 = gen(31)
list_42 = gen(42)
set_31 = set(list_31)
set_42 = set(list_42)
len_31 = len(list_31[0])
len_42 = len(list_42[0])


def attempt_match_31(s):
    if(s==''):
        return 0
    elif(len(s)<len_31):
        return None
    elif(s[:len_31] in set_31):
        match_next = attempt_match_31(s[len_31:])
        if(match_next is not None):
            return 1+match_next
        else:
            return None
    else:
        return None

def attempt_match_42(s):
    if(len(s)<len_42):
        return None, None
    elif(s[:len_42] in set_42):
        n_42, n_31 = attempt_match_42(s[len_42:])
        if(n_31 is not None):
            return (1+n_42, n_31)
        else:
            return (None, None)
    else:
        n_31 = attempt_match_31(s)
        if(n_31 is not None):
            return (0, n_31)
        else:
            return None,None


part1 = 0
part2 = 0
for string in strings:
    n_42, n_31 = attempt_match_42(string)
    if(n_42 is not None and n_31 is not None):
        if(n_42 == 2 and n_31 == 1):
            part1 += 1
        if(n_42 > n_31):
            part2+=1

print(part1)
print(part2)

