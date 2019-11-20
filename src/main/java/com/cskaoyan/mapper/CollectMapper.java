package com.cskaoyan.mapper;

import com.cskaoyan.bean.goods.GoodsForCollect;
import com.cskaoyan.bean.user.Collect;
import com.cskaoyan.bean.user.CollectExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CollectMapper {
    long countByExample(CollectExample example);

    int deleteByExample(CollectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Collect record);

    int insertSelective(Collect record);

    List<Collect> selectByExample(CollectExample example);

    Collect selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Collect record, @Param("example") CollectExample example);

    int updateByExample(@Param("record") Collect record, @Param("example") CollectExample example);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);

    @Select({
            "select c.id as id,g.brief as brief,g.name as name,g.pic_url as picUrl,g.retail_price as retailPrice,c.type as type,c.value_id as valueId",
            " from cskaoyan_mall_collect c left join cskaoyan_mall_goods g on c.value_id = g.id",
            "where user_id = #{userId} and type = #{type}"
    })
    List<GoodsForCollect> selectGoodsForCollect(@Param("type") Integer type,@Param("userId") Integer userId);
}