package dotDrawer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import structure.DrawableNet;

public class PetriDotDrawer {

  private static final String DOT_Name = "dot";
  DrawableNet net;
  DotConfig dotConfig;
  private ArrayList<String> placeStr;
  protected ArrayList<String> transitionNames;
  protected ArrayList<String> transitionDelcaration;

  public PetriDotDrawer(DrawableNet ll) {
    net = ll;
    dotConfig = new DotConfig();
  }

  public void setDotConfig(DotConfig config) {
    this.dotConfig = config;
  }

  public void makeImageAndTk(String dot) {
    String dotName = dot + ".txt";
    String imgName = dot + ".png";
    String tk = dot + ".tk";
    writeToFile(dotName);
    try {
      Runtime.getRuntime().exec(new String[] { DOT_Name, "-T", "png", dotName, "-o", imgName });
      Runtime.getRuntime().exec(new String[] { DOT_Name, "-T", "tk", dotName, "-o", tk });
    } catch (IOException ex) {
      System.err.println("problems with dot process (do you have installed?), >>> PetriDotDrawer");
      ex.printStackTrace();
    }
  }

  public void writeToFile(String fileName) {

    try {
      Files.write(Paths.get(fileName), dotString().getBytes(), StandardOpenOption.CREATE);
    } catch (IOException e) {
      System.err.println("problems with writeing the file, >>> PetriDotDrawer");
      e.printStackTrace();
    }
  }

  public void makeImage(String dot) {
    String dotName = dot + ".txt";
    String imgName = dot + ".png";
    writeToFile(dotName);
    try {
      Runtime.getRuntime().exec(new String[] { DOT_Name, "-T", "png", dotName, "-o", imgName });
    } catch (IOException e) {
      System.err.println("problems with dot process (do you have installed?), >>> PetriDotDrawer");
      e.printStackTrace();
    }
  }

  public String dotString() {
    buildPlaceStrings();
    buildTransitionStrings();
    StringBuilder bld = new StringBuilder();
    bld.append(dotConfig.getAllStarter()).append("\n");
    bld.append(dotConfig.getPlaceStarter()).append("\n");
    for (String ss : placeStr) {
      bld.append(ss).append(dotConfig.getLimit());
    }
    bld.append(dotConfig.getPlaceEnder()).append("\n");

    bld.append(dotConfig.getTranStarter()).append("\n");
    for (String ss : transitionDelcaration) {
      bld.append(ss).append(dotConfig.getLimit());
    }
    bld.append(dotConfig.getTransEnder()).append("\n");
    net.getArcs().forEach(arc -> {
      if (arc.arcFromPlaceToTransition) {
        bld.append(placeStr.get(arc.placeId)).append(dotConfig.getArc());
        bld.append(transitionNames.get(arc.transitionId)).append(dotConfig.getLimit()).append("\n");
      } else {
        bld.append(transitionNames.get(arc.transitionId)).append(dotConfig.getArc());
        bld.append(placeStr.get(arc.placeId)).append(dotConfig.getLimit()).append("\n");
      }

    });

    bld.append(dotConfig.getAllEnder());

    return bld.toString();
  }

  protected void buildTransitionStrings() {
    transitionNames = new ArrayList<String>();
    transitionDelcaration = new ArrayList<String>();
    for (int i = 0; i < net.getNrOfTransition(); i++) {
      String toPut = net.getTransitionName(i);
      transitionNames.add((new Character('"')) + toPut + (new Character('"')));
      transitionDelcaration.add((new Character('"')) + toPut + (new Character('"')));
    }
  }

  private void buildPlaceStrings() {
    placeStr = new ArrayList<String>();
    for (int i = 0; i < net.getNrOfPlaces(); i++) {
      placeStr.add((new Character('"')) + net.getPlaceName(i) + new Character('"'));
    }
  }

  public static void draw(DrawableNet myNode, String string) {
    PetriDotDrawer temp = new PetriDotDrawer(myNode);
    temp.makeImage(string);
  }

}
