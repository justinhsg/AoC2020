package day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import utils.AoCSolvable;
import utils.NoSolutionException;

public class Solution implements AoCSolvable {
  private static final boolean USE_SAMPLE = false;

  private final List<List<String>> groups;

  Solution(List<String> lines){
    groups = new ArrayList<>();
    List<String> group = new ArrayList<>();
    for (String line:lines) {
      if (line.length() == 0) {
        groups.add(group);
        group = new ArrayList<>();
      } else {
        group.add(line);
      }
    }
    if(group.size()!=0) groups.add(group);
  }

  public String partOne() throws NoSolutionException {
    int solution = 0;
    for(List<String> group:groups){
      Set<Character> yes_responses = new HashSet<>();
      for(String resp:group){
        char[] resp_array = resp.toCharArray();
        for(char c:resp_array){
          yes_responses.add(c);
        }
      }
      solution += yes_responses.size();
    }
    return String.valueOf(solution);
  }

  public String partTwo() throws NoSolutionException {
    int solution = 0;
    for(List<String> group:groups){
      Set<Character> yes_responses = new HashSet<>();
      for (char c: group.get(0).toCharArray()){
        yes_responses.add(c);
      }
      for(String resp:group){
        Set<Character> new_responses = new HashSet<>();
        for(char c:resp.toCharArray()){
          new_responses.add(c);
        }
        yes_responses.retainAll(new_responses);
      }
      solution += yes_responses.size();
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
