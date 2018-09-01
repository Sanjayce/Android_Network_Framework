package com.sanjayce.rxjava_retrofit.entity;

import java.util.List;

/**
 *  json数据最外层目录
 */
public class HttpResult {
    private int count;

    private int start;

    private int total;

    private List<Subject> subjects ;

    private String title;


    public void setCount(int count){
        this.count = count;
    }
    public int getCount(){
        return this.count;
    }
    public void setStart(int start){
        this.start = start;
    }
    public int getStart(){
        return this.start;
    }
    public void setTotal(int total){
        this.total = total;
    }
    public int getTotal(){
        return this.total;
    }
    public void setSubjects(List<Subject> subjects){
        this.subjects = subjects;
    }
    public List<Subject> getSubjects(){
        return this.subjects;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("count=" + count)
                .append("\n")
                .append("start=" + start)
                .append("\n")
                .append( "total=" + total)
                .append("\n")
                .append("title='" + title)
                .append("\n")
                .append("subjects=" + subjects.toString());

        return buffer.toString();
    }
}
