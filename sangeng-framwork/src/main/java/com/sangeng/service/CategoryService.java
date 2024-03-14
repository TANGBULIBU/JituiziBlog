package com.sangeng.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.Category;
import com.sangeng.domain.ResponseResult;

/**
* @author 77943
* @description 针对表【sg_category(分类表)】的数据库操作Service
* @createDate 2024-03-07 14:52:53
*/
public interface CategoryService extends IService<Category> {

    //查询文章分类接口
    ResponseResult getCategoryList();
}
