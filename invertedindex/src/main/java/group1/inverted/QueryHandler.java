package group1.inverted;

import org.apache.hadoop.conf.Configured;
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
	
	public static class Map extends Mapper<Text, Text, Text, Text>
	{
		@Override
		public void map(Text unused, Text value, Context context)
		{
			FileSplit fileSplit = (FileSplit) context.getInputSplit();
			String filename = fileSplit.getPath().getName();
			if ("_SUCCESS".equals(filename))
			{
				return;
			}
			
			
		}
	}
	
	public static class Reduce extends Reducer<Text, Text, Text, Text>
	{
		@Override
		public void reduce(Text key, Iterable<Text> values, Context context)
		{
			
		}
	}

	public int run(String[] args) throws Exception {
		Job job = new Job();
		job.setJarByClass(QueryHandler.class);
		job.setJobName("QueryHandler");
		job.setMapperClass(Map.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Posting.class);
		job.setCombinerClass(Reduce.class);
		job.setReducerClass(Reduce.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.waitForCompletion(true);
		return 0;
	}

}
