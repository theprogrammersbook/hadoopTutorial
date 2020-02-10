package com.theprogrammersbook.mapreduce.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.log4j.Logger;

import java.util.Date;

public class WC {
	public static final Logger logger = Logger.getLogger(WC.class);
	public static void main(String[] args) throws Exception {
		logger.info("Starting.. Reading StackOverFlow .... stackoverflowInput/users file");
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length != 2) {
			logger.info("Usage: WC <in> <out>");
			System.exit(2);
		}


		FileSystem.get(conf).delete(new Path(otherArgs[1]));
		//Job job = Job.getInstance(conf, "Word Count");
		Job job = Job.getInstance();

		job.setJarByClass(WC.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		

		job.setMapperClass(WCMapper.class);
		job.setReducerClass(WCReducer.class);
		job.setCombinerClass(WCReducer.class);
		
		job.setNumReduceTasks(2);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileSystem.get(conf).delete(new Path(args[1]),true);

		TextInputFormat.addInputPath(job, new Path(args[0]));
        TextOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);


		job.waitForCompletion(true);
	}
}