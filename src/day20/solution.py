import sys
import os
import re
from collections import deque
with open(os.path.join(sys.path[0], 'input' if len(sys.argv)==1 else 'sample'), "r") as infile:
    tiles = infile.read().split("\n\n")

#Map tile_id to full tile
full_tiles = dict()

#Map border to ids
borders = dict()
#Map ids to borders
border_info = dict()

tile_size = 10

for tile in tiles:
    lines = tile.split("\n")[:11]
    m = re.match("^Tile ([0-9]+):$", lines[0])
    tile_id = int(m.group(1))
    
    full_tiles[tile_id] = lines[1:]
    
    left_border = "".join(map(lambda x: x[0], lines[1:]))[::-1]
    if(left_border not in borders):
        borders[left_border] = []
        borders[left_border[::-1]] = []
    borders[left_border].append((tile_id, 0, False))
    borders[left_border[::-1]].append((tile_id, 0, True))
    
    top_border = lines[1]
    if(top_border not in borders):
        borders[top_border] = []
        borders[top_border[::-1]] = []
    borders[top_border].append((tile_id, 1, False))
    borders[top_border[::-1]].append((tile_id, 1, True))
    
    right_border = "".join(map(lambda x: x[-1], lines[1:]))
    if(right_border not in borders):
        borders[right_border] = []
        borders[right_border[::-1]] = []
    borders[right_border].append((tile_id, 2, False))
    borders[right_border[::-1]].append((tile_id, 2, True))
    
    bottom_border = lines[-1][::-1]
    if(bottom_border not in borders):
        borders[bottom_border] = []
        borders[bottom_border[::-1]] = []
    borders[bottom_border].append((tile_id, 3, False))
    borders[bottom_border[::-1]].append((tile_id, 3, True))
    
    border_info[tile_id] = (left_border, top_border, right_border, bottom_border)

#maps tiles to other tiles that share a border
TL_id = None
start_rot = None
part1 = 1
for tile_id in border_info:
    n_adj = 0
    matched_edges = 0
    for (side, border) in enumerate(border_info[tile_id]):
        for (other_tile_id, other_side, reversed) in borders[border]:
            if(other_tile_id != tile_id):
                n_adj +=1
                matched_edges += 1<<side
    if(n_adj == 2):
        part1 *= tile_id
        if(TL_id is None):
            TL_id = tile_id
            start_rot = {6:1, 12:0, 8:3, 3:2}[matched_edges]


def rot_tile(tile, rot):
    if(rot == 0):
        return tile
    else:
        t_tile = [ "".join([tile[::-1][col_id][row_id] for col_id in range(len(tile))]) for row_id in range(len(tile))]
        return rot_tile(t_tile, rot-1)

def transpose_tile(tile):
    return [ "".join([tile[col_id][row_id] for col_id in range(len(tile))]) for row_id in range(len(tile))]

def apply_transform(tile, n_rot, transpose):
    if(transpose):
        t_tile = transpose_tile(tile)
        return rot_tile(t_tile, n_rot)
    else:
        return rot_tile(tile, n_rot)

transpose_swaps = [1,0,3,2]
def get_transform(side, reversed, req_side):
    if(reversed):
        t_side = transpose_swaps[side]
        return ((req_side-t_side)%4, True)
    else:
        return ((req_side-side)%4, False)
        
def get_border_from_transform(req_side, n_rot, transposed):
    if(transposed):
        return (transpose_swaps[(req_side-n_rot)%4], True)
    else:
        return ((req_side-n_rot)%4                 , False)

image_tile_width = round(len(full_tiles)**0.5)
image_transforms = [[None for _ in range(image_tile_width)] for _ in range(image_tile_width)]

image_transforms[0][0] = (TL_id, start_rot, False)

full_image = [[None for _ in range(image_tile_width*(tile_size-2))] for _ in range(image_tile_width*(tile_size-2))]

for row in range(image_tile_width):
    for col in range(image_tile_width):
        #print(row, col)
        if(row == col == 0):
            next_id, next_rot, next_trans = image_transforms[row][col]
            next_tile = apply_transform(full_tiles[next_id], next_rot, next_trans)
        elif(col == 0):
            prev_id, prev_rot, prev_trans = image_transforms[row-1][col]
            prev_side, prev_reverse = get_border_from_transform(3, prev_rot, prev_trans)
            prev_border = border_info[prev_id][prev_side]
            if prev_reverse:
                prev_border = prev_border[::-1]
            next_border = prev_border[::-1]
            for (next_id, next_side, next_rev) in borders[next_border]:
                if(next_id != prev_id):
                    next_rot, next_trans = get_transform(next_side, next_rev, 1)
                    image_transforms[row][col] = (next_id, next_rot, next_trans)
                    next_tile = apply_transform(full_tiles[next_id], next_rot, next_trans)

        else:
            prev_id, prev_rot, prev_trans = image_transforms[row][col-1]
            prev_side, prev_reverse = get_border_from_transform(2, prev_rot, prev_trans)
            prev_border = border_info[prev_id][prev_side]
            if prev_reverse:
                prev_border = prev_border[::-1]
            next_border = prev_border[::-1]
            for (next_id, next_side, next_rev) in borders[next_border]:
                if(next_id != prev_id):
                    next_rot, next_trans = get_transform(next_side, next_rev, 0)
                    image_transforms[row][col] = (next_id, next_rot, next_trans)
                    next_tile = apply_transform(full_tiles[next_id], next_rot, next_trans)
                    
        for img_r in range(row*(tile_size-2), (row+1)*(tile_size-2)):
            for img_c in range(col*(tile_size-2), (col+1)*(tile_size-2)):
                full_image[img_r][img_c] = next_tile[img_r%(tile_size-2)+1][img_c%(tile_size-2)+1]
    
with open(os.path.join(sys.path[0], 'monster'), "r") as infile:
    monster = infile.read().split("\n")

coords = []
for r_i, r in enumerate(monster):
    for c_i, c in enumerate(r):
        if(c == '#'):
            coords.append((r_i, c_i))

def find_monster(full_image):
    n_monster = 0
    for row in range(image_tile_width*(tile_size-2)-len(monster)):
        for col in range(image_tile_width*(tile_size-2)-len(monster[0])):
            is_monster = True
            for (dr, dc) in coords:
                is_monster = is_monster and (full_image[row+dr][col+dc] == '#')
                if(not is_monster):
                    break
            if(is_monster):
                n_monster += 1
    return n_monster
part2 = 0
for row in full_image:
    for c in row:
        part2 += 1 if c=='#' else 0

for rot in range(4):
    new_image = apply_transform(full_image, rot, False)
    n_monster = find_monster(new_image)
    part2 -= n_monster*len(coords)
    new_image = apply_transform(full_image, rot, True)        
    n_monster = find_monster(new_image)
    part2 -= n_monster*len(coords)


print(part1)
print(part2)