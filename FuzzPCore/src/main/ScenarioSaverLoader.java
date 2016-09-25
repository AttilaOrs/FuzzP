package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNetJsonSaver;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorder;

public class ScenarioSaverLoader {
  
  private FuzzyPetriNet net;
  private FullRecorder rec;
  private Gson mySerilaizer;

  public ScenarioSaverLoader(){
    mySerilaizer = new GsonBuilder().serializeNulls().enableComplexMapKeySerialization().setPrettyPrinting().create();
  }

  public void setPetriNet(FuzzyPetriNet net) {
    this.net = net;
  }

  public FuzzyPetriNet getPetriNet() {
    return net;
  }

  public void setFullRec(FullRecorder rec) {
    this.rec = rec;
  }

  public FullRecorder getFullRec() {
    return rec;
  }

  public void save(File file) {
    Exporter expoert = new Exporter();
    if (net != null) {
    expoert.petriNet = FuzzyPetriNetJsonSaver.makeJsonString(net);
    } else {
      expoert.petriNet = "None";
    }

    if (rec != null) {
      expoert.recorder = rec.makeStr();
    } else {
      expoert.recorder = "None";
    }
    String toExpor = mySerilaizer.toJson(expoert);
    if (file.exists()) {
      file.delete();
    }
    try {
      file.createNewFile();
      BufferedWriter out = new BufferedWriter(new FileWriter(file));
      out.write(toExpor);
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void load(File file) {

    try {
      String content = new Scanner(file).useDelimiter("\\Z").next();
      Exporter exp = mySerilaizer.fromJson(content, Exporter.class);
      if (!exp.petriNet.equals("None")) {
        net = FuzzyPetriNetJsonSaver.loadJsonString(exp.petriNet);
      } else {
        net = null;
      }
      rec = new FullRecorder();
      if (!exp.recorder.equals("None")) {
        rec.buildFromStr(exp.recorder);
      }

    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  private static class Exporter {
    String petriNet;
    String recorder;

  }

}
