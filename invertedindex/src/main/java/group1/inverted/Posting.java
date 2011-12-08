package group1.inverted;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class Posting implements WritableComparable<Posting> {

	String docid;
	int count;

	public Posting() {
	}

	public Posting(Posting other) {
		this.docid = other.docid;
		this.count = other.count;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + count;
		result = prime * result + ((docid == null) ? 0 : docid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Posting other = (Posting) obj;
		if (count != other.count)
			return false;
		if (docid == null) {
			if (other.docid != null)
				return false;
		} else if (!docid.equals(other.docid))
			return false;
		return true;
	}

	public int compareTo(Posting rhs) {
		int rval = new Integer(count).compareTo(rhs.count);
		if (rval == 0) {
			rval = docid.compareTo(rhs.docid);
		}
		return rval;
	}

	public void readFields(DataInput in) throws IOException {
		count = in.readInt();
		this.docid = in.readUTF();
	}

	public void write(DataOutput out) throws IOException {
		out.writeInt(count);
		out.writeUTF(docid);
	}

	public static Posting read(DataInput in) throws IOException {
		Posting rval = new Posting();
		rval.readFields(in);
		return rval;
	}

	@Override
	public String toString() {
		return "" + count + ":" + docid;
	}
}
