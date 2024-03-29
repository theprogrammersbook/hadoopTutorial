MRUnit Example
==============================

This project shows how to setup a Maven project to build and test a MapReduce jar file using MRUnit.  

Only a couple of simple tests are included here; MRUnit can test many more aspects of MapReduce functionality than is shown in this project.

For full information on MRUnit see http://mrunit.apache.org/

Other good links are:

* MRUnit Tutorial:	https://cwiki.apache.org/confluence/display/MRUNIT/MRUnit+Tutorial

* MRUnit Javadoc:	http://mrunit.apache.org/documentation/javadocs/0.9.0-incubating/overview-summary.html

* Getting Started with MRUnit: http://mrunit.apache.org/documentation/javadocs/0.9.0-incubating/org/apache/hadoop/mrunit/package-summary.html


###MRUnit Example Tests
The MRUnit tests in this project are located in /src/test/java. In this project, the class WordCountTestBase is used to setup the drivers used by the other test classes.



###Install Maven
If maven is not already installed:

* Download maven from http://maven.apache.org/download.html

* Unzip the maven download and place it somewhere.  Mine is at /Users/mbrooks/apache/apache-maven-3.0.4

* Set an environment variable M2_HOME to point to the maven home directory

* Add $M2_HOME/bin to your path

Test maven is installed by running the mvn command from a dir without a pom.xml file and you should get a message like this:

	$ mvn
	[INFO] Scanning for projects...
	[INFO] ------------------------------------------------------------------------
	[INFO] BUILD FAILURE
	[INFO] ------------------------------------------------------------------------
	...
	

###Compile, run the MRUnit tests and package the MapReduce job's jar file
Switch to the project's root dir and execute the command: 

	> mvn clean package
	
You should see output like this:

	$ mvn clean package
	[INFO] Scanning for projects...
	[INFO]                                                                         
	[INFO] ------------------------------------------------------------------------
	[INFO] Building mrunit-example 1.0
	[INFO] ------------------------------------------------------------------------
	[INFO] 
	[INFO] --- maven-clean-plugin:2.4.1:clean (default-clean) @ mrunit-example ---
	[INFO] Deleting /Users/mbrooks/github-repos/mrunit-example/target
	[INFO] 
	[INFO] --- maven-resources-plugin:2.5:resources (default-resources) @ mrunit-example ---
	[INFO] Using 'UTF-8' encoding to copy filtered resources.
	[INFO] skip non existing resourceDirectory /Users/mbrooks/github-repos/mrunit-example/src/main/resources
	[INFO] 
	[INFO] --- maven-compiler-plugin:2.3.2:compile (default-compile) @ mrunit-example ---
	[INFO] Compiling 3 source files to /Users/mbrooks/github-repos/mrunit-example/target/classes
	[INFO] 
	[INFO] --- maven-resources-plugin:2.5:testResources (default-testResources) @ mrunit-example ---
	[INFO] Using 'UTF-8' encoding to copy filtered resources.
	[INFO] skip non existing resourceDirectory /Users/mbrooks/github-repos/mrunit-example/src/test/resources
	[INFO] 
	[INFO] --- maven-compiler-plugin:2.3.2:testCompile (default-testCompile) @ mrunit-example ---
	[INFO] Compiling 4 source files to /Users/mbrooks/github-repos/mrunit-example/target/test-classes
	[INFO] 
	[INFO] --- maven-surefire-plugin:2.10:test (default-test) @ mrunit-example ---
	[INFO] Surefire report directory: /Users/mbrooks/github-repos/mrunit-example/target/surefire-reports

	-------------------------------------------------------
	 T E S T S
	-------------------------------------------------------
	Running com.onefoursix.wordcount.WordCountMapperTest
	Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.393 sec
	Running com.onefoursix.wordcount.WordCountMapReduceTest
	Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.105 sec
	Running com.onefoursix.wordcount.WordCountReducerTest
	Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.063 sec

	Results :

	Tests run: 7, Failures: 0, Errors: 0, Skipped: 0

	[INFO] 
	[INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ mrunit-example ---
	[INFO] Building jar: /Users/mbrooks/github-repos/mrunit-example/mrunit-example-1.0.jar
	[INFO] ------------------------------------------------------------------------
	[INFO] BUILD SUCCESS
	[INFO] ------------------------------------------------------------------------
	[INFO] Total time: 2.871s
	[INFO] Finished at: Mon Sep 24 10:41:06 PDT 2012
	[INFO] Final Memory: 12M/81M
	[INFO] ---------------
	
Note that all seven MRUnit tests passed, and the MapReduce job's jar file was generated at /Users/mbrooks/github-repos/mrunit-example/mrunit-example-1.0.jar

The jar file generated by this example can be run using the command:

	hadoop jar mrunit-example-1.0.jar WordCount <input-data> <output-dir>


###Viewing Test Failures
In each of the three test files in /src/test/java there are tests that will fail if uncommented.  If you uncomment the tests that fail and re-run the build, the TEST section output will look like this:


	-------------------------------------------------------
	 T E S T S
	-------------------------------------------------------
	Running com.onefoursix.wordcount.WordCountMapperTest
	12/09/24 10:20:11 ERROR mrunit.TestDriver: Matched expected output (horse, 1) but at incorrect position 2 (expected position 1)
	12/09/24 10:20:11 ERROR mrunit.TestDriver: Matched expected output (zebra, 1) but at incorrect position 1 (expected position 2)
	Tests run: 3, Failures: 1, Errors: 0, Skipped: 0, Time elapsed: 0.436 sec <<< FAILURE!
	Running com.onefoursix.wordcount.WordCountMapReduceTest
	12/09/24 10:20:12 ERROR mrunit.TestDriver: Missing expected output (hyena, 1) at position 1.
	12/09/24 10:20:12 ERROR mrunit.TestDriver: Matched expected output (lizard, 2) but at incorrect position 1 (expected position 0)
	12/09/24 10:20:12 ERROR mrunit.TestDriver: Received unexpected output (goat, 1) at position 0.
	Tests run: 3, Failures: 1, Errors: 0, Skipped: 0, Time elapsed: 0.123 sec <<< FAILURE!
	Running com.onefoursix.wordcount.WordCountReducerTest
	12/09/24 10:20:12 ERROR mrunit.TestDriver: Missing expected output (zebra, 2) at position 0.
	12/09/24 10:20:12 ERROR mrunit.TestDriver: Received unexpected output (horse, 2) at position 0.
	Tests run: 4, Failures: 1, Errors: 0, Skipped: 0, Time elapsed: 0.069 sec <<< FAILURE!

	Results :

	Failed tests:   
	  testMapperWithZebrasAndHorses(com.onefoursix.wordcount.WordCountMapperTest): 2 Error(s): (Matched expected output (horse, 1) but at incorrect position 2 (expected position 1), Matched expected output (zebra, 1) but at incorrect position 1 (expected position 2))
	  testMapReduceWithLizardsAndGiats(com.onefoursix.wordcount.WordCountMapReduceTest): 3 Error(s): (Missing expected output (hyena, 1) at position 1., Matched expected output (lizard, 2) but at incorrect position 1 (expected position 0), Received unexpected output (goat, 1) at position 0.)
	  testReducerWithZebras(com.onefoursix.wordcount.WordCountReducerTest): 2 Error(s): (Missing expected output (zebra, 2) at position 0., Received unexpected output (horse, 2) at position 0.)

	Tests run: 10, Failures: 3, Errors: 0, Skipped: 0


And since the MRUnit tests failed, the MapReduce job's jar file was not created.


###Running Maven and MRUnit Tests within Eclipse


If you want to use Maven within Eclipse, open a workspace, switch to the Java persepctive and choose Import | Maven | Existing Maven Project, and point to the project dir's pom.xml.  You can build the project and create the jar file by right clicking on the pom.xml and choosing Run As... Maven Install:



You will  see your test output on the console. You can also right click the test package and choose "Run As... JUnit" to run the tests using the JUnit tooling within Eclipse.  Here we see one of the tests has failed:


