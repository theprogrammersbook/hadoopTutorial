package com.theprogrammersbook.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordCountReducerTest extends WordCountTestBase {

    @Test
    public void testReducerWithCats() throws IOException {
        List<IntWritable> values = new ArrayList<IntWritable>();
        values.add(new IntWritable(1));
        values.add(new IntWritable(1));
        reduceDriver.withInput(new Text("cat"), values);
        reduceDriver.withOutput(new Text("cat"), new IntWritable(2));
        reduceDriver.runTest();
    }
    @Test
    public void testReduceWithCompleteName() throws IOException {
        List<IntWritable> values = new ArrayList<IntWritable>();
        values.add(new IntWritable(1));
        values.add(new IntWritable(1));

        reduceDriver.withInput(new Text("Nagaraju"),values);
        reduceDriver.withOutput(new Text("Nagaraju"),new IntWritable(2));
        reduceDriver.runTest();
    }
}
