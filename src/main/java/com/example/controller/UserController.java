package com.example.controller;


import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2020-06-12
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public Object test(@PathVariable("id") Long id) {
        System.out.println("id---" + id);
        return userService.getById(id);
    }

    /**
     * @RequestBody 注解用于接收前端传来的实体，接收参数也是对应的实体，
     * 比如前端通过 json 提交传来两个参数 username 和 password，
     * 此时我们需要在后端封装一个实体来接收。
     * 在传递的参数比较多的情况下，使用 @RequestBody 接收会非常方便。
     * **/
    @PostMapping("/save")
    public Object saveUser(@Validated @RequestBody User user) {
        return user.toString();
    }
}
