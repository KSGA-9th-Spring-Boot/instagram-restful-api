package org.ksga.springboot.springsecuritydemo.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.ksga.springboot.springsecuritydemo.model.Post;
import org.ksga.springboot.springsecuritydemo.model.auth.User;
import org.ksga.springboot.springsecuritydemo.repository.provider.PostFilter;
import org.ksga.springboot.springsecuritydemo.repository.provider.PostProvider;
import org.ksga.springboot.springsecuritydemo.utils.Paging;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostRepository {

    @Insert("INSERT INTO posts (caption, image, user_id) VALUES (#{caption}, #{image}, #{owner.id})")
    @Options(keyColumn = "id", keyProperty = "id", useGeneratedKeys = true)
    boolean create(Post post);

    @Select("SELECT * FROM posts p INNER JOIN users u ON p.user_id = u.id")
    @Results({
            @Result(property = "numberOfLikes", column = "number_of_likes"),
            @Result(property = "owner", column = "user_id", one = @One(select = "findUserById"))
    })
    List<Post> findAllPosts();

    @Select("SELECT * FROM posts p user_id = #{userId}")
    @Results({
            @Result(property = "numberOfLikes", column = "number_of_likes")
    })
    List<Post> findPostsByUserId(Long userId);


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
    @Results({
            @Result(property = "numberOfLikes", column = "number_of_likes"),
            @Result(property = "owner", column = "user_id", one = @One(select = "findUserById"))
    })
    List<Post> findPostByFilter(@Param("filter") PostFilter filter, @Param("paging") Paging paging);

    @SelectProvider(type = PostProvider.class, method = "countAllPostsByFilter")
    int countAllPostsByFilter(@Param("filter") PostFilter filter);

    //    @Update("UPDATE posts SET caption = #{caption}, image = #{image} WHERE id = #{id}")
    @UpdateProvider(type = PostProvider.class, method = "updatePost")
    boolean update(@Param("post") Post post);

    @Update("UPDATE posts SET number_of_likes = #{numberOfLikes} WHERE id = #{id}")
    boolean setNumberOfLikes(Post post);

    @Insert("INSERT INTO users_like_posts (user_id, post_id) VALUES (#{userId}, #{postId})")
    boolean likePost(Long userId, Long postId);

    @Delete("DELETE FROM users_like_posts WHERE user_id = #{userId} AND post_id = #{postId}")
    boolean dislikePost(Long userId, Long postId);
}
