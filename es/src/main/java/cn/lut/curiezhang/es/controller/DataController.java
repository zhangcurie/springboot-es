package cn.lut.curiezhang.es.controller;

import cn.lut.curiezhang.es.model.es.EsBlog;
import cn.lut.curiezhang.es.model.mysql.MysqlBlog;
import cn.lut.curiezhang.es.repository.es.EsBlogRepository;
import cn.lut.curiezhang.es.repository.mysql.MysqlBlogRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.ConstantScoreQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>Package: cn.lut.curiezhang.es.controller</p>
 * <p>Class Name: DataController</p>
 * <p>Description: </p>
 * <p>
 * author: Curie Zhang<br>
 * date: 2022/8/24 9:11<br>
 * version: 1.0<br>
 */
@RestController
@Slf4j
public class DataController {

    private static final String MYSQL = "mysql";
    private static final String ES = "es";

    @Autowired
    MysqlBlogRepository mysqlBlogRepository;

    @Autowired
    EsBlogRepository esBlogRepository;

    @GetMapping("/blogs")
    public Object blogList() {
        List<MysqlBlog> mysqlBlogs = mysqlBlogRepository.queryAll();
        return mysqlBlogs;
    }

    @PostMapping("/search")
    public Object search(@RequestBody Param param) {
        Map<String, Object> map = new HashMap<>();
        // 统计耗时
        StopWatch watch = new StopWatch();
        watch.start();
        String type = param.getType();
        // mysql 的搜索
        if (MYSQL.equals(type)) {
            List<MysqlBlog> mysqlBlogs = mysqlBlogRepository.queryBlog(param.getKeyword());
            map.put("list", mysqlBlogs);
            // es 的搜索
        } else if (ES.equals(type)) {
            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            builder.should(QueryBuilders.matchPhraseQuery("title", param.getKeyword()));
            builder.should(QueryBuilders.matchPhraseQuery("content", param.getKeyword()));
            String s = builder.toString();
            log.info("s={}", s);
            Page<EsBlog> search = (Page<EsBlog>) esBlogRepository.search(builder);
            List<EsBlog> content = search.getContent();
            map.put("list", content);
        } else {
            return "你要啥呢小老弟";
        }
        watch.stop();
        // 计算耗时
        long millis = watch.getTotalTimeMillis();
        map.put("duration", millis);
        return map;
    }

    @GetMapping("/blog/{id}")
    public Object blog(@PathVariable Integer id) {
        Optional<MysqlBlog> byId = mysqlBlogRepository.findById(id);
        return byId.get();
    }

    @Data
    private static class Param {
        private String type;
        private String keyword;
    }

}
