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

public class Q1 {

    public static class mapper extends Mapper<Object, Text, Text, IntWritable>{
        private final static IntWritable one = new IntWritable(1);
        private Text number = new Text();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String numberRegex = "[0-9]+"; // any integer from 0-9
            Pattern pattern = Pattern.compile(numberRegex);

            Matcher matcher = pattern.matcher(value.toString());
            while (matcher.find()) {
                // extracting each int from input
                number.set(matcher.group());
                // final mapping
                context.write(number, one);
            }
        }
    }

    public static class reduce extends Reducer<Text,IntWritable,Text,IntWritable> {
        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int count = 0;
            // for each value in map
            for (IntWritable val : values) {
                int keyInt = Integer.parseInt(key.toString());
                // (inputValue, count)
                if (keyInt % 3 == 0) {
                    count += val.get();
                }
            }
            result.set(count);
            context.write(key, result);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Divisible By 3");
        job.setJarByClass(Q1.class);
        job.setMapperClass(mapper.class); // mapper
        job.setCombinerClass(reduce.class); // combiner
        job.setReducerClass(reduce.class); // reducer
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path("input1"));
        FileOutputFormat.setOutputPath(job, new Path("output1"));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}