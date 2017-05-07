package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.function.Function;

import FuzzyPLang.FuzzyPLangMain.FuzzyPLang;
import PetriNetToCode.FuzzyNetMakerCodeGenerator;
import PetriNetToCode.UnifiedNetMakerCodeGenerator;
import UnifiedPLang.UnifiedPLang;
import config.IConfigurator;
import core.Drawable.DrawableNetWithExternalNames;
import core.Drawable.TransitionPlaceNameStore;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import core.FuzzyPetriLogic.PetriNet.PetriNetJsonSaver;
import core.UnifiedPetriLogic.DrawableUnifiedPetriNetWithExternalNames;
import core.UnifiedPetriLogic.UnifiedPetriNet;
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
    String rez = "";
    String path = loadedFile.getParentFile().toString();
    String fileName = "";
    String netName =loadedFile.getName().replaceFirst("[.][^.]+$", "") ;

    if (net instanceof FuzzyPetriNet) {
      FuzzyNetMakerCodeGenerator gen = new FuzzyNetMakerCodeGenerator((FuzzyPetriNet) net, getSore(), null);
      rez = gen.createMaker(netName);
      fileName = gen.getGeneratedClassName() + ".java";
    } else {
      
      UnifiedNetMakerCodeGenerator gen = new UnifiedNetMakerCodeGenerator((UnifiedPetriNet) net, netName,
          getNameStore());
      rez = gen.generateMaker();
      fileName = gen.getClassName() + ".java";
    }
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

  }

  private TransitionPlaceNameStore getSore() {
    if (store == null) {
      store = TransitionPlaceNameStore.createOrdinarNames(net);
    }
    return store;
  }

  public void loadFuzzyPLang(File selectedFile) {
    if (myConfig.getPetriClass().equals(FuzzyPetriNet.class)) {
      FuzzyPLang lang = new FuzzyPLang();
      lang.loadFile(selectedFile);
      setNet((TPetriNetType) lang.getNet());
      setDrawableNet(new DrawableNetWithExternalNames(lang.getNet(), lang.getNameStore()));
      setFullRecorder(new FullRecorder());
      setNameStore(lang.getNameStore());
    } else {
      UnifiedPLang lang = new UnifiedPLang();
      lang.loadFile(selectedFile);
      UnifiedPetriNet rezNet = lang.getRezNet();
      setNet((TPetriNetType) rezNet);
      setDrawableNet(new DrawableUnifiedPetriNetWithExternalNames(rezNet, lang.getNameStrore()));
      setFullRecorder(new FullRecorder<>());
      setNameStore(lang.getNameStrore());
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
    }
    return drawableNet;
  }

  public void setFullRecorder(FullRecorder<TTokenType> recorder) {
    behavourModel = new FuzzyPetrinetBehaviourModel<>(recorder, myConfig.getDoubleConverter());
  }

  public DataTable getDataForPlace(Integer placeId) {
    return behavourModel.getDataForPlace(placeId);
  }

  public double getMaxForPlace(Integer placeId) {
    return behavourModel.getMaxForPlace(placeId);
  }

  public double getMinForPlace(Integer placeId) {
    return behavourModel.getMinForPlace(placeId);
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
