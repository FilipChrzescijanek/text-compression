package pwr.chrzescijanek.filip.huffman.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class Tree {

	private final String input;
	private final Node root;

	private Tree(final Node root) {
		this.root = root;
		this.input = "";
	}

	public Tree(final String input) {
		this.input = input;
		this.root = createRoot();
	}

	public static Tree deserialize(final int inputLength, final byte[] data) {
		final List<Character> chars = new LinkedList<>();
		for (final byte b : data) {
			chars.add((char) (b & 0xFF));
		}
		if (!chars.isEmpty() && chars.remove(0) == '\0') {
			return new Tree(new InternalNode(inputLength, deserialize(chars), deserialize(chars)));
		}
		throw new CorruptDataException("Tree data is corrupted!");
	}

	private static Node deserialize(final List<Character> chars){
		final Character c = chars.remove(0);
		if (c == '\0') {
			return new InternalNode(0, deserialize(chars), deserialize(chars));
		} else {
			return new LeafNode(0, c);
		}
	}

	private Node createRoot() {
		final PriorityQueue<Node> queue = createQueue(input);

		while(queue.size() > 1) {
			final Node left = queue.remove();
			final Node right = queue.remove();
			queue.add(new InternalNode(left.getWeight() + right.getWeight(), left, right));
		}

		return queue.remove();
	}

	private PriorityQueue<Node> createQueue(final String input) {
		final Map<Character, Integer> probabilities = new HashMap<>();

		for (int i = 0; i < input.length(); i++) {
			probabilities.put(input.charAt(i), probabilities.getOrDefault(input.charAt(i), 0) + 1);
		}

		final List<Node> leafs = probabilities.entrySet()
		                                      .stream()
		                                      .map(e -> new LeafNode(e.getValue(), e.getKey()))
		                                      .collect(Collectors.toList());

		return new PriorityQueue<>(leafs);
	}

	public byte[] serialize() {
		final List<Byte> bytes = new ArrayList<>();
		serialize(root, bytes);
		return toByteArray(bytes);
	}

	private void serialize(final Node current, final List<Byte> bytes) {
		if (current instanceof InternalNode) {
			bytes.add((byte) (0));
			serialize(((InternalNode) current).getLeft(), bytes);
			serialize(((InternalNode) current).getRight(), bytes);
		} else {
			bytes.add((byte) (((LeafNode) current).getSymbol() & 0x00FF));
		}
	}

	private byte[] toByteArray(final List<Byte> list){
		final byte[] array = new byte[list.size()];
		for(int i = 0; i < array.length; i++)
			array[i] = list.get(i);
		return array;
	}

	public byte[] encode() {
		return encode(input);
	}

	public byte[] encode(final String input) {
		return getBytes(encode(input, getDictionary()));
	}

	private Map<Character, List<Boolean>> getDictionary() {
		final Map<Character, List<Boolean>> dictionary = new HashMap<>();
		root.encode(dictionary);
		return dictionary;
	}

	private List<Boolean> encode(final String input, final Map<Character, List<Boolean>> dictionary) {
		final List<Boolean> encoded = new ArrayList<>();
		for (int i = 0; i < input.length(); i++) {
			final List<Boolean> bitSet = dictionary.get(input.charAt(i));
			encoded.addAll(bitSet);
		}
		return encoded;
	}

	private byte[] getBytes(final List<Boolean> encoded) {
		final int predictedLength = (encoded.size() + 7) / 8;
		final byte[] array = new byte[predictedLength];
		for (int i = 0; i < encoded.size(); i++) {
			array[i / 8] |= (encoded.get(i) ? 0x1 : 0x0) << (7 - (i % 8));
		}
		return array;
	}

	public String decode(final byte[] encoded) {
		int charactersLeft = root.getWeight();
		int currentIndex = 0;
		final StringBuilder sb = new StringBuilder();

		while (charactersLeft > 0) {
			Node current = root;
			while (!(current instanceof LeafNode)) {
				final int value = encoded[currentIndex / 8] >> (7 - (currentIndex % 8)) & 0x1;
				if (value == 0) {
					current = ((InternalNode) current).getLeft();
				} else {
					current = ((InternalNode) current).getRight();
				}
				currentIndex++;
			}
			sb.append(((LeafNode) current).getSymbol());
			--charactersLeft;
		}

		return sb.toString();
	}

}
