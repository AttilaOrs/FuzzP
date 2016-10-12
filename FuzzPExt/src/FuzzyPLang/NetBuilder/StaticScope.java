package FuzzyPLang.NetBuilder;

public class StaticScope extends ScopeState {

    public StaticScope cloneSubState() {
        StaticScope ss = new StaticScope();
        for (String sub : subs) {
            ss.addSub(sub);
        }
        return ss;
    }

	public boolean sameFamily(StaticScope secific) {
		if(secific.subs.size() < subs.size()){
			return false;
		}
		for(int i = 0; i<subs.size(); i++){
			if(!secific.subs.get(i).equals(subs.get(i))){
				return false;
			}
		}
		return true;
	}
}
