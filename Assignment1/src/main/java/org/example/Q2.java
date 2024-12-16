package org.example;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Q2 {

    public static class mapper2 extends Mapper<Object, Text, Text, IntWritable>{
        private Text date = new Text();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String valueAsString = value.toString().trim();
            String[] tokens = valueAsString.split("\\s+");
            if (tokens.length != 2){
                return;
            }
            // (##/##/##, ##)
            context.write(new Text(tokens[0]), new IntWritable(Integer.parseInt(tokens[1])));
        }
    }
// finds the max temperature for each day
    public static class reduce2 extends Reducer<Text,IntWritable,Text,IntWritable> {
        public void reduce(Text date, Iterable<IntWritable> temperatures, Context context) throws IOException, InterruptedException {
            int max = Integer.MIN_VALUE;
            for(IntWritable temperature : temperatures){
                max = Math.max(temperature.get(), max);
            }
            context.write(date, new IntWritable(max));
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Max Temp");
        job.setJarByClass(Q2.class);
        job.setMapperClass(mapper2.class); // mapper
        job.setCombinerClass(reduce2.class); // combiner
        job.setReducerClass(reduce2.class); // reducer
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path("input2"));
        FileOutputFormat.setOutputPath(job, new Path("output2"));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}