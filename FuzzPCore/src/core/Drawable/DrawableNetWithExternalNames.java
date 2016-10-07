package core.Drawable;

import core.FuzzyPetriLogic.PetriNet.FuzzyPetriNet;

public class DrawableNetWithExternalNames extends DrawableFuzzyPetriNet {

	private TransitionPlaceNameStore nameStore;

	public DrawableNetWithExternalNames(FuzzyPetriNet net, TransitionPlaceNameStore nameStore) {
		super(net);
		this.nameStore = nameStore;
	}

	@Override
	public String getPlaceName(int placeId) {
		String name = nameStore.getPlaceName(placeId);
		if (isInitialMerkingRepezented) {
			name += ((!net.getInitialMarkingForPlace(placeId).isPhi()) ? " \u25CF" : "");
		}
		return name;
	}

	@Override
	public String getTransitionName(int transitionId) {
		return nameStore.getTransitionName(transitionId);
	}
}
