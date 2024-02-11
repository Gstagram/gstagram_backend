package com.gstagram.gstagram.user.application;

import com.gstagram.gstagram.common.api.ResponseCode;
import com.gstagram.gstagram.common.exception.UserException;
import com.gstagram.gstagram.user.domain.User;
import com.gstagram.gstagram.user.dto.ResponseUserDto;
import com.gstagram.gstagram.user.dto.UpdateUserDto;
import com.gstagram.gstagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public User getUserInfoByUuid(String uuid){
        return userRepository.findByUuid(uuid).orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));

    }

    public void updateUserInfo(UpdateUserDto updateUserDto, String uuid){
        User user = userRepository.findByUuid(uuid).orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));
        user.updateUser(updateUserDto);
        userRepository.save(user);
    }

    public void deleteUser(String uuid) {
        User user = userRepository.findByUuid(uuid).orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));
        userRepository.delete(user);
    }
}

