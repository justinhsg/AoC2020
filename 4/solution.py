import sys
import os
import re
with open(os.path.join(sys.path[0], 'input' if len(sys.argv)==1 else 'sample'), "r") as infile:
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
    fields = re.split("\n| |:", entry)
    headers = set()
    info = dict()
    for i in range(len(fields)//2):
        headers.add(fields[i*2])
    diff_set = req_fields - headers
    if(len(diff_set) == 0 or (len(diff_set)==1 and 'cid' in diff_set)):
        part1 += 1
        valid = True
        for i in range(len(fields)//2):
            valid = valid & validate(fields[i*2], fields[i*2+1])
        if(valid):
            part2 += 1
print(part1)
print(part2)
    