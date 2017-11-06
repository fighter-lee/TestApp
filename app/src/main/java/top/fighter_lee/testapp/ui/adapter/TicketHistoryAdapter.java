package top.fighter_lee.testapp.ui.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.fighter_lee.testapp.R;
import top.fighter_lee.testapp.gloable.App;
import top.fighter_lee.testapp.info.TicketOpenData;

/**
 * @author fighter_lee
 * @date 2017/11/6
 */
public class TicketHistoryAdapter extends RecyclerView.Adapter<TicketHistoryAdapter.MyViewHolder> {

    ArrayList<TicketOpenData> data = new ArrayList<>();


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_ticket_history, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(data.get(position), position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(ArrayList<TicketOpenData> ticketTypes) {
        this.data = ticketTypes;
        notifyDataSetChanged();
    }

    public View.OnClickListener mOnItemClickListener;
    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        mOnItemClickListener = onClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvOpenSerial)
        TextView tvOpenSerial;
        @BindView(R.id.tvOpenTime)
        TextView tvOpenTime;
        @BindView(R.id.rlTime)
        RelativeLayout rlTime;
        @BindView(R.id.rvOpenResult)
        RecyclerView rvOpenResult;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(final TicketOpenData ticketOpenData, int realPosition) {
            tvOpenTime.setText("开奖日期：" + ticketOpenData.getTime());
            tvOpenSerial.setText("第" + ticketOpenData.getExpect() + "期");

            OpenCodeAdapter openCodeAdapter = new OpenCodeAdapter(App.context, ticketOpenData.getOpenCode());
            rvOpenResult.setLayoutManager(new GridLayoutManager(App.context, 7));
            rvOpenResult.setAdapter(openCodeAdapter);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        v.setTag(ticketOpenData);
                        mOnItemClickListener.onClick(v);
                    }
                }
            });

        }
    }
}
