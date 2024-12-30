package com.example.carproject.models;

import java.time.LocalDateTime;

public class User {
    private int id;
    private String name;
    private String surname;
    private String nickname;
    private LocalDateTime creationTimestamp;
    private int points;

    public User(int id, String name, String surname, String nickname, LocalDateTime creationTimestamp, int points){
        this.id = id;
        this. name =name;
        this. surname = surname;
        this.nickname = nickname;
        this.creationTimestamp = creationTimestamp;
        this.points = points;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public String getNickname(){
        return nickname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setNickname(String nick){
        this.nickname = nick;
    }
    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(LocalDateTime creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }



}

