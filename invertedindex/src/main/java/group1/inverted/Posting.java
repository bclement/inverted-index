package group1.inverted;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class Posting implements WritableComparable<Posting>{
	
	String docid;
	int count;
	
	public Posting()
	{
	}
	
	public int compareTo(Posting rhs) {
		return docid.compareTo(rhs.docid);
	}

	public void readFields(DataInput in) throws IOException {
		count = in.readInt();
		byte[] buffer = new byte[256];
		try
		{
			in.readFully(buffer);
		}
		catch (Exception e)
		{
			// ignore
		}
		
		if (buffer.length > 0)
		{
			docid = new String(buffer);
		}
		else
		{
			// This should never happen.
			docid = "";
		}
	}

	public void write(DataOutput out) throws IOException {
		out.writeInt(count);
		out.writeBytes(docid);
		
	}
}
