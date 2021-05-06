package org.ksga.springboot.springsecuritydemo.service;

import org.ksga.springboot.springsecuritydemo.payload.dto.UserDto;

public interface AccountService {
    boolean updateAccount(UserDto userDto);
    boolean closeAccount(Long id);
    UserDto findUserById(Long id);
}
