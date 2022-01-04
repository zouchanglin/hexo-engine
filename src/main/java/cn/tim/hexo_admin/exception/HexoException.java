package cn.tim.hexo_admin.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HexoException extends RuntimeException{
    private static final String TAG = "HexoException";

    public HexoException(String message) {
        super(message);
    }

    @Override
    public void printStackTrace() {
        log.error(TAG, this);
    }
}
