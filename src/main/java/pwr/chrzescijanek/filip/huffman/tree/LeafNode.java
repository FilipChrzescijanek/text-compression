package pwr.chrzescijanek.filip.huffman.tree;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

class LeafNode extends Node {

	private final char symbol;

	LeafNode(final int weight, final char symbol) {
		super(weight);
		this.symbol = symbol;
	}

	char getSymbol() {
		return symbol;
	}

	@Override
	void encode(final Map<Character, List<Boolean>> dictionary, final Boolean... values) {
		dictionary.put(getSymbol(), Arrays.asList(values));
	}

}
