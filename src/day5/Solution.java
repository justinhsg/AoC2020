package day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import utils.AoCSolvable;
import utils.NoSolutionException;

public class Solution implements AoCSolvable {
  private static final boolean USE_SAMPLE = false;

  private int seats[];

  Solution(List<String> lines){
    seats = new int[lines.size()];
    int idx = 0;
    for (String line:lines){
      int t_seat = 0;
      for (int c_i = 0; c_i < line.length(); c_i++){
        char c = line.charAt(c_i);
        t_seat <<= 1;
        if(c == 'B' || c == 'R'){
          t_seat += 1;
        }
      }
      seats[idx++] = t_seat;
    }
  }

  public String partOne() throws NoSolutionException {
    int solution = 0;
    for(int seat:seats){
      solution = seat>solution?seat:solution;
    }
    return String.valueOf(solution);
  }

  public String partTwo() throws NoSolutionException {
    boolean occupied[] = new boolean[1<<10];

    for(int seat:seats){
      occupied[seat] = true;
    }
    int seat = 0;
    while(!occupied[seat]) seat++;
    while(occupied[seat]) seat++;

    return String.valueOf(seat);
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
