package com.example.carproject.models;
import java.time.LocalDateTime;

public class BookRental {
    private int id;
    private int bookId;
    private int userId;
    private LocalDateTime rentTimestamp;
    private LocalDateTime deadline;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getRentTimestamp() {
        return rentTimestamp;
    }

    public void setRentTimestamp(LocalDateTime rentTimestamp) {
        this.rentTimestamp = rentTimestamp;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
