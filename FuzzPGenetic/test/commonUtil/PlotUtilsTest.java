package commonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class PlotUtilsTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void tt() {
    Map<String, ArrayList<Double>> what = new HashMap<>();
    ArrayList<Double> kkk = new ArrayList<>();
    kkk.add(0.0);
    kkk.add(0.5);
    kkk.add(1.0);
    ArrayList<Double> ll = new ArrayList<>();
    ll.add(0.1);
    ll.add(0.6);
    ll.add(1.1);
    what.put("kkkk", kkk);
    what.put("ll", ll);

    PlotUtils.plot(what, "test.svg");

  }

}
