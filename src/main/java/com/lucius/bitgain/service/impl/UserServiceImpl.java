package com.lucius.bitgain.service.impl;

import com.lucius.bitgain.context.BaseContext;
import com.lucius.bitgain.dto.UserLoginDTO;
import com.lucius.bitgain.dto.UserRegisterDTO;
import com.lucius.bitgain.dto.UserUpdateDTO;
import com.lucius.bitgain.entity.User;
import com.lucius.bitgain.mapper.UserMapper;
import com.lucius.bitgain.properties.JwtProperties;
import com.lucius.bitgain.service.UserService;
import com.lucius.bitgain.utils.JwtUtil;
import com.lucius.bitgain.utils.Result;
import com.lucius.bitgain.vo.UserLoginVO;
import com.lucius.bitgain.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务实现类
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtProperties jwtProperties;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Result<User> getUser(Long id) {
        User user = userMapper.getUserById(id);
        if (user != null) {
            return Result.success(user);
        } else {
            return Result.error("用户不存在");
        }
    }

    @Override
    public Result<String> register(UserRegisterDTO userRegisterDTO) {
        log.info("用户注册，用户名：{}", userRegisterDTO.getUsername());

        // 检查用户名是否已存在
        User existingUserByUsername = userMapper.getUserByUsername(userRegisterDTO.getUsername());
        if (existingUserByUsername != null) {
            return Result.error("用户名已存在");
        }

        // 检查手机号是否已存在
        User existingUserByPhone = userMapper.getUserByPhone(userRegisterDTO.getPhone());
        if (existingUserByPhone != null) {
            return Result.error("手机号已被注册");
        }

        // 创建新用户
        User user = new User();
        user.setUsername(userRegisterDTO.getUsername());
        // 密码加密
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setPhone(userRegisterDTO.getPhone());
        user.setEmailSubscribe(0); // 默认关闭状态
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        try {
            userMapper.insertUser(user);
            log.info("用户注册成功，用户ID：{}", user.getId());
            return Result.success("注册成功");
        } catch (Exception e) {
            log.error("用户注册失败", e);
            return Result.error("注册失败，请稍后重试");
        }
    }

    @Override
    public Result<UserLoginVO> login(UserLoginDTO userLoginDTO) {
        log.info("用户登录，用户名：{}", userLoginDTO.getUsername());

        // 根据用户名查询用户
        User user = userMapper.getUserByUsername(userLoginDTO.getUsername());
        if (user == null) {
            return Result.error("用户名或密码错误");
        }

        // 验证用户状态
        if (user.getDeleted() == 1) {
            return Result.error("账户已被禁用");
        }

        // 验证密码
        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            return Result.error("用户名或密码错误");
        }

        try {
            // 生成JWT令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", user.getId());
            claims.put("username", user.getUsername());
            String token = JwtUtil.createJWT(
                    jwtProperties.getUserSecretKey(),  // 使用配置文件中的密钥
                    jwtProperties.getUserTtl(),        // 使用配置文件中的过期时间
                    claims
            );

            // 构建返回对象
            UserLoginVO loginVO = new UserLoginVO();
            loginVO.setUserId(user.getId());
            loginVO.setUsername(user.getUsername());
            loginVO.setToken(token);

            log.info("用户登录成功，用户ID：{}", user.getId());
            return Result.success(loginVO);
        } catch (Exception e) {
            log.error("生成JWT令牌失败", e);
            return Result.error("登录失败，请稍后重试");
        }
    }

    @Override
    public Result<UserInfoVO> getCurrentUserInfo() {
        // 从ThreadLocal中获取当前用户ID
        Long userId = BaseContext.getCurrentId();
        if (userId == null) {
            return Result.error("用户未登录");
        }

        log.info("获取用户信息，用户ID：{}", userId);

        // 根据用户ID查询用户信息
        User user = userMapper.getUserById(userId);
        if (user == null) {
            return Result.error("用户不存在");
        }

        // 构建返回对象
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUsername(user.getUsername());
        userInfoVO.setPhone(user.getPhone());
        userInfoVO.setEmail(user.getEmail());
        userInfoVO.setProfession(user.getProfession());
        userInfoVO.setSkills(user.getSkills());
        userInfoVO.setGoals(user.getGoals());
        userInfoVO.setEmailSubscribe(user.getEmailSubscribe());

        log.info("获取用户信息成功，用户名：{}", user.getUsername());
        return Result.success(userInfoVO);
    }

    @Override
    public Result<String> updateUserInfo(UserUpdateDTO userUpdateDTO) {
        // 从ThreadLocal中获取当前用户ID
        Long userId = BaseContext.getCurrentId();
        if (userId == null) {
            return Result.error("用户未登录");
        }

        log.info("更新用户信息，用户ID：{}", userId);

        // 检查用户名是否已存在（排除当前用户）
        User existingUserByUsername = userMapper.getUserByUsername(userUpdateDTO.getUsername());
        if (existingUserByUsername != null && !existingUserByUsername.getId().equals(userId)) {
            return Result.error("用户名已存在");
        }

        // 检查手机号是否已存在（排除当前用户）
        User existingUserByPhone = userMapper.getUserByPhone(userUpdateDTO.getPhone());
        if (existingUserByPhone != null && !existingUserByPhone.getId().equals(userId)) {
            return Result.error("手机号已存在");
        }

        // 构建更新对象
        User user = new User();
        user.setId(userId);
        user.setUsername(userUpdateDTO.getUsername());
        user.setPhone(userUpdateDTO.getPhone());
        user.setEmail(userUpdateDTO.getEmail());
        user.setProfession(userUpdateDTO.getProfession());
        user.setSkills(userUpdateDTO.getSkills());
        user.setGoals(userUpdateDTO.getGoals());
        user.setEmailSubscribe(userUpdateDTO.getEmailSubscribe());
        user.setUpdateTime(LocalDateTime.now());

        // 执行更新
        int result = userMapper.updateUser(user);
        if (result > 0) {
            log.info("更新用户信息成功，用户ID：{}", userId);
            return Result.success("更新成功");
        } else {
            log.error("更新用户信息失败，用户ID：{}", userId);
            return Result.error("更新失败");
        }
    }
}