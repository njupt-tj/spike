package com.spike.services;

import com.spike.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * Author: tangji
 * Date: 2019 07 26 18:03
 * Description: <...>
 */
public interface UserService {

    public boolean register(User user);

    public boolean updatePassword(Integer userId, String userPassword);

    public boolean getUserInfo(Integer userId);

}
