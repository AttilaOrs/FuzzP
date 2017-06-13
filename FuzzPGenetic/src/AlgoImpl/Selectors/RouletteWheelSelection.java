package AlgoImpl.Selectors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;

import structure.ISelector;

public class RouletteWheelSelection implements ISelector {
  int basedOn;
  Random rnd;

  public RouletteWheelSelection() {
    rnd = new Random();
  }

  @Override
  public List<int[]> selectOne(Map<Integer, Double[]> res, int basedOn, int howMany, int arraySize) {
    double sum = res.values().stream().mapToDouble(r -> r[basedOn]).sum();
    List<Double> randms = rnd.doubles().limit(howMany).mapToObj(d -> d * sum).sorted((d1, d2) -> d1.compareTo(d2))
        .collect(Collectors.toList());
    List<int[]> toRet = new ArrayList<>();
    double tempSum = 0;
    int rndIndex = 0;
    ArrayList<Entry<Integer, Double[]>> entryes = new ArrayList<>(res.entrySet());
    for (int index = 0; index < entryes.size();) {
      Entry<Integer, Double[]> ee = entryes.get(index);

      tempSum += ee.getValue()[basedOn];
      if (tempSum > randms.get(rndIndex)) {
        int[] temp = new int[arraySize];
        temp[0] = ee.getKey();
        toRet.add(temp);
        rndIndex++;
      } else {
        index++;
      }
      if (rndIndex >= randms.size()) {
        break;
      }
    }

    return toRet;
  }

  @Override
  public List<int[]> selectPairs(Map<Integer, Double[]> res, int basedOn, int howMany, int arraySize) {
    List<int[]> ff1 = selectOne(res, basedOn, howMany, arraySize);
    List<int[]> ff2 = selectOne(res, basedOn, howMany, 1);
    for (int i = 0; i < ff1.size(); i++) {
      ff1.get(i)[1] = ff2.get(i)[0];
    }
    return ff1;
  }

}
