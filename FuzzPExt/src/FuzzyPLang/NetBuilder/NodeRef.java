package FuzzyPLang.NetBuilder;

public class NodeRef {

    String nodeName;
    DynamicScope subState;

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

}
