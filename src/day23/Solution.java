package day23;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import utils.AoCSolvable;
import utils.NoSolutionException;

public class Solution implements AoCSolvable {
  private static final boolean USE_SAMPLE = false;

  private final int[] sequence;

  Solution(List<String> lines) throws NoSolutionException {
    sequence = Arrays.stream(lines.get(0).split("")).mapToInt(Integer::parseInt).toArray();
  }

  public String partOne() throws NoSolutionException {
    int[] nextLabel = new int[10];
    for(int i = 0; i<sequence.length; i++){
      nextLabel[sequence[i]] = sequence[(i+1)%9];
    }
    int currentLabel = sequence[0];
    for(int turn = 0; turn<100; turn++){
      int threeStart = nextLabel[currentLabel];
      int threeMiddle = nextLabel[threeStart];
      int threeEnd = nextLabel[threeMiddle];
      int afterThree = nextLabel[threeEnd];
      nextLabel[currentLabel] = afterThree;
      int targetLabel = currentLabel ==1? 9:currentLabel-1;
      while(targetLabel == threeStart || targetLabel == threeMiddle || targetLabel == threeEnd){
        targetLabel = targetLabel==1?9:targetLabel-1;
      }

      int afterTarget = nextLabel[targetLabel];
      nextLabel[targetLabel] = threeStart;
      nextLabel[threeEnd] = afterTarget;
      currentLabel = nextLabel[currentLabel];
    }

    int[] sequence = new int[8];
    sequence[0] = nextLabel[1];
    for(int i = 1; i<sequence.length; i++){
      sequence[i] = nextLabel[sequence[i-1]];
    }
    return String.join("", Arrays.stream(sequence).mapToObj(String::valueOf).toArray(String[]::new));
  }

  public String partTwo() throws NoSolutionException {

    int[] nextLabel = new int[1000001];
    for(int i = 0; i<sequence.length-1; i++){
      nextLabel[sequence[i]] = sequence[i+1];
    }
    nextLabel[sequence[sequence.length-1]] = 10;
    for(int i = 10; i<nextLabel.length; i++){
      nextLabel[i] = i+1;
    }
    nextLabel[1000000] = sequence[0];
    int currentLabel = sequence[0];
    for(int turn = 0; turn<10000000; turn++){
      if(currentLabel == 0){
        throw new NoSolutionException();
      }
      int threeStart = nextLabel[currentLabel];
      int threeMiddle = nextLabel[threeStart];
      int threeEnd = nextLabel[threeMiddle];
      int afterThree = nextLabel[threeEnd];
      nextLabel[currentLabel] = afterThree;
      int targetLabel = currentLabel ==1? 1000000:currentLabel-1;
      while(targetLabel == threeStart || targetLabel == threeMiddle || targetLabel == threeEnd){
        targetLabel = targetLabel==1?1000000:targetLabel-1;
      }

      int afterTarget = nextLabel[targetLabel];
      nextLabel[targetLabel] = threeStart;
      nextLabel[threeEnd] = afterTarget;
      currentLabel = nextLabel[currentLabel];
    }
    return String.valueOf(((long)(nextLabel[1])*(long)nextLabel[nextLabel[1]]));
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
