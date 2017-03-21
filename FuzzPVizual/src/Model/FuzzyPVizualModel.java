package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.function.Function;

import FuzzyPLang.FuzzyPLangMain.FuzzyPLang;
import PetriNetToCode.FuzzyMakerGenerator;
import config.IConfigurator;
import core.Drawable.DrawableNetWithExternalNames;
import core.Drawable.TransitionPlaceNameStore;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.PetriNet.PetriNetJsonSaver;
import core.common.AbstractPetriNet;
import core.common.recoder.FullRecordable;
import core.common.recoder.FullRecorder;
import de.erichseifert.gral.data.DataTable;
import main.ScenarioSaverLoader;
import structure.DrawableNet;

public class FuzzyPVizualModel<TTokenType extends FullRecordable<TTokenType>, TTableType, TOuTableType extends TTableType, TPetriNetType extends AbstractPetriNet<TTokenType, TTableType, TOuTableType>> {

  TPetriNetType net;
  FuzzyPetrinetBehaviourModel<TTokenType> behavourModel;
  DrawableNet drawableNet;
  private FuzzyPLang lang;
  private TransitionPlaceNameStore store;


  IConfigurator<TTokenType, TTableType, TOuTableType, TPetriNetType> myConfig;
  Function<TPetriNetType, IConfigurator<TTokenType, TTableType, TOuTableType, TPetriNetType>> configFactory;


  public FuzzyPVizualModel(
      Function<TPetriNetType, IConfigurator<TTokenType, TTableType, TOuTableType, TPetriNetType>> configFactory) {
    this.configFactory = configFactory;
  }

  public TPetriNetType getNet() {
    return net;
  }

  public IConfigurator<TTokenType, TTableType, TOuTableType, TPetriNetType> getConfigurator() {
    return myConfig;
  }

  public void save(File file) {
    ScenarioSaverLoader<TPetriNetType, TTokenType> saver = new ScenarioSaverLoader<>(myConfig.getPetriClass());
    saver.setPetriNet(net);
    saver.setFullRec(behavourModel.recorder);
    saver.save(file);
  }

  public void load(File selectedFile) {
    ScenarioSaverLoader<TPetriNetType, TTokenType> loader = new ScenarioSaverLoader<>(myConfig.getPetriClass());
    loader.load(selectedFile, myConfig.getStringConverter());
    setNet(loader.getPetriNet());
    setFullRecorder(loader.getFullRec());
  }

  public void saveToJava(File loadedFile) {
    if (net instanceof FuzzyPetriNet) {
      FuzzyMakerGenerator gen = new FuzzyMakerGenerator((FuzzyPetriNet) net, getSore(), null);
      String rez = gen.createMaker(loadedFile.getName().replaceFirst("[.][^.]+$", ""));
      String path = loadedFile.getParentFile().toString();
      String fileName = gen.getGeneratedClassName() + ".java";
      System.out.println(path + File.separator + fileName);
      File outFile = new File(path, fileName);
      try {
        PrintWriter writer = new PrintWriter(outFile);
        writer.print(rez);
        writer.flush();
        writer.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    } else {
      throw new RuntimeException("Unsuported operation");
    }

  }

  private TransitionPlaceNameStore getSore() {
    if (store == null) {
      store = TransitionPlaceNameStore.createOrdinarNames(net);
    }
    return store;
  }

  public void loadFuzzyPLang(File selectedFile) {
    if (myConfig.getPetriClass().equals(FuzzyPetriNet.class)) {
      if (lang == null) {
        lang = new FuzzyPLang();
      }
      lang.loadFile(selectedFile);
      setNet((TPetriNetType) lang.getNet());
      setDrawableNet(new DrawableNetWithExternalNames(lang.getNet(), lang.getNameStore()));
      setFullRecorder(new FullRecorder());
      setNameStore(lang.getNameStore());
    } else {
      throw new RuntimeException("Unsupported operation");
    }
  }

  public void setNameStore(TransitionPlaceNameStore nameStore) {
    this.store = nameStore;
  }

  public void setNet(TPetriNetType net) {
    this.net = net;
    myConfig = configFactory.apply(net);
  }

  public void setDrawableNet(DrawableNet net) {
    this.drawableNet = net;
  }

  public TransitionPlaceNameStore getNameStore() {
    if (store == null) {
      store = TransitionPlaceNameStore.createOrdinarNames(net);
    }
    return store;
  }

  public DrawableNet getDrowableNet() {
    if (drawableNet == null) {
      drawableNet = myConfig.getDrawableNetFactory().apply(net, getNameStore());
      // new DrawableNetWithExternalNames((FuzzyPetriNet) net, getNameStore());
    }
    return drawableNet;
  }

  public void setFullRecorder(FullRecorder<TTokenType> recorder) {
    behavourModel = new FuzzyPetrinetBehaviourModel<>(recorder, myConfig.getDoubleConverter());
  }

  public DataTable getDataForPlace(int placeId) {
    return behavourModel.getDataForPlace(placeId);
  }

  public int getTickNr() {
    return behavourModel.getTickNr();
  }

  public TTableType getTableForTranition(int trId) {
    return net.getTableForTransition(trId);
  }

  public void savePetriJsonOnly(File selectedFile) {
    PetriNetJsonSaver<TPetriNetType> tt = new PetriNetJsonSaver<>();
    tt.save(net, selectedFile.getAbsolutePath());
  }

}
