package AlgoImpl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class IterationLogger {
  private static final String numberFormat = "#0.00";
  public static String MAX_FIT = "maximum of Fitnes";
  public static String AVG_FIT = "avarage of Fitnes";
  public static String POP_SIZE = "population size";

  public static String MEM_USE = "used memory";
  public static String GC_SEC = "garbage collector";

  public static String TREE_DEPTH_AVG = "avg tree depth";
  public static String TREE_OPS_AVG = "avg tree ops";
  public static String TREE_LEAFS_AVG = "avg tree leafs";

  public static String TREE_DEPTH_MAX = "max tree depth";
  public static String TREE_OPS_MAX = "max tree ops";
  public static String TREE_LEAFS_MAX = "max tree leafs";

  public static String TREE_DEPTH_MIN = "min tree depth";
  public static String TREE_OPS_MIN = "min tree ops";
  public static String TREE_LEAFS_MIN = "min tree leafs";


  public static final String TREE_SIZE_AVG = "avg tree size";
  public static final String TREE_SIZE_MAX = "max tree size";
  public static final String TREE_SIZE_MIN = "min tree size";
  public static String TIME = "time: millisec/solution";

  HashMap<String, ArrayList<Double>> logs;
  List<String> prntingGroup;
  private NumberFormat formatter;

  public IterationLogger() {
    this(Arrays.asList(MAX_FIT, AVG_FIT, POP_SIZE));
  }

  public IterationLogger(List<String> prntingGroup) {
    logs = new HashMap<>();
    this.prntingGroup = prntingGroup;
    formatter = new DecimalFormat(numberFormat);
  }

  public void addLogToTopic(String topic, Double val) {
    if (logs.containsKey(topic)) {
      logs.get(topic).add(val);
    } else {
      ArrayList<Double> temp = new ArrayList<>();
      logs.put(topic, temp);
      temp.add(val);
    }
  }

  public void iterFinished(int iterNr) {
    StringBuilder strb = new StringBuilder("IterNR: ");
    strb.append(iterNr);
    for (String topic : this.prntingGroup) {
      if (logs.containsKey(topic) && logs.get(topic).size() > iterNr)
        strb.append(" ").append(topic).append(" ").append(formatter.format(logs.get(topic).get(iterNr)));
    }
    System.out.println(strb.toString());
  }

  public Map<String, List<Double>> getLogsForPlotting(String... topicsToGet) {
    return Arrays.stream(topicsToGet).filter(t -> logs.containsKey(t))
        .collect(Collectors.toMap(t -> t, t -> logs.get(t)));
  }

  public Map<String, List<Double>> getLogsForPlottingContatinigStrings(String... pattrsn) {
    return logs.entrySet().stream().filter(e -> IsOneOf(e.getKey(), pattrsn))
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
  }

  private boolean IsOneOf(String key, String[] pattrsn) {
    for (String pa : pattrsn) {
      if (key.contains(pa))
        return true;
    }
    return false;
  }

  public String exportToDat(String... pattrsn) {
    StringBuilder bld = new StringBuilder();
    Map<String, List<Double>> logs = getLogsForPlottingContatinigStrings(pattrsn);
    int maxCount = logs.entrySet().stream().map(e -> e.getValue().size()).mapToInt(i -> i).max().getAsInt();
    String header = logs.keySet().stream().collect(Collectors.joining(" "));
    bld.append("#tick ").append(header).append("\n");
    for (int i = 0; i < maxCount; i++) {
      bld.append(i).append(" ");
      for (Entry<String, List<Double>> e : logs.entrySet()) {
        if (e.getValue().size() > i) {
          bld.append(e.getValue().get(i)).append(" ");
        } else {
          bld.append(0).append(" ");
        }
        
      }
      bld.append("\n");
    }

    return bld.toString();
  }


}
