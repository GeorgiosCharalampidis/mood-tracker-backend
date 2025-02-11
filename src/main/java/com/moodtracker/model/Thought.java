package com.moodtracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "thoughts")
public class Thought {

    // Getters and Setters
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