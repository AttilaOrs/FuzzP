package UnifiedGp;

public interface ScaleProvider {

  public double defaultScale();

  public Double getScaleForInp(int inpNr);

  public Double getScaleForOut(int inpNr);

}
