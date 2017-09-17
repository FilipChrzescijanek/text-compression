package pwr.chrzescijanek.filip.huffman;

import pwr.chrzescijanek.filip.huffman.tree.Tree;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Decompressor {

	public void run(final String inputFilePath, final String outputFilePath) {
		try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(inputFilePath));
		     BufferedWriter out = new BufferedWriter(new FileWriter(outputFilePath))) {
			final int length = in.read();
			final byte[] tree = new byte[in.read()];
			in.read(tree);
			final byte[] bytes = new byte[in.read()];
			in.read(bytes);
			out.write(Tree.deserialize(length, tree).decode(bytes));
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
