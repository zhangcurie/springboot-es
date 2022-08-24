package cn.lut.curiezhang.es.model.mysql;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>Package: cn.lut.curiezhang.es.model.mysql</p>
 * <p>Class Name: MysqlBlog</p>
 * <p>Description: </p>
 * <p>
 * author: Curie Zhang<br>
 * date: 2022/8/24 8:29<br>
 * version: 1.0<br>
 */
@Data
@Table(name = "t_blog")
@Entity
public class MysqlBlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String author;
    @Column(columnDefinition = "mediumtext")
    private String content;
    private Date createTime;
    private Date updateTime;
}
