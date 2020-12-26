package day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import utils.AoCSolvable;
import utils.NoSolutionException;
import utils.Pair;

public class Solution implements AoCSolvable {
  private static final boolean USE_SAMPLE = false;

  private final List<Pair<String, Integer>> ops;

  Solution(List<String> lines){
    ops = new ArrayList<>();
    for (String line:lines){
      String[] splits = line.split(" ");
      ops.add(new Pair<>(splits[0], Integer.parseInt(splits[1])));
    }
  }

  private Pair<Boolean, Integer> execute() throws NoSolutionException{
    boolean[] seen = new boolean[ops.size()];
    int acc = 0;
    int pc = 0;
    while(pc >= 0 & pc <ops.size()){
      if(seen[pc]){
        return new Pair(false, acc);
      } else{
        seen[pc] = true;
        Pair<String, Integer> op = ops.get(pc);
        switch (op.fst) {
          case "nop":
            pc += 1;
            break;
          case "acc":
            acc += op.snd;
            pc += 1;
            break;
          case "jmp":
            pc += op.snd;
            break;
          default:
            throw new NoSolutionException();
        }
      }
    }
    return new Pair(true, acc);
  }

  public String partOne() throws NoSolutionException {
    return String.valueOf(execute().snd);
  }

  public String partTwo() throws NoSolutionException {
    for (int i = 0; i<ops.size(); i++){
      if(ops.get(i).fst.equals("nop")){
        Pair<String, Integer> t_op = ops.get(i);
        ops.set(i, new Pair<>("jmp", t_op.snd));
        Pair<Boolean, Integer> res = execute();
        if(res.fst){
          return String.valueOf(res.snd);
        } else {
          ops.set(i, t_op);
        }
      } else if(ops.get(i).fst.equals("jmp")){
        Pair<String, Integer> t_op = ops.get(i);
        ops.set(i, new Pair<>("nop", t_op.snd));
        Pair<Boolean, Integer> res = execute();
        if(res.fst){
          return String.valueOf(res.snd);
        } else {
          ops.set(i, t_op);
        }
      }
    }
    throw new NoSolutionException();
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
