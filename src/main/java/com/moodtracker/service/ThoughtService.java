package com.moodtracker.service;

import com.moodtracker.model.Thought;
import com.moodtracker.model.User;
import com.moodtracker.repository.ThoughtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ThoughtService {

    @Autowired
    private ThoughtRepository thoughtRepository;

    @Autowired
    private com.moodtracker.service.UserService userService;

    public Thought createThoughtForUser(String username, Thought thought) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        } else {
            System.out.println("User found: " + user.getUsername());
        }

        if (thought != null) System.out.println("Thought: " + thought.toString());

        else System.out.println("Thought is null");

        if (thought.getText() == null || thought.getText().isEmpty()) {
            throw new RuntimeException("Thought text cannot be empty");
        }

        thought.setUser(user);
        thought.setDate(LocalDate.now()); // Auto-set the date
        return thoughtRepository.save(thought);
    }


    public List<Thought> getThoughtsByUser(User user) {
        return thoughtRepository.findByUser(user);
    }

    public List<Thought> getThoughtsByUserAndDateRange(User user, LocalDate startDate, LocalDate endDate) {
        return thoughtRepository.findByUserAndDateBetween(user, startDate, endDate);
    }

    public void deleteThought(Long thoughtId) {
        if (!thoughtRepository.existsById(thoughtId)) {
            throw new RuntimeException("Thought not found with ID: " + thoughtId);
        }
        thoughtRepository.deleteById(thoughtId);
    }

    public void deleteAllThoughts() {
        thoughtRepository.deleteAll();
    }


}