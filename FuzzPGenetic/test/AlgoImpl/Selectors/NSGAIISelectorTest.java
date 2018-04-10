package AlgoImpl.Selectors;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.Test;

public class NSGAIISelectorTest {

  @Test
  public void test() {
    HashMap<Integer, Double[]> res = new HashMap<>();
    res.put(0, new Double[] { 10.0, 5.0 });
    res.put(1, new Double[] { 5.0, 10.0 });
    res.put(2, new Double[] { 7.5, 7.5 });

    res.put(3, new Double[] { 5.0, 5.0 });
    res.put(4, new Double[] { 7.5, 2.5 });
    res.put(5, new Double[] { 2.5, 7.5 });

    res.put(6, new Double[] { 2.5, 2.5 });
    res.put(7, new Double[] { 5.0, 0.0 });
    res.put(8, new Double[] { 0.0, 5.0 });

    res.put(9, new Double[] { 1.0, 1.0 });

    NSGAIISelector s = new NSGAIISelector(new ForkJoinPool(1));
    s.initialize(res);
    assertTrue(String.valueOf(s.fronts).equals("[[0, 1, 2], [3, 4, 5], [6, 7, 8], [9]]"));

  }

  @Test
  public void test2() {
    HashMap<Integer, Double[]> res = new HashMap<>();
    res.put(0, new Double[] { 7.0, 0.0 });
    res.put(1, new Double[] { 0.0, 7.0 });
    res.put(2, new Double[] { 5.0, 2.0 });
    res.put(3, new Double[] { 4.0, 3.0 });
    res.put(4, new Double[] { 3.0, 4.0 });
    res.put(5, new Double[] { 0.5, 6.5 });

    res.put(6, new Double[] { 10.0, 5.0 });
    res.put(7, new Double[] { 5.0, 10.0 });
    res.put(8, new Double[] { 7.5, 7.5 });

    res.put(9, new Double[] { 4.0, 4.0 });
    res.put(10, new Double[] { 2.5, 5.5 });
    res.put(11, new Double[] { 4.5, 3.5 });
    res.put(12, new Double[] { 1.5, 6.6 });

    res.put(13, new Double[] { 2.5, 2.5 });
    res.put(14, new Double[] { 5.0, 0.0 });
    res.put(15, new Double[] { 0.0, 5.0 });

    res.put(16, new Double[] { 1.0, 1.0 });

    NSGAIISelector s = new NSGAIISelector(new ForkJoinPool(1));
    s.initialize(res);
    List<int[]> rez = s.selectNondeterministicly(3, 1);
    assertTrue(rez.size() == 3);
    Set<Integer> ii = rez.stream().map(i -> i[0]).collect(Collectors.toSet());
    assertTrue(ii.equals(new HashSet<>(asList(6, 7, 8))));

    rez = s.selectNondeterministicly(10, 1);
    assertTrue(rez.size() == 10);
    ii = rez.stream().map(i -> i[0]).collect(Collectors.toSet());
    assertEquals(ii, new HashSet<>(asList(6, 7, 8, 0, 1, 2, 9, 10, 11, 12)));

    rez = s.selectNondeterministicly(13, 1);
    assertTrue(rez.size() == 13);
    ii = rez.stream().map(i -> i[0]).collect(Collectors.toSet());
    assertEquals(ii, new HashSet<>(asList(0, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14)));

  }

  @Test
  public void test_three_fitness() {
    HashMap<Integer, Double[]> res = new HashMap<>();
    res.put(0, new Double[] { 15.0, 0.0, 0.0 });
    res.put(1, new Double[] { 0.0, 15.0, 0.0 });
    res.put(2, new Double[] { 0.0, 0.0, 15.0 });
    res.put(3, new Double[] { 1.0, 1.0, 13.0 });
    res.put(4, new Double[] { 1.0, 13.0, 1.0 });
    res.put(5, new Double[] { 13.0, 1.0, 1.0 });

    res.put(6, new Double[] { 7.0, 0.0, 0.0 });
    res.put(7, new Double[] { 0.0, 7.0, 0.0 });
    res.put(8, new Double[] { 0.0, 0.0, 7.0 });

    res.put(9, new Double[] { 6.0, 0.0, 0.0 });
    res.put(10, new Double[] { 0.0, 6.0, 0.0 });
    res.put(11, new Double[] { 0.0, 0.0, 6.0 });

    res.put(12, new Double[] { 5.0, 0.0, 0.0 });
    res.put(13, new Double[] { 0.0, 5.0, 0.0 });
    res.put(14, new Double[] { 0.0, 0.0, 5.0 });

    NSGAIISelector s = new NSGAIISelector(new ForkJoinPool(1));
    s.initialize(res);

    assertTrue(String.valueOf(s.fronts).equals("[[0, 1, 2, 3, 4, 5], [6, 7, 8], [9, 10, 11], [12, 13, 14]]"));

    List<int[]> rez = s.selectNondeterministicly(6, 1);
    assertTrue(rez.size() == 6);
    Set<Integer> ii = rez.stream().map(i -> i[0]).collect(Collectors.toSet());
    assertEquals(ii, new HashSet<>(asList(0, 1, 2, 3, 4, 5)));

    rez = s.selectNondeterministicly(9, 1);
    assertTrue(rez.size() == 9);
    ii = rez.stream().map(i -> i[0]).collect(Collectors.toSet());
    assertEquals(ii, new HashSet<>(asList(0, 1, 2, 3, 4, 5, 6, 7, 8)));
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
      res.put(i, new Double[] { f1, f2, f3 });
    }
    long start = System.nanoTime();
    NSGAIISelector s = new NSGAIISelector(new ForkJoinPool(1));
    s.initialize(res);
    long stop = System.nanoTime();
    long startMulti = System.nanoTime();
    NSGAIISelector s2 = new NSGAIISelector(new ForkJoinPool(8));
    s2.initialize(res);
    /*
    long stopMulti = System.nanoTime();
    System.out.println(stop - start);
    System.out.println(stopMulti - startMulti);

    long simpple = 0;
    long multu = 0;
    int run = 5;
    for (int i = 0; i < run; i++) {
      start = System.nanoTime();
      s = new NSGAIISelector(new ForkJoinPool(1));
      s.initialize(res);
      stop = System.nanoTime();
      startMulti = System.nanoTime();
      s2 = new NSGAIISelector(new ForkJoinPool(16));
      s2.initialize(res);
      stopMulti = System.nanoTime();
      simpple += stop-start;
      multu += stopMulti - startMulti;
    }

    System.out.println(TimeUnit.NANOSECONDS.toMillis( simpple/run));
    System.out.println(TimeUnit.NANOSECONDS.toMillis( multu/run));
    System.out.println(multu/(double) simpple);
    */
    
    assertEquals(s.fronts, s2.fronts);
  }

}
