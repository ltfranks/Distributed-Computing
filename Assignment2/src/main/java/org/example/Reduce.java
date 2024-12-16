package org.example;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Reduce {
    public static class reduce extends Reducer<Text, IntWritable,Text,IntWritable> {
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
    public static class SecondarySortReducer extends org.apache.hadoop.mapreduce.Reducer<CompositeKey, Text, Text, Text> {
        @Override
        protected void reduce(CompositeKey key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            String result="";
            for (Text value : values) {
                result += (value.toString()+",");
            }
            result = result.substring(0, result.length()-1);
            context.write(new Text(key.getName()), new Text(result));
        }
    }
}
