package org.ksga.springboot.springsecuritydemo.controller.rest;

import org.ksga.springboot.springsecuritydemo.payload.dto.CommentDto;
import org.ksga.springboot.springsecuritydemo.payload.dto.UserDto;
import org.ksga.springboot.springsecuritydemo.payload.request.CommentRequest;
import org.ksga.springboot.springsecuritydemo.payload.response.Response;
import org.ksga.springboot.springsecuritydemo.service.AccountService;
import org.ksga.springboot.springsecuritydemo.service.CommentService;
import org.ksga.springboot.springsecuritydemo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentRestController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/comments")
    public Response<CommentDto> insertComment(@RequestBody CommentRequest commentRequest) {
        UserDto userDto = accountService.findUserById(commentRequest.getUserId());
        CommentDto commentDto = new CommentDto()
                .setContent(commentRequest.getContent())
                .setPostId(commentRequest.getPostId())
                .setUser(userDto);
        commentService.insertComment(commentDto);
        return Response
                .<CommentDto>ok()
                .setPayload(commentDto);
    }

    @PostMapping("/replies")
    public Response<CommentDto> insertReply(@RequestBody CommentRequest commentRequest) {
        UserDto userDto = accountService.findUserById(commentRequest.getUserId());
        CommentDto commentDto = commentService.findByCommentId(commentRequest.getParentId())
                .setContent(commentRequest.getContent())
                .setPostId(commentRequest.getPostId())
                .setParentId(commentRequest.getParentId())
                .setUser(userDto);
        commentService.insertReply(commentDto);
        return Response
                .<CommentDto>ok()
                .setPayload(commentDto);
    }

    @GetMapping("/posts/{id}/comments")
    public Response<List<CommentDto>> findCommentsByPostId(@PathVariable("id") Long postId) {
        List<CommentDto> comments = commentService.findCommentsByPostId(postId);
        if (comments == null || comments.isEmpty()) {
            return Response
                    .<List<CommentDto>>notFound()
                    .setErrors("Comments Not Found with Post Id " + postId);
        }
        return Response.<List<CommentDto>>ok().setPayload(comments);
    }

    @GetMapping("/comments/{id}/replies")
    public Response<List<CommentDto>> findRepliesByCommentId(@PathVariable("id") Long commentId) {
        List<CommentDto> comments = commentService.findRepliesByCommentId(commentId);
        if (comments == null || comments.isEmpty()) {
            return Response
                    .<List<CommentDto>>notFound()
                    .setErrors("Comments Not Found with Comment Id " + commentId);
        }
        return Response.<List<CommentDto>>ok().setPayload(comments);
    }

}
