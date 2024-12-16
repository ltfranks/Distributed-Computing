package org.example;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Reduce {
    public static class DateReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
        private IntWritable sum = new IntWritable();
        public void reduce(Text date, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int count = 0;
            // for each value in map
            for (IntWritable val : values){
                count += val.get();
            }
            sum.set(count);
            context.write(date, sum);
        }
    }
}
