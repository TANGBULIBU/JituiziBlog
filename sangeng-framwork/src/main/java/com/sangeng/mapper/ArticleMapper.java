package com.sangeng.mapper;

import com.sangeng.domain.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 77943
* @description 针对表【sg_article(文章表)】的数据库操作Mapper
* @createDate 2024-02-28 15:43:15
* @Entity generator.entity.Article
*/
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}




