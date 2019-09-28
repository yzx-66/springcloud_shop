package com.yzx.shop.item.mapper;

import com.yzx.shop.item.entity.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {

    @Insert("INSERT INTO tb_category_brand(category_id, brand_id) VALUES (#{cid},#{bid})")
    void insertBrandAndCategory(@Param("cid")long cid,@Param("bid")long bid);

    @Select("SELECT * FROM tb_brand a INNER JOIN tb_category_brand b on a.id=b.brand_id where b.category_id=#{cid3}")
    List<Brand> queryBrandsByCid3(@Param("cid3")long cid3);
}
