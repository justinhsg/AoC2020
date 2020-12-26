package day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import utils.AoCSolvable;
import utils.NoSolutionException;
import utils.Pair;

public class Solution implements AoCSolvable {
  private static final boolean USE_SAMPLE = false;

  private final char[] op;
  private final int[] vals;

  Solution(List<String> lines){
    op = new char[lines.size()];
    vals = new int[lines.size()];
    int idx = 0;
    for (String line:lines){
      op[idx] = line.charAt(0);
      vals[idx++] = Integer.parseInt(line.substring(1));
    }
  }

  private static void rotCW(Pair<Integer, Integer> p){
    int t = p.fst;
    p.fst = p.snd;
    p.snd = -t;
  }

  private static void rotCCW(Pair<Integer, Integer> p){
    int t = p.fst;
    p.fst = -p.snd;
    p.snd = t;
  }

  public String partOne() throws NoSolutionException {
    int[] dHorz = {0, 1, 0, -1};
    int[] dVert = {1, 0, -1, 0};
    int face = 1;
    Pair<Integer, Integer> pos = new Pair(0,0);
    for(int i = 0; i < op.length; i++){
      switch(op[i]){
        case 'N':
          pos.snd += vals[i];
          break;
        case 'E':
          pos.fst += vals[i];
          break;
        case 'S':
          pos.snd -= vals[i];
          break;
        case 'W':
          pos.fst -= vals[i];
          break;
        case 'R':
          face = (face+(vals[i]/90))%4;
          break;
        case 'L':
          face = (face-vals[i]/90%4);
          face = face<0?face+4:face;
          break;
        case 'F':
          pos.snd += vals[i]*dVert[face];
          pos.fst += vals[i]*dHorz[face];
          break;
        default:
          throw new NoSolutionException();
      }
    }

    return String.valueOf(Math.abs(pos.fst)+Math.abs(pos.snd));
  }

  public String partTwo() throws NoSolutionException {
    Pair<Integer, Integer> way = new Pair(10,1);
    Pair<Integer, Integer> pos = new Pair(0,0);
    for(int i = 0; i < op.length; i++){
      switch(op[i]){
        case 'N':
          way.snd += vals[i];
          break;
        case 'E':
          way.fst += vals[i];
          break;
        case 'S':
          way.snd -= vals[i];
          break;
        case 'W':
          way.fst -= vals[i];
          break;
        case 'R':
          for(int r = 0; r<vals[i]/90%4; r++) rotCW(way);
          break;
        case 'L':
          for(int r = 0; r<vals[i]/90%4; r++) rotCCW(way);
          break;
        case 'F':
          pos.snd += vals[i]*way.snd;
          pos.fst += vals[i]*way.fst;
          break;
        default:
          throw new NoSolutionException();
      }
    }
    return String.valueOf(Math.abs(pos.fst)+Math.abs(pos.snd));
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
