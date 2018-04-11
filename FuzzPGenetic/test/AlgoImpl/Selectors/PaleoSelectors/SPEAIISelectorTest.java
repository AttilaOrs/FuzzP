package AlgoImpl.Selectors.PaleoSelectors;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import org.junit.Test;

import AlgoImpl.Selectors.PaleoSelectors.SPEAIISelector;

public class SPEAIISelectorTest {

  @Test
  public void test() {
    HashMap<Integer, Double[]> res = new HashMap<>();
    res.put(0, new Double[]{10.0, 5.0});
    res.put(1, new Double[]{5.0, 10.0});
    res.put(2, new Double[]{7.5, 7.5});

    res.put(3, new Double[]{5.0, 5.0});
    res.put(4, new Double[]{7.5, 2.5});
    res.put(5, new Double[]{2.5, 7.5});

    res.put(6, new Double[]{2.5, 2.5});
    res.put(7, new Double[]{5.0, 0.0});
    res.put(8, new Double[]{0.0, 5.0});

    res.put(9, new Double[]{1.0, 1.0});

    SPEAIISelector selec = new SPEAIISelector(new ForkJoinPool(1));
    selec.initialize(res);


    List<int[]> rez = selec.selectNondeterministicly(3, 1);

    assertTrue(rez.size() == 3);
    Set<Integer> ii = rez.stream().map(i -> i[0]).collect(Collectors.toSet());
    assertTrue(ii.equals(new HashSet<>(asList(0, 1, 2))));

    rez = selec.selectNondeterministicly(2, 1);

    assertTrue(rez.size() == 2);
    ii = rez.stream().map(i -> i[0]).collect(Collectors.toSet());
    assertTrue(ii.equals(new HashSet<>(asList(0, 1))));

    rez = selec.selectNondeterministicly(4, 1);

    assertTrue(rez.size() == 4);
    ii = rez.stream().map(i -> i[0]).collect(Collectors.toSet());
    assertTrue(ii.equals(new HashSet<>(asList(0, 1, 2, 4))));

  }

  @Test
  public void test_bigFitness() {
    HashMap<Integer, Double[]> res = new HashMap<>();
    Random rnd = new Random();
    for (int i = 0; i < 1000; i++) {
      Double x = rnd.nextDouble();
      Double f1 = x * x - x + 2;
      Double f2 = x * x * x + x + 2;
      Double f3 = x * x * x - 10 * x * x + x + 100000;
      res.put(i, new Double[]{f1, f2, f3});
    }
    long start = System.nanoTime();
    SPEAIISelector s = new SPEAIISelector(new ForkJoinPool(1));
    s.initialize(res);
    long stop = System.nanoTime();
    long startMulti = System.nanoTime();
    SPEAIISelector s2 = new SPEAIISelector(new ForkJoinPool(8));
    s2.initialize(res);
    /*
     * long stopMulti = System.nanoTime(); System.out.println(stop - start);
     * System.out.println(stopMulti - startMulti);
     * 
     * long simpple = 0; long multu = 0; int run = 5; for (int i = 0; i < run;
     * i++) { start = System.nanoTime(); s = new NSGAIISelector(new
     * ForkJoinPool(1)); s.initialize(res); stop = System.nanoTime(); startMulti
     * = System.nanoTime(); s2 = new NSGAIISelector(new ForkJoinPool(16));
     * s2.initialize(res); stopMulti = System.nanoTime(); simpple += stop-start;
     * multu += stopMulti - startMulti; }
     * 
     * System.out.println(TimeUnit.NANOSECONDS.toMillis( simpple/run));
     * System.out.println(TimeUnit.NANOSECONDS.toMillis( multu/run));
     * System.out.println(multu/(double) simpple);
     */

    assertEquals(s.assignedFitnes, s2.assignedFitnes);
  }
}
