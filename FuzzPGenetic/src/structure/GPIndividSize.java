package structure;

public class GPIndividSize {

	public int ops;
	public int leafs;
	public int depth;

	public GPIndividSize() {
		ops = 0;
		leafs = 0;
		depth = 0;
	}

	public GPIndividSize(int ops, int leafs, int depth) {
		this.ops = ops;
		this.leafs = leafs;
		this.depth = depth;
	}

	public void add(GPIndividSize ll) {
		ops += ll.ops;
		leafs += ll.leafs;
		depth += ll.depth;
	}

	public static GPIndividSize uniteAtOperator(GPIndividSize ff,
			GPIndividSize ss) {
		return new GPIndividSize(ff.ops + ss.ops + 1, ff.leafs + ss.leafs,
				((ff.depth > ss.depth) ? ff.depth : ss.depth) + 1);

	}
}
