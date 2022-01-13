package com.wuzx.api;

import com.wuzx.pojo.User;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName IUserService.java
 * @Description 用户服务
 * @createTime 2022年01月13日 17:13:00
 */
public interface IUserService {


    /**
     * 根据id查询用户信息
     *
     * @param id
     * @return
     */
    User getById(int id);
}
