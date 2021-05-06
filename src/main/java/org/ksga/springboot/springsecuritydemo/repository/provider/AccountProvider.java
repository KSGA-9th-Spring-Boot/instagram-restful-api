package org.ksga.springboot.springsecuritydemo.repository.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.ksga.springboot.springsecuritydemo.model.auth.User;

public class AccountProvider {
    public String updateAccount(@Param("user") User user) {
        return new SQL() {{
            UPDATE("users");
            if (user.getFullname() != null) {
                SET("fullname = #{user.fullname}");
            }
            if (user.getImageUrl() != null) {
                SET("profile = #{user.imageUrl}");
            }
            if (user.getUsername() != null) {
                SET("username = #{user.username}");
            }
            WHERE("id = #{user.id}");
        }}.toString();
    }
}
