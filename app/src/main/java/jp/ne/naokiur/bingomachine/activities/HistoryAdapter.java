package jp.ne.naokiur.bingomachine.activities;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import jp.ne.naokiur.bingomachine.R;
import jp.ne.naokiur.bingomachine.service.HistoryItem;

/**
 * Created by nao-ur on 2017/07/23.
 */

public class HistoryAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<HistoryItem> historyList;

    public HistoryAdapter(Context context, List<HistoryItem> historyList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
        this.historyList = historyList;
    }

    private class Holder {
        private TextView historyItem;
    }

    @Override
    public int getCount() {
        return historyList.size();
    }

    @Override
    public Object getItem(int position) {
        return historyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return historyList.get(position).getNumber();
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

        holder.historyItem.setText(String.valueOf(historyList.get(position).getNumber()));
        if (historyList.get(position).isDrawn()) {
            holder.historyItem.setTextColor(ContextCompat.getColor(context, R.color.drawn_item));
        }

        return convertView;
    }
}
