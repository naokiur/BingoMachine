package jp.ne.naokiur.bingomachine.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nao-ur on 2017/09/14.
 */

public enum HistoryColumn {

    FIRST_COLUMN(1, 1, 15),
    SECOND_COLUMN(2, 16, 30),
    THIRD_COLUMN(3, 31, 45),
    FORTH_COLUMN(4, 46, 60),
    FIFTH_COLUMN(5, 61, 75);

    private int index;
    private int begin;
    private int end;

    HistoryColumn(int index, int begin, int end) {
        this.index = index;
        this.begin = begin;
        this.end = end;
    }

    public int getIndex() {
        return index;
    }

    public int getBegin() {
        return begin;
    }

    public int getEnd() {
        return end;
    }

    public boolean isRange(int currentNumber) {
        if (range(this.getBegin(), this.getEnd() + 1).contains(currentNumber)) {
            return true;
        }

        return false;
    }

    /**
     * Create integer list from 'begin' to 'end - 1'.
     * The reason of 'end - 1' is range function of Python3 is like this.
     *
     * @param begin begin number. this number is contained.
     * @param end   end number. this number is not contained.
     * @return Integer list.
     */
    private List<Integer> range(final int begin, final int end) {
        return new ArrayList<Integer>() {
            {
                for (int i = begin; i < end; i++) {
                    add(i);
                }
            }
        };
    }
}
