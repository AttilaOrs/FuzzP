package UnifiedGpProblmes.Multiplexer;

import java.util.function.Function;

public class Problem {

  public int hits(Function<boolean[], Boolean> what) {
    int hits = 0;
    for (int i = 0; i < 1 << 11; i++) {
      boolean[] bits = intToBit(i);
      Boolean rez = what.apply(bits);
      if (rez != null) {
        int index = 4 * ((bits[10] ? 1 : 0)) + 2 * (bits[9] ? 1 : 0) + (bits[8] ? 1 : 0);
        boolean theory = bits[index];
        if (rez.booleanValue() == theory) {
          hits += 1;
        }
      }

    }
    return hits;

  }

  private static boolean[] intToBit(int inp) {
    boolean[] toRet = new boolean[11];
    for (int i = 0; i < 11; i++) {
      toRet[i] = (inp & (1 << i)) != 0;
    }
    return toRet;
  }

}
