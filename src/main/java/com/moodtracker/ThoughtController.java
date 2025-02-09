package com.moodtracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        Thought createdThought = thoughtService.createThought(thought);
        return ResponseEntity.ok(createdThought);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Thought>> getThoughtsByUser(@PathVariable Long userId) {
        // Fetch user by ID (you’ll need a UserService for this)
        User user = userService.getUserById(userId);
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
}