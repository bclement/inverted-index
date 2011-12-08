package group1.inverted;


public class IndexParser {
	
	public static class Index {
		public String word = null;
		public Integer count = null;
		public String docid = null;
		
		@Override
		public String toString()
		{
			return word + ":" + count + ":" + docid;
		}
	}

	public static Index parseLine(final String line) {
		Index retval = new Index();
		
		String[] part1 = line.split("[\\s|\\t]+");
		if (part1.length == 2)
		{
			retval.word = part1[0];
			String[] part2 = part1[1].split(":");
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
	
	public static void main(final String[] args)
	{
		String[] lines = {
				"actor\t1:2.txt<REU001-0020.940701>",
				"added\t2:1.txt<REU001-0013.940701>",
				"additional\t2:1.txt<REU001-0013.940701>",
				"africa\t1:3.txt<REU001-0045.940701>",
				"after\t1:1.txt<REU001-0013.940701>",
				"afterwards\t1:3.txt<REU001-0045.940701>"
		};
		
		for (String line : lines)
		{
			IndexParser.Index i = IndexParser.parseLine(line);
			System.out.println(i);
		}
		
	}
}
