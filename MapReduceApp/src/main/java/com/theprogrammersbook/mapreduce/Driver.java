package com.theprogrammersbook.mapreduce;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.Logger;

import java.util.Date;


public class Driver {
    private static Logger logger = Logger.getLogger(Driver.class);
    public static  void main(String[] args) throws Exception {
        logger.info("Drive Code...");
        // instantiate a configuration
        Configuration configuration = new Configuration();

        // instantiate a job
        Job job = Job.getInstance(configuration, "Word Count");

        // set job parameters
        job.setJarByClass(WordCount.class);
        job.setMapperClass(WordCount.CountMapper.class);
        job.setCombinerClass(WordCount.CountReducer.class);
        job.setReducerClass(WordCount.CountReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // set io paths
        FileInputFormat.addInputPath(job, new Path("input/sample1.txt"));
        FileOutputFormat.setOutputPath(job, new Path("output"+new Date().getTime()));

        System.exit(job.waitForCompletion(true)? 0 : 1);
    }
}
