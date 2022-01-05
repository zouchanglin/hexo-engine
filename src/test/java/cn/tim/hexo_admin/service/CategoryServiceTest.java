package cn.tim.hexo_admin.service;

import cn.tim.hexo_admin.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryServiceTest {

    @Resource
    CategoryService categoryService;

    @Resource
    ArticleService articleService;

    @Test
    public void getAllCategory() {
        Set<String> categories = categoryService.getAllCategory();
        for(String category: categories){
            log.info("category = " + category);
        }
        assertNotNull(categories);
    }

    @Test
    public void getAllArticleByCategory() throws UnsupportedEncodingException {
        List<Article> articles = categoryService.getAllArticleByCategory("软件测试技术");
        log.info("articles size = " + articles.size());
        for(Article article: articles){
            log.info("" + article);
        }
    }

    @Test
    public void updateCategory() {
        categoryService.updateCategory("JavaEE/Web", "JavaEE/Web");
    }
}
