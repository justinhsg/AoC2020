package day10;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import utils.NoSolutionException;

public class Solution {
  static private boolean USE_SAMPLE = false;
  private int[] numbers;

  Solution(List<String> lines){
    numbers = new int[lines.size()];
    int idx = 0;
    for (String line:lines){
      numbers[idx++] = Integer.parseInt(line);
    }
    Arrays.sort(numbers);
  }
  public long partOne() throws NoSolutionException {
    int nOnes = 1;
    int nThrees = 1;
    for(int i = 0; i<numbers.length-1; i++){
      if(numbers[i+1]-numbers[i] == 1){
        nOnes += 1;
      } else if (numbers[i+1]-numbers[i] == 3){
        nThrees += 1;
      }

    }
    return nOnes * nThrees;
  }

  public long partTwo() throws NoSolutionException {
    long[] ways = new long[numbers.length];
    for (int i = 0; i<numbers.length; i++){
      int n = numbers[i];
      if(n<=3) ways[i] += 1;
      for (int j = i-3<0?0:i-3; j<i; j++){
        if(n - numbers[j] <= 3){
          ways[i] += ways[j];
        }
      }
    }
    return ways[ways.length-1];
  }
  
  public static void main(String[] args) {
    try{
      List<String> lines = Files.readAllLines(Paths.get(USE_SAMPLE?"./sample/day10":"./input/day10"));
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
