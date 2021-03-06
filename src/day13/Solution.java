package day13;

import static utils.ModuloArithmetic.expMod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import utils.NoSolutionException;

public class Solution {
  private static final boolean USE_SAMPLE = false;
  private final List<Integer> ids;
  private final List<Integer> delays;
  private final int target;

  Solution(List<String> lines){
    int idx = 0;
    ids = new ArrayList<>();
    delays = new ArrayList<>();
    target = Integer.parseInt(lines.get(0));
    for(String s:lines.get(1).split(",")){
      if(!s.matches("x")){
        ids.add(Integer.parseInt(s));
        delays.add(idx);
      }
      idx++;
    }
  }
  public String partOne() throws NoSolutionException {
    int best = Integer.MAX_VALUE;
    int solution = -1;
    for (int id:ids){
      int delay = Math.floorMod(-target, id);
      best =  best<delay? best:delay;
      solution = best<delay?solution:delay*id;
    }
    return String.valueOf(solution);
  }

  public String partTwo() throws NoSolutionException {
    long[] times = new long[ids.size()];
    long[] prods = new long[ids.size()];
    prods[0] = ids.get(0);
    for(int i = 1; i<ids.size(); i++){
      prods[i] = ((long)ids.get(i))*prods[i-1];
    }
    for(int i = 0; i<times.length-1; i++){
      long inv_prod = expMod(Math.floorMod(prods[i], ids.get(i+1)), ids.get(i+1)-2L, ids.get(i+1));
      long f = Math.floorMod( (-times[i]-delays.get(i+1))*inv_prod, ids.get(i+1));
      times[i+1] = times[i]+f*prods[i];
    }
    return String.valueOf(times[times.length-1]);
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
