package group1.inverted;

import java.io.File;

import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.util.ToolRunner;

public class Main {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String arg = args[0];
		File outputDir = new File(args[2]);
		if (outputDir.exists()) {
			FileUtil.fullyDelete(outputDir);
		}

		String[] newArgs = { args[1], args[2] };
		if (arg.equalsIgnoreCase("index")) {
			ToolRunner.run(new Indexer(), newArgs);
		} else if (arg.equalsIgnoreCase("query")) {
			// query
		} else {
			System.err.println("Usage: [index|query] [inputdir] [outputdir]");
		}
	}

}
