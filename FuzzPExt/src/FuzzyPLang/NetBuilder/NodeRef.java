package FuzzyPLang.NetBuilder;

public class NodeRef {

    String nodeName;
    DynamicScope subState;
    
    public NodeRef(String nodeName){
    	this(nodeName, null);
    }

    public NodeRef(String nodeName, DynamicScope subState) {
        this.nodeName = nodeName;
        if (subState == null) {
            subState = new DynamicScope();

        } else {
            this.subState = subState;
        }
    }

    public String getNodeName() {
        return nodeName;
    }

    public DynamicScope getDynamicScope() {
        return subState;
    }

    public void updateToFullDynScope(DynamicScope dinScope) {
        while (!dinScope.current()) {
            subState.addToHead(dinScope.removeFirstSub());
        }

    }

    public NodeRef copyNodeRef() {
        return new NodeRef(nodeName, subState.cloneSubState());
    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof NodeRef) {
            NodeRef ref = (NodeRef) o;
            return nodeName.equals(ref.nodeName) && subState.equals(ref.subState);
        }
        return false;
    }

    @Override
    public String toString() {
        return getDynamicScope().toString() + "." + nodeName;

    }

    @Override
    public int hashCode(){
		return 37*( 29 + subState.hashCode()) + nodeName.hashCode();
    }

}
