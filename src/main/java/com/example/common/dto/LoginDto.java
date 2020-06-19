package com.example.common.dto;

import lombok.Data;
import org.springframework.web.util.pattern.PathPattern;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2020/6/13
 * Time: 下午 10:19
 * Description:
 */

@Data
public class LoginDto implements Serializable {
    @NotBlank(message = "昵称不能为空！")
    private String username;
    @NotBlank(message = "密码不能为空！")
    private String password;
}
