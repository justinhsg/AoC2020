package day14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utils.AoCSolvable;
import utils.NoSolutionException;

public class Solution implements AoCSolvable {
  private static final boolean USE_SAMPLE = false;
  private static final Pattern memPattern = Pattern.compile("^mem\\[([0-9]+)] = ([0-9]+)$");
  private final List<String> lines;

  Solution(List<String> lines){
    this.lines = lines;
  }

  public String partOne() throws NoSolutionException {
    Map<Integer, Long> mem = new HashMap<>();
    long maskZero = 0L;
    long maskOne = 0L;
    for (String line: lines){
      if(line.charAt(1)=='a'){
        maskZero = 0L;
        maskOne = 0L;
        String bits = line.substring(7);
        for(char c: bits.toCharArray()){
          maskZero <<= 1;
          maskOne <<= 1;
          if(c == '1'){
            maskOne += 1;
          } else if(c == '0'){
            maskZero += 1;
          }
        }
      } else{
        Matcher m = memPattern.matcher(line);
        m.matches();
        int loc = Integer.parseInt(m.group(1));
        long val = Long.parseLong(m.group(2));
        mem.put(loc, (val | maskOne)& ~maskZero);
      }
    }
    long solution = 0;
    for(int key:mem.keySet()) solution+=mem.get(key);

    return String.valueOf(solution);
  }

  public String partTwo() throws NoSolutionException {
    Map<Long, Long> mem = new HashMap<>();
    long maskOne = 0L;
    long maskX = 0L;
    ArrayList<Long> masks = new ArrayList<>();
    masks.add(0L);
    for (String line: lines){
      if(line.charAt(1)=='a'){
        maskOne = 0L;
        maskX = 0L;
        masks.clear();
        masks.add(0L);
        String bits = line.substring(7);
        for(char c: bits.toCharArray()){
          int maskSize = masks.size();
          maskOne <<= 1;
          maskX <<= 1;
          for(int i = 0; i<maskSize; i++){
            masks.set(i, masks.get(i)<<1);
          }
          if(c == '1'){
            maskOne += 1;
          } else if(c == 'X'){
            maskX+= 1;
            for(int i = 0; i<maskSize; i++){
              masks.add(masks.get(i)+1);
            }
          }
        }
      } else{
        Matcher m = memPattern.matcher(line);
        m.matches();
        long loc = Integer.parseInt(m.group(1));
        long val = Long.parseLong(m.group(2));
        loc = ((loc|maskOne)&~maskX);
        for (long mask:masks){
          long new_loc = loc|mask;
          mem.put(new_loc, val);
        }
      }
    }
    long solution = 0;
    for(long key:mem.keySet()) solution+=mem.get(key);

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
