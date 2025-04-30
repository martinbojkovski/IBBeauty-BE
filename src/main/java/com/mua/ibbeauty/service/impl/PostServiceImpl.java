package com.mua.ibbeauty.service.impl;

import com.mua.ibbeauty.exceptions.PostNotFoundException;
import com.mua.ibbeauty.model.DTO.PostRequestDTO;
import com.mua.ibbeauty.model.DTO.PostResponseDTO;
import com.mua.ibbeauty.model.Post;
import com.mua.ibbeauty.repository.PostRepository;
import com.mua.ibbeauty.service.PostService;
import com.mua.ibbeauty.converters.PostConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Page<PostResponseDTO> getAllPostsPageable(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);

        List<PostResponseDTO> responseList = posts.stream()
                .map(PostConverter::toPostResponseDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(responseList, pageable, posts.getTotalElements());
    }

    @Override
    public Post getPost(UUID Id) {
        return postRepository.findById(Id).orElseThrow(() -> new PostNotFoundException(Id));
    }

    @Override
    public Post addPost(PostRequestDTO postRequestDTO) {
        Post post = new Post();
        post.setText(postRequestDTO.text());
        post.setPhoto(postRequestDTO.photo()); // Convert base64 to byte[]


        postRepository.save(post);

        return postRepository.findAll().getLast();
    }

    @Override
    public Post deletePost(UUID Id) {
        Post post = postRepository.findById(Id).orElseThrow(() -> new PostNotFoundException(Id));
        postRepository.deleteById(Id);

        return post;
    }

    @Override
    public Post editPost(PostRequestDTO postRequestDTO) {
        UUID Id = postRequestDTO.Id();
        Post post = postRepository.findById(Id).orElseThrow(() -> new PostNotFoundException(Id));

        post.setText(postRequestDTO.text() != null && !postRequestDTO.text().isEmpty() ? postRequestDTO.text() :
                post.getText());
        post.setPhoto(postRequestDTO.photo() != null ? postRequestDTO.photo() : post.getPhoto());

        postRepository.save(post);

        return post;
    }
}
