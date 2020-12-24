package day11;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import utils.NoSolutionException;
import utils.Pair;

public class Solution {
  static private boolean USE_SAMPLE = false;
  static private int[] dHoriz = {-1, 0, 1, -1, 1, -1, 0, 1};
  static private int[] dVert = {-1, -1, -1, 0, 0, 1, 1, 1};
  private char[][] grid;
  Solution(List<String> lines){
    int idx = 0;
    grid = new char[lines.size()][lines.get(0).length()];
    for (String line:lines){
      grid[idx++] = line.toCharArray();
    }
  }
  public long partOne() throws NoSolutionException {
    char[][] state = new char[grid.length][grid[0].length];
    Map<Pair<Integer, Integer>, List<Pair<Integer, Integer>>> adj = new HashMap<>();

    Set<Pair<Integer, Integer>> toCheck = new HashSet<>();
    Set<Pair<Integer, Integer>> toFlip = new HashSet<>();
    for(int r = 0; r<grid.length; r++){
      for (int c = 0; c<grid[0].length; c++){
        state[r][c] = grid[r][c];
        if(state[r][c]!='.'){
          List<Pair<Integer, Integer>> adjList = new ArrayList<>();

          for(int d=0; d<8; d++){
            int nr = r + dVert[d];
            int nc = c + dHoriz[d];
            if(nr >= 0 && nr <grid.length && nc >= 0 && nc <grid[0].length){
              if(grid[nr][nc]!='.'){
                adjList.add(new Pair(nr,nc));
              }
            }
          }
          adj.put(new Pair(r,c), adjList);
          toCheck.add(new Pair(r,c));
        }
      }
    }
    while(!toCheck.isEmpty()){
      toFlip.clear();
      for (Pair<Integer, Integer> p:toCheck){
        int nOccupied = 0;
        for (Pair<Integer, Integer> adjSeat: adj.get(p)){

          if(state[adjSeat.fst][adjSeat.snd]=='#'){
            nOccupied += 1;
          }
        }
        if((state[p.fst][p.snd] == '#' && nOccupied >= 4) ||
           (state[p.fst][p.snd] == 'L' && nOccupied == 0)){
          toFlip.add(p);
        }
      }
      toCheck.clear();
      for(Pair<Integer, Integer> p:toFlip){
        state[p.fst][p.snd] = state[p.fst][p.snd]=='#'?'L':'#';
        toCheck.add(p);
        toCheck.addAll(adj.get(p));
      }
    }
    int solution = 0;
    for(int r = 0; r<grid.length; r++) {
      for (int c = 0; c < grid[0].length; c++) {
        if (state[r][c] == '#') {
          solution += 1;
        }
      }
    }
    return solution;

  }

  public long partTwo() throws NoSolutionException {

    char[][] state = new char[grid.length][grid[0].length];
    Map<Pair<Integer, Integer>, List<Pair<Integer, Integer>>> adj = new HashMap<>();

    Set<Pair<Integer, Integer>> toCheck = new HashSet<>();
    Set<Pair<Integer, Integer>> toFlip = new HashSet<>();
    for(int r = 0; r<grid.length; r++){
      for (int c = 0; c<grid[0].length; c++){
        state[r][c] = grid[r][c];
        if(state[r][c]!='.'){
          List<Pair<Integer, Integer>> adjList = new ArrayList<>();

          for(int d=0; d<8; d++){
            int step = 1;
            while(true) {
              int nr = r + dVert[d] * step;
              int nc = c + dHoriz[d] * step;
              if (nr >= 0 && nr < grid.length && nc >= 0 && nc < grid[0].length) {
                if (grid[nr][nc] != '.') {
                  adjList.add(new Pair(nr, nc));
                  break;
                }
              } else {
                break;
              }
              step++;
            }
          }
          adj.put(new Pair(r,c), adjList);
          toCheck.add(new Pair(r,c));
        }
      }
    }
    while(!toCheck.isEmpty()){
      toFlip.clear();
      for (Pair<Integer, Integer> p:toCheck){
        int nOccupied = 0;
        for (Pair<Integer, Integer> adjSeat: adj.get(p)){

          if(state[adjSeat.fst][adjSeat.snd]=='#'){
            nOccupied += 1;
          }
        }
        if((state[p.fst][p.snd] == '#' && nOccupied >= 5) ||
            (state[p.fst][p.snd] == 'L' && nOccupied == 0)){
          toFlip.add(p);
        }
      }
      toCheck.clear();
      for(Pair<Integer, Integer> p:toFlip){
        state[p.fst][p.snd] = state[p.fst][p.snd]=='#'?'L':'#';
        toCheck.add(p);
        toCheck.addAll(adj.get(p));
      }

    }
    int solution = 0;
    for(int r = 0; r<grid.length; r++) {
      for (int c = 0; c < grid[0].length; c++) {
        if (state[r][c] == '#') {
          solution += 1;
        }
      }
    }

    return solution;
  }
  
  public static void main(String[] args) {
    try{
      List<String> lines = Files.readAllLines(Paths.get(USE_SAMPLE?"./sample/day11":"./input/day11"));
      Solution s = new Solution(lines);
      System.out.println(s.partOne());
      System.out.println(s.partTwo());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NoSolutionException e) {
      e.printStackTrace();
    }
  }
}
