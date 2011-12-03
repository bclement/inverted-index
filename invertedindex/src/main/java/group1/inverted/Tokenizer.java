package group1.inverted;

import java.util.Iterator;

import org.apache.hadoop.io.Text;

public class Tokenizer implements Iterable<String> {

	protected Text t;

	public Tokenizer(Text t) {
		this.t = t;
	}

	public String getDocId() {
		return "FAKE";
	}

	public Iterator<String> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
