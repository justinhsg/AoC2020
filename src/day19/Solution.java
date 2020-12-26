package day19;

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
import utils.Pair;

public class Solution implements AoCSolvable {
  private static final boolean USE_SAMPLE = false;
  private static final Pattern RULE_PATTERN = Pattern.compile("^([0-9]+): ([0-9|\"ab ]+)$");

  private final List<String> queries;
  private final Map<Integer, List<int[]>> rules;
  private final Map<Integer, Set<String>> mem;
  private final int string31Length, string42Length;

  Solution(List<String> lines) throws NoSolutionException {
    int flag = 0;
    queries = new ArrayList<>();
    rules = new HashMap<>();
    for(String line:lines){
      if(line.length()==0){
        flag++;
        continue;
      }
      if(flag==1){
        queries.add(line);
      } else {
        Matcher m = RULE_PATTERN.matcher(line);
        if(m.matches()){
          Integer ruleId = Integer.valueOf(m.group(1));
          String body = m.group(2);
          List<int[]> alts = new ArrayList<>();
          switch(body.charAt(1)){
            case 'a':
              alts.add(new int[]{-1});
              break;
            case 'b':
              alts.add(new int[]{-2});
              break;
            default:
              for(String s:body.split(" \\| ")){
                alts.add(Arrays.stream(s.split(" ")).mapToInt(Integer::parseInt).toArray());
              }
              break;
          }
          rules.put(ruleId, alts);
        }else {
          throw new NoSolutionException();
        }
      }
    }
    mem = new HashMap<>();
    string31Length = generate(31).iterator().next().length();
    string42Length = generate(42).iterator().next().length();
  }

  private Integer attemptMatch31(String s){
    if(s.length()==0) return 0;
    else if(s.length() < string31Length) return null;
    else if(generate(31).contains(s.substring(0,string31Length))){
      Integer nextMatch = attemptMatch31(s.substring(string31Length));
      if(nextMatch == null) return null;
      else return 1+nextMatch;
    } else {
      return null;
    }
  }

  private Pair<Integer, Integer> attemptMatch42(String s){
    if(s.length() < string42Length){
      return new Pair(null, null);
    } else if(generate(42).contains(s.substring(0, string42Length))){
      Pair<Integer, Integer> nextMatch = attemptMatch42(s.substring(string42Length));
      if(nextMatch.snd==null || nextMatch.fst == null) return new Pair(null, null);
      else return new Pair(1+nextMatch.fst, nextMatch.snd);
    } else{
      Integer nextMatch = attemptMatch31(s);
      if(nextMatch == null) return new Pair(null, null);
      else return new Pair(0, nextMatch);
    }
  }

  private Set<String> generate(int ruleId){
    if(mem.containsKey(ruleId)) return mem.get(ruleId);
    if(ruleId == -1) return Set.of("a");
    if(ruleId == -2) return Set.of("b");
    Set<String> genStrings = new HashSet<>();
    List<int[]> alts = rules.get(ruleId);
    for(int[] alt: alts){
      Set<String> altStrings = new HashSet<>();
      altStrings.add("");
      for(int nextRule: alt){
        Set<String> innerGen = generate(nextRule);
        Set<String> newAltStrings = new HashSet<>();
        for(String s1: altStrings){
          for(String s2: innerGen){
            newAltStrings.add(s1+s2);
          }
        }
        altStrings = newAltStrings;
      }
      genStrings.addAll(altStrings);
    }
    mem.put(ruleId, genStrings);
    return mem.get(ruleId);
  }

  public String partOne() throws NoSolutionException {
    long solution = 0L;
    for (String s: queries){
      Pair<Integer, Integer> matchResult = attemptMatch42(s);
      if(matchResult.fst != null && matchResult.snd != null && matchResult.fst.equals(2) && matchResult.snd.equals(1)){
        solution += 1L;
      }
    }
    return String.valueOf(solution);
  }

  public String partTwo() throws NoSolutionException {
    long solution = 0L;
    for (String s: queries){
      Pair<Integer, Integer> matchResult = attemptMatch42(s);
      if(matchResult.fst != null && matchResult.snd != null && matchResult.fst > matchResult.snd){
        solution += 1L;
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
