package cn.tim.hexo_admin.init;

import cn.tim.hexo_admin.cache.ArticleCache;
import cn.tim.hexo_admin.config.HexoPath;
import cn.tim.hexo_admin.entity.Article;
import cn.tim.hexo_admin.parse.IParsePost;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

@Slf4j
@Component
@Configuration
public class HexoApplicationRunner implements ApplicationRunner {

    @Resource
    IParsePost parsePost;

    @Resource
    HexoPath hexoPath;

    @Resource
    ArticleCache articleCache;

    @Override
    public void run(ApplicationArguments args) {
        File hexoSourceDir = new File(hexoPath.getPath());
        if(!hexoSourceDir.exists()) {
            throw new RuntimeException("Hexo source path not exists!");
        }
        List<Article> articles = parsePost.parseAllPost(hexoSourceDir);
        for(Article article: articles){
            articleCache.putArticle(article);
        }

        articleCache.list = articles;
    }
}
