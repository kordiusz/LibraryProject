package com.example.carproject.models;

import java.time.LocalDate;

public class Book {
    private int id;
    private String title;
    private String author;
    private int borrowCount;
    private LocalDate publishDate;
    private int rental_id;


    public Book(int id, String title, String author, int borrowCount, LocalDate publishDate, int rental_id){
        this. id = id;
        this.title =title;
        this.author = author;
        this.borrowCount = borrowCount;
        this.publishDate = publishDate;
        this.rental_id = rental_id;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getRental_id(){
        return rental_id;
    }
    public void setRental_id(int rental_id){
        this.rental_id = rental_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(int borrowCount) {
        this.borrowCount = borrowCount;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }
}

