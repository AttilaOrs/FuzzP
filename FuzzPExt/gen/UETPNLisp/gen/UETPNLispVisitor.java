// Generated from UETPNLisp.g4 by ANTLR 4.5
package UETPNLisp.gen;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link UETPNLispParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface UETPNLispVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link UETPNLispParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(UETPNLispParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link UETPNLispParser#subexp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubexp(UETPNLispParser.SubexpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Seq}
	 * labeled alternative in {@link UETPNLispParser#op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSeq(UETPNLispParser.SeqContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Conc}
	 * labeled alternative in {@link UETPNLispParser#op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConc(UETPNLispParser.ConcContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Selc}
	 * labeled alternative in {@link UETPNLispParser#op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelc(UETPNLispParser.SelcContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Loop}
	 * labeled alternative in {@link UETPNLispParser#op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoop(UETPNLispParser.LoopContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Add}
	 * labeled alternative in {@link UETPNLispParser#op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdd(UETPNLispParser.AddContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Multi}
	 * labeled alternative in {@link UETPNLispParser#op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulti(UETPNLispParser.MultiContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PosNegSplit}
	 * labeled alternative in {@link UETPNLispParser#op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPosNegSplit(UETPNLispParser.PosNegSplitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Delay}
	 * labeled alternative in {@link UETPNLispParser#leaf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDelay(UETPNLispParser.DelayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Input}
	 * labeled alternative in {@link UETPNLispParser#leaf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInput(UETPNLispParser.InputContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Output}
	 * labeled alternative in {@link UETPNLispParser#leaf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOutput(UETPNLispParser.OutputContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Block}
	 * labeled alternative in {@link UETPNLispParser#leaf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(UETPNLispParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Memory}
	 * labeled alternative in {@link UETPNLispParser#leaf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemory(UETPNLispParser.MemoryContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Const}
	 * labeled alternative in {@link UETPNLispParser#leaf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConst(UETPNLispParser.ConstContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Inv}
	 * labeled alternative in {@link UETPNLispParser#leaf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInv(UETPNLispParser.InvContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Negate}
	 * labeled alternative in {@link UETPNLispParser#leaf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegate(UETPNLispParser.NegateContext ctx);
	/**
	 * Visit a parse tree produced by {@link UETPNLispParser#nr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNr(UETPNLispParser.NrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CopyOutType}
	 * labeled alternative in {@link UETPNLispParser#outType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCopyOutType(UETPNLispParser.CopyOutTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BlockingReader}
	 * labeled alternative in {@link UETPNLispParser#inpType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockingReader(UETPNLispParser.BlockingReaderContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NonblockingReader}
	 * labeled alternative in {@link UETPNLispParser#inpType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonblockingReader(UETPNLispParser.NonblockingReaderContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EnableIfPhi}
	 * labeled alternative in {@link UETPNLispParser#inpType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnableIfPhi(UETPNLispParser.EnableIfPhiContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EnableIfNotPhi}
	 * labeled alternative in {@link UETPNLispParser#inpType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnableIfNotPhi(UETPNLispParser.EnableIfNotPhiContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ShiftUpIfEventTable}
	 * labeled alternative in {@link UETPNLispParser#inpType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShiftUpIfEventTable(UETPNLispParser.ShiftUpIfEventTableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ShiftDownIfEventTable}
	 * labeled alternative in {@link UETPNLispParser#inpType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShiftDownIfEventTable(UETPNLispParser.ShiftDownIfEventTableContext ctx);
	/**
	 * Visit a parse tree produced by {@link UETPNLispParser#poz_neg_double}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPoz_neg_double(UETPNLispParser.Poz_neg_doubleContext ctx);
}