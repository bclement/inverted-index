package group1.inverted;

import java.util.Iterator;

import junit.framework.Assert;

import org.apache.hadoop.io.Text;
import org.junit.Test;

public class TokenizerTest {

	protected String simpleXml = "<DOC>\n"
			+ "<DOCID>REU001-0013.940701</DOCID>\n"
			+ "<TDTID>TDT000002</TDTID>\n"
			+ "<SOURCE>REUTERS</SOURCE>\n"
			+ "<DATE>07/01/94 00:57</DATE>\n"
			+ "<TITLE> PALESTINIANS TO RECEIVE AID TO POLICE GAZA, JERICHO</TITLE>\n"
			+ "<HEADLINE> PALESTINIANS TO RECEIVE AID TO POLICE GAZA, JERICHO</HEADLINE>\n"
			+ "<SUBJECT> BC-MIDEAST-AID </SUBJECT>\n" + "<AUTHOR></AUTHOR>\n"
			+ "<TEXT>the cake is a lie  flabber newline</TEXT></DOC>";

	@Test
	public void simpleTest() throws Exception {
		System.out.println(simpleXml);
		Text text = new Text(simpleXml);
		Tokenizer tokenizer = new Tokenizer(text);
		String docId = tokenizer.getDocId();
		Assert.assertEquals("REU001-0013.940701", docId);
		Iterator<String> i = tokenizer.iterator();
		Assert.assertEquals("PALESTINIANS", i.next());
		Assert.assertEquals("RECEIVE", i.next());
		Assert.assertEquals("POLICE", i.next());
		Assert.assertEquals("JERICHO", i.next());
		Assert.assertEquals("flabber", i.next());
		Assert.assertEquals("newline", i.next());
		Assert.assertEquals(false, i.hasNext());
	}

}
