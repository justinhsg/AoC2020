package day3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import utils.NoSolutionException;

public class Solution {
  static boolean USE_SAMPLE = false;
  private char[][] field;

  Solution(List<String> lines){
    this.field = new char[lines.size()][lines.get(0).length()];
    for(int i = 0; i<lines.size(); i++){
      String s = lines.get(i);
      field[i] = s.toCharArray();
    }
  }
  private long traverse(int right, int down){
    int h_pos = 0, v_pos = 0, trees = 0;
    while(h_pos < field.length){
      if(field[h_pos][v_pos] == '#'){
        trees += 1;
      }
      h_pos += down;
      v_pos = (v_pos+right)%field[0].length;
    }
    return trees;
  }

  public long partOne() throws NoSolutionException {
    return traverse(3,1);
  }

  public long partTwo() throws NoSolutionException {
    return traverse(1,1)
            *traverse(3,1)
            *traverse(5,1)
            *traverse(7,1)
            *traverse(1,2);
  }

  public static void main(String[] args) {
    try{
      List<String> lines = Files.readAllLines(Paths.get(USE_SAMPLE?"./sample/day3":"./input/day3"));
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
