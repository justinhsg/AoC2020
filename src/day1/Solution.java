package day1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import utils.NoSolutionException;

public class Solution {
  static boolean USE_SAMPLE = false;

  Set<Integer> numbers;

  Solution(List<String> lines){
    this.numbers = new HashSet<>();
    for (String s:lines){
      this.numbers.add(Integer.parseInt(s));
    }

  }

  public int partOne() throws NoSolutionException {
    for (int i:numbers){
      if(numbers.contains(2020-i) && i != 2020-i){
        return (2020-i)*i;
      }
    }
    throw new NoSolutionException();
  }

  public int partTwo() throws NoSolutionException {
    for (int i: numbers){
      for (int j: numbers){
        int k= 2020-i-j;
        if(i != j && i!=k && j!=k && numbers.contains(k)){
          return i*j*k;
        }
      }
    }
    throw new NoSolutionException();
  }

  public static void main(String[] args) {
    try{
      List<String> lines = Files.readAllLines(Paths.get(USE_SAMPLE?"./sample/day1":"./input/day1"));
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
