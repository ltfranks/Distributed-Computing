package org.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Main {
    // use map/reduce to find the total sales for each day
    // Map -> sort -> combine/reduce each day
    public static void main(String[] args) throws Exception{
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Sales per Day");
        job.setJarByClass(Main.class);

        FileInputFormat.addInputPath(job, new Path("Input"));
        FileOutputFormat.setOutputPath(job, new Path("Output"));

        job.setMapperClass(Map.DateMapper.class);
        job.setCombinerClass(Reduce.DateReducer.class);
        job.setReducerClass(Reduce.DateReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        System.exit(job.waitForCompletion(true)? 0 : 1);


    }
}