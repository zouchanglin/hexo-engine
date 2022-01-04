package cn.tim.hexo_admin.service;


import cn.tim.hexo_admin.entity.Article;
import cn.tim.hexo_admin.utils.DateUtils;
import cn.tim.hexo_admin.utils.SeparatorUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class ArticleService {

    /**
     * 更新某篇文章，Object -> File
     * @param article Article
     */
    public void updateArticle(Article article) {
        val postFile = article.getPostFile();
        StringBuilder md = new StringBuilder();
        md.append("---").append(SeparatorUtils.newLine);
        md.append("title: ").append(article.getTitle()).append(SeparatorUtils.newLine);
        md.append("categories: ").append(article.getCategories()).append(SeparatorUtils.newLine);

        md.append("tags: ").append(SeparatorUtils.newLine);
        for(String tag: article.getTags()){
            md.append("  - ").append(tag).append(SeparatorUtils.newLine);
        }

        md.append("abbrlink: ").append(article.getAbbrlink()).append(SeparatorUtils.newLine);
        md.append("date: ").append(DateUtils.writeDfs.format(article.getDate())).append(SeparatorUtils.newLine);
        md.append("---").append(SeparatorUtils.newLine);

        md.append(article.getContent());

        try {
            // 先删除，再新建文件即可
            FileUtils.forceDelete(postFile);
            boolean newFile = postFile.createNewFile();
            FileUtils.write(postFile, md, "utf-8");
            log.info("create newFile ret = " + newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
