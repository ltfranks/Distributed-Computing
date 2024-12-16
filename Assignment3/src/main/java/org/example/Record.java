package org.example;

public class Record implements Comparable<Record> {
    private String className;
    private int weight;

    public Record(String className, int weight) {
        this.className = className;
        this.weight = weight;
    }

    public String toString() {
        return className + "," + weight;
    }

    public int compareTo(Record other) { //descending order
        if (this.weight > other.weight) {
            return -1;
        }
        if (this.weight < other.weight) {
            return 1;
        }
        return 0;
    }
}
