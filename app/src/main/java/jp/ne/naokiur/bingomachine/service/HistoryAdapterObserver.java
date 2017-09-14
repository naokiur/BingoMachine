package jp.ne.naokiur.bingomachine.service;

import android.content.Context;
import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;

import jp.ne.naokiur.bingomachine.activities.HistoryAdapter;

/**
 * Created by nao-ur on 2017/09/14.
 */

public class HistoryAdapterObserver {
    private Map<Integer, HistoryAdapter<SparseArray<HistoryItem>>> adapterMap;
    private final int maxNumber;
    private final Context context;

    public HistoryAdapterObserver(int maxNumber, Context context) {
        this.maxNumber = maxNumber;
        this.context = context;

        this.adapterMap = new HashMap<Integer, HistoryAdapter<SparseArray<HistoryItem>>>();
        initialize();
    }

    public void updateAdapters(Integer current) {

        HistoryColumn first = HistoryColumn.FIRST_COLUMN;
        HistoryColumn second = HistoryColumn.SECOND_COLUMN;
        HistoryColumn third = HistoryColumn.THIRD_COLUMN;
        HistoryColumn forth = HistoryColumn.FORTH_COLUMN;
        HistoryColumn fifth = HistoryColumn.FIFTH_COLUMN;

        if (first.isRange(current)) {
            adapterMap.get(first.getIndex()).getHistoryStatuses().get(current).setDrawn(true);

        } else if (second.isRange(current)) {
            adapterMap.get(second.getIndex()).getHistoryStatuses().get(current).setDrawn(true);

        } else if (third.isRange(current)) {
            adapterMap.get(third.getIndex()).getHistoryStatuses().get(current).setDrawn(true);

        } else if (forth.isRange(current)) {
            adapterMap.get(forth.getIndex()).getHistoryStatuses().get(current).setDrawn(true);

        } else if (fifth.isRange(current)) {
            adapterMap.get(fifth.getIndex()).getHistoryStatuses().get(current).setDrawn(true);
        }

    }

    public void reflesh() {
        initialize();
    }

    public HistoryAdapter<SparseArray<HistoryItem>> getAdapter(int key) {
        return adapterMap.get(key);
    }

    private void initialize() {

        HistoryColumn first = HistoryColumn.FIRST_COLUMN;
        this.adapterMap.put(first.getIndex(), generateInitialAdapter(first.getBegin(), first.getEnd() <= maxNumber ? first.getEnd() : maxNumber));

        HistoryColumn second = HistoryColumn.SECOND_COLUMN;
        this.adapterMap.put(second.getIndex(), generateInitialAdapter(second.getBegin(), second.getEnd() <= maxNumber ? second.getEnd() : maxNumber));

        HistoryColumn third = HistoryColumn.THIRD_COLUMN;
        this.adapterMap.put(third.getIndex(), generateInitialAdapter(third.getBegin(), third.getEnd() <= maxNumber ? third.getEnd() : maxNumber));

        HistoryColumn forth = HistoryColumn.FORTH_COLUMN;
        this.adapterMap.put(forth.getIndex(), generateInitialAdapter(forth.getBegin(), forth.getEnd() <= maxNumber ? forth.getEnd() : maxNumber));

        HistoryColumn fifth = HistoryColumn.FIFTH_COLUMN;
        this.adapterMap.put(fifth.getIndex(), generateInitialAdapter(fifth.getBegin(), fifth.getEnd() <= maxNumber ? fifth.getEnd() : maxNumber));

    }

    private HistoryAdapter<SparseArray<HistoryItem>> generateInitialAdapter(final int begin, final int end) {

        return new HistoryAdapter<SparseArray<HistoryItem>>(context, new SparseArray<HistoryItem>() {
            {
                for (int i = begin; i <= end; i++) {
                    put(i, new HistoryItem(i, false));

                }
            }
        });
    }
}
