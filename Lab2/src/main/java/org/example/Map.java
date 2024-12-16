package org.example;

import org.apache.hadoop.fs.Options;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Map {
    // input: 1, 1996/03/05, 14:42:54, 1, 1
    // (ID, YYYY/MM/DD, Time, storeID, customerID) -> (1, Date)
    public static class DateMapper extends Mapper<Object, Text, Text, IntWritable>{
        private final static IntWritable one = new IntWritable(1);
        private Text date = new Text();
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String dateRegex = "\\d{4}/\\d{2}/\\d{2}";
            Pattern pattern = Pattern.compile(dateRegex);
            Matcher matcher = pattern.matcher(value.toString());
            // extracting date from each line
            while (matcher.find()){
                date.set(matcher.group());
                // map: (date, 1)
                // making the value one, we'll just sum the ones
                // from common dates -> total sales for each day
                context.write(date, one);
            }
        }
    }
}
