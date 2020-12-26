package day18;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import utils.AoCSolvable;
import utils.NoSolutionException;

public class Solution implements AoCSolvable {
  private static final boolean USE_SAMPLE = false;

  private static class TagPair{
    private int tag;
    private long i;
    private char c;

    private TagPair(long i){
      this.tag = 0;
      this.i = i;
    }

    private TagPair(char c){
      this.tag = 1;
      this.c = c;
    }
  }

  private final List<char[] >symbols;

  Solution(List<String> lines) throws NoSolutionException {
    symbols = new ArrayList<>();
    for (String line:lines){
      symbols.add(line.replace(" ", "").toCharArray());
    }
  }

  private static long evalRPN(List<TagPair> revPolish) throws NoSolutionException{
    Deque<Long> stack = new LinkedList<>();
    for(TagPair t:revPolish){
      if(t.tag == 0) stack.addFirst(t.i);
      else{
        Long t1 = stack.removeFirst();
        Long t2 = stack.removeFirst();
        if(t.c == '*') stack.addFirst(t1*t2);
        else if(t.c == '+') stack.addFirst(t1+t2);
        else throw new NoSolutionException();
      }
    }
    if(stack.size()!=1) throw new NoSolutionException();
    else return stack.removeFirst();
  }

  public String partOne() throws NoSolutionException {
    long solution = 0;
    for (char[] symbolArr:symbols){
      List<TagPair> revPolish = new ArrayList<>();
      Deque<Character> opStack = new LinkedList<>();
      opStack.addFirst('$');
      for (char c:symbolArr){
        switch(c){
          case '0': case '1': case '2': case '3': case '4':
          case '5': case '6': case '7': case '8': case '9':
            revPolish.add(new TagPair(Character.getNumericValue(c)));
            break;
          case '+': case '*':
            while(opStack.peekFirst() != '$' && opStack.peekFirst() != '('){
              revPolish.add(new TagPair(opStack.removeFirst()));
            }
            opStack.addFirst(c);
            break;
          case '(':
            opStack.addFirst(c);
            break;
          case ')':
            while(opStack.peekFirst() != '('){
              revPolish.add(new TagPair(opStack.removeFirst()));
            }
            opStack.removeFirst();
            break;
          default:
            throw new NoSolutionException();
        }
      }
      while(opStack.peekFirst() != '$') revPolish.add(new TagPair(opStack.removeFirst()));
      solution +=  evalRPN(revPolish);
    }
    return String.valueOf(solution);
  }

  public String partTwo() throws NoSolutionException {
    long solution = 0;
    for (char[] symbolArr:symbols){

      List<TagPair> revPolish = new ArrayList<>();
      Deque<Character> opStack = new LinkedList<>();
      opStack.addFirst('$');
      for (char c:symbolArr){
        switch(c){
          case '0': case '1': case '2': case '3': case '4':
          case '5': case '6': case '7': case '8': case '9':
            revPolish.add(new TagPair(Character.getNumericValue(c)));
            break;
          case '+':
            opStack.addFirst(c);
            break;
          case '*':
            while(opStack.peekFirst() == '+'){
              revPolish.add(new TagPair(opStack.removeFirst()));
            }
            opStack.addFirst(c);
            break;
          case '(':
            opStack.addFirst(c);
            break;
          case ')':
            while(opStack.peekFirst() != '('){
              revPolish.add(new TagPair(opStack.removeFirst()));
            }
            opStack.removeFirst();
            break;
          default:
            throw new NoSolutionException();
        }
      }
      while(opStack.peekFirst() != '$') revPolish.add(new TagPair(opStack.removeFirst()));
      solution += evalRPN(revPolish);
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
