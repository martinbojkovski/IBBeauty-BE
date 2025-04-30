package com.mua.ibbeauty.converters;

import com.mua.ibbeauty.model.DTO.PostResponseDTO;
import com.mua.ibbeauty.model.Post;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class PostConverter {

    public PostResponseDTO toPostResponseDTO(Post post) {
        LocalDate date = post.getDatePublished().toLocalDate();
        return new PostResponseDTO(post.getId(), post.getPhoto(), post.getText(), date);
    }
}
