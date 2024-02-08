package com.gstagram.gstagram.user.application;

import com.gstagram.gstagram.user.domain.User;
import com.gstagram.gstagram.user.dto.ResponseUserDto;
import com.gstagram.gstagram.user.dto.UpdateUserDto;

public interface UserService {
    User getUserInfoByUuid(String uuid);

    void updateUserInfo(UpdateUserDto updateUserDto, String uuid);

    void deleteUser(String uuid);
}
