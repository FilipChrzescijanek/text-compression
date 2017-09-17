package pwr.chrzescijanek.filip.huffman;

import pwr.chrzescijanek.filip.huffman.tree.Tree;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Compressor {

	public void run(final String inputFilePath, final String outputFilePath) {
		try (Scanner in = new Scanner(new BufferedReader(new FileReader(inputFilePath)));
		     BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outputFilePath))) {
			final String input = in.useDelimiter("\\Z").next();
			final Tree tree = new Tree(input);
			out.write(input.length());
			final byte[] serializedTree = tree.serialize();
			out.write(serializedTree.length);
			out.write(serializedTree);
			final byte[] encodedBytes = tree.encode();
			out.write(encodedBytes.length);
			out.write(encodedBytes);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
