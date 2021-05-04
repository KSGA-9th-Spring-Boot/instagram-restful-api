package org.ksga.springboot.springsecuritydemo.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.ksga.springboot.springsecuritydemo.model.Post;
import org.ksga.springboot.springsecuritydemo.model.auth.User;
import org.ksga.springboot.springsecuritydemo.repository.provider.PostFilter;
import org.ksga.springboot.springsecuritydemo.repository.provider.PostProvider;
import org.ksga.springboot.springsecuritydemo.utils.Paging;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostRepository {

    @Select("SELECT * FROM posts p INNER JOIN users u ON p.user_id = u.id")
    @Results({
            @Result(property = "numberOfLikes", column = "number_of_likes"),
            @Result(property = "owner", column = "user_id", one = @One(select = "findUserById"))
    })
    List<Post> findAllPosts();

    @Select("SELECT * FROM users WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
    })
    User findUserById(Long id);

    @Select("SELECT * FROM posts WHERE id = #{id}")
    @Results({
            @Result(property = "numberOfLikes", column = "number_of_likes"),
            @Result(property = "owner", column = "user_id", one = @One(select = "findUserById"))
    })
    Post findPostById(Long id);

    @SelectProvider(type = PostProvider.class, method = "findPostsByFilter")
    List<Post> findPostByFilter(@Param("filter") PostFilter filter, @Param("paging") Paging paging);

    @SelectProvider(type = PostProvider.class, method = "countAllPostsByFilter")
    Long countAllPostsByFilter(@Param("filter") PostFilter filter);

    @Update("UPDATE posts SET number_of_likes = #{numberOfLikes} WHERE id = #{id}")
    boolean update(Post post);

    @Insert("INSERT INTO users_like_posts (user_id, post_id) VALUES (#{userId}, #{postId})")
    boolean likePost(Long userId, Long postId);
}
