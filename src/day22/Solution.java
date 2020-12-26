package day22;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import utils.AoCSolvable;
import utils.NoSolutionException;

public class Solution implements AoCSolvable {
  private static final boolean USE_SAMPLE = false;

  private final Deque<Integer> initP1, initP2;

  Solution(List<String> lines) throws NoSolutionException {
    initP1 = new LinkedList<>();
    initP2 = new LinkedList<>();
    int flag = 0;
    for(String line: lines){
      if(line.length()==0){
        flag += 1;
        continue;
      }
      if(line.charAt(0)=='P') continue;
      if(flag == 0) initP1.addLast(Integer.valueOf(line));
      else initP2.addLast(Integer.valueOf(line));
    }
  }

  public String partOne() throws NoSolutionException {
    Deque<Integer> curP1 = new LinkedList<>(initP1);
    Deque<Integer> curP2 = new LinkedList<>(initP2);
    while(curP1.size()!=0 && curP2.size()!=0){
      int topP1 = curP1.removeFirst();
      int topP2 = curP2.removeFirst();
      if(topP1 > topP2){
        curP1.addLast(topP1);
        curP1.addLast(topP2);
      } else{
        curP2.addLast(topP2);
        curP2.addLast(topP1);
      }
    }
    Deque<Integer> winnerDeck = curP1.size()==0?curP2:curP1;
    long solution = 0;
    int idx = 0;
    for(int val:winnerDeck){
      solution += (winnerDeck.size()-(idx++))*val;
    }
    return String.valueOf(solution);
  }

  private static int subGame(Deque<Integer> p1Stack, Deque<Integer> p2Stack){
    Set<List<Deque<Integer>>> seenStates = new HashSet<>();
    while(p1Stack.size()!=0 && p2Stack.size()!=0){
      List<Deque<Integer>> curState = List.of(new LinkedList<>(p1Stack), new LinkedList<>(p2Stack));
      if(seenStates.contains(curState)){
        return 1;
      }
      seenStates.add(curState);
      int p1Top = p1Stack.removeFirst();
      int p2Top = p2Stack.removeFirst();
      int winner;
      if(p1Top <= p1Stack.size() && p2Top <= p2Stack.size()){
        Deque<Integer> newP1Stack = new LinkedList<>(new ArrayList<>(p1Stack).subList(0, p1Top));
        Deque<Integer> newP2Stack = new LinkedList<>(new ArrayList<>(p2Stack).subList(0, p2Top));
        winner = subGame(newP1Stack, newP2Stack);
      } else {
        winner = p1Top>p2Top?1:2;
      }
      if(winner == 1){
        p1Stack.addLast(p1Top);
        p1Stack.addLast(p2Top);
      } else {
        p2Stack.addLast(p2Top);
        p2Stack.addLast(p1Top);
      }
    }
    return p1Stack.size()==0?2:1;
  }

  public String partTwo() throws NoSolutionException {
    Deque<Integer> curP1 = new LinkedList<>(initP1);
    Deque<Integer> curP2 = new LinkedList<>(initP2);
    subGame(curP1, curP2);
    Deque<Integer> winnerDeck = curP1.size()==0?curP2:curP1;
    long solution = 0;
    int idx = 0;
    for(int val:winnerDeck){
      solution += (winnerDeck.size()-(idx++))*val;
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
