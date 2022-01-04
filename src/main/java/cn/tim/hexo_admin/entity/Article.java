package cn.tim.hexo_admin.entity;

import lombok.Data;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

@Data
public class Article {
    /**
     * 对应文件
     */
    File postFile;
    /**
     * 文章标题
     */
    String title;
    /**
     * 标签
     */
    String[] tags;
    /**
     * 分类
     */
    String categories;
    /**
     * linkId
     */
    String abbrlink;
    /**
     * 日期
     */
    Date date;
    /**
     * 内容
     */
    String content;

    @Override
    public String toString() {
        return "Article{" +
                "postFile=" + postFile +
                ", title='" + title + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", categories='" + categories + '\'' +
                ", abbrlink='" + abbrlink + '\'' +
                ", date=" + date +
                '}';
    }
}
