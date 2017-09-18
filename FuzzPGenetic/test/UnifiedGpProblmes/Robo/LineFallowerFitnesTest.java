package UnifiedGpProblmes.Robo;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;

import org.junit.Test;

import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.Tree.Nodes.ConstantLeaf;
import UnifiedGp.Tree.Nodes.DelayLeaf;
import UnifiedGp.Tree.Nodes.InnerNode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGp.Tree.Nodes.OutType;
import UnifiedGp.Tree.Nodes.OutputLeaf;
import UnifiedGpProblmes.Robo.Simulator.ToRead.ISegmentProvider;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Point;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Points;

public class LineFallowerFitnesTest {
  
  @Test
  public void straight() {
    Points p =new Points();
    p.Elements = new ArrayList<>();
    p.Elements.add(new Point(0, 0));
    p.Elements.add(new Point(0, 100.0));
    p.Elements.add(new Point(0, 0));
    LineFallowerFitnes f = new LineFallowerFitnes(p);
    UnifiedGpIndi l = new UnifiedGpIndi(createCreature(0));
    double rez = f.evaluate(l);
    assertTrue(rez > 6.0);
  }
  
  @Test
  public void circle() {
    Points p =new Points();
    p.Elements = new ArrayList<>();
    p.Elements.add(new Point(0, 0));
    p.Elements.add(new Point(0, 100.0));
    p.Elements.add(new Point(0, 0));
    LineFallowerFitnes f = new LineFallowerFitnes(p);
    UnifiedGpIndi l = new UnifiedGpIndi(createCreature(1));
    double rez = f.evaluate(l);
    assertTrue(rez< 2.0);
  }
  
  private static InnerNode createCreature(int outNr){
    ConstantLeaf c = new ConstantLeaf(5.0);
    OutputLeaf o = new OutputLeaf(outNr, OutType.Copy);
    DelayLeaf d = new DelayLeaf(1);
    InnerNode i = new InnerNode(NodeType.Seq, c, o);
    return new InnerNode(NodeType.Loop, i, d);
  }

}
