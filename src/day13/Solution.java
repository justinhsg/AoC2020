package day13;

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
  private List<Integer> ids;
  private List<Integer> delays;
  private int target;
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
  public long partOne() throws NoSolutionException {
    int best = Integer.MAX_VALUE;
    int solution = -1;
    for (int id:ids){
      int delay = Math.floorMod(-target, id);
      best =  best<delay? best:delay;
      solution = best<delay?solution:delay*id;
    }
    return solution;
  }

  private long expMod(long a, long pow, long mod){
    if(pow == 0L){
      return 1L;
    } else if(pow == 1L){
      return Math.floorMod(a, mod);
    } else {
      long newPower = Math.floorDiv(pow, 2);
      long newA = Math.floorMod(a*a, mod);
      if(pow % 2L == 1){
        return Math.floorMod(expMod(newA, newPower, mod) * a, mod);
      } else {
        return Math.floorMod(expMod(newA, newPower, mod), mod);
      }
    }
  }

  public long partTwo() throws NoSolutionException {
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
    return times[times.length-1];
  }
  
  public static void main(String[] args) {
    try{
      List<String> lines = Files.readAllLines(Paths.get(USE_SAMPLE?"./sample/day13":"./input/day13"));
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
