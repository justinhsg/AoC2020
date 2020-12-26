package day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.AoCSolvable;
import utils.NoSolutionException;

public class Solution implements AoCSolvable {
  private static final boolean USE_SAMPLE = false;
  private static final Pattern line_pattern = Pattern.compile("^([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)$");
  private final List<String> lines;

  Solution(List<String> lines){
    this.lines = lines;
  }

  public String partOne() throws NoSolutionException {
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
    return String.valueOf(solution);
  }

  public String partTwo() throws NoSolutionException {
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
    return String.valueOf(solution);
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
