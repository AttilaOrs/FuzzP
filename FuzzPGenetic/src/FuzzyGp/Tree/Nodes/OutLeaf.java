package FuzzyGp.Tree.Nodes;

public class OutLeaf implements ILeaf {

  private final int outNr;
  private final OutType type;

  public OutLeaf(int outNr) {
    this(outNr, OutType.defultOutType);
  }

  public OutLeaf(int outNr, OutType type) {
    this.outNr = outNr;
    this.type = type;
  }

  public int getOutNr() {
    return this.outNr;
  }

  public OutType getOutType() {
    return this.type;
  }

  @Override
  public ILeaf myClone() {
    return new OutLeaf(outNr);
  }

  public static String writeZero = "[ZR;ZR;ZR;ZR;ZR;FF;NL; NM;ZR;PL;PL;FF ]";
  public static String eventIfPositive = "[NL;NM;ZR;PM;PL;FF;FF; FF;FF;ZR;ZR;FF ]";
  public static String eventIfNegative = "[NL;NM;ZR;PM;PL;FF;ZR; ZR;FF;FF;FF;FF ]";

}
