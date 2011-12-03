package group1.inverted;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.hadoop.io.Text;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Tokenizer implements Iterable<String> {

	protected String docId;

	protected List<String> tokens = new ArrayList<String>(50);

	protected static SAXParserFactory saxFact = SAXParserFactory.newInstance();

	protected DefaultHandler handler = new DefaultHandler() {

		protected boolean inDocid = false;

		protected boolean inText = false;

		protected static final String ID_TAG = "DOCID";

		protected static final String TITLE_TAG = "TITLE";

		protected static final String TEXT_TAG = "TEXT";

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			if (inText) {
				String str = new String(ch, start, length);
				StringTokenizer toker = new StringTokenizer(str, " \t\n.,");
				while (toker.hasMoreTokens()) {
					String next = toker.nextToken();
					if (next.length() >= 5) {
						tokens.add(next);
					}
				}
				inText = false;
			} else if (inDocid) {
				String str = new String(ch, start, length);
				docId = str;
				inDocid = false;
			}
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			if (qName.equalsIgnoreCase(ID_TAG)) {
				this.inDocid = true;
				this.inText = false;
			} else if (qName.equalsIgnoreCase(TITLE_TAG)
					|| qName.equalsIgnoreCase(TEXT_TAG)) {
				this.inDocid = false;
				this.inText = true;
			} else {
				this.inDocid = false;
				this.inText = false;
			}
		}

	};

	public Tokenizer(Text t) throws Exception {
		parse(t);
	}

	protected void parse(Text t) throws ParserConfigurationException,
			SAXException, IOException {
		SAXParser parser = saxFact.newSAXParser();
		String trimmed = new String(t.getBytes()).trim();
		ByteArrayInputStream bin = new ByteArrayInputStream(trimmed.getBytes());
		parser.parse(bin, handler);
	}

	public String getDocId() {
		return docId;
	}

	public Iterator<String> iterator() {
		return tokens.iterator();
	}

}
