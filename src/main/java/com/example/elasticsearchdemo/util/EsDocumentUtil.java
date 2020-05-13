package com.example.elasticsearchdemo.util;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * <p>标题: TODO</p>
 * <p>功能: TODO</p>
 * <p>模块: elasticsearch-demo</p>
 * <p>类路径: com.example.elasticsearchdemo.util</p>
 * <p>作者: topIdeal</p>
 * <p>创建日期: 2019-09-16 11:09</p>
 *
 * @version 1.0
 */
@Component
public class EsDocumentUtil {

    private static final Logger logger = LoggerFactory.getLogger(EsDocumentUtil.class);

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private static RestHighLevelClient client;

    @PostConstruct
    private void init() {
        client = this.restHighLevelClient;
    }

    /**
     * 新增单个文档
     *
     * @param index
     * @param json
     * @return
     * @throws IOException
     */
    public static String addDoc(String index, String json) throws IOException {
        if (!EsIndexUtil.isIndexExist(index)) {
            logger.error("Index \"" + index + "\" does not exist!");
            return null;
        }
        IndexRequest request = new IndexRequest(index);
        request.id(UUIDGenerator.getUUID32());
        request.source(json, XContentType.JSON);
        request.timeout(TimeValue.timeValueSeconds(1));
        request.opType(DocWriteRequest.OpType.CREATE);
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        return indexResponse.getId();
    }

    /**
     * 根据id删除文档
     *
     * @param index
     * @param id
     * @return
     * @throws IOException
     */
    public static boolean deleteDoc(String index, String id) throws IOException {
        DeleteRequest request = new DeleteRequest(index, id);
        request.timeout(TimeValue.timeValueMinutes(2));
        DeleteResponse deleteResponse = client.delete(request, RequestOptions.DEFAULT);
        if (deleteResponse.getResult() == DocWriteResponse.Result.NOT_FOUND) {
            return false;
        }
        return true;
    }

    /**
     * 根据id更新文档，不存在则创建
     *
     * @param index
     * @param id
     * @param json
     * @return
     * @throws IOException
     */
    public static boolean updateDoc(String index, String id, String json) throws IOException {
        UpdateRequest request = new UpdateRequest(index, id);
        request.doc(json, XContentType.JSON);
        request.timeout(TimeValue.timeValueSeconds(1));
        // doc不存在则新增
        request.docAsUpsert(true);
        UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
        switch (updateResponse.getResult()) {
            case CREATED:
                break;
            case UPDATED:
                break;
            default:
                return false;
        }
        return true;
    }

    /**
     * 根据id查询文档
     *
     * @param index
     * @param id
     * @return
     * @throws IOException
     */
    public static String getDoc(String index, String id) throws IOException {
        GetRequest getRequest = new GetRequest(index, id);
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        if (!getResponse.isExists()) {
            return null;
        }
        return getResponse.getSourceAsString();
    }
}
