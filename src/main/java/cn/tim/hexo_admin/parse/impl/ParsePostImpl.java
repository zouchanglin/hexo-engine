package cn.tim.hexo_admin.parse.impl;

import cn.tim.hexo_admin.entity.Article;
import cn.tim.hexo_admin.exception.HexoException;
import cn.tim.hexo_admin.parse.IParsePost;
import cn.tim.hexo_admin.parse.StateMachine;
import cn.tim.hexo_admin.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.FileUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ParsePostImpl implements IParsePost {
    static final String TITLE = "title:";
    static final String TAG = "tag:";     // 目前有两种tag、tags
    static final String TAGS = "tags:";
    static final String CUT_LINE = "---";
    static final String CATEGORY = "categories:";
    static final String LINK = "abbrlink:";
    static final String DATE = "date:";

    @Override
    public Article parsePost(@NonNull File postFile) throws IOException {
        if(!postFile.exists()) {
            throw new HexoException("File not exists");
        }
        val article = new Article();
        article.setPostFile(postFile);
        val tagList = new ArrayList<String>();

        List<String> lines = FileUtils.readLines(postFile, "UTF-8");

        StateMachine machine = new StateMachine();
        val contentBuilder = new StringBuilder();
        // 设置初始标志
        machine.current = StateMachine.START;
        for(String line: lines){
            String oldLine = line;
            line = line.trim();
            if(machine.current != StateMachine.CONTENT) {
                String trim = line.substring(line.indexOf(":") + 1).trim();
                if(line.contains(TITLE)){  // 解析标题
                    machine.current = StateMachine.TITLE;
                    article.setTitle(trim);
                } else if(line.contains(TAG)){
                    machine.current = StateMachine.TAG;
                    if(line.length() != TAG.length()){
                        tagList.add(line.substring(line.indexOf(":")));
                    }
                } else if(line.contains(TAGS)){
                    machine.current = StateMachine.TAG;
                    if(line.length() != TAGS.length()){
                        // 说明下面一行或者几行是TAG
                        tagList.add(trim);
                    }
                } else if(line.contains(CATEGORY)){ // 解析分类
                    machine.current = StateMachine.CATEGORY;
                    if(line.length() != CATEGORY.length()){
                        article.setCategories(trim);
                    }
                } else if(line.contains(LINK)){ // 解析ABBRLINK
                    machine.current = StateMachine.ABBRLINK;
                    article.setAbbrlink(trim);
                } else if(line.contains(DATE)){  // 解析日期时间
                    machine.current = StateMachine.DATE;
                    try {
                        Date date = DateUtils.artDfs.parse(line.substring(line.indexOf(": ") + 2));
                        article.setDate(date);
                    } catch (ParseException e) {
                        log.error("article title = " + article.getTitle());
                    }
                } else if(line.contains(CUT_LINE)){
                    // 进入第一行解析
                    if(machine.FIRST_CUT_LINE) {
                        machine.FIRST_CUT_LINE = false;
                    }else {
                        // 开始正文解析
                        machine.current = StateMachine.CONTENT;
                    }
                }else {
                    // 如果此时处于解析TAG的状态
                    String tagOrCategory = line.substring(line.indexOf("-") + 1).trim();
                    if(machine.current == StateMachine.TAG){
                        tagList.add(tagOrCategory);
                    }else if(machine.current == StateMachine.CATEGORY) {
                        // 如果此时处于解析分类的状态
                        article.setCategories(tagOrCategory);
                    }
                }
            }else {
                // 正文解析
                contentBuilder.append(oldLine).append("\n");
            }
            machine.currentLineIndex++;
        }

        String[] tags = new String[tagList.size()];
        article.setTags(tagList.toArray(tags));
        article.setContent(contentBuilder.toString());
        return article;
    }

    @Override
    public List<Article> parseAllPost(File sourceDir) {
        val files = sourceDir.listFiles();
        if(!sourceDir.exists() || files == null || files.length == 0) return new ArrayList<>();
        List<Article> list = new ArrayList<>(files.length);
        for(File file: files) {
            try {
                list.add(parsePost(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
