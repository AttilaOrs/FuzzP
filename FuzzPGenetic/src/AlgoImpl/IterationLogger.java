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
  public static String MAX_FIT = "maximum of fitnes";
  public static String AVG_FIT = "avarage of fitnes";
  public static String POP_SIZE = "population size";

  public static String MEM_USE = "used memory";
  public static String GC_SEC = "garbage collector";

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

  public Map<String, ArrayList<Double>> getLogsForPlotting(String... topicsToGet) {
    return Arrays.stream(topicsToGet).filter(t -> logs.containsKey(t))
        .collect(Collectors.toMap(t -> t, t -> logs.get(t)));
  }

  public Map<String, ArrayList<Double>> getLogsForPlottingContatinigStrings(String... pattrsn) {
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

}
