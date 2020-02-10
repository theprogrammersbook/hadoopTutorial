package com.theprogrammersbook.mapreduce.partition;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.log4j.Logger;

public class LastAccessDateDriver {
 public static final Logger logger  = Logger.getLogger(LastAccessDateDriver.class);
	public static void main(String[] args) throws Exception {
		logger.info("Starting :: Reading file :: stackoverflowInput/users");
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args)
		.getRemainingArgs();
		if (otherArgs.length != 2) {
			logger.info("Usage: PartitionedUsers <users> <outdir>");
			System.exit(2);
		}
       //Delete the Old or any existing output folder.
		FileSystem.get(conf).delete(new Path(otherArgs[1]),true);

		Job job = new Job(conf, "PartitionedUsers");

		job.setJarByClass(LastAccessDateDriver.class);

		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(Text.class);
		
		job.setMapperClass(LastAccessDateMapper.class);
		job.setReducerClass(LastAccessDateReducer.class);
		// Last access dates span between 2008-2014, or 7 years
		job.setNumReduceTasks(7);
		
		// Set custom partitioner and min last access date
		job.setPartitionerClass(LastAccessDatePartitioner.class);
		LastAccessDatePartitioner.setMinLastAccessDateYear(job, 2008);

		
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));

		
		job.setOutputFormatClass(TextOutputFormat.class);
		job.getConfiguration().set("mapred.textoutputformat.separator", "");

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
