package cn.lut.curiezhang.es.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Package: cn.lut.curiezhang.es.config</p>
 * <p>Class Name: ElasticSearchConfig</p>
 * <p>Description: </p>
 * <p>
 * author: Curie Zhang<br>
 * date: 2022/8/24 9:42<br>
 * version: 1.0<br>
 */
//@Configuration
public class ElasticSearchConfig {

    @Value("#{elasticsearch.url}")
    private String url;

    @Value("#{elasticsearch.port}")
    private String port;

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(url, 9200)));
        return client;
    }
}
