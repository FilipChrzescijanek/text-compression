package pwr.chrzescijanek.filip.huffman.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

class InternalNode extends Node {

	private final Node left;
	private final Node right;

	InternalNode(final int weight, final Node left, final Node right) {
		super(weight);
		this.left = left;
		this.right = right;
	}

	Node getLeft() {
		return left;
	}

	Node getRight() {
		return right;
	}

	@Override
	void encode(final Map<Character, List<Boolean>> dictionary, final Boolean... values) {
		getLeft().encode(dictionary, addValue(values, Boolean.FALSE));
		getRight().encode(dictionary, addValue(values, Boolean.TRUE));
	}

	private Boolean[] addValue(final Boolean[] values, final Boolean newValue) {
		final List<Boolean> list = new ArrayList<>(Arrays.asList(values));
		list.add(newValue);
		return list.toArray(new Boolean[0]);
	}
}
