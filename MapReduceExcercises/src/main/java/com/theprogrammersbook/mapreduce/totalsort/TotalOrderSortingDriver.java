package com.theprogrammersbook.mapreduce.totalsort;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
//import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.InputSampler;
import org.apache.hadoop.mapreduce.lib.partition.TotalOrderPartitioner;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.log4j.Logger;

public class TotalOrderSortingDriver {
	public static final Logger logger = Logger.getLogger(TotalOrderSortingDriver.class);
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		logger.info("Starting.. Reading StackOverFlow .... stackoverflowInput/users file");
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length != 3) {
			logger.error("Usage: TotalOrderSortingDriver <input> <out> <numReducers>");
			System.exit(1);
		}

		Path inputPath = new Path(otherArgs[0]);
		Path partitionFile = new Path(otherArgs[1] + "_partitions.lst");
		Path outputStage = new Path(otherArgs[1] + "_staging");
		Path outputPath = new Path(otherArgs[1]);
		int numReducers = Integer.parseInt(otherArgs[2]);

		FileSystem.get(conf).delete(outputPath, true);
		FileSystem.get(conf).delete(partitionFile, true);
		FileSystem.get(conf).delete(outputStage, true);

		// Configure job to prepare for sampling
		Job converterJob = new Job(conf, "TotalOrderSorting");
		converterJob.setJarByClass(TotalOrderSortingDriver.class);

		// Use the mapper implementation with zero reduce tasks
		converterJob.setMapperClass(FormatConverterMapper.class);
		converterJob.setNumReduceTasks(0);

		TextInputFormat.setInputPaths(converterJob, inputPath);
		//TextOutputFormat.setOutputPath(converterJob,outputPath);
		// Set the output format to a sequence file
		converterJob.setOutputFormatClass(SequenceFileOutputFormat.class);
		SequenceFileOutputFormat.setOutputPath(converterJob, outputStage);

		
		converterJob.setOutputKeyClass(LongWritable.class);
		converterJob.setOutputValueClass(Text.class);

		// Submit the job and get completion code.
		int code = converterJob.waitForCompletion(true) ? 0 : 1;
		if (code == 0) {
			InputSampler.Sampler<LongWritable, Text> sampler =
					new InputSampler.RandomSampler<LongWritable, Text>(0.1, 10000);


			Job totalSortJob =  new Job(conf, "TotalOrderSorting-SortUserByLastAccessDate");
			totalSortJob.setJarByClass(TotalOrderSortingDriver.class);

			totalSortJob.setMapperClass(LastAccessDateTotalSortMapper.class);
			totalSortJob.setReducerClass(LastAccessDateTotalSortReducer.class);

			totalSortJob.setNumReduceTasks(numReducers);

			totalSortJob.setInputFormatClass(SequenceFileInputFormat.class);
			SequenceFileInputFormat.setInputPaths(totalSortJob, outputStage);
			totalSortJob.setMapOutputKeyClass(LongWritable.class);
			totalSortJob.setMapOutputValueClass(Text.class);



			totalSortJob.setOutputFormatClass(TextOutputFormat.class);
			FileOutputFormat.setOutputPath(totalSortJob, outputPath);
			// Use hadoop's TotalOrderPartitioner class and set the partition file
			totalSortJob.setPartitionerClass(TotalOrderPartitioner.class);
			TotalOrderPartitioner.setPartitionFile(totalSortJob.getConfiguration(), partitionFile);
			//totalSortJob.setSortComparatorClass(DescSortKeyComparator.class);
			totalSortJob.setOutputKeyClass(Text.class);
			totalSortJob.setOutputValueClass(Text.class);

			// Set the separator to an empty string
			totalSortJob.getConfiguration().set("mapred.textoutputformat.separator", "");

			InputSampler.writePartitionFile(totalSortJob, sampler);

			// Submit the job
			code = totalSortJob.waitForCompletion(true) ? 0 : 1;
			// Cleanup the partition file and the staging directory
			//FileSystem.get(conf).delete(partitionFile, false);
			//FileSystem.get(conf).delete(outputStage, true);			

			System.exit(code);
		}
	}
}