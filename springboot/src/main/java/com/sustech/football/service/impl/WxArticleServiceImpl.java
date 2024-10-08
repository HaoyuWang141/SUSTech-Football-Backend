package com.sustech.football.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.football.entity.WxArticle;
import com.sustech.football.mapper.WxArticleMapper;
import com.sustech.football.service.WxArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxArticleServiceImpl extends ServiceImpl<WxArticleMapper, WxArticle> implements WxArticleService {
    @Override
    public List<WxArticle> getLatestArticles() {
        return baseMapper.selectLatestArticles();
    }
}
