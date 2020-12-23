import sys
import os
import re
from collections import deque
with open(os.path.join(sys.path[0], 'input' if len(sys.argv)==1 else 'sample'), "r") as infile:
    players = infile.read().split("\n\n")

player1 = deque(map(int, players[0].split("\n")[1:]))
player2 = deque(map(int, players[1].split("\n")[1:]))

while(len(player1) * len(player2) != 0):
    top1 = player1.popleft()
    top2 = player2.popleft()
    if(top1 > top2):
        player1.append(top1)
        player1.append(top2)
    else:
        player2.append(top2)
        player2.append(top1)
winner = player1 if len(player2) == 0 else player2

i = len(winner)
part1 = 0
while(len(winner)!=0):
    part1 += i*winner.popleft()
    i-=1



with open(os.path.join(sys.path[0], 'input' if len(sys.argv)==1 else 'sample'), "r") as infile:
    players = infile.read().split("\n\n")

player1 = deque(map(int, players[0].split("\n")[1:]))
player2 = deque(map(int, players[1].split("\n")[1:]))


def sub_game(p1, p2):
    prev_rounds = set()
    while(len(p1)*len(p2) != 0):
        if (tuple(p1), tuple(p2)) in prev_rounds:
            return (True, None)
        prev_rounds.add((tuple(p1), tuple(p2)))
        t1 = p1.popleft()
        t2 = p2.popleft()
        if(t1 <= len(p1) and t2 <= len(p2)):
            winner, _ = sub_game(deque(list(p1)[:t1]), deque(list(p2)[:t2]))
        else:
            winner = t1 > t2
        if(winner):
            p1.append(t1)
            p1.append(t2)
        else:
            p2.append(t2)
            p2.append(t1)
    if(len(p1) == 0):
        return (False, p2.copy())
    else:
        return (True, p1.copy())
        
_, winner = sub_game(player1, player2)

i = len(winner)
part2 = 0
while(len(winner)!=0):
    part2 += i*winner.popleft()
    i-=1
    
print(part1)
print(part2)