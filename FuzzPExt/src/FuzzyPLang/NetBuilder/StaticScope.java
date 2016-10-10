package FuzzyPLang.NetBuilder;

public class StaticScope extends ScopeState {

    public StaticScope cloneSubState() {
        StaticScope ss = new StaticScope();
        for (String sub : subs) {
            ss.addSub(sub);
        }
        return ss;
    }
}
