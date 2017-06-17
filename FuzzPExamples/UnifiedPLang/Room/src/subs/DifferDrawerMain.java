package subs;

import core.UnifiedPetriLogic.DrawableUnifiedPetriNet;
import dotDrawer.PetriDotDrawerVertical;

public class DifferDrawerMain {
  public static void main(String args[]) {
    DifferUnifiedPetriMaker maker = new DifferUnifiedPetriMaker();
    DrawableUnifiedPetriNet drawableNet = new DrawableUnifiedPetriNet(maker.net, false, maker.nameStore);
    drawableNet.setReprezentInitialMarking(true);
    PetriDotDrawerVertical vv = new PetriDotDrawerVertical(
        drawableNet);

    vv.makeImage("differ");

    SplitterUnifiedPetriMaker spl = new SplitterUnifiedPetriMaker();
    DrawableUnifiedPetriNet drawableNet2 = new DrawableUnifiedPetriNet(spl.net, false, spl.nameStore);
    drawableNet2.setReprezentInitialMarking(true);
    PetriDotDrawerVertical vv2 = new PetriDotDrawerVertical(
        drawableNet2);
    vv2.makeImage("splitter");

    EventStarterUnifiedPetriMaker eve = new EventStarterUnifiedPetriMaker();
    DrawableUnifiedPetriNet drawableNet3 = new DrawableUnifiedPetriNet(eve.net, false, eve.nameStore);
    drawableNet3.setReprezentInitialMarking(true);
    PetriDotDrawerVertical vv3 = new PetriDotDrawerVertical(
        drawableNet3);

    vv3.makeImage("eventStarter");


  }

}
