package AlgoImpl.Selectors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;

import structure.ISelector;

public class LinearRankSelection implements ISelector {
	private double sp;
	private double sum;
	private Random rnd;

	public LinearRankSelection() {
		this(2.0);
	}

	public LinearRankSelection(double sp) {
		this.sp = sp;
		this.rnd = new Random();
	}

	@Override
	public List<int[]> selectOne(Map<Integer, Double[]> res, int basedOn,
			int howMany, int arraySize) {
		if (howMany != 0) {
			HashMap<Integer, Double> ranking = calcRank(res, basedOn);
			List<int[]> toRet = roulette(howMany, arraySize, ranking);
			return toRet;
		}
		return new ArrayList<>();
	}

	private List<int[]> roulette(int howMany, int arraySize,
			HashMap<Integer, Double> ranking) {
		List<Double> randms = rnd.doubles().limit(howMany)
				.mapToObj(d -> d * sum).sorted((d1, d2) -> d1.compareTo(d2))
				.collect(Collectors.toList());
		List<int[]> toRet = new ArrayList<>();

		double tempSum = 0;
		int rndIndex = 0;
		ArrayList<Entry<Integer, Double>> entryes = new ArrayList<>(
				ranking.entrySet());
		for (int index = 0; index < entryes.size();) {
			Entry<Integer, Double> ee = entryes.get(index);

			tempSum += ee.getValue();
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

	private HashMap<Integer, Double> calcRank(Map<Integer, Double[]> res,
			int basedOn) {
		List<Integer> sorted = res.entrySet().stream()
				.sorted((Entry<Integer, Double[]> en1,
						Entry<Integer, Double[]> en2) -> Double.compare(
								en1.getValue()[basedOn],
								en2.getValue()[basedOn]))
				.map(e -> e.getKey()).collect(Collectors.toList());
		HashMap<Integer, Double> ranking = new HashMap<>();
		this.sum = 0.0;
		for (int index = 0; index < sorted.size(); index++) {
			Double rank = rankOf(index, sorted.size());
			ranking.put(sorted.get(index), rank);
			sum += rank;
		}
		return ranking;
	}

	private Double rankOf(int index, int size) {
		return 2.0 - sp + (2 * (sp - 1.0) * ((index - 1.0) / (size - 1.0)));
	}

	@Override
	public List<int[]> selectPairs(Map<Integer, Double[]> res, int basedOn,
			int howMany, int arraySize) {
		HashMap<Integer, Double> ranking = calcRank(res, basedOn);
		List<int[]> toRet = roulette(howMany, arraySize, ranking);
		LinkedList<int[]> pairs = new LinkedList<>(
				roulette(howMany, 1, ranking));

		for (int index = 0; index < toRet.size() - 1; index++) {
			int randindex = rnd.nextInt(pairs.size());
			int elem = pairs.get(randindex)[0];
			toRet.get(index)[1] = elem;
			pairs.remove(randindex);
		}
		toRet.get(toRet.size() - 1)[1] = pairs.get(0)[0];
		return toRet;
	}

}
