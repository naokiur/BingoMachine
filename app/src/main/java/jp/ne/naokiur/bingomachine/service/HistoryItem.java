package jp.ne.naokiur.bingomachine.service;

/**
 * Created by nao-ur on 2017/08/01.
 */

public class HistoryItem {
    private Integer number;
    private boolean isDrawn;

    public HistoryItem(Integer number, boolean isDrawn) {
        this.number = number;
        this.isDrawn = isDrawn;
    }

    public Integer getNumber() {
        return number;
    }

    public boolean isDrawn() {
        return isDrawn;
    }

    public void setDrawn(boolean drawn) {
        isDrawn = drawn;
    }
}
