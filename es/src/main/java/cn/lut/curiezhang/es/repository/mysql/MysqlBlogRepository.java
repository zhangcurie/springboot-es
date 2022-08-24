package cn.lut.curiezhang.es.repository.mysql;

import cn.lut.curiezhang.es.model.mysql.MysqlBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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
    @Query("select e from MysqlBlog e order by e.createTime desc")
    List<MysqlBlog> queryAll();

    @Query("select e from MysqlBlog e where e.title like concat('%', :keyword,'%') or e.content " +
            "like concat('%', :keyword,'%') ")
    List<MysqlBlog> queryBlog(@Param("keyword") String keyword);
}
