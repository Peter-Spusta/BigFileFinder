package com.example.bigfilefinder;

public class HeapFile implements Comparable<HeapFile> {
    private String name;
    private String path;
    private long size;

    @Override
    public int compareTo(HeapFile heapFile) {
        return this.getSize() < heapFile.getSize() ? -1 : this.getSize() > heapFile.getSize() ? 1 : 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
