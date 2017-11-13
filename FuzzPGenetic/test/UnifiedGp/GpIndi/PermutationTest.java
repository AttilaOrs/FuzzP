package UnifiedGp.GpIndi;

import static UnifiedGp.TestUtils.countNode;
import static org.junit.Assert.assertEquals;

import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import UnifiedGp.Tree.IInnerNode;
import UnifiedGp.Tree.INode;
import UnifiedGp.Tree.Nodes.BlockLeaf;
import UnifiedGp.Tree.Nodes.DelayLeaf;
import UnifiedGp.Tree.Nodes.InnerNode;
import UnifiedGp.Tree.Nodes.NodeType;

public class PermutationTest {
  
  private Permutation permuatation;

  @Before
  public void setup(){
    permuatation = new Permutation(false);
  }
  
  @Test
  public void simpleExpressionPermutate(){
    IInnerNode<NodeType> val = new InnerNode(NodeType.Seq, new BlockLeaf(), new DelayLeaf(2));
    UnifiedGpIndi indi = new UnifiedGpIndi(val);
    
    UnifiedGpIndi newIndi = permuatation.mutate(indi, new Random());
    
    assertEquals("(@ d:2 b)", newIndi.getRoot().toString());
  }
  
  
  @Test
  public void complexEXpressionPermutate(){
    IInnerNode<NodeType> val1 = new InnerNode(NodeType.Seq, new DelayLeaf(0), new DelayLeaf(1));
    IInnerNode<NodeType> val2 = new InnerNode(NodeType.Seq, new DelayLeaf(2), new DelayLeaf(3));
    IInnerNode<NodeType> vall1 = new InnerNode(NodeType.Loop, val1, new DelayLeaf(4));
    IInnerNode<NodeType> vall2 = new InnerNode(NodeType.Multiply, val2, new DelayLeaf(5));
    IInnerNode<NodeType>  root = new InnerNode(NodeType.PosNegSplit, vall1, vall2);
    
    
    UnifiedGpIndi newIndi = permuatation.mutate(new UnifiedGpIndi(root), new Random());
    IInnerNode<NodeType> newRoot = newIndi.getRoot();
    
    long delaysLeafsExacltyOnce= IntStream.range(0, 6).mapToObj(ii -> ((Predicate<INode<NodeType>>) node -> {
      
        if(node.getType().isDelay()){
           DelayLeaf asDelayLeaf = (DelayLeaf) node;
           return asDelayLeaf.getDelay() == ii;
        }
        return false;
    }) ).map(pred -> countNode(newRoot, pred)).filter(count -> count==1).count();
    
    assertEquals(delaysLeafsExacltyOnce, 6);
    
    
    
    
  }
  
  

}
