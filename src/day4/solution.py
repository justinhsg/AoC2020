import sys
import os
import re
day_number = sys.path[0].split('\\')[-1]
if len(sys.argv)==1:
    path_to_source = os.path.join("\\".join(sys.path[0].split("\\")[:-2]), f"input\\{day_number}")
else:
    path_to_source = os.path.join("\\".join(sys.path[0].split("\\")[:-2]), f"sample\\{day_number}")
with open(path_to_source, "r") as infile:
    data = infile.read().split("\n\n")
    
req_fields = set(['byr', 'iyr', 'eyr', 'hgt', 'hcl', 'ecl', 'pid', 'cid'])
part1 = 0
part2 = 0
def validate(key, value):
    if(key == 'byr'):
        return re.match('19[02-9][0-9]|200[0-2]$', value) is not None
    elif(key=='iyr'):
        return re.match('^201[0-9]|2020$', value) is not None
    elif(key == 'eyr'):
        return re.match('^202[0-9]|2030$', value) is not None
    elif(key == 'hgt'):
        return re.match('^(?:(?:15[0-9]|1[6-8][0-9]|19[0-3])cm)|(?:(?:59|6[0-9]|7[0-6])in)$', value) is not None
    elif(key == 'hcl'):
        return re.match('^#(?:[a-f]|[0-9]){6}$', value) is not None
    elif(key == 'ecl'):
        return re.match('^amb|blu|brn|gry|grn|hzl|oth$', value) is not None
    elif(key == 'pid'):
        return re.match('^[0-9]{9}$', value) is not None
    elif(key == 'cid'):
        return True

for entry in data:
    fields = re.split("\n| ", entry)
    headers = set()
    info = dict()
    valid = True
    for field in fields:
        [key, val] = field.split(":")
        headers.add(key)
        valid = valid & validate(key, val)
    diff_set = req_fields - headers
    if(len(diff_set) == 0 or (len(diff_set)==1 and 'cid' in diff_set)):
        part1 += 1
        if(valid):
            part2 += 1
print(part1)
print(part2)
    