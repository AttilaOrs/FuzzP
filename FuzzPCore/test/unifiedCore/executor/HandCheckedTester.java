package unifiedCore.executor;

import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.junit.Test;

import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.common.recoder.FullRecordable;
import core.common.recoder.FullRecorder;
import core.common.recoder.fullrecorderevents.IFullRecorderEvent;
import core.common.recoder.fullrecorderevents.OuputTransitionFired;
import core.common.recoder.fullrecorderevents.TickFinsihed;
import main.ScenarioSaverLoader;

public class HandCheckedTester {

  private static final String HAND_CHECKED_SCENARIOS = "handCheckedScenarios";

  public static void saveHandCheckedScenario(File file, UnifiedPetriNet petri, FullRecorder<UnifiedToken> rec) {
    ScenarioSaverLoader<UnifiedPetriNet, UnifiedToken> saver = new ScenarioSaverLoader<>(
        UnifiedPetriNet.class);
    saver.setFullRec(rec);
    saver.setPetriNet(petri);
    saver.save(file);
  }

  @Test
  public void test() throws IOException {
    try (Stream<Path> paths = Files.walk(Paths.get(HAND_CHECKED_SCENARIOS))) {
      paths.forEach(filePath -> {
        if (Files.isRegularFile(filePath)) {
          checkScenario(filePath);
        }
      });
    }

  }

  private void checkScenario(Path filePath) {
    System.out.println(filePath.getFileName().toString());
    ScenarioSaverLoader<UnifiedPetriNet, UnifiedToken> loader = new ScenarioSaverLoader<>(UnifiedPetriNet.class);
    loader.load(filePath.toFile(), UnifiedToken::buildFromString);
    FullRecorder<UnifiedToken> rec = loader.getFullRec();
    UnifiedPetriNet net = loader.getPetriNet();
    FullRecorder<UnifiedToken> rez = runPetri(net, rec.getInputs());
    assertTrue(checkOuputsAndStateMatch(rec, rez));

  }

  private FullRecorder<UnifiedToken> runPetri(UnifiedPetriNet net, List<Map<Integer, UnifiedToken>> inputs) {
    SyncronousUnifiedPetriExecutor exec = new SyncronousUnifiedPetriExecutor(net);
    FullRecorder<UnifiedToken> rec = new FullRecorder<>();
    exec.setRecorder(rec);

    for (Map<Integer, UnifiedToken> inp : inputs) {
      exec.runTick(inp);
    }
    return rec;

  }

  public static <TokenType extends FullRecordable<TokenType>> boolean checkOuputsAndStateMatch(
      FullRecorder<TokenType> rec1, FullRecorder<TokenType> rec2) {
    List<List<IFullRecorderEvent>> firstPlack = rec1.eventGroupedByTicks();
    List<List<IFullRecorderEvent>> secondPack = rec2.eventGroupedByTicks();
    if (firstPlack.size() != secondPack.size()) {
      return false;
    }
    String firstInsp = joinToStringEvenetsWitch(firstPlack, s -> s instanceof OuputTransitionFired);
    String secondInps = joinToStringEvenetsWitch(firstPlack, s -> s instanceof OuputTransitionFired);
    if (!firstInsp.equals(secondInps)) {
      return false;
    }

    String firstStates = joinToStringEvenetsWitch(firstPlack, s -> s instanceof TickFinsihed);
    String secondStates = joinToStringEvenetsWitch(firstPlack, s -> s instanceof TickFinsihed);

    if (!firstStates.equals(secondStates)) {
      return false;
    }
    return true;

  }

  private static String joinToStringEvenetsWitch(List<List<IFullRecorderEvent>> firstPlack,
      Predicate<IFullRecorderEvent> p) {
    return firstPlack.stream()
        .map(evs -> evs.stream().filter(p).map(s -> s.makeString())
            .collect(joining("~~")))
        .collect(joining("\n"));
  }
}
