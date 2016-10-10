package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import FuzzyPLang.FuzzyPLangMain.FuzzyPLang;
import FuzzyPetriNetToCode.MakerGenerator;
import core.Drawable.DrawableFuzzyPetriNet;
import core.Drawable.DrawableNetWithExternalNames;
import core.Drawable.TransitionPlaceNameStore;
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
	DrawableNet drawableNet;
	private FuzzyPLang lang;
	private TransitionPlaceNameStore store;

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

	public void saveToJava(File loadedFile) {
		MakerGenerator gen = new MakerGenerator(net, getSore(), null);
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

	}

	private TransitionPlaceNameStore getSore() {
		if (store == null) {
			store = TransitionPlaceNameStore.createOrdinarNames(net);
		}
		return store;
	}

	public void loadFuzzyPLang(File selectedFile) {
		if (lang == null) {
			lang = new FuzzyPLang();
		}
		lang.loadFile(selectedFile);
		setNet(lang.getNet());
		setDrawableNet(new DrawableNetWithExternalNames(lang.getNet(), lang.getNameStore()));
		setFullRecorder(new FullRecorder());
		setNameStore(lang.getNameStore());
	}

	private void setNameStore(TransitionPlaceNameStore nameStore) {
		this.store = nameStore;

	}

	public void setNet(FuzzyPetriNet net) {
		this.net = net;
	}

	public void setDrawableNet(DrawableNet net) {
		this.drawableNet = net;
	}

	public DrawableNet getDrowableNet() {
		if (drawableNet == null) {
			drawableNet = new DrawableFuzzyPetriNet(net);
		}
		return drawableNet;
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
