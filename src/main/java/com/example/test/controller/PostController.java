package com.example.game.controller;

import com.example.game.entity.Post;
import com.example.game.entity.User;
import com.example.game.exception.ResourceNotFoundException;
import com.example.game.repository.PostRepository;
import com.example.game.repository.UserRepository;
import com.example.game.request.PostRequest;
import com.example.game.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// PostController.java
@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostRequest request) {
        User user = userRepository.findById(request.getUserid())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setUser(user);

        return ResponseEntity.ok(postRepository.save(post));
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + id));
        return ResponseEntity.ok(post);
    }

}

