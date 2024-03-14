package com.sangeng.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.Category;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Article;
import com.sangeng.domain.vo.CategoryVo;
import com.sangeng.mapper.CategoryMapper;
import com.sangeng.service.ArticleService;
import com.sangeng.service.CategoryService;

import com.sangeng.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 77943
 * @description 针对表【sg_category(分类表)】的数据库操作Service实现
 * @createDate 2024-03-07 14:52:53
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ArticleService articleService;

    //查询分类
    @Override
    public ResponseResult getCategoryList() {
        //查询文章表 状态为已经发布的文章
        //mybatis-plus用法
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        //需要Article.getStatus 获取状值 最后等于0
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);

        //articleService 存在获取文章
        List<Article> articleList = articleService.list(articleWrapper);

        //获取文章分类id 去重
        Set<Long> categoryIds = articleList.stream()
                //.map改变的其中的元素类型
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());//转换为set集合就可以去重

        //查询分类表
        List<Category> categories = listByIds(categoryIds);

        // 过滤操作 剩下状态正常的
        categories.stream()
                //判断状态是否是需要的 是的话就添加到list中
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装成CategoryVo实体类后返回给前端，CategoryVo的作用是只返回前端需要的字段。BeanCopyUtils是我们写的工具类

        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }
}




