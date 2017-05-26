import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import core.UnifiedPetriLogic.UnifiedPetriNet;
import core.UnifiedPetriLogic.UnifiedToken;
import core.UnifiedPetriLogic.executor.SyncronousUnifiedPetriExecutor;
import core.UnifiedPetriLogic.executor.cached.UnifiedPetrinetCacheTableResultWrapper;
import core.common.AbstractExecutor;
import core.common.tokencache.TokenCache;
import core.common.tokencache.TokenCacheDisabling;

public class MeasureMain {

  private static class Configuration {
    final Supplier<List<Map<Integer, UnifiedToken>>> inp;
    final Supplier<SyncronousUnifiedPetriExecutor> executor;
    final String name;

    public Configuration(Supplier<List<Map<Integer, UnifiedToken>>> inp,
        Supplier<SyncronousUnifiedPetriExecutor> executor, String name) {
      this.inp = inp;
      this.executor = executor;
      this.name = name;
    }
  }

  static final List<Configuration> confs = new ArrayList<>();
  static {
    MaxTableTryOutUnifiedPetriMaker maxFinderMaker = new MaxTableTryOutUnifiedPetriMaker();
    IntersectionUnifiedPetriMaker interSectionMaker = new IntersectionUnifiedPetriMaker();
    LaneUnifiedPetriMaker laneMaker = new LaneUnifiedPetriMaker();
    RoomModelUnifiedPetriMaker roomMaker = new RoomModelUnifiedPetriMaker();
    RoomStateUnifiedPetriMaker roomStateMaker = new RoomStateUnifiedPetriMaker();
    ControllerUnifiedPetriMaker controllerMaker = new ControllerUnifiedPetriMaker();

    UnifiedPetriNet[] nets = new UnifiedPetriNet[] { maxFinderMaker.net, interSectionMaker.net, laneMaker.net,
        roomMaker.net, roomStateMaker.net, controllerMaker.net };
    String[] names = new String[] { "max", "inter", "lane", "room", "roomState", "controller" };
    List<Supplier<List<Map<Integer, UnifiedToken>>>> sups = new ArrayList<>();
    sups.add(MaxFinderTryOutMain::createInput);
    sups.add(IntersectionMain::createInput);
    sups.add(LaneMain::createInput);
    sups.add(RoomMain::createInpForRoom);
    sups.add(RoomStateMain::createInpForRoom);
    sups.add(ControllerMain::createInp);


    for (int i = 0; i < nets.length; i++) {
      UnifiedPetriNet net = nets[i];
      String name = names[i];
      Supplier<List<Map<Integer, UnifiedToken>>> sup = sups.get(i);

      confs.add(new Configuration(sup,
          () -> {
            AbstractExecutor.OLD_VERSION = false;
            return new SyncronousUnifiedPetriExecutor(net, false, false);
          }, name + "_simple"));

      confs.add(new Configuration(sup,
          () -> {
            AbstractExecutor.OLD_VERSION = true;
            return new SyncronousUnifiedPetriExecutor(net, false, false);
          }, name + "_old"));

      confs.add(new Configuration(sup,
          () -> {
            AbstractExecutor.OLD_VERSION = false;
            return new SyncronousUnifiedPetriExecutor(net, false, true);
          }, name + "_runc"));
      int[] cc = new int[] { 5, 10, 15 };
      for (int c : cc) {
        confs.add(new Configuration(sup,
            () -> {
              AbstractExecutor.OLD_VERSION = false;
              return new SyncronousUnifiedPetriExecutor(new UnifiedPetrinetCacheTableResultWrapper(net,
                  () -> new TokenCache<>(c)), false, false);
            }, name + "_simple_C" + c));

        confs.add(new Configuration(sup,
            () -> {
              AbstractExecutor.OLD_VERSION = true;
              return new SyncronousUnifiedPetriExecutor(new UnifiedPetrinetCacheTableResultWrapper(net,
                  () -> new TokenCache<>(c)), false, false);
            }, name + "_old_C" + c));

        confs.add(new Configuration(sup,
            () -> {
              AbstractExecutor.OLD_VERSION = false;
              return new SyncronousUnifiedPetriExecutor(new UnifiedPetrinetCacheTableResultWrapper(net,
                  () -> new TokenCache<>(c)), false, true);
            }, name + "_runc_C" + c));

      }
      for (int c : cc) {
        confs.add(new Configuration(sup,
            () -> {
              AbstractExecutor.OLD_VERSION = false;
              return new SyncronousUnifiedPetriExecutor(new UnifiedPetrinetCacheTableResultWrapper(net,
                  () -> new TokenCacheDisabling<>(c)), false, false);
            }, name + "_simple_CD" + c));

        confs.add(new Configuration(sup,
            () -> {
              AbstractExecutor.OLD_VERSION = true;
              return new SyncronousUnifiedPetriExecutor(new UnifiedPetrinetCacheTableResultWrapper(net,
                  () -> new TokenCacheDisabling<>(c)), false, false);
            }, name + "_old_CD" + c));

        confs.add(new Configuration(sup,
            () -> {
              AbstractExecutor.OLD_VERSION = false;
              return new SyncronousUnifiedPetriExecutor(new UnifiedPetrinetCacheTableResultWrapper(net,
                  () -> new TokenCacheDisabling<>(c)), false, true);
            }, name + "_runc_CD" + c));

      }
    }

  }

  static final int TRY = 10;

  public static void main(String[] args) {
    Map<String, List<Long>> results = new HashMap<>();
    for (int i = 0; i < TRY; i++) {
      for (Configuration conf : confs) {
        List<Map<Integer, UnifiedToken>> inps = conf.inp.get();
        SyncronousUnifiedPetriExecutor executor = conf.executor.get();
        long start = System.nanoTime();
        for (Map<Integer, UnifiedToken> inp : inps) {
          executor.runTick(inp);
        }
        long end = System.nanoTime();
        if (!results.containsKey(conf.name)) {
          results.put(conf.name, new ArrayList<>());
        }
        results.get(conf.name).add(end - start);
      }
      System.out.println("set " + i + " terminated");

    }
    saveToFileResults(results, "all.txt");

  }

  private static void saveToFileResults(Map<String, List<Long>> results, String file) {
    StringBuilder bld = new StringBuilder();
    for (Entry<String, List<Long>> e : results.entrySet()) {
      bld.append(e.getKey()).append(",");
      String rest = e.getValue().stream().map(t -> Long.toString((t))).collect(Collectors.joining(","));
      bld.append(rest).append("\n");
    }
    try {
      Files.write(Paths.get(file), bld.toString().getBytes());
    } catch (IOException e1) {
      e1.printStackTrace();
    }

  }

}
