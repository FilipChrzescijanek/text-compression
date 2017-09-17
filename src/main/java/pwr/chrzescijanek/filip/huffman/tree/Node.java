package pwr.chrzescijanek.filip.huffman.tree;

import java.util.List;
import java.util.Map;

abstract class Node implements Comparable<Node> {

	private final int weight;

	Node(final int weight) {
		this.weight = weight;
	}

	int getWeight() {
		return weight;
	}

	abstract void encode(Map<Character, List<Boolean>> dictionary, Boolean... values);

	@Override
	public int compareTo(final Node other) {
		return Integer.compare(this.getWeight(), other.getWeight());
	}

}
