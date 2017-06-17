import core.UnifiedPetriLogic.DrawableUnifiedPetriNet;
import dotDrawer.PetriDotDrawerVertical;

public class printMain {

  public static void main(String[] args) {
    printUnifiedPetriMaker maker = new printUnifiedPetriMaker();
    PetriDotDrawerVertical vv = new PetriDotDrawerVertical(
        new DrawableUnifiedPetriNet(maker.net, false, maker.nameStore));
    vv.makeImage("example");

  }

}
