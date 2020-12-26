package day16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utils.AoCSolvable;
import utils.NoSolutionException;

public class Solution implements AoCSolvable {
  private static final boolean USE_SAMPLE = false;
  private static final Pattern rulePattern = Pattern.compile("^([a-z ]+): ([0-9]+)-([0-9]+) or ([0-9]+)-([0-9]+)$");
  private int[] yourTicket;
  private boolean[] validTickets = null;

  private final List<int[]> otherTickets;
  private final Map<String, int[]> rules;

  Solution(List<String> lines) throws NoSolutionException {
    int flag = 0;
    rules = new HashMap<>();
    otherTickets = new ArrayList<>();
    for (String line: lines){
      if(line.length()==0){
        flag += 1;
        continue;
      }
      if(flag==0){
        Matcher m = rulePattern.matcher(line);
        if(m.matches()){
          int[] bounds = new int[4];
          for(int i = 0; i<4; i++){
            bounds[i] = Integer.parseInt(m.group(i+2));
          }
          rules.put(m.group(1), bounds);
        }else{
          throw new NoSolutionException();
        }
      } else if(flag == 1){
        if(line.charAt(0) == 'y') continue;
        yourTicket = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
      } else{
        if(line.charAt(0) == 'n') continue;
        otherTickets.add(Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray());
      }
    }
  }

  private static boolean withinRange(int val, int[] bounds){
    return (val >= bounds[0] && val <= bounds[1]) || (val >= bounds[2] && val <= bounds[3]);
  }

  public String partOne() throws NoSolutionException {
    int errorRate = 0;
    validTickets = new boolean[otherTickets.size()];
    for(int tid = 0; tid<otherTickets.size(); tid++){
      validTickets[tid] = true;
      for(int val:otherTickets.get(tid)){
        boolean withinBounds = false;
        for(int[] bounds:rules.values()){
          withinBounds |= withinRange(val, bounds);
        }
        if(!withinBounds) errorRate += val;
        validTickets[tid] &= withinBounds;
      }
    }
    return String.valueOf(errorRate);
  }

  public String partTwo() throws NoSolutionException {
    if(validTickets==null) partOne();
    Set<String>[] possibles = new Set[yourTicket.length];
    String[] confirmed = new String[yourTicket.length];
    Set<Integer> toRemove = new HashSet<>();
    Set<Integer> notConfirmed = new HashSet<>();
    for(int pos = 0; pos<yourTicket.length; pos++){
      Set<String> possible = new HashSet<>(rules.keySet());
      for(int tid = 0; tid<validTickets.length; tid++){
        if(!validTickets[tid]) continue;
        for(String key:rules.keySet()){
          if(!withinRange(otherTickets.get(tid)[pos], rules.get(key))) possible.remove(key);
        }
      }
      possibles[pos] = possible;
      if(possible.size()==1){
        confirmed[pos] = possible.iterator().next();
        toRemove.add(pos);
      } else {
        notConfirmed.add(pos);
      }
    }
    while(notConfirmed.size()!=0){
      for (int pos:notConfirmed){
        for(int remPos:toRemove){
          possibles[pos].remove(confirmed[remPos]);
        }
      }
      toRemove.clear();
      for (int pos:notConfirmed){
        if(possibles[pos].size()==1){
          confirmed[pos] = possibles[pos].iterator().next();
          toRemove.add(pos);
        }
      }
      notConfirmed.removeAll(toRemove);
    }
    long solution = 1;
    for (int pos = 0; pos<confirmed.length; pos++){
      if(confirmed[pos].matches("^departure [a-z ]+$")){
        solution *= yourTicket[pos];
      }
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
