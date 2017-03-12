package dotDrawer;

import java.util.ArrayList;

import structure.DrawableNet;

public class PetriDotDrawerVerical extends PetriDotDrawer {

  public PetriDotDrawerVerical(DrawableNet ll) {
    super(ll);
    super.setDotConfig(new DotConfigVertical());
  }

  // t1[label="" xlabel=<<FONT POINT-SIZE='15'> t1[i3]</FONT>>];
  protected void buildTransitionStrings() {
    transitionNames = new ArrayList<String>();
    transitionDelcaration = new ArrayList<String>();
    for (int i = 0; i < net.getNrOfTransition(); i++) {
      String tarnsitionFullName = net.getTransitionName(i);
      String smallName = "t" + Integer.toString(i);
      String declaration = smallName + "[label=" + '"' + '"' + "xlabel=<<FONT POINT-SIZE='15'> " + tarnsitionFullName
          + "</FONT>>]";
      transitionDelcaration.add(declaration);
      transitionNames.add(smallName);
    }
  }
}
