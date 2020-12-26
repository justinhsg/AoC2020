package day17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import utils.AoCSolvable;
import utils.NoSolutionException;

public class Solution implements AoCSolvable {
  private static final boolean USE_SAMPLE = false;

  private static class Point{
    private int x;
    private int y;
    private int z;
    private int w;
    private int nDim;

    private Point(int x, int y, int z){
      this.x=x;
      this.y=y;
      this.z=z;
      this.w=0;
      this.nDim=3;
    }

    private Point(int x, int y, int z, int w){
      this.x=x;
      this.y=y;
      this.z=z;
      this.w=w;
      this.nDim = 4;
    }

    private List<Point> getNeighbours(int maxX, int maxY, int maxZ, int maxW){
      List<Point> neighbours = new ArrayList<>();
      for(int nx = Math.max(0, x-1); nx<Math.min(x+2, maxX); nx++){
        for(int ny = Math.max(0, y-1); ny<Math.min(y+2, maxY); ny++) {
          for (int nz = Math.max(0, z-1); nz<Math.min(z+2, maxZ); nz++){
            if(nDim==3){
              if(!(nx==x && ny==y && nz==z)) neighbours.add(new Point(nx, ny, nz));
            } else {
              for (int nw = Math.max(0, w-1); nw<Math.min(w+2, maxW); nw++){
                if(!(nx==x && ny==y && nz==z && nw==w))neighbours.add(new Point(nx,ny,nz, nw));
              }
            }
          }
        }
      }
      return neighbours;
    }

    @Override
    public int hashCode(){
      return x*y*z*w*nDim;
    }

    @Override
    public boolean equals(Object o){
      Point other = (Point) o;
      return x==other.x && y==other.y && z==other.z && w==other.w && nDim==other.nDim;
    }
  }

  private final int maxX, maxY, maxZ, maxW;
  private final boolean[][][][] initialState;

  Solution(List<String> lines) throws NoSolutionException {
    maxX = lines.get(0).length()+6*2;
    maxY = lines.size()+6*2;
    maxZ = 13;
    maxW = 13;
    initialState = new boolean[maxX][maxY][maxZ][maxW];
    for(int x = 0; x<lines.get(0).length(); x++){
      for(int y = 0; y<lines.size(); y++){
        initialState[x+6][y+6][6][6] = lines.get(y).charAt(x) == '#';
      }
    }
  }

  public String partOne() throws NoSolutionException {
    boolean[][][] state = new boolean[maxX][maxY][maxZ];
    Set<Point> toCheck = new HashSet<>();

    for(int x = 6; x<maxX-6; x++){
      for(int y = 6; y<maxY-6; y++){
        state[x][y][6] = initialState[x][y][6][6];
        if(state[x][y][6]){
          Point p = new Point(x,y,6);
          toCheck.add(p);
          toCheck.addAll(p.getNeighbours(maxX, maxY, maxZ, maxW));
        }
      }
    }

    Set<Point> toFlip = new HashSet<>();
    for(int t = 0; t<6; t++){
      toFlip.clear();
      for(Point p:toCheck){
        int neigh = 0;
        for(Point op:p.getNeighbours(maxX, maxY, maxZ, maxW)){
          if(state[op.x][op.y][op.z]){
            neigh += 1;
          }
        }
        if( (state[p.x][p.y][p.z]&&(neigh<2 || neigh>3) ) ||
            (!state[p.x][p.y][p.z])&&neigh==3){
          toFlip.add(p);
        }
      }
      toCheck.clear();
      for(Point p:toFlip){
        state[p.x][p.y][p.z] = !state[p.x][p.y][p.z];
        toCheck.add(p);
        toCheck.addAll(p.getNeighbours(maxX, maxY, maxZ, maxW));
      }

    }
    int solution = 0;
    for(int x = 0; x<maxX; x++) {
      for (int y = 0; y < maxY; y++) {
        for (int z = 0; z < maxZ; z++) {
          if (state[x][y][z])
            solution++;
        }
      }
    }
    return String.valueOf(solution);
  }

  public String partTwo() throws NoSolutionException {
    boolean[][][][] state = new boolean[maxX][maxY][maxZ][maxW];
    Set<Point> toCheck = new HashSet<>();
    for(int x = 6; x<maxX-6; x++){
      for(int y = 6; y<maxY-6; y++){
        state[x][y][6][6] = initialState[x][y][6][6];
        if(state[x][y][6][6]){
          Point p = new Point(x,y,6, 6);
          toCheck.add(p);
          toCheck.addAll(p.getNeighbours(maxX, maxY, maxZ, maxW));
        }
      }
    }

    Set<Point> toFlip = new HashSet<>();
    for(int t = 0; t<6; t++){
      toFlip.clear();
      for(Point p:toCheck){
        int neigh = 0;
        for(Point op:p.getNeighbours(maxX, maxY, maxZ, maxW)){
          if(state[op.x][op.y][op.z][op.w]){
            neigh += 1;
          }
        }
        if( (state[p.x][p.y][p.z][p.w]&&(neigh<2 || neigh>3) ) ||
            (!state[p.x][p.y][p.z][p.w])&&neigh==3){
          toFlip.add(p);
        }
      }
      toCheck.clear();
      for(Point p:toFlip){
        state[p.x][p.y][p.z][p.w] = !state[p.x][p.y][p.z][p.w];
        toCheck.add(p);
        toCheck.addAll(p.getNeighbours(maxX, maxY, maxZ, maxW));
      }
    }
    int solution = 0;
    for(int x = 0; x<maxX; x++) {
      for (int y = 0; y < maxY; y++) {
        for (int z = 0; z < maxZ; z++) {
          for (int w = 0; w < maxW; w++) {
            if (state[x][y][z][w]) solution++;
          }
        }
      }
    }
    return String.valueOf(solution);
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
