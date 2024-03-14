package com.sangeng.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sangeng.domain.Category;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 77943
* @description 针对表【sg_category(分类表)】的数据库操作Mapper
* @createDate 2024-03-07 14:52:53
* @Entity .domian.com.sangeng.domain.Category
*/

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




