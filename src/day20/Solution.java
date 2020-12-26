package day20;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utils.AoCSolvable;
import utils.NoSolutionException;
import utils.Pair;

public class Solution implements AoCSolvable {
  private static final boolean USE_SAMPLE = false;
  private static final int TILE_SIZE = 10;
  private static final Pattern TITLE_PATTERN = Pattern.compile("^Tile ([0-9]+):$");

  private enum Side{
    LEFT(0),
    TOP(1),
    RIGHT(2),
    BOTTOM(3);

    private final int val;
    private Side(int val){
      this.val = val;
    }

  }
  static private final Side[] SIDES = Side.values();

  static private class BorderInfo{
    private int tileId;
    private Side side;
    private boolean isReversed;

    BorderInfo(int tileId, Side side, boolean isReversed){
      this.tileId = tileId;
      this.side = side;
      this.isReversed = isReversed;
    }
  }

  static private class TransformInfo{
    private int tileId;
    private int nRot;
    private boolean isTransposed;

    TransformInfo(int tileId, int nRot, boolean isTransposed){
      this.tileId=tileId;
      this.nRot=nRot;
      this.isTransposed = isTransposed;
    }

    @Override
    public String toString(){
      return String.format("%d: Rotate %d, Flip: %b", tileId, nRot, isTransposed);
    }
  }

  private final Map<Integer, char[][]> idToTile;
  private final Map<String, List<BorderInfo>> borderToInfo;
  private final Map<Integer, String[]> idToBorders;
  private final int nTiles;

  Solution(List<String> lines) throws NoSolutionException {
    nTiles = ((lines.size()+1)/12);
    idToBorders = new HashMap<>();
    borderToInfo = new HashMap<>();
    idToTile = new HashMap<>();
    for(int i = 0; i<nTiles; i++){
      Matcher m = TITLE_PATTERN.matcher(lines.get(i*12));
      if(m.matches()){
        int tileId = Integer.parseInt(m.group(1));
        char[][] tile = new char[TILE_SIZE][TILE_SIZE];
        for(int j = 0; j<TILE_SIZE; j++){
          tile[j] = lines.get(i*12+j+1).toCharArray();
        }
        char[][] charBorders = new char[4][TILE_SIZE];
        for(int j = 0; j<TILE_SIZE; j++){
          charBorders[Side.LEFT.val  ][j] = tile[TILE_SIZE-1-j][0];
          charBorders[Side.TOP.val   ][j] = tile[0][j];
          charBorders[Side.RIGHT.val ][j] = tile[j][TILE_SIZE-1];
          charBorders[Side.BOTTOM.val][j] = tile[TILE_SIZE-1][TILE_SIZE-1-j];
        }

        String[] stringBorders = Arrays.stream(charBorders).map((x) -> String.valueOf(x)).toArray(String[]::new);
        idToTile.put(tileId, tile);
        idToBorders.put(tileId, stringBorders);

        for(Side side: SIDES){
          String border = stringBorders[side.val];
          List<BorderInfo> infos = borderToInfo.getOrDefault(border, new ArrayList<>());
          infos.add(new BorderInfo(tileId, side, false));
          borderToInfo.put(border, infos);

          String revBorder = new StringBuilder(border).reverse().toString();
          List<BorderInfo> revInfos = borderToInfo.getOrDefault(revBorder, new ArrayList<>());
          revInfos.add(new BorderInfo(tileId, side, true));
          borderToInfo.put(revBorder, revInfos);
        }
      } else{
        throw new NoSolutionException();
      }
    }
  }

  private static char[][] rotate(char[][] grid, int amount){
    if(amount == 0){
      return Arrays.stream(grid).map(char[]::clone).toArray(char[][]::new);
    } else {
      char[][] newGrid = new char[grid[0].length][grid.length];
      for(int r = 0; r<grid.length; r++){
        for(int c = 0; c<grid[r].length; c++){
          newGrid[c][grid.length-1-r] = grid[r][c];
        }
      }
      if(amount == 1) return newGrid;
      else return rotate(newGrid, amount-1);
    }
  }

  private static char[][] transpose(char[][] grid){
    char[][] newGrid = new char[grid[0].length][grid.length];
    for(int r = 0; r<grid.length; r++){
      for(int c = 0; c<grid[r].length; c++){
        newGrid[c][r] = grid[r][c];
      }
    }
    return newGrid;
  }

  private static char[][] applyTransform(char[][] grid, int nRot, boolean transpose){
    return rotate(transpose?transpose(grid):grid, nRot);
  }

  private static final Side[] TRANSPOSE_SWAPS = new Side[]{Side.TOP, Side.LEFT, Side.BOTTOM, Side.RIGHT};
  private static Pair<Integer, Boolean> getTransform(Side curSide, boolean reversed, Side reqSide){
    Side tSide = reversed?TRANSPOSE_SWAPS[curSide.val]:curSide;
    return new Pair<>(Math.floorMod(reqSide.val-tSide.val,4), reversed);
  }
  private static Pair<Integer, Boolean> getBorderFromTransform(Side reqSide, int nRot, boolean transposed){
    return new Pair<>(transposed?TRANSPOSE_SWAPS[Math.floorMod(reqSide.val-nRot, 4)].val:Math.floorMod(reqSide.val-nRot, 4), transposed);
  }

  private static final List<Pair<Integer, Integer>> MONSTER_COORDS = new ArrayList<>();
  private static final String MONSTER = "                  # \n"
                                      + "#    ##    ##    ###\n"
                                      + " #  #  #  #  #  #   ";
  private static final char[][] MONSTER_ARRAY= Arrays.stream(MONSTER.split("\n")).map(String::toCharArray).toArray(char[][]::new);
  static{
    for(int r = 0; r<MONSTER_ARRAY.length; r++){
      for(int c = 0; c<MONSTER_ARRAY[r].length; c++){
        if(MONSTER_ARRAY[r][c]=='#') MONSTER_COORDS.add(new Pair(r,c));
      }
    }
  }

  public String partOne() throws NoSolutionException {
    long solution = 1L;
    for(int tileId: idToBorders.keySet()){
      int nMatches = 0;
      for (String border: idToBorders.get(tileId)){
        for(BorderInfo bi: borderToInfo.get(border)){
          if(bi.tileId != tileId){
            nMatches+=1;
            break;
          }
        }
      }
      if(nMatches == 2) solution *= tileId;
    }
    return String.valueOf(solution);
  }

  public String partTwo() throws NoSolutionException {
    int startingTileId = -1;
    for(int tileId: idToBorders.keySet()){
      int nMatches = 0;
      for (String border: idToBorders.get(tileId)){
        for(BorderInfo bi: borderToInfo.get(border)){
          if(bi.tileId != tileId){
            nMatches+=1;
            break;
          }
        }
      }
      if(nMatches == 2){
        startingTileId = tileId;
        break;
      }
    }
    int matchedSides = 0;
    for(int side = 0; side<4; side++){

      String border = idToBorders.get(startingTileId)[side];
      for(BorderInfo bi: borderToInfo.get(border)){
        if(bi.tileId != startingTileId){
          matchedSides += 1<<side;
        }
      }
    }

    int widthInTiles = (int)Math.sqrt(nTiles);
    TransformInfo[][] transforms = new TransformInfo[widthInTiles][widthInTiles];
    switch(matchedSides){
      case 6:
        transforms[0][0] = new TransformInfo(startingTileId, 1, false);
        break;
      case 12:
        transforms[0][0] = new TransformInfo(startingTileId, 0, false);
        break;
      case 9:
        transforms[0][0] = new TransformInfo(startingTileId, 3, false);
        break;
      case 3:
        transforms[0][0] = new TransformInfo(startingTileId, 2, false);
        break;
      default:
        throw new NoSolutionException();
    }
    char[][] fullPicture = new char[widthInTiles*(TILE_SIZE-2)][widthInTiles*(TILE_SIZE-2)];
    char[][] transformedStartTile = applyTransform(idToTile.get(startingTileId), transforms[0][0].nRot, transforms[0][0].isTransposed);
    int nHashes = 0;
    for(int i = 0; i<TILE_SIZE-2; i++){
      for(int j = 0; j<TILE_SIZE-2; j++){
        fullPicture[i][j] = transformedStartTile[i+1][j+1];
        if(transformedStartTile[i+1][j+1]=='#') nHashes += 1;
      }
    }

    for(int tileRow = 0; tileRow < widthInTiles; tileRow++){
      for(int tileCol = 0; tileCol < widthInTiles; tileCol++){
        if(tileCol == 0  && tileRow == 0) continue;
        else {
          TransformInfo prevTransform = tileCol==0?transforms[tileRow-1][0]:transforms[tileRow][tileCol-1];
          Pair<Integer, Boolean> prevBorderInfo = getBorderFromTransform(tileCol==0?Side.BOTTOM:Side.RIGHT, prevTransform.nRot, prevTransform.isTransposed);
          String prevBorder = idToBorders.get(prevTransform.tileId)[prevBorderInfo.fst];
          if(!prevBorderInfo.snd) prevBorder = new StringBuilder(prevBorder).reverse().toString();
          for (BorderInfo nextBorderInfo: borderToInfo.get(prevBorder)){

            if(nextBorderInfo.tileId != prevTransform.tileId){
              Pair<Integer, Boolean> nextTransform = getTransform(nextBorderInfo.side, nextBorderInfo.isReversed, tileCol==0?Side.TOP:Side.LEFT);
              transforms[tileRow][tileCol] = new TransformInfo(nextBorderInfo.tileId, nextTransform.fst, nextTransform.snd);
            }
          }
          char[][] transformedTile = applyTransform(idToTile.get(transforms[tileRow][tileCol].tileId),
                                                      transforms[tileRow][tileCol].nRot,
                                                      transforms[tileRow][tileCol].isTransposed);
          for(int r = 0; r<TILE_SIZE-2; r++){
            for(int c = 0; c<TILE_SIZE-2; c++){
              fullPicture[tileRow*(TILE_SIZE-2)+r][tileCol*(TILE_SIZE-2)+c] = transformedTile[r+1][c+1];
              if(transformedTile[r+1][c+1]=='#') nHashes += 1;
            }
          }
        }
      }
    }
    int monsterCount = 0;
    for(int nRot = 0; nRot<4; nRot++){
      char[][] newPicture = applyTransform(fullPicture, nRot, false);

      for(int row = 0; row<newPicture.length-MONSTER_ARRAY.length; row++){
        for(int col = 0; col < newPicture[0].length-MONSTER_ARRAY[0].length; col++){
          boolean isMonster = true;
          for(Pair<Integer, Integer> coord: MONSTER_COORDS){
            if(newPicture[row+coord.fst][col+coord.snd]!='#'){
              isMonster = false;
              break;
            }
          }
          if(isMonster) monsterCount += 1;
        }
      }
      if(monsterCount!=0) break;

      newPicture = transpose(newPicture);

      for(int row = 0; row<newPicture.length-MONSTER_ARRAY.length; row++){
        for(int col = 0; col < newPicture[0].length-MONSTER_ARRAY[0].length; col++){
          boolean isMonster = true;
          for(Pair<Integer, Integer> coord: MONSTER_COORDS){
            if(newPicture[row+coord.fst][col+coord.snd]!='#'){
              isMonster = false;
              break;
            }
          }
          if(isMonster) monsterCount += 1;
        }
      }
      if(monsterCount!=0) break;
    }
    return String.valueOf(nHashes-monsterCount*MONSTER_COORDS.size());
  }

  public static void main(String[] args) {
    try{
      List<String> lines = Files.readAllLines(Paths.get((USE_SAMPLE?"./sample/":"./input/")+Solution.class.getPackageName()));
      Solution s = new Solution(lines);
      System.out.println(s.partOne());
      System.out.println(s.partTwo());
    } catch (IOException | NoSolutionException e) {
      e.printStackTrace();
    }
  }
}
