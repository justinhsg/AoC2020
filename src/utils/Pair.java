package utils;

public class Pair<A extends Comparable<? super A>, B extends Comparable<? super B>>  implements Comparable{
  public A fst;
  public B snd;
  public Pair(A fst, B snd){
    this.fst = fst;
    this.snd = snd;
  }

  @Override
  public String toString(){
    return "(" + fst.toString() + ", " + snd.toString() + ")";
  }

  @Override
  public int hashCode(){
    return this.fst.hashCode()*this.snd.hashCode();
  }

  @Override
  public boolean equals(Object o){
    Pair<A, B> other = (Pair<A,B>) o;
    return this.fst.equals(other.fst) && this.snd.equals(other.snd);
  }

  @Override
  public int compareTo(Object o) {
    Pair<A,B> other = (Pair<A,B>) o;
    return this.fst.compareTo(other.fst) != 0? this.fst.compareTo(other.fst) : this.snd.compareTo(other.snd);
  }
}
