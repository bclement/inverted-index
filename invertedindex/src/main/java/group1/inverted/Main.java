package group1.inverted;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;

public class Main {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String arg = args[0];
		String[] newArgs = { args[1], args[2] };
		if (arg.equalsIgnoreCase("index")) {
			ToolRunner.run(new Configuration(), new Indexer(), newArgs);
		} else if (arg.equalsIgnoreCase("query")) {
			// query
		} else {
			System.err.println("Usage: [index|query] [inputdir] [outputdir]");
		}
	}

}
