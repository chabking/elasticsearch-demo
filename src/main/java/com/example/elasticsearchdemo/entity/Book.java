package com.example.elasticsearchdemo.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>标题: TODO</p>
 * <p>功能: TODO</p>
 * <p>模块: elasticsearch-demo</p>
 * <p>版权: Copyright © 2019 topideal</p>
 * <p>公司: 广东卓志供应链科技有限公司武汉分公司</p>
 * <p>类路径: com.example.elasticsearchdemo.entity</p>
 * <p>作者: topIdeal</p>
 * <p>创建日期: 2019-09-16 15:38</p>
 *
 * @version 1.0
 */
@Data
public class Book implements Serializable {

    private static final long serialVersionUID = 567312661928729560L;

    private String bookName;
    private BigDecimal price;
}
