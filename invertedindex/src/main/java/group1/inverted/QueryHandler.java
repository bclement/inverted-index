package group1.inverted;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;

public class QueryHandler extends Configured implements Tool {

	public static class Map extends Mapper<Text, Text, Text, Text> {
		@Override
		public void map(Text unused, Text value, Context context)
				throws IOException, InterruptedException {
			FileSplit fileSplit = (FileSplit) context.getInputSplit();
			Path inputDir = fileSplit.getPath().getParent();
			Path inputParent = inputDir.getParent();
			String query = readFile(new Path(inputParent, "query.txt"));
			Set<String> qset = new HashSet<String>();
			qset.addAll(Arrays.asList(query.toLowerCase().split(" ")));

			String current = null; // get from index parse method
			String docId = null;
			if (qset.contains(current)) {
				context.write(new Text(current), new Text(docId));
			}
		}

		protected String readFile(Path p) throws IOException {
			FileSystem fs = FileSystem.get(new Configuration());
			FSDataInputStream in = fs.open(p);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			String line = null;
			StringBuilder builder = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				builder.append(line).append("\n");
			}
			reader.close();
			return builder.toString();
		}
	}

	public static class Reduce extends Reducer<Text, Text, Text, Text> {
		@Override
		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			for (Text t : values) {
				context.write(key, t);
			}
		}
	}

	public int run(String[] args) throws Exception {
		Job job = new Job();
		job.setJarByClass(QueryHandler.class);
		job.setJobName("QueryHandler");
		job.setMapperClass(Map.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.setCombinerClass(Reduce.class);
		job.setReducerClass(Reduce.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.waitForCompletion(true);
		return 0;
	}

}
