package org.ksga.springboot.springsecuritydemo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.ksga.springboot.springsecuritydemo.model.auth.User;
import org.ksga.springboot.springsecuritydemo.repository.provider.AccountProvider;

@Mapper
public interface AccountRepository {

    @UpdateProvider(type = AccountProvider.class, method = "updateAccount")
    boolean updateAccount(@Param("user") User user);

    @Update("UPDATE users SET is_account_closed = true WHERE id = #{id}")
    boolean closeAccount(Long id);

    @Select("SELECT * FROM users WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
    })
    User findUserById(Long id);

}
