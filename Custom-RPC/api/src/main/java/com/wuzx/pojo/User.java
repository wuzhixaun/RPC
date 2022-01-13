package com.wuzx.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName User.java
 * @Description 用户pojo
 * @createTime 2022年01月13日 17:12:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id; // 用户id
    private String name; // 用户名称

}
