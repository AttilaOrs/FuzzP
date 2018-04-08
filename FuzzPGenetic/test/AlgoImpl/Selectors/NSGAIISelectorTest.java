package AlgoImpl.Selectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    NSGAIISelector s = new NSGAIISelector();
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

    NSGAIISelector s = new NSGAIISelector();
    s.initialize(res);
    List<int[]> rez = s.selectNondeterministicly(3, 1);
    assertTrue(rez.size() == 3);
    Set<Integer> ii = rez.stream().map(i -> i[0]).collect(Collectors.toSet());
    assertTrue(ii.equals(new HashSet<>(Arrays.asList(6, 7, 8))));

    rez = s.selectNondeterministicly(10, 1);
    assertTrue(rez.size() == 10);
    ii = rez.stream().map(i -> i[0]).collect(Collectors.toSet());
    assertEquals(ii, new HashSet<>(Arrays.asList(6, 7, 8, 0, 1, 2, 9, 10, 11, 12)));

    rez = s.selectNondeterministicly(13, 1);
    assertTrue(rez.size() == 13);
    ii = rez.stream().map(i -> i[0]).collect(Collectors.toSet());
    assertEquals(ii, new HashSet<>(Arrays.asList(0, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14)));

  }

  @Test
  public void test3() {
    HashMap<Integer, Double[]> res = new HashMap<>();
    res.put(0, new Double[] { 7.0, 0.0 });
    res.put(1, new Double[] { 0.0, 7.0 });
    res.put(2, new Double[] { 5.0, 2.0 });
    res.put(3, new Double[] { 4.0, 3.0 });
    res.put(4, new Double[] { 3.0, 4.0 });
    res.put(5, new Double[] { 0.5, 6.5 });

  }

}
