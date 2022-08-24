package cn.lut.curiezhang.es.repository.es;

import cn.lut.curiezhang.es.model.es.EsBlog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * <p>Package: cn.lut.curiezhang.es.repository.es</p>
 * <p>Class Name: EsBlogRepository</p>
 * <p>Description: </p>
 * <p>
 * author: Curie Zhang<br>
 * date: 2022/8/24 9:03<br>
 * version: 1.0<br>
 */
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, Integer> {
}
