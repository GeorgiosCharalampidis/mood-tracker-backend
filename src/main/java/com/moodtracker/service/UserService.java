package com.moodtracker.service;

import com.moodtracker.exception.UserNotFoundException;
import com.moodtracker.model.Thought;
import com.moodtracker.model.User;
import com.moodtracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AiService aiService;

    public UserService(UserRepository userRepository, AiService aiService) {
        this.userRepository = userRepository;
        this.aiService = aiService;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Thought> getThoughtsByUser(User user) {
        return user.getThoughts();
    }

    public User updateUser(String username, User userDetails) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User " + username + " not found!");
        }
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        return userRepository.save(user);
    }

    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User " + username + " not found!");
        }
        userRepository.delete(user);
    }

    public String getDeepSeekThoughts(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User " + username + " not found!");
        }
        System.out.println("Getting thoughts for user: " + user.getUsername());
        StringBuilder thoughtsBuilder = new StringBuilder();
        user.getThoughts().forEach(thought -> thoughtsBuilder.append(thought.getText()).append("\n"));
        return aiService.getThoughtsFromDeepSeek(thoughtsBuilder.toString());
    }
}