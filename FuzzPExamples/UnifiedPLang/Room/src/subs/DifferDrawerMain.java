package subs;

import core.UnifiedPetriLogic.DrawableUnifiedPetriNet;

public class DifferDrawerMain {
  public static void main(String args[]) {
    DifferUnifiedPetriMaker maker = new DifferUnifiedPetriMaker();
    DrawableUnifiedPetriNet drawableNet = new DrawableUnifiedPetriNet(maker.net, false, maker.nameStore);
    drawableNet.setReprezentInitialMarking(true);


    SplitterUnifiedPetriMaker spl = new SplitterUnifiedPetriMaker();
    DrawableUnifiedPetriNet drawableNet2 = new DrawableUnifiedPetriNet(spl.net, false, spl.nameStore);
    drawableNet2.setReprezentInitialMarking(true);

    EventStarterUnifiedPetriMaker eve = new EventStarterUnifiedPetriMaker();
    DrawableUnifiedPetriNet drawableNet3 = new DrawableUnifiedPetriNet(eve.net, false, eve.nameStore);
    drawableNet3.setReprezentInitialMarking(true);



  }

}
