package day25;

import static utils.ModuloArithmetic.discreteLog;
import static utils.ModuloArithmetic.expMod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import utils.AoCSolvable;
import utils.NoSolutionException;

public class Solution implements AoCSolvable {
  private static final boolean USE_SAMPLE = false;
  private static final long MOD = 20201227;
  private final int pkey1, pkey2;

  Solution(List<String> lines) throws NoSolutionException {
    pkey1 = Integer.parseInt(lines.get(0));
    pkey2 = Integer.parseInt(lines.get(1));
  }

  public String partOne() throws NoSolutionException {
    long loopNumber = discreteLog(7, pkey1, MOD);
    return String.valueOf(expMod(pkey2, loopNumber, MOD));
  }

  public String partTwo() throws NoSolutionException {
    return "0";
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
