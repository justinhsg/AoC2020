package day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import utils.AoCSolvable;
import utils.NoSolutionException;

public class Solution implements AoCSolvable {
  private static final boolean USE_SAMPLE = false;
  private static final Set<String> categories = Set.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");
  private static final Map<String, Pattern> validator= new HashMap<>();
  static{
    validator.put("byr", Pattern.compile("^19[02-9][0-9]|200[0-2]$"));
    validator.put("iyr", Pattern.compile("^201[0-9]|2020$"));
    validator.put("eyr", Pattern.compile("^202[0-9]|2030$"));
    validator.put("hgt", Pattern.compile("^(?:(?:15[0-9]|1[6-8][0-9]|19[0-3])cm)|(?:(?:59|6[0-9]|7[0-6])in)$"));
    validator.put("hcl", Pattern.compile("^#(?:[a-f]|[0-9]){6}$"));
    validator.put("ecl", Pattern.compile("^amb|blu|brn|gry|grn|hzl|oth$"));
    validator.put("pid", Pattern.compile("^[0-9]{9}$"));
  }
  private final List<Map<String, String>> passports;

  Solution(List<String> lines){
    passports = new ArrayList<>();
    Map<String, String> passport = new HashMap<>();
    for (String line:lines){
      if(line.length()==0){
        passports.add(passport);
        passport = new HashMap<>();
      } else{
        for (String s: line.split(" ")){
          String[] entries = s.split(":");
          passport.put(entries[0], entries[1]);
        }
      }

    }
    if (passport.size() != 0){
      passports.add(passport);
    }

  }

  public String partOne() throws NoSolutionException {
    int solution = 0;
    for (Map<String, String> passport: passports){
      boolean valid = true;
      for (String catName:categories){
        if(!passport.containsKey(catName)){
          valid = false;
          break;
        }
      }
      if(valid) solution += 1;

    }
    return String.valueOf(solution);
  }

  public String partTwo() throws NoSolutionException {
    int solution = 0;
    for (Map<String, String> passport: passports){
      boolean valid = true;
      for (String catName:categories){
        if(!passport.containsKey(catName)){
          valid = false;
          break;
        }
        if(!validator.get(catName).matcher(passport.get(catName)).matches()){
          valid = false;
          break;
        }
      }
      if(valid) solution += 1;
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
