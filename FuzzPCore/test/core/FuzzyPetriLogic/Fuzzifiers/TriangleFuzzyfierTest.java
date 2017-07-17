package core.FuzzyPetriLogic.Fuzzifiers;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.HashMap;

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
    ll.put("sin", new ArrayList<>());
    ll.put("defizified", new ArrayList<>());
    ll.put(FuzzyValue.NL.name(), new ArrayList<>());
    ll.put(FuzzyValue.NM.name(), new ArrayList<>());
    ll.put(FuzzyValue.ZR.name(), new ArrayList<>());
    ll.put(FuzzyValue.PM.name(), new ArrayList<>());
    ll.put(FuzzyValue.PL.name(), new ArrayList<>());
    for (double r = 0; r < 2 * Math.PI; r += 0.001) {
      double ss = Math.sin(r) * 2.2;
      FuzzyToken res = underTest.fuzzifie(ss);
      ll.get("sin").add(ss);
      ll.get(FuzzyValue.NL.name()).add(res.getFuzzyValue(FuzzyValue.NL));
      ll.get(FuzzyValue.NM.name()).add(res.getFuzzyValue(FuzzyValue.NM));
      ll.get(FuzzyValue.ZR.name()).add(res.getFuzzyValue(FuzzyValue.ZR));
      ll.get(FuzzyValue.PM.name()).add(res.getFuzzyValue(FuzzyValue.PM));
      ll.get(FuzzyValue.PL.name()).add(res.getFuzzyValue(FuzzyValue.PL));
      ll.get("defizified").add(underTest.defuzzify(res));
    }

		// PlotUtils.plot(ll, "kacsa.svg");
  }

}
