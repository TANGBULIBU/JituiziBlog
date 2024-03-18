package com.sangeng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.Link;
import com.sangeng.domain.ResponseResult;

public interface LinkService extends IService<Link> {

    //查询友链
    ResponseResult getAllLink();
}
