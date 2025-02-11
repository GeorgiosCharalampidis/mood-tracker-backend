package com.moodtracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "thoughts")
public class Thought {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    @Size(min = 1, max = 1000) // Limit text length
    private String text;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Default constructor (required by JPA)
    public Thought() {
    }

    // Parameterized constructor
    public Thought(String text, LocalDate date, User user) {
        this.text = text;
        this.date = date;
        this.user = user;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // toString() method (optional, for debugging)
    @Override
    public String toString() {
        return "Thought{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", user=" + user +
                '}';
    }
}