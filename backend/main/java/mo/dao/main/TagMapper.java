package mo.dao.main;

import mo.entity.po.main.Tag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TagMapper {

    /**
     * 根据题目Id查找题目标签
     *
     * @param problem_id 题目Id
     * @return 标签集合
     */
    @Select("select * from tag where tag_id in(select tag_id from problem_tag where problem_id = #{problem_id})")
    List<Tag> findTagsByProblemId(@Param("problem_id") Integer problem_id);

    /**
     * 查找是否存在指定tag
     *
     * @param tagname 标签名程
     * @return
     */
    @Select("select tag_id from tag where tagname = #{tagname}")
    Integer findTagIdByTagName(@Param("tagname") String tagname);

    /**
     * 根据标签Id查找tag
     *
     * @param tag_id 标签Id
     * @return
     */
    @Select("select * from tag where tag_id = #{tag_id}")
    Tag findTagByTagId(@Param("tag_id") int tag_id);

    /**
     * 添加新标签
     *
     * @param tagname 标签名
     * @return影响行数
     */
    @Insert("insert into tag (tagname) values (#{tagname})")
    int insertTag(@Param("tagname") String tagname);

    /**
     * 查询上个插入Id
     *
     * @return
     */
    @Select("select LAST_INSERT_ID()")
    Integer findLastInsertId();

    /**
     * 查询标签
     *
     * @param start    起始
     * @param per_page 每页数量
     * @return
     */
    @Select("select * from tag limit #{start},#{per_page}")
    List<Tag> findTagsByPage(@Param("start") int start, @Param("per_page") int per_page);

}
