with open("input", "r") as infile:
    numbers = list(map(int, infile.read().split("\n")))

unique = set(numbers)
if(len(unique) == len(numbers)):
    for n in unique:
        if (2020-n) in unique:
            print(n*(2020-n))
            break
    part2_found = False
    for n in unique:
        for m in unique:
            if(n != m and 2020-n-m in unique and 2020-n-m != n and 2020-n-m != m):
                    print(n*m*(2020-n-m))
                    part2_found = True
                    break
        if(part2_found):
            break
            
    

