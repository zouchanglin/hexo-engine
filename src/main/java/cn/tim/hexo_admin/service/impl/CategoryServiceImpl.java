package cn.tim.hexo_admin.service.impl;

import cn.tim.hexo_admin.cache.ArticleCache;
import cn.tim.hexo_admin.entity.Article;
import cn.tim.hexo_admin.service.ArticleService;
import cn.tim.hexo_admin.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    ArticleCache articleCache;

    @Resource
    ArticleService articleService;

    @Override
    public Set<String> getAllCategory() {
        return articleCache.list.stream().map(Article::getCategories).collect(Collectors.toSet());
    }

    @Override
    public List<Article> getAllArticleByCategory(String name) {
        val retArticles = new ArrayList<Article>();
        for(Article article: articleCache.list){
            if(article.getCategories().equals(name)){
                retArticles.add(article);
            }
        }
        return retArticles;
    }

    @Override
    public List<Article> updateCategory(String oldName, String newName) {
        val articles = getAllArticleByCategory(oldName);
        val retArticles = new ArrayList<Article>();
        for(Article article: articles){
            updateArticleCategory(article, newName);
            retArticles.add(article);
        }
        return retArticles;
    }

    // 更新某篇文章的分类
    private void updateArticleCategory(Article article, String newName) {
        if(article == null) return;
        article.setCategories(newName);
        articleService.updateArticle(article);
    }
}
