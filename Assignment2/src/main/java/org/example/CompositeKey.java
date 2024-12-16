package org.example;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CompositeKey implements Writable, WritableComparable<CompositeKey> {

    private final Text name = new Text();
    private final Text id = new Text();
    private final Text grade = new Text();
// were sorting by name, then by grade so that's why
// the composite key is the name, grade
    public CompositeKey(){
    }
    public CompositeKey(String name, String id, String grade) {
        this.name.set(name);
        this.id.set(id);
        this.grade.set(grade);
    }

    @Override
    public void write(DataOutput output) throws IOException {
        name.write(output);
        id.write(output);
        grade.write(output);
    }

    @Override
    public void readFields(DataInput input) throws IOException {
        name.readFields(input);
        id.readFields(input);
        grade.readFields(input);
    }

    @Override
    public int compareTo(CompositeKey other) {
        int result = name.compareTo(other.name);
        // if names are the same then compare grades
        if (0 == result) {
            result = grade.compareTo(other.grade);
        }
        return result;
    }

    public String getName(){
        return name.toString();
    }
    public String getId(){
        return id.toString();
    }
    public String getGrade(){
        return grade.toString();
    }
}
