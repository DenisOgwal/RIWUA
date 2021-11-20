package com.example.cropprotection.SMS;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cropprotection.R;

import java.util.List;

public class MyRecievedSMSRecyclerViewAdapter extends RecyclerView.Adapter<MyRecievedSMSRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<SentMessage> list;

    public MyRecievedSMSRecyclerViewAdapter(Context context, List<SentMessage> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_recievedsms, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(MyRecievedSMSRecyclerViewAdapter.ViewHolder holder, int position) {
        SentMessage SentMessage = list.get(position);

        holder.textsender.setText(SentMessage.getSender());
        holder.textmessage.setText(String.valueOf(SentMessage.getMessage()));
        holder.texttimesent.setText(String.valueOf(SentMessage.gettimesent()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textsender, textmessage, texttimesent;

        public ViewHolder(View itemView) {
            super(itemView);

            textsender = itemView.findViewById(R.id.Sendername);
            textmessage = itemView.findViewById(R.id.messagesent);
            texttimesent = itemView.findViewById(R.id.datesent);
        }
    }
}
