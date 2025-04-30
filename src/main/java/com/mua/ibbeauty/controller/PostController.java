package com.mua.ibbeauty.controller;

import com.mua.ibbeauty.model.DTO.PostRequestDTO;
import com.mua.ibbeauty.model.DTO.PostResponseDTO;
import com.mua.ibbeauty.model.Post;
import com.mua.ibbeauty.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@CrossOrigin(origins = "http://localhost:3000") // Allow only the frontend origin
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(){
        List<Post> result = postService.getAllPosts();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable UUID postId){
        Post result = postService.getPost(postId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<PostResponseDTO>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "datePublished") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(postService.getAllPostsPageable(pageable));
    }

    @PostMapping("/insert")
    public ResponseEntity<Post> addPost(@RequestParam("text") String text,
                                        @RequestParam("photo") MultipartFile photo) throws IOException {

        byte[] photoBytes = photo.getBytes();

        PostRequestDTO postRequestDTO = new PostRequestDTO(null, photoBytes, text);

        Post post = postService.addPost(postRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @PatchMapping("/edit")
    public ResponseEntity<Post> editPost(@RequestParam("text") String text,
                                         @RequestParam("photo") MultipartFile photo,
                                         @RequestParam("Id") UUID Id) throws IOException {

        byte[] photoBytes = photo.getBytes();

        PostRequestDTO postRequestDTO = new PostRequestDTO(Id, photoBytes, text);

        Post response = postService.editPost(postRequestDTO);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<Post> deletePost(@PathVariable UUID Id){
        Post response = postService.deletePost(Id);

        return ResponseEntity.ok(response);
    }


}
