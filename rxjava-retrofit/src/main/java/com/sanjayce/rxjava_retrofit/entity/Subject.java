package com.sanjayce.rxjava_retrofit.entity;

import java.util.List;

/**
 *  json数据内部子目录
 */
public class Subject {
    private Rating rating;

    private List<String> genres ;

    private String title;

    private List<Casts> casts ;

    private int collect_count;

    private String original_title;

    private String subtype;

    private List<Directors> directors ;

    private String year;

    private Images images;

    private String alt;

    private String id;

    public void setRating(Rating rating){
        this.rating = rating;
    }
    public Rating getRating(){
        return this.rating;
    }
    public void setString(List<String> genres){
        this.genres = genres;
    }
    public List<String> getString(){
        return this.genres;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setCasts(List<Casts> casts){
        this.casts = casts;
    }
    public List<Casts> getCasts(){
        return this.casts;
    }
    public void setCollect_count(int collect_count){
        this.collect_count = collect_count;
    }
    public int getCollect_count(){
        return this.collect_count;
    }
    public void setOriginal_title(String original_title){
        this.original_title = original_title;
    }
    public String getOriginal_title(){
        return this.original_title;
    }
    public void setSubtype(String subtype){
        this.subtype = subtype;
    }
    public String getSubtype(){
        return this.subtype;
    }
    public void setDirectors(List<Directors> directors){
        this.directors = directors;
    }
    public List<Directors> getDirectors(){
        return this.directors;
    }
    public void setYear(String year){
        this.year = year;
    }
    public String getYear(){
        return this.year;
    }
    public void setImages(Images images){
        this.images = images;
    }
    public Images getImages(){
        return this.images;
    }
    public void setAlt(String alt){
        this.alt = alt;
    }
    public String getAlt(){
        return this.alt;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "rating=" + rating.toString() +
                ", genres=" + genres +
                ", title='" + title + '\'' +
                ", casts=" + casts.toString() +
                ", collect_count=" + collect_count +
                ", original_title='" + original_title + '\'' +
                ", subtype='" + subtype + '\'' +
                ", directors=" + directors.toString() +
                ", year='" + year + '\'' +
                ", images=" + images.toString() +
                ", alt='" + alt + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
