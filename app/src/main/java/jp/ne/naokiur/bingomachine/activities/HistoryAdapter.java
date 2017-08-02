package jp.ne.naokiur.bingomachine.activities;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import jp.ne.naokiur.bingomachine.R;
import jp.ne.naokiur.bingomachine.service.HistoryItem;

/**
 * Created by nao-ur on 2017/07/23.
 */

public class HistoryAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private SparseArray<HistoryItem> historyStatuses;

    public HistoryAdapter(Context context, SparseArray<HistoryItem> historyStatuses) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
        this.historyStatuses = historyStatuses;
    }

    private class Holder {
        private TextView historyItem;
    }

    @Override
    public int getCount() {
        return historyStatuses.size();
    }

    @Override
    public Object getItem(int position) {
        return historyStatuses.get(historyStatuses.keyAt(position));
    }

    @Override
    public long getItemId(int position) {
        return historyStatuses.get(historyStatuses.keyAt(position)).getNumber();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.history_item, null);
            holder = new Holder();

            holder.historyItem = (TextView) convertView.findViewById(R.id.history_item);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.historyItem.setText(String.valueOf(historyStatuses.get(historyStatuses.keyAt(position)).getNumber()));
        if (historyStatuses.get(historyStatuses.keyAt(position)).isDrawn()) {
            holder.historyItem.setTextColor(ContextCompat.getColor(context, R.color.drawn_item));
        }

        return convertView;
    }
}
