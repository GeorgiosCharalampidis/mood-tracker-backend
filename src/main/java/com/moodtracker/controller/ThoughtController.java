package com.moodtracker.controller;

import com.moodtracker.service.ThoughtService;
import com.moodtracker.service.UserService;
import com.moodtracker.model.Thought;
import com.moodtracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/thoughts")
public class ThoughtController {

    @Autowired
    private ThoughtService thoughtService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Thought> createThought(@RequestBody Thought thought) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Thought createdThought = thoughtService.createThoughtForUser(username, thought);
        return ResponseEntity.ok(createdThought);
    }

    @GetMapping
    public ResponseEntity<List<Thought>> getMyThoughts() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        List<Thought> thoughts = thoughtService.getThoughtsByUser(user);
        return ResponseEntity.ok(thoughts);
    }

    @GetMapping("/user/{userName}")
    public ResponseEntity<List<Thought>> getThoughtsByUser(@PathVariable String userName) {
        // Fetch user by ID (you’ll need a UserService for this)
        User user = userService.getUserByUsername(userName);
        List<Thought> thoughts = thoughtService.getThoughtsByUser(user);
        return ResponseEntity.ok(thoughts);
    }

    @GetMapping("/user/{userId}/date-range")
    public ResponseEntity<List<Thought>> getThoughtsByUserAndDateRange(
            @PathVariable Long userId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        // Fetch user by ID
        User user = userService.getUserById(userId);
        List<Thought> thoughts = thoughtService.getThoughtsByUserAndDateRange(user, startDate, endDate);
        return ResponseEntity.ok(thoughts);
    }

    @DeleteMapping("/{thoughtId}")
    public ResponseEntity<Void> deleteThought(@PathVariable Long thoughtId) {
        thoughtService.deleteThought(thoughtId);
        return ResponseEntity.noContent().build(); // 204 No Content on success
    }

    // delete all thoughts
    @DeleteMapping
    public ResponseEntity<Void> deleteAllThoughts() {
        thoughtService.deleteAllThoughts();
        return ResponseEntity.noContent().build(); // 204 No Content on success
    }

}