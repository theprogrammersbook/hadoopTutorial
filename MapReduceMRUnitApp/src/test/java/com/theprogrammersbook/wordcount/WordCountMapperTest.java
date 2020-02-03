package com.theprogrammersbook.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.junit.Test;

import java.io.IOException;

public class WordCountMapperTest extends WordCountTestBase {

    @Test
    public void testMapperWithCatsAndDogs() throws IOException {
        mapDriver.withInput(new LongWritable(1), new Text("cat cat dog"));
        mapDriver.withOutput(new Text("cat"), new IntWritable(1));
        mapDriver.withOutput(new Text("cat"), new IntWritable(1));
        mapDriver.withOutput(new Text("dog"), new IntWritable(1));
        mapDriver.runTest();
    }

    @Test
    public void testCompleteNameWordCount() throws IOException {
        mapDriver.withInput(new LongWritable(1),new Text("Nagaraju Gajula"));
        mapDriver.withOutput(new Text("Nagaraju"),new IntWritable(1));
        mapDriver.withOutput(new Text("Gajula"),new IntWritable(1));
        mapDriver.runTest();;
    }

    @Test
    public void testMapperWithHorsesAndZebras() throws IOException {
        mapDriver.withInput(new LongWritable(1), new Text("horse horse zebra"));
        mapDriver.withOutput(new Text("horse"), new IntWritable(1));
        mapDriver.withOutput(new Text("horse"), new IntWritable(1));
        mapDriver.withOutput(new Text("zebra"), new IntWritable(1));
        mapDriver.runTest();
    }

    /* if uncommented, this test will fail */
/*

	@Test
	public void testMapperWithZebrasAndHorses() throws IOException {
		*/
/*
		2 Error(s): (Matched expected output (horse, 1) but at incorrect position 2 (expected position 1),
		Matched expected output (zebra, 1) but at incorrect position 1 (expected position 2))
		 *//*

	    mapDriver.withInput(new LongWritable(1), new Text("horse zebra horse"));
		mapDriver.withOutput(new Text("horse"), new IntWritable(1));
		mapDriver.withOutput(new Text("horse"), new IntWritable(1));
		mapDriver.withOutput(new Text("zebra"), new IntWritable(1));
		mapDriver.runTest();
	}
*/




}