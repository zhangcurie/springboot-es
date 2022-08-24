package cn.lut.curiezhang.es.controller;

import cn.lut.curiezhang.es.model.mysql.MysqlBlog;
import cn.lut.curiezhang.es.repository.mysql.MysqlBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * <p>Package: cn.lut.curiezhang.es.controller</p>
 * <p>Class Name: IndexController</p>
 * <p>Description: </p>
 * <p>
 * author: Curie Zhang<br>
 * date: 2022/8/24 8:36<br>
 * version: 1.0<br>
 */
@Controller
public class IndexController {
    @Autowired
    MysqlBlogRepository mysqlBlogRepository;
    @RequestMapping("/")
    public String index() {
        List<MysqlBlog> all = mysqlBlogRepository.findAll();
        System.out.println(all.size());
        return "index.html";
    }
}
