package commonUtil.PetriDotDrawer;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import core.Drawable.DrawableFuzzyPetriNet;
import dotDrawer.PetriDotDrawer;
import exampleNets.SimpleDelayPetriNetBuilder;

public class PetriDotDrawerTest {

  @Test
  public void test() {
    SimpleDelayPetriNetBuilder simple = new SimpleDelayPetriNetBuilder();
    DrawableFuzzyPetriNet dpn = new DrawableFuzzyPetriNet(simple.getNet());
    PetriDotDrawer dotDrawer = new PetriDotDrawer(dpn);
    String dotStr = dotDrawer.dotString();
    assertTrue(dotStr.split("->").length == 8);

  }

}
