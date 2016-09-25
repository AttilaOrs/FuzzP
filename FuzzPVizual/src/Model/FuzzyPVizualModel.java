package Model;

import java.io.File;

import core.DrawableFuzzyPetriNet;
import core.FuzzyPetriLogic.ITable;
import core.FuzzyPetriLogic.Fuzzifiers.TriangleFuzzyfier;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.PetriNet.Recorders.FullRecorder;
import de.erichseifert.gral.data.DataTable;
import main.ScenarioSaverLoader;
import structure.DrawableNet;

public class FuzzyPVizualModel {

  TriangleFuzzyfier defalutFuzzyfier = TriangleFuzzyfier.defaultFuzzyfier();
  FuzzyPetriNet net;
  FuzzyPetrinetBehaviourModel behavourModel;
  DrawableNet drowableNet;

  public FuzzyPVizualModel() {
  }

  public FuzzyPetriNet getNet() {
    return net;
  }

  public void save(File file) {
    ScenarioSaverLoader saver = new ScenarioSaverLoader();
    saver.setPetriNet(net);
    saver.setFullRec(behavourModel.recorder);
    saver.save(file);
  }

  public void load(File selectedFile) {
    ScenarioSaverLoader loader = new ScenarioSaverLoader();
    loader.load(selectedFile);
    setNet(loader.getPetriNet());
    setFullRecorder(loader.getFullRec());
  }

  public void setNet(FuzzyPetriNet net) {
    this.net = net;
    this.drowableNet = new DrawableFuzzyPetriNet(net);
  }

  public DrawableNet getDrowableNet() {
    return drowableNet;
  }

  public void setFullRecorder(FullRecorder recorder) {
    behavourModel = new FuzzyPetrinetBehaviourModel(recorder);
  }

  public DataTable getDataForPlace(int placeId) {
    return behavourModel.getDataForPlace(placeId);
  }

  public int getTickNr() {
    return behavourModel.getTickNr();
  }

  public ITable getTableForTranition(int trId) {
    return net.getTableForTransition(trId);
  }

}
