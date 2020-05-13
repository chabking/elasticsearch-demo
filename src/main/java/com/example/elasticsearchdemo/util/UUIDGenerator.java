package com.example.elasticsearchdemo.util;

import java.util.UUID;

/**
 * <p>标题: TODO</p>
 * <p>功能: TODO</p>
 * <p>模块: elasticsearch-demo</p>
 * <p>版权: Copyright © 2019 topideal</p>
 * <p>公司: 广东卓志供应链科技有限公司武汉分公司</p>
 * <p>类路径: com.example.elasticsearchdemo.util</p>
 * <p>作者: topIdeal</p>
 * <p>创建日期: 2019-09-16 10:59</p>
 *
 * @version 1.0
 */
public class UUIDGenerator {

    /**
     *
     * 功能描述：生成32位UUID
     *
     * @return:
     * @auther: topIdeal
     * @date: 2019/9/16 11:06
     *
     */
    public static String getUUID32(){

        return UUID.randomUUID().toString().replace("-", "").toLowerCase();

    }
}
