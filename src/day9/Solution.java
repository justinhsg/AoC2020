package day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utils.AoCSolvable;
import utils.NoSolutionException;

public class Solution implements AoCSolvable {
  private static final boolean USE_SAMPLE = false;
  private static final int PREAMBLE = USE_SAMPLE? 5:25;
  private final long[] numbers;
  private long partOne = -1L;

  Solution(List<String> lines){
    numbers = new long[lines.size()];
    int idx = 0;
    for (String line:lines){
      numbers[idx++] = Long.parseLong(line);
    }
  }

  public String partOne() throws NoSolutionException {
    Map<Long, Integer> sums = new HashMap<>();
    for(int i = 0; i<PREAMBLE; i++){
      for (int j = 0; j<PREAMBLE; j++){
        sums.put(numbers[i]+numbers[j], sums.getOrDefault(numbers[i]+numbers[j], 0)+1);
      }
    }
    for(int i = PREAMBLE; i<numbers.length; i++){
      long target = numbers[i];
      if(sums.getOrDefault(target, 0) == 0){
        this.partOne = target;
        return String.valueOf(this.partOne);
      } else {
        long removeNum = numbers[i-PREAMBLE];
        for (int j = i-PREAMBLE+1; j<i; j++){
          long tNum = numbers[j];
          sums.put(removeNum+tNum, sums.get(removeNum+tNum)-1);
          sums.put(target+tNum, sums.getOrDefault(target+tNum, 0)+1);
        }
      }
    }
    throw new NoSolutionException();
  }

  public String partTwo() throws NoSolutionException {
    if(this.partOne == -1L) partOne();
    int start = 0;
    int end = 1;
    long cumsum = numbers[start];
    while(start != numbers.length && end != numbers.length+1){
      if(cumsum == partOne){
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;

        for(int i = start; i < end; i++){
          min = min<numbers[i]?min:numbers[i];
          max = max>numbers[i]?max:numbers[i];
        }
        return String.valueOf(min + max);
      } else if (cumsum < partOne){
        if(end == numbers.length) break;
        cumsum += numbers[end];
        end += 1;
      } else if (cumsum > partOne){
        cumsum -= numbers[start];
        start += 1;
      }
    }
    throw new NoSolutionException();
  }

  public static void main(String[] args) {
    try{
      List<String> lines = Files.readAllLines(Paths.get((USE_SAMPLE?"./sample/":"./input/")+ Solution.class.getPackageName()));
      Solution s = new Solution(lines);
      System.out.println(s.partOne());
      System.out.println(s.partTwo());
    } catch (IOException | NoSolutionException e) {
      e.printStackTrace();
    }
  }
}
