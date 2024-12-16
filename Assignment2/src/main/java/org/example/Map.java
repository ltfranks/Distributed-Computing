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
    public static class mapper extends Mapper<Object, Text, Text, IntWritable> {
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
// input: John Back, 23, B, CSC366
    public static class SecondarySortMapper extends Mapper<LongWritable, Text, CompositeKey, Text>{
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] tokens = line.split(",");
            String name = tokens[0].trim();
            String id = tokens[1].trim();
            String grade = tokens[2].trim();
            String course = tokens[3].trim();
            // sort by name then grade.
            // map output: name, (grade, course)
            context.write(new CompositeKey(name, id, grade),
                            new Text("("+grade+","+course+")"));
        }
    }
}
