package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.function.Function;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import core.FuzzyPetriLogic.PetriNet.PetriNetJsonSaver;
import core.common.recoder.FullRecordable;
import core.common.recoder.FullRecorder;

public class ScenarioSaverLoader<PetriNetType, TokenType extends FullRecordable<TokenType>> {
  
  private PetriNetType net;
  private FullRecorder<TokenType> rec;
  private Gson mySerilaizer;
  private Class<PetriNetType> clazz;

  public ScenarioSaverLoader(Class<PetriNetType> clazz) {
    this.clazz = clazz;
    mySerilaizer = new GsonBuilder().serializeNulls().enableComplexMapKeySerialization().setPrettyPrinting().create();
  }

  public void setPetriNet(PetriNetType net) {
    this.net = net;
  }

  public PetriNetType getPetriNet() {
    return net;
  }

  public void setFullRec(FullRecorder<TokenType> rec) {
    this.rec = rec;
  }

  public FullRecorder<TokenType> getFullRec() {
    return rec;
  }

  public void save(File file) {
    Exporter expoert = new Exporter();
    if (net != null) {
      PetriNetJsonSaver<PetriNetType> saver = new PetriNetJsonSaver<>();
      expoert.petriNet = saver.makeString(net);
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

  public void load(File file, Function<String, TokenType> conv) {

    try {
      Scanner scanner = new Scanner(file);
      String content = scanner.useDelimiter("\\Z").next();
      Exporter exp = mySerilaizer.fromJson(content, Exporter.class);
      if (!exp.petriNet.equals("None")) {
        PetriNetJsonSaver<PetriNetType> saver = new PetriNetJsonSaver<>();
        net = saver.loadFromJsonString(exp.petriNet, clazz);
      } else {
        net = null;
      }
      rec = new FullRecorder<>();
      if (!exp.recorder.equals("None")) {
        rec.buildFromStr(exp.recorder, conv);
      }
      scanner.close();

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
