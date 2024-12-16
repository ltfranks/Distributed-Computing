package org.example;

import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.io.Text;

public class NaturalKeyPartitioner extends Partitioner<CompositeKey, Text> {

    @Override
    public int getPartition(CompositeKey key, Text val, int numPartitions) {
        int hash = key.getName().hashCode();
        int partition = Math.abs(hash % numPartitions);
        return partition;
    }
}
