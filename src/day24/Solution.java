package day24;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import utils.AoCSolvable;
import utils.NoSolutionException;
import utils.Pair;

public class Solution implements AoCSolvable {
  private static final boolean USE_SAMPLE = false;
  private final List<char[]> lines;
  private Set<Pair<Integer, Integer>> initialGrid = null;

  Solution(List<String> lines) throws NoSolutionException {
    this.lines = new ArrayList<>();
    for(String line:lines){
      this.lines.add(line.toCharArray());
    }
  }

  public String partOne() throws NoSolutionException {
   initialGrid = new HashSet<>();
   for(char[] line:lines){
     Pair<Integer, Integer> pt = new Pair(0,0);
     for(int i = 0; i<line.length; i++){
       switch(line[i]){
         case 'n':
           if(line[i+1]=='w') {
             pt.fst -= 1;
             pt.snd += 1;
           } else if(line[i+1] == 'e'){
             pt.fst += 1;
             pt.snd += 1;
           } else {
             throw new NoSolutionException();
           }
           i+=1;
           break;
         case 's':
           if(line[i+1]=='w'){
             pt.fst -= 1;
             pt.snd -= 1;
           } else if(line[i+1]=='e') {
             pt.fst += 1;
             pt.snd -= 1;
           } else {
             throw new NoSolutionException();
           }
           i+=1;
           break;
         case 'e':
           pt.fst += 2;
           break;
         case 'w':
           pt.fst -= 2;
           break;
         default:
           throw new NoSolutionException();
       }
     }

     if(initialGrid.contains(pt)){
       initialGrid.remove(pt);
     } else {
       initialGrid.add(pt);
     }
   }
   return String.valueOf(initialGrid.size());
  }

  private static HashSet<Pair<Integer, Integer>> neighbours (Pair<Integer, Integer> pt){
    HashSet<Pair<Integer, Integer>> neighbourSet = new HashSet<>();
    neighbourSet.add(new Pair(pt.fst-2, pt.snd));
    neighbourSet.add(new Pair(pt.fst-1, pt.snd+1));
    neighbourSet.add(new Pair(pt.fst+1, pt.snd+1));
    neighbourSet.add(new Pair(pt.fst+2, pt.snd));
    neighbourSet.add(new Pair(pt.fst+1, pt.snd-1));
    neighbourSet.add(new Pair(pt.fst-1, pt.snd-1));
    return neighbourSet;
  }

  public String partTwo() throws NoSolutionException {
    if(initialGrid == null) partOne();
    Set<Pair<Integer, Integer>> curState = new HashSet<>(initialGrid);
    Set<Pair<Integer, Integer>> toCheck = new HashSet<>(initialGrid);
    Set<Pair<Integer, Integer>> toFlip = new HashSet<>();
    for(Pair<Integer, Integer> pt: curState){
      toCheck.addAll(neighbours(pt));
    }
    for(int i = 0; i<100; i++){
      toFlip.clear();

      for(Pair<Integer, Integer> pt:toCheck) {
        int nBlackNeighbours = 0;
        for (Pair<Integer, Integer> neighbour : neighbours(pt)){
          if (curState.contains(neighbour)) {
            nBlackNeighbours += 1;
          }
        }
        if( (curState.contains(pt)&&(nBlackNeighbours==0||nBlackNeighbours>2)) ||
            (!curState.contains(pt)&&(nBlackNeighbours==2)) ){
          toFlip.add(pt);
        }
      }

      toCheck.clear();
      for(Pair<Integer, Integer> pt:toFlip){
        if(curState.contains(pt)) curState.remove(pt);
        else curState.add(pt);
        toCheck.add(pt);
        toCheck.addAll(neighbours(pt));

      }
    }
    return String.valueOf(curState.size());
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
