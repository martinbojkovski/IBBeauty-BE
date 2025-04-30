package com.mua.ibbeauty.service;

import com.mua.ibbeauty.model.DTO.PostRequestDTO;
import com.mua.ibbeauty.model.DTO.PostResponseDTO;
import com.mua.ibbeauty.model.Post;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostService {

    List<Post> getAllPosts();

    Page<PostResponseDTO> getAllPostsPageable(Pageable pageable);

    Post getPost(UUID Id);

    Post addPost(PostRequestDTO postRequestDTO);

    Post deletePost(UUID Id);

    Post editPost(PostRequestDTO postRequestDTO);
}
