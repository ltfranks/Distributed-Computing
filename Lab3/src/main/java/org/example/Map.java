package org.example;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Map extends Mapper<LongWritable, Text, Text, Text> {
    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] parts = line.split(" "); // Assuming space as delimiter; adjust as necessary

        if (parts.length >= 3) {
            String date = parts[0];
            String time = parts[1];
            String saleID = parts[2];
            context.write(new Text(date + " " + time), new Text(saleID));
        }
    }
}