package core.Drawable;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;
import structure.DrawableNet;

public class DrawableFuzzyPetriNet implements DrawableNet {
	protected FuzzyPetriNet net;
	protected boolean isInitialMerkingRepezented;

	public DrawableFuzzyPetriNet(FuzzyPetriNet net) {
		this.net = net;
		isInitialMerkingRepezented = true;
	}

	@Override
	public int getNrOfPlaces() {
		return net.getNrOfPlaces();
	}

	@Override
	public int getNrOfTransition() {
		return net.getNrOfTransition();
	}

	@Override
	public boolean isInputPlace(int placeID) {
		return net.isInputPlace(placeID);
	}

	@Override
	public boolean isOuputTransition(int trId) {
		return net.isOuputTransition(trId);
	}

	public Stream<DrawableNet.Arc> getTransitionPlaceArcs() {
		return IntStream.range(0, getNrOfTransition()).boxed().flatMap(trId -> net.getOutputPlacesForTransition(trId)
				.stream().map(plId -> new DrawableNet.Arc("", plId, trId, false)));
	}

	private NumberFormat formatter = new DecimalFormat("#0.00");

	public Stream<DrawableNet.Arc> getPlaceTransitinArc() {
		return IntStream.range(0, getNrOfPlaces()).boxed().flatMap(placeID -> net.getTransitionAfterPlace(placeID)
				.stream()
                .map(trID -> {
                    double weigth = net.getWeigth(placeID, trID);
                    String label = "";
                    if (weigth != 1.0) {
                        label = formatter.format(net.getWeigth(placeID, trID));
                    }
                    return new DrawableNet.Arc(label, placeID, trID, true);
                }));

	}

	@Override
	public String getPlaceName(int placeId) {
		if (isInitialMerkingRepezented) {
			return "P" + placeId + ((!net.getInitialMarkingForPlace(placeId).isPhi()) ? " \u25CF" : "");
		} else {
			return "P" + placeId;
		}
	}

	@Override
	public String getTransitionName(int transitionId) {
		String toRet = "T" + transitionId;
		Integer time = net.getDelayForTransition(transitionId);
		if (time != null && time != 0) {
			toRet += "[" + time + "]";
		}
		return toRet;
	}

	@Override
	public void setReprezentInitialMarking(boolean reprezentInitialMarking) {
		isInitialMerkingRepezented = reprezentInitialMarking;
	}

	@Override
	public Stream<Arc> getArcs() {
		return Stream.concat(getTransitionPlaceArcs(), getPlaceTransitinArc());
	}

}
