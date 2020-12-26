package day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import utils.AoCSolvable;
import utils.NoSolutionException;
import utils.Pair;

public class Solution implements AoCSolvable {
  private static final boolean USE_SAMPLE = false;
  private static final String targetBag = "shinygold";
  private final Map<String, List<Pair<String, Integer>>> contains;
  private final Map<String, List<String>> contained_by;

  Solution(List<String> lines){
    contained_by = new HashMap<>();
    contains = new HashMap<>();
    for (String line:lines){
      String[] splits = line.split(" |, ");
      String parentBag = splits[0]+splits[1];
      List<Pair<String, Integer>> childBags = new ArrayList<>();
      int nChildren = (splits.length - 4)/4;
      for(int i = 0; i<nChildren; i++){
        int nBag = Integer.parseInt(splits[4+i*4]);
        String bagName = splits[4+i*4+1] + splits[4+i*4+2];
        childBags.add(new Pair(bagName, nBag));
        if(!contained_by.containsKey(bagName)){
          contained_by.put(bagName, new ArrayList<>());
        }
        contained_by.get(bagName).add(parentBag);
      }
      contains.put(parentBag, childBags);
    }

  }

  public String partOne() throws NoSolutionException {
    Set<String> visited = new HashSet<>();
    Deque<String> toVisit = new LinkedList<>();

    visited.add(targetBag);
    toVisit.addLast(targetBag);
    while(!toVisit.isEmpty()){
      String curBag = toVisit.removeFirst();
      for(String parentBag: contained_by.getOrDefault(curBag, new ArrayList<>())){
        if(!visited.contains(parentBag)){
          toVisit.addLast(parentBag);
          visited.add(parentBag);
        }
      }
    }
    return String.valueOf(visited.size()-1);
  }

  public String partTwo() throws NoSolutionException {
    Map<String, Integer> memContains = new HashMap<>();
    Deque<String> toVisit = new LinkedList<>();

    toVisit.addLast(targetBag);
    while(!toVisit.isEmpty()){
      String curBag = toVisit.removeLast();
      if(!memContains.containsKey(curBag)){
        boolean canCompute = true;
        for(Pair<String, Integer> p: contains.get(curBag)){
          canCompute &= memContains.containsKey(p.fst);
        }
        if(canCompute){
          int nBags = 1;
          for(Pair<String, Integer> p: contains.get(curBag)){
            nBags += p.snd*memContains.get(p.fst);
          }
          memContains.put(curBag, nBags);
        } else{
          toVisit.addLast(targetBag);
          for(Pair<String, Integer> p: contains.get(curBag)){
            toVisit.addLast(p.fst);
          }
        }
      }
    }
    return String.valueOf(memContains.get(targetBag)-1);
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
