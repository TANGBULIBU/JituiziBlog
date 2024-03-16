package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

//    @GetMapping("/list")
//    public List<Article> test(){
//        return articleService.list();
//    }

    //热点标题
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList() {
        //查询热门文章 controller不能直接查询 需要使用service

        ResponseResult result = articleService.hotArticleList();
        return result;

    }

    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询热门文章 controller不能直接查询 需要使用service

        return articleService.articleList(pageNum, pageSize, categoryId);


    }
}
