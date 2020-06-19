package com.example.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.JsonResult;
import com.example.entity.Blog;
import com.example.service.BlogService;
import com.example.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2020-06-12
 */
@RestController
public class BlogController {

    @Autowired
    BlogService blogService;

    /**
     * 查询博客列表
     **/
    @GetMapping("/blogs")
    public JsonResult blogs(Integer currentPage) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        // 当前第几页，每页显示几条数据
        Page page = new Page(currentPage, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
        return JsonResult.succ(pageData);
    }

    /**
     * 根据博客id查看博客
     **/
    @GetMapping("/blog/{id}")
    public JsonResult detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已经被删除！");
        return JsonResult.succ(blog);

    }

    /**
     * 修改博客
     **/
    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public JsonResult edit(@Validated @RequestBody Blog blog) {
        Blog temp = null;
        if (null != blog.getId()) {
            temp = blogService.getById(blog.getId());
            // 只能编辑自己的文章
            Assert.isTrue(temp.getUserId() == ShiroUtil.getProfile().getId(), "没有编辑权限");
        } else {
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(new Date());
            temp.setStatus(0);
        }
        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.save(temp);
        return JsonResult.succ("操作成功!", null);
    }

}
