package utils;

import java.util.HashMap;
import java.util.Map;

public class ModuloArithmetic {
  public static long expMod(long a, long pow, long mod) {
    if (pow == 0L) {
      return 1L;
    } else if (pow == 1L) {
      return Math.floorMod(a, mod);
    } else {
      long newPower = Math.floorDiv(pow, 2);
      long newA = Math.floorMod(a * a, mod);
      if (pow % 2L == 1) {
        return Math.floorMod(expMod(newA, newPower, mod) * a, mod);
      } else {
        return Math.floorMod(expMod(newA, newPower, mod), mod);
      }
    }
  }
  public static long discreteLog(long a, long target, long mod) {
    Map<Long, Long> lookUpTable = new HashMap<>();
    long m = (long) (Math.ceil(Math.sqrt(mod-1)));
    for(long j = 0; j<m; j++){
      lookUpTable.put(expMod(a, j, mod), j);
    }
    long aPowNegM = expMod(a, (mod-1-m), mod);
    long gamma = target;
    for(long i = 0; i<m; i++){
      if(lookUpTable.containsKey(gamma)){
        return lookUpTable.get(gamma)+i*m;
      } else {
        gamma = (gamma * aPowNegM)%mod;
      }
    }
    return -1;
  }

}
