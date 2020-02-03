# Map Reduce Project
**I have created this project with Maven**

**Word Count Map Reduce Application**

I have created this Application to test word count of a small file 
which is simple1.txt file , I have kept this file in input folder.

1. Curently I am using hadoop version 2.10.0.
So that i have used the following maven dependencies.

        <dependency>
            <groupId>org.apache.hadoop</groupId>
            <artifactId>hadoop-client</artifactId>
            <version>2.10.0</version>
        </dependency>

        <dependency>
            <groupId>org.apache.hadoop</groupId>
            <artifactId>hadoop-common</artifactId>
            <version>2.10.0</version>
        </dependency>

How to Run Application.
1. I have created Driver.java class .
Running on Eclipse : Right click on the Driver.java class then select Run button.

    a. Initially  Log4j is not added to the project , becuase of this Application will not show 
    the hadoop log to us.
    To get clarity on Log , we have to add log4j.property file to src folder.

2. To run the application as jar file
    
    a. first we have to create jar file with the help of maven 
    as :  mvn clean install , then it will create jar file in target location.
    
    b. Now have to open terminal and move to the target location and then type the following.
    
    When we observe the following we came to know the 
     
     First time application is failed becuase we have not created input folder in hdfs.
     Second time it started executing the appliaiton .
     
    
    nagaraju@nagaraju:~/workspace/theprogrammersbook/repository/hadoop/MapReduceApp/target$ hadoop jar MapReduceApp-1.0-SNAPSHOT.jar com.theprogrammersbook.mapreduce.Driver
    20/01/31 16:23:55 INFO mapreduce.Driver: Drive Code...
    20/01/31 16:24:00 INFO client.RMProxy: Connecting to ResourceManager at /0.0.0.0:8032
    20/01/31 16:24:04 WARN mapreduce.JobResourceUploader: Hadoop command-line option parsing not performed. Implement the Tool interface and execute your application with ToolRunner to remedy this.
    20/01/31 16:24:10 INFO mapreduce.JobSubmitter: Cleaning up the staging area /tmp/hadoop-yarn/staging/nagaraju/.staging/job_1580461059969_0002
    Exception in thread "main" org.apache.hadoop.mapreduce.lib.input.InvalidInputException: Input path does not exist: hdfs://localhost:9000/user/nagaraju/input/sample1.txt
    	at org.apache.hadoop.mapreduce.lib.input.FileInputFormat.singleThreadedListStatus(FileInputFormat.java:329)
    	at org.apache.hadoop.mapreduce.lib.input.FileInputFormat.listStatus(FileInputFormat.java:271)
    	at org.apache.hadoop.mapreduce.lib.input.FileInputFormat.getSplits(FileInputFormat.java:393)
    	at org.apache.hadoop.mapreduce.JobSubmitter.writeNewSplits(JobSubmitter.java:314)
    	at org.apache.hadoop.mapreduce.JobSubmitter.writeSplits(JobSubmitter.java:331)
    	at org.apache.hadoop.mapreduce.JobSubmitter.submitJobInternal(JobSubmitter.java:202)
    	at org.apache.hadoop.mapreduce.Job$11.run(Job.java:1570)
    	at org.apache.hadoop.mapreduce.Job$11.run(Job.java:1567)
    	at java.security.AccessController.doPrivileged(Native Method)
    	at javax.security.auth.Subject.doAs(Subject.java:422)
    	at org.apache.hadoop.security.UserGroupInformation.doAs(UserGroupInformation.java:1893)
    	at org.apache.hadoop.mapreduce.Job.submit(Job.java:1567)
    	at org.apache.hadoop.mapreduce.Job.waitForCompletion(Job.java:1588)
    	at com.theprogrammersbook.mapreduce.Driver.main(Driver.java:38)
    	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
    	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
    	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
    	at java.lang.reflect.Method.invoke(Method.java:498)
    	at org.apache.hadoop.util.RunJar.run(RunJar.java:244)
    	at org.apache.hadoop.util.RunJar.main(RunJar.java:158)
    
    nagaraju@nagaraju:~/workspace/theprogrammersbook/repository/hadoop/MapReduceApp/target$ hdfs -mkdir /user
    Unrecognized option: -mkdir
    Error: Could not create the Java Virtual Machine.
    Error: A fatal exception has occurred. Program will exit.
    nagaraju@nagaraju:~/workspace/theprogrammersbook/repository/hadoop/MapReduceApp/target$ hdfs dfs -mkdir /user
    nagaraju@nagaraju:~/workspace/theprogrammersbook/repository/hadoop/MapReduceApp/target$ hdfs dfs -mkdir /user/nagaraju
    nagaraju@nagaraju:~/workspace/theprogrammersbook/repository/hadoop/MapReduceApp/target$ cd ..
    nagaraju@nagaraju:~/workspace/theprogrammersbook/repository/hadoop/MapReduceApp$ ls
    input  MapReduceApp.iml  pom.xml  READM.md  src  target
    nagaraju@nagaraju:~/workspace/theprogrammersbook/repository/hadoop/MapReduceApp$ hdfs dfs -put input/*.txt input
    put: `input': No such file or directory
    nagaraju@nagaraju:~/workspace/theprogrammersbook/repository/hadoop/MapReduceApp$ hdfs dfs -mkdir input
    nagaraju@nagaraju:~/workspace/theprogrammersbook/repository/hadoop/MapReduceApp$ hdfs dfs -put input/*.txt input
    nagaraju@nagaraju:~/workspace/theprogrammersbook/repository/hadoop/MapReduceApp$ hdfs dfs -ls
    Found 1 items
    drwxr-xr-x   - nagaraju supergroup          0 2020-01-31 16:26 input
    nagaraju@nagaraju:~/workspace/theprogrammersbook/repository/hadoop/MapReduceApp$ hdfs dfs -ls input
    Found 2 items
    -rw-r--r--   1 nagaraju supergroup         64 2020-01-31 16:26 input/sample1.txt
    -rw-r--r--   1 nagaraju supergroup         69 2020-01-31 16:26 input/sample2.txt
    nagaraju@nagaraju:~/workspace/theprogrammersbook/repository/hadoop/MapReduceApp$ cd target/
    nagaraju@nagaraju:~/workspace/theprogrammersbook/repository/hadoop/MapReduceApp/target$ hadoop jar MapReduceApp-1.0-SNAPSHOT.jar com.theprogrammersbook.mapreduce.Driver
    20/01/31 16:27:47 INFO mapreduce.Driver: Drive Code...
    20/01/31 16:27:49 INFO client.RMProxy: Connecting to ResourceManager at /0.0.0.0:8032
    20/01/31 16:27:50 WARN mapreduce.JobResourceUploader: Hadoop command-line option parsing not performed. Implement the Tool interface and execute your application with ToolRunner to remedy this.
    20/01/31 16:27:51 INFO input.FileInputFormat: Total input files to process : 1
    20/01/31 16:27:52 INFO mapreduce.JobSubmitter: number of splits:1
    20/01/31 16:27:52 INFO Configuration.deprecation: yarn.resourcemanager.system-metrics-publisher.enabled is deprecated. Instead, use yarn.system-metrics-publisher.enabled
    20/01/31 16:27:53 INFO mapreduce.JobSubmitter: Submitting tokens for job: job_1580461059969_0003
    20/01/31 16:27:54 INFO conf.Configuration: resource-types.xml not found
    20/01/31 16:27:54 INFO resource.ResourceUtils: Unable to find 'resource-types.xml'.


