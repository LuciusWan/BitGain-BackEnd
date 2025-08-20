package com.lucius.bitgain.controller;

import com.lucius.bitgain.dto.UserLoginDTO;
import com.lucius.bitgain.dto.UserRegisterDTO;
import com.lucius.bitgain.dto.UserUpdateDTO;
import com.lucius.bitgain.entity.User;
import com.lucius.bitgain.service.UserService;
import com.lucius.bitgain.utils.Result;
import com.lucius.bitgain.vo.UserLoginVO;
import com.lucius.bitgain.vo.UserInfoVO;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/user")
@Tag(name = "用户管理", description = "用户相关的API接口")
public class UserController {
    @Autowired
    private UserService userService;
    /**
     * 根据用户ID获取用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    @Operation(summary = "获取用户信息", description = "根据用户ID获取用户的详细信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取用户信息"),
            @ApiResponse(responseCode = "400", description = "请求参数错误"),
            @ApiResponse(responseCode = "404", description = "用户不存在")
    })
    @GetMapping("/getUser")
    public Result<User> getUser(
            @Parameter(description = "用户ID", required = true, example = "1")
            @RequestParam Long id) {
        return userService.getUser(id);
    }

    /**
     * 用户注册
     * @param userRegisterDTO 注册信息
     * @return 注册结果
     */
    @Operation(summary = "用户注册", description = "用户注册接口，接收用户名、手机号和密码，注册成功后状态默认为启用")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "注册成功"),
            @ApiResponse(responseCode = "400", description = "请求参数错误或用户名/手机号已存在"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/register")
    public Result<String> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        // 手机号正则验证
        String phoneRegex = "^1[3-9]\\d{9}$";
        if (!Pattern.matches(phoneRegex, userRegisterDTO.getPhone())) {
            return Result.error("手机号格式不正确，请重新注册");
        }
        
        // 生成符合HS256要求的密钥（自动保证≥256位）
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        // 转成Base64字符串（方便存储到配置文件，避免乱码）
        String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("HS256密钥（Base64格式）：" + base64Key);
        return userService.register(userRegisterDTO);
    }

    /**
     * 用户登录
     * @param userLoginDTO 登录信息
     * @return 登录结果
     */
    @Operation(summary = "用户登录", description = "用户登录接口，验证用户名和密码，返回用户信息和JWT令牌")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "登录成功"),
            @ApiResponse(responseCode = "400", description = "请求参数错误或用户名/密码错误"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/login")
    public Result<UserLoginVO> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        return userService.login(userLoginDTO);
    }

    /**
     * 获取当前用户信息
     * @return 用户信息
     */
    @Operation(summary = "获取当前用户信息", description = "获取当前登录用户的基本信息（用户名、手机号、邮箱、职业、技能、目标、邮件订阅设置）")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "成功获取用户信息"),
            @ApiResponse(responseCode = "401", description = "用户未登录"),
            @ApiResponse(responseCode = "404", description = "用户不存在")
    })
    @GetMapping("/info")
    public Result<UserInfoVO> getCurrentUserInfo() {
        return userService.getCurrentUserInfo();
    }

    /**
     * 更新用户信息
     * @param userUpdateDTO 用户更新信息
     * @return 更新结果
     */
    @Operation(summary = "更新用户信息", description = "更新当前登录用户的基本信息（用户名、手机号、邮箱、职业、技能、目标、邮件订阅设置）")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "更新成功"),
            @ApiResponse(responseCode = "400", description = "请求参数错误或用户名/手机号已存在"),
            @ApiResponse(responseCode = "401", description = "用户未登录"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PutMapping("/info")
    public Result<String> updateUserInfo(@Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        return userService.updateUserInfo(userUpdateDTO);
    }
}
