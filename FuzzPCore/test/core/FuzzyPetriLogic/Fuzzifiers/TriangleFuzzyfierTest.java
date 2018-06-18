package core.FuzzyPetriLogic.Fuzzifiers;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import core.FuzzyPetriLogic.FuzzyDriver;
import core.FuzzyPetriLogic.FuzzyToken;
import core.FuzzyPetriLogic.FuzzyValue;

public class TriangleFuzzyfierTest {

  private TriangleFuzzyfier underTest;

  @Before
  public void setUp() throws Exception {
    underTest = new TriangleFuzzyfier(new Double[] { null, -2.0, -1.0 }, new Double[] { -2.0, -1.0, 0.0 },
        new Double[] { -1.0, 0.0, 1.0 }, new Double[] { 0.0, 1.0, 2.0 }, new Double[] { 1.0, 2.0, null });
  }

  @Test
  public void NaN_proof_test() {
    FuzzyDriver f = FuzzyDriver.createDriverFromMinMax(0.0 * -1.0, 0.0);
    FuzzyToken rez = f.fuzzifie(0.0);
    for (FuzzyValue v : FuzzyValue.inOrderWithoutPhi) {
      assertFalse(Double.isNaN(rez.getFuzzyValue(v)));
    }

  }

  @Test
  public void testWithPlot() {
    HashMap<String, ArrayList<Double>> ll = new HashMap<>();

    underTest = new TriangleFuzzyfier(new Double[] { null, 10.0, 16.0 }, new Double[] { 10.0, 16.0, 22.0 },
        new Double[] { 16.0, 22.0, 28.0 }, new Double[] { 22.0, 28.0, 34.0 }, new Double[] { 28.0, 34.0, null });
    List<String> strList = new ArrayList<>();
    for (double r = 5; r < 39; r += 0.1) {
      StringBuilder bld = new StringBuilder();
      FuzzyToken res = underTest.fuzzifie(r);
      bld.append(r).append(" ");
      for (FuzzyValue fv : FuzzyValue.inOrderWithoutPhi) {
        bld.append(res.getFuzzyValue(fv)).append(" ");
      }
      strList.add(bld.toString());
    }
      // Files.write(Paths.get("fuzzy_temp.dat"), strList, Charset.forName("UTF-8"));

  }

}
