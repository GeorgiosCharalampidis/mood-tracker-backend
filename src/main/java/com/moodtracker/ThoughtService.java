package com.moodtracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ThoughtService {

    @Autowired
    private ThoughtRepository thoughtRepository;

    public Thought createThought(Thought thought) {
        return thoughtRepository.save(thought);
    }

    public List<Thought> getThoughtsByUser(User user) {
        return thoughtRepository.findByUser(user);
    }

    public List<Thought> getThoughtsByUserAndDateRange(User user, LocalDate startDate, LocalDate endDate) {
        return thoughtRepository.findByUserAndDateBetween(user, startDate, endDate);
    }
}