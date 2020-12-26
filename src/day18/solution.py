import sys
import os
import re
from collections import deque
with open(os.path.join(sys.path[0], 'input' if len(sys.argv)==1 else 'sample'), "r") as infile:
    lines = infile.read().split("\n")

def try_process(val_stack, op_stack):
    if(len(val_stack) >= 2 and len(op_stack)>=1):
        if(op_stack[-1] == "+" or op_stack[-1] == "*" ):
            t1 = val_stack.pop()
            t2 = val_stack.pop()
            res = t1+t2 if op_stack.pop()=="+" else t1*t2
            val_stack.append(res)


part1 = 0
part2 = 0
for line in lines:
    '''
    op_stack = deque()
    val_stack = deque()
    bracket_depth = 0
    symbols = list(line.replace(" ",""))
    for s in symbols:
        if(s == " "):
            continue
        elif(ord('0') <= ord(s) <= ord('9')):
            val_stack.append(int(s))
            try_process(val_stack, op_stack)
        elif(s == '+' or s == '*'):
            op_stack.append(s)
        elif(s == '('):
            bracket_depth += 1
            op_stack.append(s)
        elif(s == ')'):
            bracket_depth -= 1
            op_stack.pop()
            try_process(val_stack, op_stack)
    solution = val_stack.pop()
    part1 += solution
    '''
    symbols = list(line.replace(" ",""))
    rpn = []
    op_stack = deque(['$'])
    for s in symbols:
        if(ord('0') <= ord(s) <= ord('9')):
            rpn.append((0,int(s)))
        elif(s == '+' or s=='*'):
            while(op_stack[-1] != '$' and op_stack[-1]!='('):
                rpn.append((1, op_stack.pop()))
            op_stack.append(s)
        elif(s == '('):
            op_stack.append('(')
        elif(s == ')'):
            while(op_stack[-1]!='('):
                rpn.append((1, op_stack.pop()))
            op_stack.pop()
    while(op_stack[-1]!='$'):
        rpn.append((1, op_stack.pop()))
    
    
    acc = deque()
    for (code, val) in rpn:
        if(code == 0):
            acc.append(val)
        else:
            t1 = acc.pop()
            t2 = acc.pop()
            acc.append(t1+t2 if val=="+" else t1*t2)
    solution = acc[0]
    part1+=solution
    
    

    rpn = []
    op_stack = deque(['$'])
    for s in symbols:
        if(ord('0') <= ord(s) <= ord('9')):
            rpn.append((0,int(s)))
        elif(s == '+'):
            op_stack.append('+')
        elif(s == '*'):
            while(op_stack[-1]=='+'):
                rpn.append((1, op_stack.pop()))
            op_stack.append('*')
        elif(s == '('):
            op_stack.append('(')
        elif(s == ')'):
            while(op_stack[-1]!='('):
                rpn.append((1, op_stack.pop()))
            op_stack.pop()
    while(op_stack[-1]!='$'):
        rpn.append((1, op_stack.pop()))
    
    
    acc = deque()
    for (code, val) in rpn:
        if(code == 0):
            acc.append(val)
        else:
            t1 = acc.pop()
            t2 = acc.pop()
            acc.append(t1+t2 if val=="+" else t1*t2)
    solution = acc[0]
    
    part2 += solution

    
    

print(part1)    
print(part2)


        