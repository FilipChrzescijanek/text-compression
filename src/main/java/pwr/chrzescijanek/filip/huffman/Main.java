package pwr.chrzescijanek.filip.huffman;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {

	public static void main(final String... args) {
		final Options options = new Options();

		final Option mode = new Option("m", "mode", true, "mode (\"compress\" or \"decompress\")");
		mode.setRequired(true);
		options.addOption(mode);

		final Option input = new Option("i", "input", true, "input file path");
		input.setRequired(true);
		options.addOption(input);

		final Option output = new Option("o", "output", true, "output file");
		output.setRequired(true);
		options.addOption(output);

		final CommandLineParser parser = new DefaultParser();
		final HelpFormatter formatter = new HelpFormatter();
		final CommandLine cmd;

		try {
			cmd = parser.parse(options, args);
		} catch (final ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("huffman.jar", options);

			System.exit(1);
			return;
		}

		final String appMode = cmd.getOptionValue("mode");
		final String inputFilePath = cmd.getOptionValue("input");
		final String outputFilePath = cmd.getOptionValue("output");

		switch (appMode) {
			case "compress":
				new Compressor().run(inputFilePath, outputFilePath);
				break;
			case "decompress":
				new Decompressor().run(inputFilePath, outputFilePath);
				break;
			default:
				System.err.println("Mode must equal \"compress\" or \"decompress\", given: " + appMode);
				break;
		}
	}

}
