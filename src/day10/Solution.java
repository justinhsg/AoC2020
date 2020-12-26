package day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import utils.AoCSolvable;
import utils.NoSolutionException;

public class Solution implements AoCSolvable {
  private static final boolean USE_SAMPLE = false;
  private final int[] numbers;

  Solution(List<String> lines){
    numbers = new int[lines.size()];
    int idx = 0;
    for (String line:lines){
      numbers[idx++] = Integer.parseInt(line);
    }
    Arrays.sort(numbers);
  }

  public String partOne() throws NoSolutionException {
    int nOnes = 1;
    int nThrees = 1;
    for(int i = 0; i<numbers.length-1; i++){
      if(numbers[i+1]-numbers[i] == 1){
        nOnes += 1;
      } else if (numbers[i+1]-numbers[i] == 3){
        nThrees += 1;
      }
    }
    return String.valueOf(nOnes * nThrees);
  }

  public String partTwo() throws NoSolutionException {
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
    return String.valueOf(ways[ways.length-1]);
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
