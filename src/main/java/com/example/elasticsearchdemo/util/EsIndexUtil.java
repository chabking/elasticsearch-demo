package com.example.elasticsearchdemo.util;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.rest.RestStatus;
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
 * <p>创建日期: 2019-09-16 11:18</p>
 *
 * @version 1.0
 */
@Component
public class EsIndexUtil {

    private static final Logger logger = LoggerFactory.getLogger(EsIndexUtil.class);

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private static RestHighLevelClient client;

    @PostConstruct
    private void init() {
        client = this.restHighLevelClient;
    }

    /**
     * 判断索引是否存在
     *
     * @param index
     * @return true：存在，false：不存在
     */
    public static boolean isIndexExist(String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest(index);
        return client.indices().exists(request, RequestOptions.DEFAULT);
    }

    /**
     * 创建索引，同步等待回执
     *
     * @param index，索引名
     * @return true：创建成功，fales：创建失败
     */
    public static boolean createIndex(String index) throws IOException {
        if (isIndexExist(index)) {
            logger.warn("Index \"" + index + "\" already exists!");
            return false;
        }
        CreateIndexRequest request = new CreateIndexRequest(index);
        // 分片，备份
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );
        /*// mapping
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                builder.startObject("message");
                {
                    builder.field("type", "text");
                }
                builder.endObject();
                builder.startObject("sendTime");
                {
                    builder.field("type", "long");
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();
        request.mapping(builder);*/
        // 等待所有节点确认创建的超时时间
        request.setTimeout(TimeValue.timeValueMinutes(2));
        // 连接主节点的超时时间
        request.setMasterTimeout(TimeValue.timeValueMinutes(1));
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        return createIndexResponse.isAcknowledged();
    }

    /**
     * 删除索引，同步等待回执
     * @param index
     * @return true：删除成功，fales：删除失败
     * @throws IOException
     */
    public static boolean deleteIndex(String index) throws IOException {
        DeleteIndexRequest request = null;
        AcknowledgedResponse deleteIndexResponse = null;
        try {
            request = new DeleteIndexRequest(index);
            // 等待所有节点确认删除的超时时间
            request.timeout(TimeValue.timeValueMinutes(2));
            // 连接主节点的超时时间
            request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
            deleteIndexResponse = client.indices().delete(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException exception) {
            if (exception.status() == RestStatus.NOT_FOUND) {
                logger.error("Index \"" + index + "\" does not found!");
            } else {
                logger.error("Index \"" + index + "\" not deleted!");
            }
            return false;
        }
        return deleteIndexResponse.isAcknowledged();
    }
}
