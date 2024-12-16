package org.example;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.TreeSet;

public class CourseMap extends Mapper<LongWritable, Text, NullWritable, Text> {
    public static final int DEFAULT_N = 10;
    private int n = DEFAULT_N;
    private TreeSet<Record> top = new TreeSet<>();
    // Q1 Input2: (className(string), difficulty(int))
    public void courseMapper(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString().trim();
        String[] tokens = line.split(",");
        int weight = Integer.parseInt(tokens[1]);
        top.add(new Record(tokens[0], weight));
        //keep only top n
        if (top.size() > n) {
            top.remove(top.last());
        }
    }

    //sets up and writes top N
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        this.n = context.getConfiguration().getInt("N", DEFAULT_N);
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        for (Record r : top) {
            context.write(NullWritable.get(),new Text(r.toString()));
        }
    }

}
