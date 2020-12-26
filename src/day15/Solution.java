package day15;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import utils.AoCSolvable;
import utils.NoSolutionException;

public class Solution implements AoCSolvable {
  private static final boolean USE_SAMPLE = false;
  private final List<Integer> numbers;

  Solution(List<String> lines){
    numbers = new ArrayList<>();
    for (String n :lines.get(0).split(",")){
      numbers.add(Integer.parseInt(n));
    }
  }

  public String partOne() throws NoSolutionException {
    int[] lastCalled = new int[2020];
    int lastNumber = 0;
    int curTurn = 0;
    for (int n:numbers){
      lastCalled[lastNumber] = curTurn;
      curTurn++;
      lastNumber = n;
    }
    while(curTurn<2020){
      int n = lastCalled[lastNumber]==0?0:curTurn-lastCalled[lastNumber];
      lastCalled[lastNumber] = curTurn;
      curTurn += 1;
      lastNumber = n;
    }
    return String.valueOf(lastNumber);
  }

  public String partTwo() throws NoSolutionException {
    int[] lastCalled = new int[30000000];
    int lastNumber = 0;
    int curTurn = 0;
    for (int n:numbers){
      lastCalled[lastNumber] = curTurn;
      curTurn++;
      lastNumber = n;
    }
    while(curTurn<30000000){
      int n = lastCalled[lastNumber]==0?0:curTurn-lastCalled[lastNumber];
      lastCalled[lastNumber] = curTurn;
      curTurn += 1;
      lastNumber = n;
    }
    return String.valueOf(lastNumber);
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
