package org.ksga.springboot.springsecuritydemo.repository.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.ksga.springboot.springsecuritydemo.model.Post;
import org.ksga.springboot.springsecuritydemo.utils.Paging;

public class PostProvider {

    public String findPostsByFilter(@Param("filter") PostFilter filter, @Param("paging") Paging paging) {
        String sql = new SQL() {{
            SELECT("*");
            FROM("posts p");
//            INNER_JOIN("users_like_posts up ON p.id = up.post_id");
            INNER_JOIN("users u ON u.id = p.user_id");
            if (filter.getCaption() != null) {
                WHERE("p.caption ILIKE '%' || #{filter.caption} || '%'");
            }
            if (filter.getUsername() != null) {
                WHERE("u.username ILIKE '%' || #{filter.username} || '%'");
            }
            LIMIT(paging.getLimit());
            OFFSET(paging.getOffset());
        }}.toString();
        System.out.println(sql);
        return sql;
    }

    public String updatePost(@Param("post") Post post) {
        return new SQL() {{
            UPDATE("posts");
            if (post.getCaption() != null) {
                SET("caption = #{post.caption}");
            }
            if (post.getImage() != null) {
                SET("image = #{post.image}");
            }
            WHERE("id = #{post.id}");
        }}.toString();
    }

    public String countAllPostsByFilter(PostFilter filter) {
        return new SQL() {{
            SELECT("COUNT(p.id)");
            FROM("posts p");
            INNER_JOIN("users u ON u.id = p.user_id");
            if (filter.getCaption() != null) {
                WHERE("p.caption ILIKE '%' || #{filter.caption} || '%'");
            }
            if (filter.getUsername() != null) {
                WHERE("u.username ILIKE '%' || #{filter.username} || '%'");
            }
        }}.toString();
    }

}
