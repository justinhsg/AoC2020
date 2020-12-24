package day15;


import java.io.FileNotFoundException;
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
import utils.NoSolutionException;

public class Solution {
  static private boolean USE_SAMPLE = false;
  private List<Integer> numbers;
  Solution(List<String> lines){
    numbers = new ArrayList<>();
    for (String n :lines.get(0).split(",")){
      numbers.add(Integer.parseInt(n));
    }
  }



  public int partOne() throws NoSolutionException {
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
    return lastNumber;
  }
  public int partTwo() throws NoSolutionException {
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
    return lastNumber;
  }
  
  public static void main(String[] args) {
    try{
      List<String> lines = Files.readAllLines(Paths.get(USE_SAMPLE?"./sample/day15":"./input/day15"));
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
