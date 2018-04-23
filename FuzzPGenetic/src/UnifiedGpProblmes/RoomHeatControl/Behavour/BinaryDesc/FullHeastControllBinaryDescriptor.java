package UnifiedGpProblmes.RoomHeatControl.Behavour.BinaryDesc;

import static UnifiedGpProblmes.RoomHeatControl.FullControllMultiScenarioFitness.getProblemSpecification;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import UnifiedGp.Behaviour.BinaryDescritor;
import UnifiedGp.Behaviour.InputOutputRecorder;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGpProblmes.RoomHeatControl.Behavour.FullHeatControllSimpleDescription;
import UnifiedGpProblmes.RoomHeatControl.Simulator.FullHeatController;
import UnifiedGpProblmes.RoomHeatControl.Simulator.FullHeatController.Control;
import UnifiedGpProblmes.RoomHeatControl.Simulator.FullSimulator;
import UnifiedGpProblmes.RoomHeatControl.Simulator.FullSimulator.Rezult;
import UnifiedGpProblmes.RoomHeatControl.Simulator.RoomScenario;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.recoder.FiredTranitionRecorder;
import core.common.recoder.MultiRecorder;
import core.common.tokencache.TokenCacheDisabling;
import structure.behaviour.IBeahviourDescriptor;

public class FullHeastControllBinaryDescriptor extends BinaryDescritor<FullHeatBinaryDescripton, UnifiedGpIndi>
    implements
      IBeahviourDescriptor<FullHeatBinaryDescripton, UnifiedGpIndi> {

  private List<RoomScenario> scenarios;

  public FullHeastControllBinaryDescriptor(List<RoomScenario> scenarios) {
    super(getProblemSpecification(), 3, FullHeatBinaryDescripton::new);
    this.scenarios = scenarios;
  }

  FullHeatController.ControllEvent lastEvent = FullHeatController.ControllEvent.None;
  double gasCmd = 0.0;
  @Override
  public FullHeatBinaryDescripton evaluate(UnifiedGpIndi creature) {
    int size = creature.getSizes().size();
    int allTick = scenarios.stream().mapToInt(sc -> sc.getScenarioLength()).sum();
    double multi = sizeMulti(size);
    if (multi == 0.0) {
      FullHeatControllSimpleDescription w = new FullHeatControllSimpleDescription(allTick, 10000.0, allTick, 10000.0,
          allTick, 0.0, size);
      FullHeatBinaryDescripton toRet = super.createDescprition(InputOutputRecorder.fakeRecorder(allTick));
      toRet.setOriginalDesc(w);
      return toRet;
    }

    FiredTranitionRecorder<UnifiedToken> rec = new FiredTranitionRecorder<>();
    InputOutputRecorder recc = new InputOutputRecorder();
    MultiRecorder<UnifiedToken> multiRec = new MultiRecorder<>(Arrays.asList(rec, recc));

    rez = super.convert(creature);
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(
        new UnifiedPetrinetCacheTableResultWrapper(rez.net, () -> new TokenCacheDisabling<>(5)), false, true);
    exec.setRecorder(multiRec);
    rez.addActionIfPossible(0, c -> lastEvent = FullHeatController.ControllEvent.StartHeating);
    rez.addActionIfPossible(1, c -> lastEvent = FullHeatController.ControllEvent.StopHeating);
    rez.addActionIfPossible(2, c -> gasCmd = c.getValue());

    Map<Integer, UnifiedToken> inp = new HashMap<>();
    int nrOfGoodState = 0;
    int waterOffLimit = 0;
    double waterError = 0.0;
    double roomTempEroor = 0.0;

    for (RoomScenario scenario : scenarios) {
      FullSimulator sim = new FullSimulator(scenario);
      exec.resetSimulator();
      Rezult simRez = sim.simulate((sensorReading, roomTempRef, waterTemp, waterRef) -> {
        lastEvent = FullHeatController.ControllEvent.None;
        gasCmd = 0.0;
        inp.clear();
        rez.addToInpIfPossible(inp, 0, new UnifiedToken(sensorReading));
        rez.addToInpIfPossible(inp, 1, new UnifiedToken(roomTempRef));
        rez.addToInpIfPossible(inp, 2, new UnifiedToken(waterTemp));
        rez.addToInpIfPossible(inp, 3, new UnifiedToken(waterRef));
        exec.runTick(inp);

        return new Control(lastEvent, gasCmd);
      });
      nrOfGoodState += simRez.roomInWrongSate;
      waterOffLimit += simRez.waterOffLimit;
      waterError += simRez.waterError;
      roomTempEroor += simRez.roomError;
    }

    super.updateCreatureWithSimplification(creature, rez, rec);
    double multi2 = super.fireCountMulti(rec, allTick);
    FullHeatBinaryDescripton toRet = super.createDescprition(recc);

    FullHeatControllSimpleDescription wer = new FullHeatControllSimpleDescription(nrOfGoodState, roomTempEroor,
        waterOffLimit, waterError, allTick, multi2, size);
    toRet.setOriginalDesc(wer);

    return toRet;
  }

}
