package AlgoImpl.Selectors;

import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import AlgoImpl.Selectors.RouletteWheelSelection;

public class RouletteWheelSelectionTest {
  RouletteWheelSelection sel;

  @Before
  public void setUp() throws Exception {
    sel = new RouletteWheelSelection();
  }

  @Test
  public void test() {
    HashMap<Integer, Double[]> res = new HashMap<>();
    res.put(0, new Double[] { 0.0 });
    res.put(2, new Double[] { 1.0 });
    res.put(3, new Double[] { 1.0 });
    res.put(1, new Double[] { 1.0 });

    List<int[]> selected = sel.selectOne(res, 0, 2, 1);

    Assert.assertTrue("size problem", selected.size() == 2);
    Assert.assertTrue("not ok", selected.get(0)[0] > 0);
    Assert.assertTrue("not ok", selected.get(1)[0] > 0);
  }

  @Test
  public void test2() {
    HashMap<Integer, Double[]> res = new HashMap<>();
    res.put(0, new Double[] { 0.0 });
    res.put(2, new Double[] { 0.5 });
    res.put(3, new Double[] { 0.5 });
    res.put(1, new Double[] { 0.5 });
    res.put(4, new Double[] { 0.5 });
    res.put(5, new Double[] { 1.0 });
    res.put(6, new Double[] { 1.0 });
    res.put(7, new Double[] { 1.0 });

    List<int[]> selected = sel.selectOne(res, 0, 4, 1);

    Assert.assertTrue("size problem", selected.size() == 4);
    Assert.assertTrue("not ok", selected.get(0)[0] > 0);
    Assert.assertTrue("not ok", selected.get(1)[0] > 0);
  }

}
