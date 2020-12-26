import sys
import os
import re
import math
from collections import deque
day_number = sys.path[0].split('\\')[-1]
if len(sys.argv)==1:
    path_to_source = os.path.join("\\".join(sys.path[0].split("\\")[:-2]), f"input\\{day_number}")
else:
    path_to_source = os.path.join("\\".join(sys.path[0].split("\\")[:-2]), f"sample\\{day_number}")
with open(path_to_source, "r") as infile:
    lines = infile.read().split("\n")

pub_key_1 = int(lines[0])
pub_key_2 = int(lines[1])


def find_discrete_log(base, target, p):
    lut = dict()
    m = math.ceil((p-1)**0.5)
    for j in range(m):
        lut[pow(base, j, p)] = j
    base_pow_neg_m = pow(base, (p-1-m), p)
    assert(base_pow_neg_m * pow(base, m, p) % p == 1)
    gamma = target
    for i in range(m):
        if(gamma in lut):
            return i*m+lut[gamma]
        else:
            gamma = (gamma*base_pow_neg_m)%p
    
loop_1 =  find_discrete_log(7, pub_key_1, 20201227)
part1 = pow(pub_key_2, loop_1, 20201227)
print(part1)