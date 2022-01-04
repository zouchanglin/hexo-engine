package cn.tim.hexo_admin.cache;


import cn.tim.hexo_admin.entity.Article;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ArticleCache {
    ConcurrentHashMap<String, Article> map = new ConcurrentHashMap<>();

    public List<Article> list;

    public void putArticle(Article article){
        map.put(article.getAbbrlink(), article);
    }

    public void getArticle(String articleAbbrlink){
        map.getOrDefault(articleAbbrlink, new Article());
    }

    public ConcurrentHashMap<String, Article> getMap() {
        return map;
    }
}
