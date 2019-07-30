package com.spike.dao;

import com.spike.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Author: tangji
 * Date: 2019 07 26 17:39
 * Description: <...>
 */
@Mapper
public interface UserMapper {


    /*
    插入用户数据
     */
    public int insertUser(User user);

    /**
     * 用过用户id检索用户
     * @param userId
     * @return
     */
    public User getUserById(Integer userId);


    /**
     * 更新用户密码
     * @param userId
     * @param userPassword
     * @return
     */
    public int updateUserPassword(@Param("userId") Integer userId,@Param("userPassword") String userPassword);
}
