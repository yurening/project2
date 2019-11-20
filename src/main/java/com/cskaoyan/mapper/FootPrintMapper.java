package com.cskaoyan.mapper;

import com.cskaoyan.bean.user.FootPrint;
import com.cskaoyan.bean.user.FootPrintExample;
import com.cskaoyan.bean.user.FootprintForList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FootPrintMapper {
    long countByExample(FootPrintExample example);

    int deleteByExample(FootPrintExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FootPrint record);

    int insertSelective(FootPrint record);

    List<FootPrint> selectByExample(FootPrintExample example);

    FootPrint selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FootPrint record, @Param("example") FootPrintExample example);

    int updateByExample(@Param("record") FootPrint record, @Param("example") FootPrintExample example);

    int updateByPrimaryKeySelective(FootPrint record);

    int updateByPrimaryKey(FootPrint record);

    @Select({
            "select f.id as id,g.brief as brief,g.name as name,g.pic_url as picUrl,g.id as goodsId,f.add_time as addTime,g.retail_price as retailPrice",
            " from cskaoyan_mall_footprint f left join cskaoyan_mall_goods g on f.goods_id = g.id",
            "where user_id = #{userId} and f.deleted = 0"
    })
    List<FootprintForList> footPrintList(@Param("userId") Integer userId);
}