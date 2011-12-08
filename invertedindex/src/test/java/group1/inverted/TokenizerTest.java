package group1.inverted;

import group1.inverted.Tokenizer.TextBounds;

import java.util.Iterator;

import junit.framework.Assert;

import org.apache.hadoop.io.Text;
import org.junit.Test;

public class TokenizerTest {

	protected String simpleXml = "<DOC>\n"
			+ "<DOCID>REU001-0013.940701</DOCID>\n"
			+ "<TITLE> PALESTINIANS TO RECEIVE AID TO POLICE GAZA, JERICHO</TITLE>\n"
			+ "<SUBJECT> BC-MIDEAST-AID </SUBJECT>\n"
			+ "<AUTHOR></AUTHOR>\n"
			+ "<TEXT><crap></crap><p>the cake is a lie</p>  flabber newline</TEXT></DOC>";

	protected String badXml = "<DOC><p>\n"
			+ "<DOCID>REU001-0013.940701<WhoKnows>\n"
			+ "<TITLE> PALESTINIANS TO RECEIVE AID TO POLICE GAZA, JERICHO</TITLE>\n"
			+ "<SUBJECT> BC-MIDEAST-AID </SUBJECT>\n" + "<AUTHOR></AUTHOR>\n"
			+ "<TEXT><p>the cake is a lie  flabber newline";

	@Test
	public void simpleTest() throws Exception {
		assertXml(simpleXml);
	}

	public void assertXml(String xml) throws Exception {
		Text text = new Text(xml);
		Tokenizer tokenizer = new Tokenizer("0", text);
		String docId = tokenizer.getDocId();
		Assert.assertEquals("0<REU001-0013.940701>", docId);
		Iterator<String> i = tokenizer.iterator();
		Assert.assertEquals("palestinians", i.next());
		Assert.assertEquals("receive", i.next());
		Assert.assertEquals("police", i.next());
		Assert.assertEquals("jericho", i.next());
		Assert.assertEquals("bc-mideast-aid", i.next());
		Assert.assertEquals("flabber", i.next());
		Assert.assertEquals("newline", i.next());
		Assert.assertEquals(false, i.hasNext());
	}

	@Test
	public void tagTest() {
		Tokenizer toker = new Tokenizer();
		String xml = "<foo>bar</foo><next>one</next>";
		toker.data = xml;
		TextBounds open = toker.findTag(0);
		TextBounds close = toker.findTag(open.end);
		Assert.assertEquals("foo", toker.getBeween(open));
		Assert.assertEquals("bar", toker.getBeween(open, close));
		open = toker.findTag(close.end);
		close = toker.findTag(open.end);
		Assert.assertEquals("next", toker.getBeween(open));
		Assert.assertEquals("one", toker.getBeween(open, close));
		open = toker.findTag(close.end);
		Assert.assertTrue(open.done);

	}

	@Test
	public void malformTest() throws Exception {
		assertXml(badXml);
	}
}
