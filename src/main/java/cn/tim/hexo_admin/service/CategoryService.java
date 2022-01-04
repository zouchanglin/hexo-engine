package cn.tim.hexo_admin.service;

import cn.tim.hexo_admin.entity.Article;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    /**
     * 返回所有类别名称
     * @return 文章分类名列表
     */
    Set<String> getAllCategory();

    /**
     * 得到此类型的全部文章
     * @param name 类别名称
     * @return 文章集合
     */
    List<Article> getAllArticleByCategory(String name);

    /**
     * 改变原本的分类名称
     * @param oldName 旧名称
     * @param newName 新名称（如果已经存在，就合并到新分类）
     * @return 分类改变的文章列表
     */
    List<Article> updateCategory(String oldName, String newName);
}
