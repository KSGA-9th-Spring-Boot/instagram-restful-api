package org.ksga.springboot.springsecuritydemo.service.impl;

import org.ksga.springboot.springsecuritydemo.model.Post;
import org.ksga.springboot.springsecuritydemo.payload.dto.PostDto;
import org.ksga.springboot.springsecuritydemo.payload.mapper.PostMapper;
import org.ksga.springboot.springsecuritydemo.repository.PostRepository;
import org.ksga.springboot.springsecuritydemo.repository.provider.PostFilter;
import org.ksga.springboot.springsecuritydemo.service.PostService;
import org.ksga.springboot.springsecuritydemo.utils.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    @Override
    public PostDto findPostById(Long id) {
        return postMapper.postToPostDto(postRepository.findPostById(id));
    }

    @Override
    public boolean update(PostDto postDto) {
        Post post = postMapper.postDtoToPost(postDto);
        return postRepository.update(post);
    }

    @Override
    public boolean setNumberOfLikes(PostDto postDto) {
        Post post = postMapper.postDtoToPost(postDto);
        return postRepository.setNumberOfLikes(post);
    }

    @Override
    public PostDto deletePostById(Long id) {
        Post post = postRepository.findPostById(id);
        return postMapper.postToPostDto(post);
    }

    @Override
    public boolean likePost(Long userId, Long postId) {
        return postRepository.likePost(userId, postId);
    }

    @Override
    public List<PostDto> findAll() {
        return postMapper.postsToPostDtos(postRepository.findAllPosts());
    }

    @Override
    public List<PostDto> findPostByFilter(PostFilter filter, Paging paging) {
        paging.setTotalCount(countAllPostsByFilter(filter));
        List<Post> posts = postRepository.findPostByFilter(filter, paging);
        return postMapper.postsToPostDtos(posts);
    }

    @Override
    public int countAllPostsByFilter(PostFilter filter) {
        return postRepository.countAllPostsByFilter(filter);
    }
}
