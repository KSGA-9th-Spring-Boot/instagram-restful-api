package org.ksga.springboot.springsecuritydemo.service;

import org.ksga.springboot.springsecuritydemo.payload.dto.PostDto;
import org.ksga.springboot.springsecuritydemo.repository.provider.PostFilter;
import org.ksga.springboot.springsecuritydemo.utils.Paging;

import java.util.List;

public interface PostService {
    PostDto findPostById(Long id);
    boolean update(PostDto postDto);
    boolean setNumberOfLikes(PostDto postDto);
    List<PostDto> findAll();
    List<PostDto> findPostByFilter(PostFilter filter, Paging paging);
    int countAllPostsByFilter(PostFilter filter);
    PostDto deletePostById(Long id);
    boolean likePost(Long userId, Long postId);
}
