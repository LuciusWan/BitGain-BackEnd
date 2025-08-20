package com.lucius.bitgain.service;

import com.lucius.bitgain.dto.UserLoginDTO;
import com.lucius.bitgain.dto.UserRegisterDTO;
import com.lucius.bitgain.dto.UserUpdateDTO;
import com.lucius.bitgain.entity.User;
import com.lucius.bitgain.utils.Result;
import com.lucius.bitgain.vo.UserLoginVO;
import com.lucius.bitgain.vo.UserInfoVO;

public interface UserService {
    Result<User> getUser(Long id);

    /**
     * 用户注册
     * @param userRegisterDTO 注册信息
     * @return 注册结果
     */
    Result<String> register(UserRegisterDTO userRegisterDTO);

    /**
     * 用户登录
     * @param userLoginDTO 登录信息
     * @return 登录结果
     */
    Result<UserLoginVO> login(UserLoginDTO userLoginDTO);

    /**
     * 获取当前用户信息
     * @return 用户信息
     */
    Result<UserInfoVO> getCurrentUserInfo();

    /**
     * 更新当前用户信息
     * @param userUpdateDTO 更新信息
     * @return 更新结果
     */
    Result<String> updateUserInfo(UserUpdateDTO userUpdateDTO);
}
