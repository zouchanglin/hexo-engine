package cn.tim.hexo_admin.parse;

public class StateMachine {
    /**
     * 开始解析
     */
    public static int START = 0;
    public static int TITLE = 1;
    public static int TAG = 2;
    public static int CATEGORY = 3;
    public static int ABBRLINK = 4;
    public static int DATE = 5;
    public static int CONTENT = 6;
    public static int END = 7;


    public int current = -1;
    public int currentLineIndex = 0;

    // 是否第一次遇到 "---"
    public boolean FIRST_CUT_LINE = true;

    public void recoverState(){
        int currentState = -1;
        int currentLineIndex = 0;
    }
}
