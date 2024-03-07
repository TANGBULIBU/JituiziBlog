package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Article;
import com.sangeng.domain.vo.HotArticleVo;
import com.sangeng.mapper.ArticleMapper;
import com.sangeng.service.ArticleService;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 77943
 * @description 针对表【sg_article(文章表)】的数据库操作Service实现
 * @createDate 2024-02-28 15:43:15
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

    @Override
    public ResponseResult hotArticleList() {

        //查询热门文章，封装成ResponseResult返回。把所有查询条件写在queryWrapper里面
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序 降序排序
        queryWrapper.orderByDesc(Article::getViewCount);

        Page<Article> page = new Page(1, 10);
        //翻页对象
        page(page, queryWrapper);
        //获取最终的查询结果，把结果封装在Article实体类里面会有很多不需要的字段
        List<Article> articles = page.getRecords();

        //bean拷贝 目的是减少查询信息的次数
        List<HotArticleVo> hotArticleVos=new ArrayList<>();

        BeanCopyUtils.copyBeanList(articles,HotArticleVo.class);

//        for (Article article : articles) {
//            HotArticleVo vo = new HotArticleVo();
//
//            //讲article内容赋值给vo 元数据值到目标值
//            BeanUtils.copyProperties(article,vo);
//
//            //再将每一个值添加到数组中
//            hotArticleVos.add(vo);
//        }


        return ResponseResult.okResult(hotArticleVos);
//        return ResponseResult.okResult(articles);
    }
}




