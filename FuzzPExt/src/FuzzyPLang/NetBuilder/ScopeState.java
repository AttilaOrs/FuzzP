package FuzzyPLang.NetBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

abstract public class ScopeState {

    List<String> subs;

    public ScopeState() {
        subs = new ArrayList<>();
    }

    public boolean current() {
        return subs.isEmpty();
    }

    public void addSub(String sub) {
        subs.add(sub);
    }

    public void addToHead(String sub) {
        subs.add(0, sub);
    }

    public String removeLastSub() {
        if (!subs.isEmpty()) {
            return subs.remove(subs.size() - 1);
        }
        return null;
    }

    public String removeFirstSub() {
        if (!subs.isEmpty()) {
            return subs.remove(0);
        }
        return null;
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof ScopeState) {
            return ((ScopeState) o).subs.equals(this.subs);
        }
        return false;
    }

    @Override
    public String toString() {
        return subs.stream().collect(Collectors.joining("."));
    }

}
