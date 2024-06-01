package com.sustech.football.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sustech.football.entity.WxArticle;

import java.util.List;

public interface WxArticleService extends IService<WxArticle> {

    List<WxArticle> getLatestArticles();
}
