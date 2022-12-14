package cn.lut.curiezhang.es;

import cn.lut.curiezhang.es.model.es.EsBlog;
import cn.lut.curiezhang.es.repository.es.EsBlogRepository;
import org.apache.catalina.User;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
//import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class EsApplicationTests {

    @Autowired
    EsBlogRepository esBlogRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void testEs() {
        Iterable<EsBlog> all = esBlogRepository.findAll();
        Iterator<EsBlog> iterator = all.iterator();
        EsBlog next = iterator.next();
        System.out.println("------------" + next.getTitle());
    }

    @Autowired
//    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;
    @Test //????????????
    void createIndex() throws IOException {
        //1.???????????????????????????????????????
        CreateIndexRequest request = new CreateIndexRequest("kuang_index");
        //2.????????????
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }
    @Test //????????????
    void getIndex() throws IOException {
        //1.???????????????????????????????????????
        GetIndexRequest request = new GetIndexRequest("kuang_index");
        //2.????????????
        boolean response = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }
    @Test //????????????
    void delIndex() throws IOException {
        //1.???????????????????????????????????????
        DeleteIndexRequest request = new DeleteIndexRequest("kuang_index");
        //2.????????????
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    @Test //????????????
    void getDoc() throws IOException {

//        GetRequest request = new GetRequest("kuang_index","1");
        //??????????????????_source????????????
        /* ????????????????????????
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");
        boolean exists = client.exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
        */
//        GetResponse response = client.get(request, RequestOptions.DEFAULT);
//        System.out.println(response.getSourceAsString()); //?????????????????????
//        System.out.println(response);//???????????????????????????????????????
    }
//    @Test //????????????
//    void delDoc() throws IOException {
//        DeleteRequest request = new DeleteRequest("kuang_index","1");
//        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
//        System.out.println(response.status());
//    }
    //??????
    //SearchRequest????????????
    //SearchSourceBuilder??????????????????
    @Test
    void query() throws Exception {
        SearchRequest searchRequest = new SearchRequest("kuang_index");

        //??????????????????
        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        //?????????????????????????????????QueryBuilders???????????????
        //QueryBuilders.termQuery???????????????????????????
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "moxuan1");
        //MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        searchBuilder.query(termQueryBuilder);
//        searchBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        searchRequest.source(searchBuilder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        for (SearchHit hit : response.getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }
}
