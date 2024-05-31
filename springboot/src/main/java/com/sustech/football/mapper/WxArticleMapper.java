package com.sustech.football.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sustech.football.entity.WxArticle;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WxArticleMapper extends BaseMapper<WxArticle> {

    @Select("SELECT * FROM wx_article ORDER BY time DESC LIMIT 10")
    List<WxArticle> selectLatestArticles();
}
