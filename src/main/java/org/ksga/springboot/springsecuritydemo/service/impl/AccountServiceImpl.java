package org.ksga.springboot.springsecuritydemo.service.impl;

import org.ksga.springboot.springsecuritydemo.model.auth.User;
import org.ksga.springboot.springsecuritydemo.payload.dto.UserDto;
import org.ksga.springboot.springsecuritydemo.payload.mapper.UserMapper;
import org.ksga.springboot.springsecuritydemo.repository.AccountRepository;
import org.ksga.springboot.springsecuritydemo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public boolean updateAccount(UserDto userDto) {
        User user = userMapper.userDtoToUser(userDto);
        return accountRepository.updateAccount(user);
    }

    @Override
    public boolean closeAccount(Long id) {
        return accountRepository.closeAccount(id);
    }

    @Override
    public UserDto findUserById(Long id) {
        return userMapper.userToUserDto(accountRepository.findUserById(id));
    }
}
