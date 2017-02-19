package UnifiedPLang;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

import org.antlr.v4.runtime.tree.TerminalNode;

import FuzzyPLang.NetBuilder.DynamicScope;
import FuzzyPLang.NetBuilder.HiearchicalIntermediateUnifiedNet;
import FuzzyPLang.NetBuilder.NodeRef;
import FuzzyPLang.NetBuilder.StaticScope;
import UnifiedPLang.gen.UnifiedPLangBaseVisitor;
import UnifiedPLang.gen.UnifiedPLangParser;
import core.FuzzyPetriLogic.FuzzyValue;
import core.UnifiedPetriLogic.tables.Operator;
import core.UnifiedPetriLogic.tables.UnifiedOneXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedOneXTwoTable;
import core.UnifiedPetriLogic.tables.UnifiedTwoXOneTable;
import core.UnifiedPetriLogic.tables.UnifiedTwoXTwoTable;

public class UnifiedPLangVisitor extends UnifiedPLangBaseVisitor<Boolean> {

  HiearchicalIntermediateUnifiedNet intermediateNet;
  StaticScope staticScope;
  DynamicScope dynamicScope;
  NodeRef prevNode;

  public UnifiedPLangVisitor() {
    intermediateNet = new HiearchicalIntermediateUnifiedNet();
    staticScope = new StaticScope();
    dynamicScope = new DynamicScope();
    prevNode = null;
  }

  public HiearchicalIntermediateUnifiedNet getIntermediateNet() {
    return intermediateNet;
  }

  @Override
  public Boolean visitSubCompDcl(UnifiedPLangParser.SubCompDclContext ctx) {
    String name = ctx.ID().getText();
    intermediateNet.makeDeclaration(staticScope.cloneSubState(), name);
    staticScope.addSub(name);
    visitChildren(ctx);
    staticScope.removeLastSub();
    return true;
  }

  @Override
  public Boolean visitNewSubComp(UnifiedPLangParser.NewSubCompContext ctx) {
    String varName = ctx.ID(0).getText();
    String subName = ctx.ID(1).getText();
    intermediateNet.makeInstenciation(staticScope.cloneSubState(), varName, subName);
    return true;
  }

  @Override
  public Boolean visitSubCompRef(UnifiedPLangParser.SubCompRefContext ctx) {
    DynamicScope ll = new DynamicScope();
    for (TerminalNode v : ctx.ID()) {
      ll.addSub(v.getText());
    }
    dynamicScope = ll;
    visitChildren(ctx);
    dynamicScope = new DynamicScope();

    return true;
  }

  @Override
  public Boolean visitPutInitToken(UnifiedPLangParser.PutInitTokenContext ctx) {
    visitChildren(ctx);
    String token = ctx.token().getText();
    intermediateNet.addInitialTokenInPlace(staticScope.cloneSubState(), prevNode.getNodeName(), token);
    prevNode = null;
    return true;
  }

  @Override
  public Boolean visitPetriLine(UnifiedPLangParser.PetriLineContext ctx) {
    prevNode = null;
    visitChildren(ctx);
    prevNode = null;
    return true;
  }

  @Override
  public Boolean visitInpPlace(UnifiedPLangParser.InpPlaceContext ctx) {
    String name = "iP" + ctx.INT();
    Double scale = Double.parseDouble(ctx.number().getText());
    if (dynamicScope.current()) {
      intermediateNet.addInpPlace(staticScope.cloneSubState(), name, scale);
    }
    addArcIfPossible(name);
    return true;
  }

  @Override
  public Boolean visitPlace(UnifiedPLangParser.PlaceContext ctx) {
    String name = "P" + ctx.INT();
    if (dynamicScope.current()) {
      intermediateNet.addPlace(staticScope.cloneSubState(), name);
    }
    addArcIfPossible(name);
    return true;
  }

  @Override
  public Boolean visitTranz(UnifiedPLangParser.TranzContext ctx) {
    String transitionName = "T" + ctx.INT();
    if (dynamicScope.current()) {
      intermediateNet.addTransition(staticScope.cloneSubState(), transitionName);
    }
    addArcIfPossible(transitionName);
    return visitChildren(ctx);
  }

  @Override
  public Boolean visitOtranz(UnifiedPLangParser.OtranzContext ctx) {
    String transitionName = "oT" + ctx.INT();

    if (dynamicScope.current()) {
      intermediateNet.addOutTransition(staticScope.cloneSubState(), transitionName);
    }

    addArcIfPossible(transitionName);
    return visitChildren(ctx);
  }

  @Override
  public Boolean visitTranzSpec(UnifiedPLangParser.TranzSpecContext ctx) {
    if (ctx.ID() != null) {
      intermediateNet.setNamedTableForTransition(staticScope.cloneSubState(), prevNode.getNodeName(),
          ctx.ID().getText());
    }
    if (ctx.INT() != null) {
      intermediateNet.setDelayForTransition(staticScope.cloneSubState(), prevNode.getNodeName(),
          Integer.parseInt(ctx.INT().getText()));
    }
    return true;
  }

  @Override
  public Boolean visitArcOp(UnifiedPLangParser.ArcOpContext ctx) {
    return true;
  }

  private void addArcIfPossible(String name) {
    NodeRef ref = new NodeRef(name, dynamicScope);
    if (prevNode != null) {
      intermediateNet.addArc(staticScope.cloneSubState(), prevNode, ref);
    }
    prevNode = ref;
  }

  ArrayList<Map<FuzzyValue, FuzzyValue>> firstMapList = new ArrayList<>();
  ArrayList<Map<FuzzyValue, FuzzyValue>> secondMapList = new ArrayList<>();
  ArrayList<FuzzyValue> firstLine = new ArrayList<>();
  ArrayList<FuzzyValue> secondLine = new ArrayList<>();

  @Override
  public Boolean visitOneXOneTable(UnifiedPLangParser.OneXOneTableContext ctx) {
    visitChildren(ctx);
    intermediateNet.addTableWithName(staticScope.cloneSubState(), ctx.ID().getText(),
        new UnifiedOneXOneTable(firstMapList.get(0)));
    firstMapList.clear();
    return true;
  }

  @Override
  public Boolean visitOneXTwoTable(UnifiedPLangParser.OneXTwoTableContext ctx) {
    visitChildren(ctx);
    intermediateNet.addTableWithName(staticScope.cloneSubState(), ctx.ID().getText(),
        new UnifiedOneXTwoTable(firstMapList.get(0), secondMapList.get(0)));
    firstMapList.clear();
    secondMapList.clear();
    return true;
  }

  @Override
  public Boolean visitTwoXOneWithoutPhi(UnifiedPLangParser.TwoXOneWithoutPhiContext ctx) {
    visitChildren(ctx);
    EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> table = new EnumMap<>(FuzzyValue.class);
    int cntr = 0;
    for (FuzzyValue index : FuzzyValue.inOrderWithoutPhi) {
      table.put(index, firstMapList.get(cntr++));
    }
    intermediateNet.addTableWithName(staticScope.cloneSubState(), ctx.ID().getText(),
        new UnifiedTwoXOneTable(table, Operator.None));
    firstMapList.clear();
    return true;
  }

  @Override
  public Boolean visitTwoXOneWithoutPhiWithOp(UnifiedPLangParser.TwoXOneWithoutPhiWithOpContext ctx) {
    visitChildren(ctx);
    EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> table = new EnumMap<>(FuzzyValue.class);
    int cntr = 0;
    for (FuzzyValue index : FuzzyValue.inOrderWithoutPhi) {
      table.put(index, firstMapList.get(cntr++));
    }
    intermediateNet.addTableWithName(staticScope.cloneSubState(), ctx.ID().getText(),
        new UnifiedTwoXOneTable(table, Operator.parse(ctx.op().getText())));
    firstMapList.clear();
    return true;
  }

  @Override
  public Boolean visitTwoXOneWithPhi(UnifiedPLangParser.TwoXOneWithPhiContext ctx) {
    visitChildren(ctx);
    EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> table = new EnumMap<>(FuzzyValue.class);
    int cntr = 0;
    for (FuzzyValue index : FuzzyValue.inOrder) {
      table.put(index, firstMapList.get(cntr++));
    }
    intermediateNet.addTableWithName(staticScope.cloneSubState(), ctx.ID().getText(),
        new UnifiedTwoXOneTable(table, Operator.None));
    firstMapList.clear();
    return true;
  }

  @Override
  public Boolean visitTwoXOneWithPhiWithOp(UnifiedPLangParser.TwoXOneWithPhiWithOpContext ctx) {
    visitChildren(ctx);
    EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> table = new EnumMap<>(FuzzyValue.class);
    int cntr = 0;
    for (FuzzyValue index : FuzzyValue.inOrder) {
      table.put(index, firstMapList.get(cntr++));
    }
    intermediateNet.addTableWithName(staticScope.cloneSubState(), ctx.ID().getText(),
        new UnifiedTwoXOneTable(table, Operator.parse(ctx.op().getText())));
    firstMapList.clear();
    return true;
  }

  @Override
  public Boolean visitTwoXTwoWithoutPhi(UnifiedPLangParser.TwoXTwoWithoutPhiContext ctx) {
    visitChildren(ctx);
    EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> firstTable = new EnumMap<>(FuzzyValue.class);
    EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> secondTable = new EnumMap<>(FuzzyValue.class);
    int cntr = 0;
    for (FuzzyValue index : FuzzyValue.inOrderWithoutPhi) {
      firstTable.put(index, firstMapList.get(cntr));
      secondTable.put(index, secondMapList.get(cntr++));
    }
    intermediateNet.addTableWithName(staticScope.cloneSubState(), ctx.ID().getText(),
        new UnifiedTwoXTwoTable(firstTable, secondTable, Operator.None));
    firstMapList.clear();
    secondMapList.clear();
    return true;
  }

  @Override
  public Boolean visitTwoXTwoWithoutPhiWithOp(UnifiedPLangParser.TwoXTwoWithoutPhiWithOpContext ctx) {
    visitChildren(ctx);
    EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> firstTable = new EnumMap<>(FuzzyValue.class);
    EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> secondTable = new EnumMap<>(FuzzyValue.class);
    int cntr = 0;
    for (FuzzyValue index : FuzzyValue.inOrderWithoutPhi) {
      firstTable.put(index, firstMapList.get(cntr));
      secondTable.put(index, secondMapList.get(cntr++));
    }
    intermediateNet.addTableWithName(staticScope.cloneSubState(), ctx.ID().getText(),
        new UnifiedTwoXTwoTable(firstTable, secondTable, Operator.parse(ctx.op().getText())));
    firstMapList.clear();
    secondMapList.clear();
    return true;
  }

  @Override
  public Boolean visitTwoXTwoWithPhi(UnifiedPLangParser.TwoXTwoWithPhiContext ctx) {
    visitChildren(ctx);
    EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> firstTable = new EnumMap<>(FuzzyValue.class);
    EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> secondTable = new EnumMap<>(FuzzyValue.class);
    int cntr = 0;
    for (FuzzyValue index : FuzzyValue.inOrder) {
      firstTable.put(index, firstMapList.get(cntr));
      secondTable.put(index, secondMapList.get(cntr++));
    }
    intermediateNet.addTableWithName(staticScope.cloneSubState(), ctx.ID().getText(),
        new UnifiedTwoXTwoTable(firstTable, secondTable, Operator.None));
    firstMapList.clear();
    secondMapList.clear();
    return true;
  }

  @Override
  public Boolean visitTwoXTwoWithPhiWithOp(UnifiedPLangParser.TwoXTwoWithPhiWithOpContext ctx) {
    visitChildren(ctx);
    EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> firstTable = new EnumMap<>(FuzzyValue.class);
    EnumMap<FuzzyValue, Map<FuzzyValue, FuzzyValue>> secondTable = new EnumMap<>(FuzzyValue.class);
    int cntr = 0;
    for (FuzzyValue index : FuzzyValue.inOrder) {
      firstTable.put(index, firstMapList.get(cntr));
      secondTable.put(index, secondMapList.get(cntr++));
    }
    intermediateNet.addTableWithName(staticScope.cloneSubState(), ctx.ID().getText(),
        new UnifiedTwoXTwoTable(firstTable, secondTable, Operator.parse(ctx.op().getText())));
    firstMapList.clear();
    secondMapList.clear();
    return true;
  }

  @Override
  public Boolean visitSimpleCellLine(UnifiedPLangParser.SimpleCellLineContext ctx) {
    visitChildren(ctx);
    Map<FuzzyValue, FuzzyValue> mapToPut = new EnumMap<>(FuzzyValue.class);
    for (int i = 0; i < firstLine.size(); i++) {
      mapToPut.put(FuzzyValue.inOrder.get(i), firstLine.get(i));
    }
    firstMapList.add(mapToPut);
    firstLine.clear();
    return true;
  }

  @Override
  public Boolean visitDoubleCellLine(UnifiedPLangParser.DoubleCellLineContext ctx) {
    visitChildren(ctx);
    Map<FuzzyValue, FuzzyValue> firstMapToPut = new EnumMap<>(FuzzyValue.class);
    Map<FuzzyValue, FuzzyValue> secondMapTpPut = new EnumMap<>(FuzzyValue.class);
    for (int i = 0; i < firstLine.size(); i++) {
      firstMapToPut.put(FuzzyValue.inOrder.get(i), firstLine.get(i));
      secondMapTpPut.put(FuzzyValue.inOrder.get(i), secondLine.get(i));
    }
    firstMapList.add(firstMapToPut);
    secondMapList.add(secondMapTpPut);
    firstLine.clear();
    secondLine.clear();
    return true;
  }

  @Override
  public Boolean visitSimpleCell(UnifiedPLangParser.SimpleCellContext ctx) {
    FuzzyValue fv = FuzzyValue.parseUnifiedStr(ctx.fv().type.getText());
    firstLine.add(fv);
    return true;
  }

  @Override
  public Boolean visitDoubleCell(UnifiedPLangParser.DoubleCellContext ctx) {
    FuzzyValue fv1 = FuzzyValue.parseUnifiedStr(ctx.fv(0).type.getText());
    FuzzyValue fv2 = FuzzyValue.parseUnifiedStr(ctx.fv(1).type.getText());
    firstLine.add(fv1);
    secondLine.add(fv2);
    return true;
  }

}
