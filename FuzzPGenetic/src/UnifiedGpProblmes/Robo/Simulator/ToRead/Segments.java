package UnifiedGpProblmes.Robo.Simulator.ToRead;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Segments extends AbstactPoints {
  private final List<Segment> segemnts;

  public Segments(List<Segment> s) {
    segemnts = s;
  }

  @Override
  public Collection<Segment> getLineSegments() {
    return segemnts;
  }

  public Segments myClone() {
    List<Segment> l = segemnts.stream().map(s -> s.myClone()).collect(Collectors.toList());
    return new Segments(l);

  }

}
