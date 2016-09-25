package core.FuzzyPetriLogic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

public class FuzzyTokenTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void first_test() {
    FuzzyToken ff = new FuzzyToken(0.0, 1.0, 0.0, 0.0, 0.0);
    FuzzyToken ff2 = new FuzzyToken(0.0, 0.0, 1.0, 1.0, 0.0);
    ff2.normalize();
    assertFalse(ff.isPhi());
    assertFalse(ff2.isPhi());
    FuzzyToken res = ff2.unite(ff);
    assertEquals("<0.00,0.50,0.25,0.25,0.00>", res.shortString());
  }

}
