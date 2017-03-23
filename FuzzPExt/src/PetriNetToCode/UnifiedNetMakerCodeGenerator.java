package PetriNetToCode;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import core.Drawable.TransitionPlaceNameStore;
import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedTableParser;

public class UnifiedNetMakerCodeGenerator {
  private static final String MakerTemplateGroupFile = "UnifiedPetriNetMakerTemplate.stg";
  private static final String MakerTemplate = "unifiedPetriTemplate";
  private static final String NET_NAME = "netName";
  private static final String INP_PLACE = "inpPlaces.{id, scale}";
  private static final String PLACE = "places.{id, scale}";
  private static final String INITAL_MARKING_PLACE_TOKEN = "initalMarking.{place, token}";
  private static final String OUT_TRANS_ID_TABLE = "outTrans.{id, table}";
  private static final String TRANS_ID_TABLE_DELAY = "delayTrans.{id, table, delay}";
  private static final String TRANS_ID_TABLE_MULTI = "multiTrans.{id, table, multi}";
  private static final String PLACE_TO_TRANS = "placeToTrans.{trans, place}";
  private static final String TRANS_TO_PLACE = "transToPlace.{trans, place}";

  private UnifiedPetriNet net;
  private String netName;
  private TransitionPlaceNameStore nameStore;
  private UnifiedTableParser parser;

  public UnifiedNetMakerCodeGenerator(UnifiedPetriNet net, String netName, TransitionPlaceNameStore nameStore) {
    this.net = net;
    this.netName = netName;
    this.nameStore = nameStore;
    this.parser = new UnifiedTableParser(true);
  }

  public String generateMaker() {
    STGroup group = new STGroupFile(MakerTemplateGroupFile);
    ST template = group.getInstanceOf(MakerTemplate);
    template.add(NET_NAME, netName);
    IntStream.range(0, net.getNrOfPlaces()).filter(net::isInputPlace)
        .forEach(id -> template.addAggr(INP_PLACE, nameStore.getPlaceName(id), net.getScale(id)));
    IntStream.range(0, net.getNrOfPlaces()).filter(((IntPredicate) net::isInputPlace).negate())
        .forEach(id -> template.addAggr(PLACE, nameStore.getPlaceName(id), net.getScale(id)));
    IntStream.range(0, net.getNrOfPlaces()).filter(plId -> !net.getInitialMarkingForPlace(plId).isPhi())
        .forEach(plId -> template.addAggr(INITAL_MARKING_PLACE_TOKEN, nameStore.getPlaceName(plId),
            net.getInitialMarkingForPlace(plId).getValue()));

    IntStream.range(0, net.getNrOfTransition()).filter(net::isOuputTransition)
        .forEach(trId -> template.addAggr(OUT_TRANS_ID_TABLE, nameStore.getTransitionName(trId),
            FuzzyNetMakerCodeGenerator.makeJavaLike(parser.createString(net.getTableForTransition(trId)))));
    

    IntStream.range(0, net.getNrOfTransition())
        .filter(tr -> (!net.isOuputTransition(tr)) && (net.getDelayMultiplierForTransition(tr)) == 0.0)
        .forEach(tr -> template.addAggr(TRANS_ID_TABLE_DELAY, nameStore.getTransitionName(tr),
            FuzzyNetMakerCodeGenerator.makeJavaLike(parser.createString(net.getTableForTransition(tr))),
            net.getDelayForTransition(tr)));

    IntStream.range(0, net.getNrOfTransition())
        .filter(tr -> (!net.isOuputTransition(tr)) && (net.getDelayMultiplierForTransition(tr)) != 0.0)
        .forEach(tr -> template.addAggr(TRANS_ID_TABLE_MULTI, nameStore.getTransitionName(tr),
            FuzzyNetMakerCodeGenerator.makeJavaLike(parser.createString(net.getTableForTransition(tr))),
            net.getDelayMultiplierForTransition(tr)));
    
    for (int trId = 0; trId < net.getNrOfTransition(); trId++) {
      for (Integer placeId : net.getOutputPlacesForTransition(trId)) {
        template.addAggr(TRANS_TO_PLACE,
            nameStore.getTransitionName(trId), nameStore.getPlaceName(placeId));
      }
      for (Integer placeId : net.getPlacesNeededForTransition(trId)) {
        template.addAggr(PLACE_TO_TRANS,
            nameStore.getTransitionName(trId), nameStore.getPlaceName(placeId));
      }
    }


    return template.render();

  }


}
