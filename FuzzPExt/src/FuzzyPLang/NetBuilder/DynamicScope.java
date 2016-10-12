package FuzzyPLang.NetBuilder;

public class DynamicScope extends ScopeState {

    public DynamicScope cloneSubState() {
        DynamicScope ss = new DynamicScope();
        for (String sub : subs) {
            ss.addSub(sub);
        }
        return ss;
    }
}
