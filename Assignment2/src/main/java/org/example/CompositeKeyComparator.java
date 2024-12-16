package org.example;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class CompositeKeyComparator extends WritableComparator {
    protected CompositeKeyComparator(){
        super(CompositeKey.class, true);
    }

    @Override
    public int compare(WritableComparable w1, WritableComparable w2){
        CompositeKey k1 = (CompositeKey) w1;
        CompositeKey k2 = (CompositeKey) w2;
        return k1.compareTo(k2);
    }

}
