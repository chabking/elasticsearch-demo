package com.example.elasticsearchdemo.controller;

import com.alibaba.fastjson.JSON;
import com.example.elasticsearchdemo.entity.Book;
import com.example.elasticsearchdemo.util.EsDocumentUtil;
import com.example.elasticsearchdemo.util.EsIndexUtil;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>标题: TODO</p>
 * <p>功能: TODO</p>
 * <p>模块: elasticsearch-demo</p>
 * <p>版权: Copyright © 2019 topideal</p>
 * <p>公司: 广东卓志供应链科技有限公司武汉分公司</p>
 * <p>类路径: com.example.elasticsearchdemo.controller</p>
 * <p>作者: topIdeal</p>
 * <p>创建日期: 2019-09-12 16:44</p>
 *
 * @version 1.0
 */
@Controller
public class TestController {

    @RequestMapping("/test")
    @ResponseBody
    public void put() throws IOException {

//        EsIndexUtil.createIndex("books");

//        EsIndexUtil.deleteIndex("books");

//        Book book = new Book();
//        book.setBookName("elasticsearch技术解析与实战");
//        book.setPrice(new BigDecimal(10));
//        EsDocumentUtil.addDoc("books", JSON.toJSONString(book));

//        Book books = JSON.parseObject(EsDocumentUtil.getDoc("books", "86edf6ba2ae44d21a7bbbc41f37c2aa8"), Book.class);

//        boolean books = EsDocumentUtil.updateDoc("books", "3", JSON.toJSONString(book));
    }

}
