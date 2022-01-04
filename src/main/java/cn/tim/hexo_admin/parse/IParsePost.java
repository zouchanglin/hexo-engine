package cn.tim.hexo_admin.parse;

import cn.tim.hexo_admin.entity.Article;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Hexo文件解析服务
 */
public interface IParsePost {
    /**
     * 对传入文件进行解析
     * @param postFile 文件
     * @return Article
     */
    Article parsePost(File postFile) throws IOException;

    List<Article> parseAllPost(File sourceDir);
}
