package PetriNetToCode;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import core.Drawable.TransitionPlaceNameStore;
import core.FuzzyPetriLogic.FuzzyTableParser;
import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;

/**
 * Created by ors.kilyen on 10/6/2016.
 */
public class FuzzyNetMakerCodeGenerator {
	private static final String MakerTemplateGroupFile = "FuzzyPetriNetMakerTemplate.stg";
	private static final String MakerTemplate = "fuzPetriTemplate";
	public static final String PACKAGE = "package";
	public static final String NET_NAME = "netName";
	public static final String INP_PLACE = "inpPlace";
	public static final String PLACE = "place";
	public static final String OUT_TRANS_ID_TABLE = "outTrans.{id, table}";
	public static final String TRANS_TO_PLACE_ARC_PLACE_TRANS = "transToPlaceArc.{place, trans}";
	public static final String PALCE_TO_TRANS_ARC_PLACE_TRANS_WEIGHT = "palceToTransArc.{place, trans, weight}";
	public static final String INITAL_MARKING_PLACE_TOKEN = "initalMarking.{place, token}";
	public static final String TRANS_ID_TABLE_DELAY = "trans.{id, table, delay}";

	private String packageToPut;
	private final FuzzyPetriNet net;
	private final TransitionPlaceNameStore store;
	private final FuzzyTableParser p;
	private String className;

	public FuzzyNetMakerCodeGenerator(FuzzyPetriNet petriNet) {
		this(petriNet, TransitionPlaceNameStore.createOrdinarNames(petriNet), null);
	}

	public FuzzyNetMakerCodeGenerator(FuzzyPetriNet petriNet, TransitionPlaceNameStore store, String packageToPut) {
		net = petriNet;
		this.store = store;
		this.packageToPut = packageToPut;
		p = new FuzzyTableParser(true);
	}

	public String getGeneratedClassName() {
		return className;
	}

	public void setPackage(String pck) {
		this.packageToPut = pck;
	}

	public String createMaker(String name) {

		STGroup group = new STGroupFile(MakerTemplateGroupFile);
		ST template = group.getInstanceOf(MakerTemplate);
		if (packageToPut != null) {
			template.add(PACKAGE, packageToPut);
		}
		template.add(NET_NAME, name);

		IntStream.range(0, net.getNrOfPlaces()).filter(net::isInputPlace)
				.forEach(id -> template.add(INP_PLACE, store.getPlaceName(id)));

		IntStream.range(0, net.getNrOfPlaces()).filter(((IntPredicate) net::isInputPlace).negate())
				.forEach(id -> template.add(PLACE, store.getPlaceName(id)));

		IntStream.range(0, net.getNrOfPlaces()).filter(plId -> !net.getInitialMarkingForPlace(plId).isPhi())
				.forEach(plId -> template.addAggr(INITAL_MARKING_PLACE_TOKEN, store.getPlaceName(plId),
						net.getInitialMarkingForPlace(plId).shortString()));

		IntStream.range(0, net.getNrOfTransition()).filter(net::isOuputTransition)
				.forEach(trId -> template.addAggr(OUT_TRANS_ID_TABLE, store.getTransitionName(trId),
						makeJavaLike(p.createString(net.getTableForTransition(trId)))));

		IntStream.range(0, net.getNrOfTransition()).filter(((IntPredicate) net::isOuputTransition).negate())
				.forEach(trId -> template.addAggr(TRANS_ID_TABLE_DELAY, store.getTransitionName(trId),
						makeJavaLike(p.createString(net.getTableForTransition(trId))),
						net.getDelayForTransition(trId)));

		for (int trId = 0; trId < net.getNrOfTransition(); trId++) {
			for (Integer placeId : net.getOutputPlacesForTransition(trId)) {
				template.addAggr(TRANS_TO_PLACE_ARC_PLACE_TRANS, store.getPlaceName(placeId),
						store.getTransitionName(trId));
			}
			for (Integer placeId : net.getPlacesNeededForTransition(trId)) {
				template.addAggr(PALCE_TO_TRANS_ARC_PLACE_TRANS_WEIGHT, store.getPlaceName(placeId),
						store.getTransitionName(trId), net.getWeigth(placeId, trId));
			}
		}

		ST classNameTemplate = group.getInstanceOf("className");
		classNameTemplate.add(NET_NAME, name);
		className = classNameTemplate.render();
		return template.render();
	}

	public static String makeJavaLike(String tableString) {
		if (tableString.contains("\n")) {
			String newStr = tableString.replace("{", "{\"+\n").replace("[", "\t\"[").replace("]", "]\"+//").replace("}",
					"\"}");

			return newStr;
		}
		return tableString;
	}
}
