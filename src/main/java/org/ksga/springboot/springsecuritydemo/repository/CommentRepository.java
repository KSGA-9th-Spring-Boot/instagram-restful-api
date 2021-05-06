package org.ksga.springboot.springsecuritydemo.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.ksga.springboot.springsecuritydemo.model.Comment;
import org.ksga.springboot.springsecuritydemo.model.Post;
import org.ksga.springboot.springsecuritydemo.model.auth.User;

import java.util.List;

@Mapper
public interface CommentRepository {

    @Insert("INSERT INTO comments (content, user_id, post_id) VALUES (#{content}, #{user.id}, #{post.id})")
    boolean insertComment(Comment comment);

    @Insert("INSERT INTO comments (content, parent_id, user_id, post_id) VALUES (#{content}, #{parentId}, #{user.id}, #{post.id})")
    boolean insertReply(Comment comment);

    @Select("SELECT * FROM comments WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "user", column = "user_id", one = @One(select = "findUserById")),
            @Result(property = "post", column = "post_id", one = @One(select = "findPostById"))
    })
    Comment findByCommentId(@Param("id") Long commentId);

    @Select("SELECT * FROM comments WHERE post_id = #{postId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "user", column = "user_id", one = @One(select = "findUserById")),
            @Result(property = "post", column = "post_id", one = @One(select = "findPostById")),
            @Result(property = "comments", column = "id", many = @Many(select = "findRepliesByCommentId"))
    })
    List<Comment> findCommentsByPostId(Long postId);

    @Select("SELECT id, username, fullname, profile FROM users WHERE id = #{id}")
    User findUserById(Long id);

    @Select("SELECT * FROM posts WHERE id = #{id}")
    @Results({
            @Result(property = "numberOfLikes", column = "number_of_likes")
    })
    Post findPostById(Long id);

    @Select("SELECT * FROM comments WHERE parent_id = #{commentId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "user", column = "user_id", one = @One(select = "findUserById")),
            @Result(property = "post", column = "post_id", one = @One(select = "findPostById"))
    })
    List<Comment> findRepliesByCommentId(Long commentId);

}
