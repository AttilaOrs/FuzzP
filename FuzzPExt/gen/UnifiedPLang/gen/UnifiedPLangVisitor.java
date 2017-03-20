// Generated from UnifiedPLang.g4 by ANTLR 4.5
package UnifiedPLang.gen;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link UnifiedPLangParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface UnifiedPLangVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(UnifiedPLangParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#stateMent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStateMent(UnifiedPLangParser.StateMentContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#subCompDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubCompDcl(UnifiedPLangParser.SubCompDclContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#newSubComp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewSubComp(UnifiedPLangParser.NewSubCompContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#subCompRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubCompRef(UnifiedPLangParser.SubCompRefContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#putInitToken}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPutInitToken(UnifiedPLangParser.PutInitTokenContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#petriLine}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPetriLine(UnifiedPLangParser.PetriLineContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#lineCont}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLineCont(UnifiedPLangParser.LineContContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#arcOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArcOp(UnifiedPLangParser.ArcOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#inpPlace}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInpPlace(UnifiedPLangParser.InpPlaceContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#place}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlace(UnifiedPLangParser.PlaceContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#tranz}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTranz(UnifiedPLangParser.TranzContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#otranz}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOtranz(UnifiedPLangParser.OtranzContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#tranzSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTranzSpec(UnifiedPLangParser.TranzSpecContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#token}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToken(UnifiedPLangParser.TokenContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(UnifiedPLangParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#poz_neg_double}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPoz_neg_double(UnifiedPLangParser.Poz_neg_doubleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OneXOneTable}
	 * labeled alternative in {@link UnifiedPLangParser#tableDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOneXOneTable(UnifiedPLangParser.OneXOneTableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OneXTwoTable}
	 * labeled alternative in {@link UnifiedPLangParser#tableDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOneXTwoTable(UnifiedPLangParser.OneXTwoTableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TwoXOneWithoutPhi}
	 * labeled alternative in {@link UnifiedPLangParser#tableDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTwoXOneWithoutPhi(UnifiedPLangParser.TwoXOneWithoutPhiContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TwoXOneWithoutPhiWithOp}
	 * labeled alternative in {@link UnifiedPLangParser#tableDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTwoXOneWithoutPhiWithOp(UnifiedPLangParser.TwoXOneWithoutPhiWithOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TwoXOneWithPhi}
	 * labeled alternative in {@link UnifiedPLangParser#tableDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTwoXOneWithPhi(UnifiedPLangParser.TwoXOneWithPhiContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TwoXOneWithPhiWithOp}
	 * labeled alternative in {@link UnifiedPLangParser#tableDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTwoXOneWithPhiWithOp(UnifiedPLangParser.TwoXOneWithPhiWithOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TwoXTwoWithoutPhi}
	 * labeled alternative in {@link UnifiedPLangParser#tableDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTwoXTwoWithoutPhi(UnifiedPLangParser.TwoXTwoWithoutPhiContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TwoXTwoWithoutPhiWithOp}
	 * labeled alternative in {@link UnifiedPLangParser#tableDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTwoXTwoWithoutPhiWithOp(UnifiedPLangParser.TwoXTwoWithoutPhiWithOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TwoXTwoWithPhi}
	 * labeled alternative in {@link UnifiedPLangParser#tableDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTwoXTwoWithPhi(UnifiedPLangParser.TwoXTwoWithPhiContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TwoXTwoWithPhiWithOp}
	 * labeled alternative in {@link UnifiedPLangParser#tableDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTwoXTwoWithPhiWithOp(UnifiedPLangParser.TwoXTwoWithPhiWithOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#simpleCellLine}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleCellLine(UnifiedPLangParser.SimpleCellLineContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#doubleCellLine}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoubleCellLine(UnifiedPLangParser.DoubleCellLineContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#simpleCell}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleCell(UnifiedPLangParser.SimpleCellContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#doubleCell}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoubleCell(UnifiedPLangParser.DoubleCellContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#fv}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFv(UnifiedPLangParser.FvContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#fv_classic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFv_classic(UnifiedPLangParser.Fv_classicContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOp(UnifiedPLangParser.OpContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#poz_neg_int}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPoz_neg_int(UnifiedPLangParser.Poz_neg_intContext ctx);
}