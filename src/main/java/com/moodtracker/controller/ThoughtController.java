package com.moodtracker.controller;

import com.moodtracker.service.ThoughtService;
import com.moodtracker.service.UserService;
import com.moodtracker.model.Thought;
import com.moodtracker.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/thoughts")
public class ThoughtController {

    private final ThoughtService thoughtService;
    private final UserService userService;

    public ThoughtController(ThoughtService thoughtService, UserService userService) {
        this.thoughtService = thoughtService;
        this.userService = userService;
    }

    @PostMapping("/{userName}")
    public ResponseEntity<Thought> createThoughtForUser(@PathVariable String userName, @RequestBody Thought thought) {
        Thought createdThought = thoughtService.createThoughtForUser(userName, thought);
        return ResponseEntity.ok(createdThought);
    }

    @GetMapping("/{userName}")
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

    @GetMapping("/{username}/summary")
    public String getUserThoughtsSummary(@PathVariable String username) {
        return userService.getDeepSeekThoughts(username);
    }

}