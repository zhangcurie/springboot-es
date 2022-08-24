package cn.lut.curiezhang.es.model.es;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * <p>Package: cn.lut.curiezhang.es.model.es</p>
 * <p>Class Name: EsBlog</p>
 * <p>Description: </p>
 * <p>
 * author: Curie Zhang<br>
 * date: 2022/8/24 8:55<br>
 * version: 1.0<br>
 */
@Data
@Document(indexName = "blog", createIndex = false)
public class EsBlog {
    @Id
    private Integer id;
    @Field(type = FieldType.Text, analyzer = "standard")
    private String title;
    @Field(type = FieldType.Text, analyzer = "standard")
    private String author;
    @Field(type = FieldType.Text, analyzer = "standard")
    private String content;
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd " +
            "HH:mm:ss||yyyy-MM-dd||epoch-millis")
    private Date createTime;
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd " +
            "HH:mm:ss||yyyy-MM-dd||epoch-millis")
    private Date updateTime;
}
