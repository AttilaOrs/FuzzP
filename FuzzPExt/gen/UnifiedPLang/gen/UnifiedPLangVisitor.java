// Generated from UnifiedPLang.g4 by ANTLR 4.4
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
	 * Visit a parse tree produced by the {@code smallpWithoutNumber}
	 * labeled alternative in {@link UnifiedPLangParser#place}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSmallpWithoutNumber(@NotNull UnifiedPLangParser.SmallpWithoutNumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TwoXTwoWithPhiWithOp}
	 * labeled alternative in {@link UnifiedPLangParser#tableDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTwoXTwoWithPhiWithOp(@NotNull UnifiedPLangParser.TwoXTwoWithPhiWithOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#putInitToken}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPutInitToken(@NotNull UnifiedPLangParser.PutInitTokenContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#subCompDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubCompDcl(@NotNull UnifiedPLangParser.SubCompDclContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#simpleCell}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleCell(@NotNull UnifiedPLangParser.SimpleCellContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#fv}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFv(@NotNull UnifiedPLangParser.FvContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(@NotNull UnifiedPLangParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#tranzSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTranzSpec(@NotNull UnifiedPLangParser.TranzSpecContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TwoXOneWithoutPhi}
	 * labeled alternative in {@link UnifiedPLangParser#tableDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTwoXOneWithoutPhi(@NotNull UnifiedPLangParser.TwoXOneWithoutPhiContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#lineCont}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLineCont(@NotNull UnifiedPLangParser.LineContContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#fv_classic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFv_classic(@NotNull UnifiedPLangParser.Fv_classicContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#inpPlace}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInpPlace(@NotNull UnifiedPLangParser.InpPlaceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OneXOneTable}
	 * labeled alternative in {@link UnifiedPLangParser#tableDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOneXOneTable(@NotNull UnifiedPLangParser.OneXOneTableContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#arcOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArcOp(@NotNull UnifiedPLangParser.ArcOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TwoXOneWithPhiWithOp}
	 * labeled alternative in {@link UnifiedPLangParser#tableDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTwoXOneWithPhiWithOp(@NotNull UnifiedPLangParser.TwoXOneWithPhiWithOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#otranz}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOtranz(@NotNull UnifiedPLangParser.OtranzContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOp(@NotNull UnifiedPLangParser.OpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code OneXTwoTable}
	 * labeled alternative in {@link UnifiedPLangParser#tableDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOneXTwoTable(@NotNull UnifiedPLangParser.OneXTwoTableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TwoXOneWithoutPhiWithOp}
	 * labeled alternative in {@link UnifiedPLangParser#tableDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTwoXOneWithoutPhiWithOp(@NotNull UnifiedPLangParser.TwoXOneWithoutPhiWithOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#newSubComp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewSubComp(@NotNull UnifiedPLangParser.NewSubCompContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#petriLine}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPetriLine(@NotNull UnifiedPLangParser.PetriLineContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#stateMent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStateMent(@NotNull UnifiedPLangParser.StateMentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TwoXTwoWithoutPhi}
	 * labeled alternative in {@link UnifiedPLangParser#tableDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTwoXTwoWithoutPhi(@NotNull UnifiedPLangParser.TwoXTwoWithoutPhiContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TwoXOneWithPhi}
	 * labeled alternative in {@link UnifiedPLangParser#tableDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTwoXOneWithPhi(@NotNull UnifiedPLangParser.TwoXOneWithPhiContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#poz_neg_double}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPoz_neg_double(@NotNull UnifiedPLangParser.Poz_neg_doubleContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#poz_neg_int}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPoz_neg_int(@NotNull UnifiedPLangParser.Poz_neg_intContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(@NotNull UnifiedPLangParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#token}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToken(@NotNull UnifiedPLangParser.TokenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TwoXTwoWithPhi}
	 * labeled alternative in {@link UnifiedPLangParser#tableDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTwoXTwoWithPhi(@NotNull UnifiedPLangParser.TwoXTwoWithPhiContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#doubleCell}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoubleCell(@NotNull UnifiedPLangParser.DoubleCellContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TwoXTwoWithoutPhiWithOp}
	 * labeled alternative in {@link UnifiedPLangParser#tableDcl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTwoXTwoWithoutPhiWithOp(@NotNull UnifiedPLangParser.TwoXTwoWithoutPhiWithOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code bigPWithoutNumber}
	 * labeled alternative in {@link UnifiedPLangParser#place}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBigPWithoutNumber(@NotNull UnifiedPLangParser.BigPWithoutNumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#subCompRef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubCompRef(@NotNull UnifiedPLangParser.SubCompRefContext ctx);
	/**
	 * Visit a parse tree produced by the {@code bigPWithNumber}
	 * labeled alternative in {@link UnifiedPLangParser#place}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBigPWithNumber(@NotNull UnifiedPLangParser.BigPWithNumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#doubleCellLine}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoubleCellLine(@NotNull UnifiedPLangParser.DoubleCellLineContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#tranz}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTranz(@NotNull UnifiedPLangParser.TranzContext ctx);
	/**
	 * Visit a parse tree produced by the {@code smallPWithNumber}
	 * labeled alternative in {@link UnifiedPLangParser#place}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSmallPWithNumber(@NotNull UnifiedPLangParser.SmallPWithNumberContext ctx);
	/**
	 * Visit a parse tree produced by {@link UnifiedPLangParser#simpleCellLine}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleCellLine(@NotNull UnifiedPLangParser.SimpleCellLineContext ctx);
}