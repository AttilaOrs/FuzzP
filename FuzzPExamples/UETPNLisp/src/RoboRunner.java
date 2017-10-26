import java.io.File;

import UETPNLisp.UETPNLisp;
import UnifiedGp.GpIndi.UnifiedGpIndi;
import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.NodeType;
import UnifiedGpProblmes.Robo.MazeFitnes;
import UnifiedGpProblmes.Robo.Simulator.ToRead.Court;

public class RoboRunner {

  public static void main(String[] args) {
    INode<NodeType> root = UETPNLisp.fromFile(new File("roboMaze.uls"));
    MazeFitnes fitnes = new MazeFitnes(Court.getMaze());
    double rez = fitnes.evaluate(new UnifiedGpIndi((IInnerNode<NodeType>) root));
    System.out.println(rez);

  }
}
