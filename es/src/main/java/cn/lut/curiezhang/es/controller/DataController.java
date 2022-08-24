package cn.lut.curiezhang.es.controller;

import cn.lut.curiezhang.es.model.es.EsBlog;
import cn.lut.curiezhang.es.model.mysql.MysqlBlog;
import cn.lut.curiezhang.es.repository.es.EsBlogRepository;
import cn.lut.curiezhang.es.repository.mysql.MysqlBlogRepository;
import lombok.Data;
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
public class DataController {
    @Autowired
    MysqlBlogRepository mysqlBlogRepository;

    @Autowired
    EsBlogRepository esBlogRepository;

    @GetMapping("/blogs")
    public Object blog() {
        List<MysqlBlog> mysqlBlogs = mysqlBlogRepository.queryAll();
        return mysqlBlogs;
    }

    @PostMapping("/search")
    public Object search(@RequestBody Param param) {
        HashMap<String, Object> map = new HashMap<>();
        StopWatch watch = new StopWatch();
        watch.start();
        String type = param.getType();
        if (type.equalsIgnoreCase("mysql")) {
            List<MysqlBlog> mysqlBlogs = mysqlBlogRepository.queryBlogs(param.getKeyword());
            map.put("list", mysqlBlogs);
        } else if (type.equalsIgnoreCase("es")) {
//            POST /blog/_search
//            {
//                "query": {
//                "bool": {
//                    "should": [
//                    {
//                        "match_phrase": {
//                        "title": "springboot"
//                    }
//                    },
//                    {
//                        "match_phrase": {
//                        "content": "springboot"
//                    }
//                    }
//      ]
//                }
//            }
//            }
//            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//            MatchQueryBuilder matchQueryBuilder= QueryBuilders.matchQuery("content", param.getKeyword());
//            ConstantScoreQueryBuilder constantScoreQueryBuilder=QueryBuilders.constantScoreQuery(matchQueryBuilder);
//            searchSourceBuilder.query(constantScoreQueryBuilder);

            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            builder.should(QueryBuilders.matchPhraseQuery("title", param.getKeyword()));
            builder.should(QueryBuilders.matchPhraseQuery("content", param.getKeyword()));
            String s = builder.toString();
            System.out.println(s);
            Page<EsBlog> search = (Page<EsBlog>) esBlogRepository.search(builder);
            List<EsBlog> content = search.getContent();
            map.put("list", content);
        }else {
            return "i don't understand";
        }
        watch.stop();
        long totalTimeMills = watch.getTotalTimeMillis();
        map.put("duration", totalTimeMills);
        return map;
    }

    @GetMapping("/blog/{id}")
    public Object blog(@PathVariable Integer id) {
        Optional<MysqlBlog> byId = mysqlBlogRepository.findById(id);
        return byId.get();
    }

    @Data
    public static class Param {
        // mysql , es
        private String type;
        private String keyword;
    }
}
