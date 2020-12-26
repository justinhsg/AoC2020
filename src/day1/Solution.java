package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import utils.AoCSolvable;
import utils.NoSolutionException;

public class Solution implements AoCSolvable {
  private static final boolean USE_SAMPLE = false;

  private final Set<Integer> numbers;

  Solution(List<String> lines){
    this.numbers = new HashSet<>();
    for (String s:lines){
      this.numbers.add(Integer.parseInt(s));
    }
  }

  public String partOne() throws NoSolutionException {
    for (int i:numbers){
      if(numbers.contains(2020-i) && i != 2020-i){
        return String.valueOf((2020-i)*i);
      }
    }
    throw new NoSolutionException();
  }

  public String partTwo() throws NoSolutionException {
    for (int i: numbers){
      for (int j: numbers){
        int k= 2020-i-j;
        if(i != j && i!=k && j!=k && numbers.contains(k)){
          return String.valueOf(i*j*k);
        }
      }
    }
    throw new NoSolutionException();
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
