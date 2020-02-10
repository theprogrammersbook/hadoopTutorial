package com.theprogrammersbook.mapreduce.binning;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.LazyOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.log4j.Logger;

public class BinningDriver {
     public static final Logger logger = Logger.getLogger(BinningDriver.class);
	public static void main(String[] args) throws Exception {
		logger.info("Starting ::  Reading :: stackoverflowInput/posts");
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args)
		.getRemainingArgs();
		if (otherArgs.length != 2) {
			logger.info("Usage: Binning <posts> <outdir>");
			System.exit(1);
		}

		FileSystem.get(conf).delete(new Path(otherArgs[1]),true);

		Job job = new Job(conf, "Binning");
		job.setJarByClass(BinningDriver.class);
		
		job.setMapperClass(BinningMapper.class);		
		job.setNumReduceTasks(0);

		TextInputFormat.setInputPaths(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));

		// Configure the MultipleOutputs by adding an output called "bins"
		// With the proper output format and mapper key/value pairs
		MultipleOutputs.addNamedOutput(job, "nagaraju", TextOutputFormat.class,
				NullWritable.class, Text.class);
		
		LazyOutputFormat.setOutputFormatClass(job, TextOutputFormat.class);
		job.getConfiguration().set("mapred.textoutputformat.separator", "");
		
		//MultipleOutputs.setCountersEnabled(job, true);

		System.exit(job.waitForCompletion(true) ? 0 : 2);
	}
}
