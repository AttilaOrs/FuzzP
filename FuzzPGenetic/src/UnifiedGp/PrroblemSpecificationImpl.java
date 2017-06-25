package UnifiedGp;

import java.util.Map;

public class PrroblemSpecificationImpl implements ProblemSpecification {


  private final double defaultScale;
  private final int maxDelay;
  private final Map<Integer, Double> inpScale;
  private final Map<Integer, Double> outScale;

  public PrroblemSpecificationImpl(double defaultScale, int maxDelay, Map<Integer, Double> inpScale,
      Map<Integer, Double> outScale) {
    this.defaultScale = defaultScale;
    this.inpScale = inpScale;
    this.outScale = outScale;
    this.maxDelay = maxDelay;
  }

  @Override
  public double defaultScale() {
    return defaultScale;
  }

  @Override
  public Double getScaleForInp(int inpNr) {
    return inpScale.get(inpNr);
  }

  @Override
  public Double getScaleForOut(int inpNr) {
    return outScale.get(inpNr);
  }

  @Override
  public int getInputCount() {
    return inpScale.size();
  }

  @Override
  public int getOuputCount() {
    return outScale.size();
  }

  @Override
  public int getMaxDelay() {
    return maxDelay;
  }

}
