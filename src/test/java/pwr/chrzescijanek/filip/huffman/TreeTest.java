package pwr.chrzescijanek.filip.huffman;

import org.junit.Assert;
import org.junit.Test;
import pwr.chrzescijanek.filip.huffman.tree.Tree;

public class TreeTest {
	
	@Test
	public void helloWorld() throws Exception {
		final String input = "Hello world!";
		final Tree tree = new Tree(input);
		Assert.assertEquals(input, tree.decode(tree.encode()));
	}

	@Test
	public void multiline() throws Exception {
		final String input = "Lorem ipsum dolor sit amet, consectetur adipiscing elit,"
		                     + "\nsed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
		final Tree tree = new Tree(input);
		Assert.assertEquals(input, tree.decode(tree.encode()));
	}

}