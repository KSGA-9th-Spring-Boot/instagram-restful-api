package org.ksga.springboot.springsecuritydemo.controller.rest;

import org.ksga.springboot.springsecuritydemo.payload.dto.PostDto;
import org.ksga.springboot.springsecuritydemo.payload.request.LikeType;
import org.ksga.springboot.springsecuritydemo.payload.request.PostLikeRequest;
import org.ksga.springboot.springsecuritydemo.payload.request.PostRequest;
import org.ksga.springboot.springsecuritydemo.payload.response.Response;
import org.ksga.springboot.springsecuritydemo.repository.provider.PostFilter;
import org.ksga.springboot.springsecuritydemo.security.service.UserDetailsImpl;
import org.ksga.springboot.springsecuritydemo.service.PostService;
import org.ksga.springboot.springsecuritydemo.utils.CurrentUser;
import org.ksga.springboot.springsecuritydemo.utils.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

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

    @GetMapping("/filter")
    public Response<List<PostDto>> findPostsByFilter(PostFilter postFilter,
                                                     int page,
                                                     int limit) {
        try {
            Paging paging = new Paging();
            paging.setLimit(limit);
            paging.setPage(page);
            List<PostDto> posts = postService.findPostByFilter(postFilter, paging);
            System.out.println(posts);
            return Response
                    .<List<PostDto>>ok()
                    .setPayload(posts);
        } catch (Exception ex) {
            ex.printStackTrace();
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

    @PutMapping("/{id}/update")
    public Response<PostDto> updatePost(@PathVariable Long id,
                                        @RequestBody PostRequest postRequest,
                                        @ApiIgnore @CurrentUser UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return Response
                    .<PostDto>exception()
                    .setErrors("You cannot update this post. Please login first.");
        } else {
            PostDto postDto = postService.findPostById(id);
            postDto.setCaption(postRequest.getCaption());
            postDto.setImage(postRequest.getImage());
            if (postDto.isOwner()) {
                boolean updated = postService.update(postDto);
                if (updated) {
                    return Response
                            .<PostDto>ok()
                            .setPayload(postDto);
                } else {
                    return Response
                            .<PostDto>ok()
                            .setErrors("Update Failed.");
                }
            }
            return Response
                    .<PostDto>ok()
                    .setErrors("Update Failed.");
        }
    }

    @PatchMapping("/likes")
    public Response<PostDto> likePost(PostLikeRequest postLikeRequest,
                                      @ApiIgnore @CurrentUser UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return Response
                    .<PostDto>exception()
                    .setErrors("You cannot like this post. Please login first.");
        } else {
            try {
                PostDto postDto = postService.findPostById(postLikeRequest.getId());
                boolean liked = postService.likePost(userDetails.getId(), postLikeRequest.getId());
                if (liked) {
                    if (postLikeRequest.getLikeType().name().equals(LikeType.LIKE.name())) {
                        postDto.setNumberOfLikes(postDto.getNumberOfLikes() + 1);
                    } else {
                        postDto.setNumberOfLikes(postDto.getNumberOfLikes() - 1);
                    }
                    boolean result = postService.setNumberOfLikes(postDto);
                    if (result) {
                        return Response
                                .<PostDto>ok()
                                .setPayload(postDto);
                    } else {
                        return Response
                                .notFound();
                    }
                } else {
                    return Response
                            .exception();
                }
            } catch (Exception ex) {
                return Response
                        .<PostDto>exception()
                        .setErrors(ex.getMessage());
            }
        }
    }

}
