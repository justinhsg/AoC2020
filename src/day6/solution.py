import sys
import os
import re
day_number = sys.path[0].split('\\')[-1]
if len(sys.argv)==1:
    path_to_source = os.path.join("\\".join(sys.path[0].split("\\")[:-2]), f"input\\{day_number}")
else:
    path_to_source = os.path.join("\\".join(sys.path[0].split("\\")[:-2]), f"sample\\{day_number}")
with open(path_to_source, "r") as infile:
    groups = infile.read().split("\n\n")
    
part1 = 0
part2 = 0


for group in groups:
    answers = set()
    commons = set([chr(ord("a")+i) for i in range(26)])
    persons = re.split(" |\n", group)
    for person in persons:
        for qn in person:
            answers.add(qn)
        commons = commons & set([c for c in person])
    part1 += len(answers)
    part2 += len(commons)
print(part1)
print(part2)