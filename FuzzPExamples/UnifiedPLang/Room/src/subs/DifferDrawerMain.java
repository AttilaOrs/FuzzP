package subs;

import core.UnifiedPetriLogic.DrawableUnifiedPetriNet;
import dotDrawer.PetriDotDrawerVerical;

public class DifferDrawerMain {
  public static void main(String args[]) {
    DifferUnifiedPetriMaker maker = new DifferUnifiedPetriMaker();
    DrawableUnifiedPetriNet drawableNet = new DrawableUnifiedPetriNet(maker.net, false, maker.nameStore);
    drawableNet.setReprezentInitialMarking(true);
    PetriDotDrawerVerical vv = new PetriDotDrawerVerical(
        drawableNet);

    vv.makeImage("differ");

    SplitterUnifiedPetriMaker spl = new SplitterUnifiedPetriMaker();
    DrawableUnifiedPetriNet drawableNet2 = new DrawableUnifiedPetriNet(spl.net, false, spl.nameStore);
    drawableNet2.setReprezentInitialMarking(true);
    PetriDotDrawerVerical vv2 = new PetriDotDrawerVerical(
        drawableNet2);
    vv2.makeImage("splitter");

    EventStarterUnifiedPetriMaker eve = new EventStarterUnifiedPetriMaker();
    DrawableUnifiedPetriNet drawableNet3 = new DrawableUnifiedPetriNet(eve.net, false, eve.nameStore);
    drawableNet3.setReprezentInitialMarking(true);
    PetriDotDrawerVerical vv3 = new PetriDotDrawerVerical(
        drawableNet3);

    vv3.makeImage("eventStarter");


  }

}
