package day2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.NoSolutionException;

public class Solution {
  static boolean USE_SAMPLE = false;
  static Pattern line_pattern = Pattern.compile("^([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)$");
  private List<String> lines;
  Set<Integer> numbers;
  private int starts[], ends[];
  private char cs[];
  private String pws[];

  Solution(List<String> lines){
    this.lines = lines;
  }

  public int partOne() throws NoSolutionException {
    int solution = 0;
    for (String line:lines){
      Matcher m = line_pattern.matcher(line);

      if(m.matches()){
        int i = Integer.parseInt(m.group(1));
        int j = Integer.parseInt(m.group(2));
        char c = m.group(3).charAt(0);
        int count = 0;
        for (char d:m.group(4).toCharArray()){
          if(c==d) count += 1;
        }
        if(count >= i && count <= j) solution += 1;
      } else{
        throw new NoSolutionException();
      }
    }
    return solution;
  }

  public int partTwo() throws NoSolutionException {
    int solution = 0;
    for (String line:lines){
      Matcher m = line_pattern.matcher(line);

      if(m.matches()){
        int i = Integer.parseInt(m.group(1));
        int j = Integer.parseInt(m.group(2));
        char c = m.group(3).charAt(0);
        if (m.group(4).charAt(i-1)==c ^ m.group(4).charAt(j-1)==c){
          solution += 1;
        }
      } else{
        throw new NoSolutionException();
      }
    }
    return solution;
  }



  public static void main(String[] args) {
    try{
      List<String> lines = Files.readAllLines(Paths.get(USE_SAMPLE?"./sample/day2":"./input/day2"));
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
