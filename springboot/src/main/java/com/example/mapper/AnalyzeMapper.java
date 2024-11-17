package com.example.mapper;

import com.example.dto.SingleAttributeItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AnalyzeMapper {
    @Select("SELECT * FROM ads_rate_use_count ORDER BY star_rating")
    List<SingleAttributeItem> selectConsumptionByRating();

    @Select("SELECT * FROM ads_area_count ORDER BY count")
    List<SingleAttributeItem> selectTopDistrictsByHomestayCount();

    @Select("SELECT * FROM ads_type_count ORDER BY CAST(SUBSTRING(layout_desc,1,LENGTH(layout_desc)-1) AS UNSIGNED)")
    List<SingleAttributeItem> selectLayoutCount();

    @Select("SELECT * FROM ads_cost_use_count ORDER BY price")
    List<SingleAttributeItem> selectPriceConsumptionRelation();

    @Select("SELECT * FROM ads_fav_use_count")
    List<SingleAttributeItem> selectRelationBetweenFavAndConsumption();

    @Select("SELECT * FROM ads_tag_map_count ORDER BY count desc")
    List<SingleAttributeItem> selectHomestayTagInfos();
}
