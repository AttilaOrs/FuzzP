import core.UnifiedPetriLogic.DrawableUnifiedPetriNet;
import dotDrawer.PetriDotDrawerVerical;

public class printMain {

  public static void main(String[] args) {
    printUnifiedPetriMaker maker = new printUnifiedPetriMaker();
    PetriDotDrawerVerical vv = new PetriDotDrawerVerical(
        new DrawableUnifiedPetriNet(maker.net, false, maker.nameStore));
    vv.makeImage("example");

  }

}
