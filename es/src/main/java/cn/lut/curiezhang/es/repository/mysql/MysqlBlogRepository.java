package cn.lut.curiezhang.es.repository.mysql;

import cn.lut.curiezhang.es.model.mysql.MysqlBlog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>Package: cn.lut.curiezhang.es.repository.mysql</p>
 * <p>Class Name: MysqlBlogRepository</p>
 * <p>Description: </p>
 * <p>
 * author: Curie Zhang<br>
 * date: 2022/8/24 8:35<br>
 * version: 1.0<br>
 */
public interface MysqlBlogRepository extends JpaRepository<MysqlBlog, Integer> {
}
