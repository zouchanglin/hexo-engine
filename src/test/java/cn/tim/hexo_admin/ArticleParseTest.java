package cn.tim.hexo_admin;


import cn.tim.hexo_admin.entity.Article;
import cn.tim.hexo_admin.parse.IParsePost;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ArticleParseTest {
    @Resource
    IParsePost parsePost;

    @Test
    public void ArticleParse(){
        if (testParsePost()) return;

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String line = scanner.nextLine();
        }
    }

    private boolean testParsePost() {
        String sourcePath = "D:\\hexo\\source";
        String postPath = sourcePath + "\\_posts";

        File _post = new File(postPath);
        if(!_post.exists() || !_post.isDirectory()) return true;
        File[] posts = _post.listFiles();

        List<Article> articles = new ArrayList<>();

        if(posts == null || posts.length == 0) return true;
        for(File post : posts) {
            try {
                val article = parsePost.parsePost(post);
                articles.add(article);
                if(article.getCategories() == null || article.getCategories().equals("null")){
                    log.info("article = {}", article);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        val categoryStream = articles.stream().map(Article::getCategories);
        final Set<String> strings = categoryStream.collect(Collectors.toSet());
        for(String cate: strings){
            log.info("my category = {}", cate);
        }
        log.info("size() = {}", strings.size());
        return false;
    }
}
