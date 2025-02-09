package com.moodtracker;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "thoughts")
public class Thought {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 500)
    @Column(nullable = false)
    private String text;

    @Column
    private Integer moodRating; // Mood rating on a scale of 1-5 (optional)

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Default constructor (required by JPA)
    public Thought() {
    }

    // Parameterized constructor
    public Thought(String text, Integer moodRating, LocalDate date, User user) {
        this.text = text;
        this.moodRating = moodRating;
        this.date = date;
        this.user = user;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getMoodRating() {
        return moodRating;
    }

    public void setMoodRating(Integer moodRating) {
        this.moodRating = moodRating;
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
                ", moodRating=" + moodRating +
                ", date=" + date +
                ", user=" + user +
                '}';
    }
}