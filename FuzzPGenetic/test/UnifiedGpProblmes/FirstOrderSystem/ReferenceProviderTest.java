package UnifiedGpProblmes.FirstOrderSystem;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

public class ReferenceProviderTest {

  private static final double DELTA = 0.0000000001;
  private ReferenceProvider provider;

  @Before
  public void setup() {
    provider = new ReferenceProvider();
  }

  @Test
  public void getReferenceTest() {
    assertEquals(0.9, provider.getReference(0), DELTA);
    assertEquals(0.8, provider.getReference(65), DELTA);
    assertEquals(0.2, provider.getReference(31), DELTA);

  }

  @Test
  public void getSteadySateErrorTest() {
    ArrayList<Double> evolution = new ArrayList<>(Collections.nCopies(80, 0.0));
    double rez = provider.getSteadyStateErrorSum(evolution);
    assertEquals(3.5, rez, DELTA);

    evolution = new ArrayList<>(Collections.nCopies(80, 0.9));
    rez = provider.getSteadyStateErrorSum(evolution);
    assertEquals(0.9, provider.getReference(0), DELTA);
  }




}
