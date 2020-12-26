package day21;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
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
  private static final Pattern LINE_PATTERN = Pattern.compile("([a-z ]+) \\(contains ([a-z ,]+)\\)");

  private final Set<String> allIngredients, allAllergens;
  private final List<Set<String>> ingredientList, allergenList;
  private final Map<String, List<Set<String>>> allergenContains;
  private List<Pair<String, String>> allergenIngredientPairs = null;

  Solution(List<String> lines) throws NoSolutionException {
    allIngredients = new HashSet<>();
    ingredientList = new ArrayList<>();
    allergenList = new ArrayList<>();
    allAllergens = new HashSet<>();
    allergenContains = new HashMap<>();
    for(String line:lines){
      Matcher m = LINE_PATTERN.matcher(line);
      if(m.matches()){
        Set<String> ingredients = Set.of(m.group(1).split(" "));
        Set<String> allergens = Set.of(m.group(2).split(", "));
        allIngredients.addAll(ingredients);
        allAllergens.addAll(allergens);
        ingredientList.add(ingredients);
        allergenList.add(allergens);
        for(String allergen: allergens){
          List<Set<String>> currentList = allergenContains.getOrDefault(allergen, new ArrayList<>());
          currentList.add(ingredients);
          allergenContains.put(allergen, currentList);
        }
      }else{
        throw new NoSolutionException();
      }
    }
  }

  public String partOne() throws NoSolutionException {
    allergenIngredientPairs = new ArrayList<>();

    Set<String> toCheck = new HashSet<>();
    Set<String> toRemove = new HashSet<>();
    Map<String, Set<String>> possibles = new HashMap<>();
    for(String allergen: allAllergens){
      Set<String> possible = new HashSet<>(allIngredients);
      for(Set<String> constraints: allergenContains.get(allergen)){
        possible.retainAll(constraints);
      }
      toCheck.add(allergen);
      possibles.put(allergen, possible);
    }
    HashSet<String> ingredientsWithAllergen = new HashSet<>();
    while(toCheck.size()!=0){
      toRemove.clear();
      for(String allergen: toCheck){
        if(possibles.get(allergen).size()==1){
          toRemove.add(allergen);
          ingredientsWithAllergen.add(possibles.get(allergen).iterator().next());
          allergenIngredientPairs.add(new Pair(allergen, possibles.get(allergen).iterator().next()));
        }
      }
      toCheck.removeAll(toRemove);
      for(String allergen: toCheck){
        for(String remAllergen:toRemove) {
          possibles.get(allergen).remove(possibles.get(remAllergen).iterator().next());
        }
      }
    }
    long solution = 0L;
    for(Set<String> ingredients: ingredientList){
      for(String ingredient:ingredients){
        if(!ingredientsWithAllergen.contains(ingredient)){
          solution+=1L;
        }
      }
    }
    return String.valueOf(solution);
  }

  public String partTwo() throws NoSolutionException {
    if (allergenIngredientPairs == null) partOne();
    Collections.sort(allergenIngredientPairs);
    String[] ingredientArray = new String[allergenIngredientPairs.size()];
    for(int i = 0; i<allergenIngredientPairs.size(); i++){
      ingredientArray[i] = allergenIngredientPairs.get(i).snd;
    }
    return String.join(",",ingredientArray);
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
