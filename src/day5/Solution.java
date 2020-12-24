package day5;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import utils.NoSolutionException;

public class Solution {
  static boolean USE_SAMPLE = false;

  int seats[];
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

  public int partOne() throws NoSolutionException {
    int solution = 0;
    for(int seat:seats){
      solution = seat>solution?seat:solution;
    }
    return solution;
  }

  public long partTwo() throws NoSolutionException {
    boolean occupied[] = new boolean[1<<10];

    for(int seat:seats){
      occupied[seat] = true;
    }
    int seat = 0;
    while(!occupied[seat]) seat++;
    while(occupied[seat]) seat++;

    return seat;
  }
  
  public static void main(String[] args) {
    try{
      List<String> lines = Files.readAllLines(Paths.get(USE_SAMPLE?"./sample/day5":"./input/day5"));
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
