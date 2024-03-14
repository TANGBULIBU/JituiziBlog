package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Article;
import com.sangeng.domain.vo.ArticleListVo;
import com.sangeng.domain.vo.HotArticleVo;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.mapper.ArticleMapper;
import com.sangeng.service.ArticleService;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 77943
 * @description 针对表【sg_article(文章表)】的数据库操作Service实现
 * @createDate 2024-02-28 15:43:15
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    //查询热门文章
    @Override
    public ResponseResult hotArticleList() {

        //查询热门文章，封装成ResponseResult返回。把所有查询条件写在queryWrapper里面
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序 降序排序
        queryWrapper.orderByDesc(Article::getViewCount);

        //分页显示 当前显示第一页数据
        Page<Article> page = new Page(SystemConstants.ARTICLE_STATUS_CURRENT, SystemConstants.ARTICLE_STATUS_SIZE);
        //翻页对象
        page(page, queryWrapper);
        //获取最终的查询结果，把结果封装在Article实体类里面会有很多不需要的字段
        List<Article> articles = page.getRecords();

        //bean拷贝 目的是减少查询信息的次数

        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);

//        List<HotArticleVo> hotArticleVos=new ArrayList<>();
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

    //分页查询文章的列表
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //判空。如果前端传了categoryId这个参数，那么查询时要和传入的相同。第二个参数是数据表的文章id，第三个字段是前端传来的文章id
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0, Article::getCategoryId,categoryId);

        //只查询状态是正式发布的文章。Article实体类的status字段跟0作比较，一样就表示是正式发布的
        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);

        //对isTop字段进行降序排序，实现置顶的文章(isTop值为1)在最前面
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);

        //把最后的查询结果封装成ArticleListVo(我们写的实体类)。BeanCopyUtils是我们写的工具类
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        //把上面那行的查询结果和文章总数封装在PageVo(我们写的实体类)
        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);

    }
}




