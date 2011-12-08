package group1.inverted;

import java.io.IOException;

public class IndexParser {
	
	public static class Index {
		public String word = null;
		public Integer count = null;
		public String docid = null;
	}

	public static Index parseLine(final String line) throws IOException {
		Index retval = new Index();
		
		String[] part1 = line.split("[\\s|\\t]+");
		if (part1.length == 2)
		{
			retval.word = part1[0];
			String[] part2 = line.split(":");
			if (part2.length == 2)
			{
				try
				{
					retval.count = Integer.parseInt(part2[0]);
				}
				catch (final Exception anything)
				{
				}
				retval.docid = part2[1];
			}
		}
		
		return retval;
	}
}
