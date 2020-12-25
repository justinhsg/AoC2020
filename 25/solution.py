import sys
import os
import re
import math
from collections import deque
with open(os.path.join(sys.path[0], 'input' if len(sys.argv)==1 else 'sample'), "r") as infile:
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