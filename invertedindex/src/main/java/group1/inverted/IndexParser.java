package group1.inverted;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class IndexParser {
	
	private final HashMap<String, String> index = new HashMap<String, String>();
	
	public IndexParser(final String fileName) throws IOException
	{
		init(fileName);
	}

	private void init(String fileName) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
		String line;
		while ((line = reader.readLine()) != null)
		{
			String[] parts = line.split("\\s+");
			if (parts.length == 2)
			{
				index.put(parts[0], parts[1]);
			}
		}
	}

	public String[] get(final String word)
	{
		if (index.containsKey(word))
		{
			return index.get(word).split(":");
		}
		return null;
	}
}
