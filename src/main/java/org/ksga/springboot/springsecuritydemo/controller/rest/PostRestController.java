package org.ksga.springboot.springsecuritydemo.controller.rest;

import org.ksga.springboot.springsecuritydemo.model.Post;
import org.ksga.springboot.springsecuritydemo.payload.dto.PostDto;
import org.ksga.springboot.springsecuritydemo.payload.request.LikeType;
import org.ksga.springboot.springsecuritydemo.payload.request.PostLikeRequest;
import org.ksga.springboot.springsecuritydemo.payload.response.Response;
import org.ksga.springboot.springsecuritydemo.security.service.UserDetailsImpl;
import org.ksga.springboot.springsecuritydemo.service.PostService;
import org.ksga.springboot.springsecuritydemo.utils.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {

    @Autowired
    private PostService postService;

    @GetMapping
    public Response<List<PostDto>> findAllPosts() {
        try {
            return Response
                    .<List<PostDto>>ok()
                    .setPayload(postService.findAll());
        } catch (Exception ex) {
            return Response
                    .<List<PostDto>>exception()
                    .setErrors(ex.getMessage());
        }
    }

    @GetMapping("/{id}/view")
    public Response<PostDto> findPostById(@PathVariable long id) {
        PostDto postDto = postService.findPostById(id);
        return Response
                .<PostDto>ok()
                .setPayload(postDto);
    }

    @DeleteMapping("/{id}/delete")
    public Response<PostDto> deletePostById(@PathVariable long id) {
        PostDto postDto = postService.deletePostById(id);
        return Response
                .<PostDto>ok()
                .setPayload(postDto);
    }

    @PostMapping("/likes")
    public Response<PostDto> likePost(PostLikeRequest postLikeRequest, @CurrentUser UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return Response
                    .<PostDto>exception()
                    .setErrors("You cannot like this post. Please login first.");
        } else {
            PostDto postDto = postService.findPostById(postLikeRequest.getId());
            postService.likePost(userDetails.getId(), postLikeRequest.getId());
            if (postLikeRequest.getLikeType().name().equals(LikeType.LIKE.name())) {
                postDto.setNumberOfLikes(postDto.getNumberOfLikes() + 1);
            } else {
                postDto.setNumberOfLikes(postDto.getNumberOfLikes() - 1);
            }
            boolean result = postService.update(postDto);
            if (result) {
                return Response
                        .<PostDto>ok()
                        .setPayload(postDto);
            } else {
                return Response
                        .notFound();
            }
        }
    }

}
