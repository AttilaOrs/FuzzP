package FuzzyPLang.NetBuilder;

import java.util.ArrayList;

import core.FuzzyPetriLogic.ITable;

public class HiearchicalIntermediateFuzzyNet
    extends AbstactHierachicalIntermediateNet<ITable, HiearchicalIntermediateFuzzyNet> {
  private ArrayList<NodeRef[]> weigthedArcs;
  private ArrayList<Double> weigthsForArc;

  public HiearchicalIntermediateFuzzyNet() {
    super(HiearchicalIntermediateFuzzyNet::new);
    weigthedArcs = new ArrayList<>();
    weigthsForArc = new ArrayList<>();
  }




  public void addArc(StaticScope sub, NodeRef firsNodeName, NodeRef secondNodeName, double weigth) {
    if (sub.current()) {
      weigthedArcs.add(new NodeRef[] { firsNodeName, secondNodeName });
      weigthsForArc.add(weigth);
    } else {
      String subName = sub.removeFirstSub();
      HiearchicalIntermediateFuzzyNet ff = ( HiearchicalIntermediateFuzzyNet)declarations.get(subName);
      ff.addArc(sub, firsNodeName, secondNodeName, weigth);
    }
  }

  public void addInpPlace(StaticScope sub, String str) {
    if (sub.current()) {
      if (!inpPlaces.contains(str)) {
        inpPlaces.add(str);
      }
    } else {
      String subName = sub.removeFirstSub();
      getDeclarations().get(subName).addInpPlace(sub, str);
    }
  }

  public ArrayList<NodeRef[]> getWeigthedArcs() {
    return weigthedArcs;
  }

  public ArrayList<Double> getWeigthsForArc() {
    return weigthsForArc;
  }


}
