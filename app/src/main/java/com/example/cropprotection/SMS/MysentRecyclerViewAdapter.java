package com.example.cropprotection.SMS;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cropprotection.R;

import java.util.List;


public class MysentRecyclerViewAdapter extends RecyclerView.Adapter<MysentRecyclerViewAdapter.ViewHolder> {

        private Context context;
        private List<SentMessage> list;

        public MysentRecyclerViewAdapter(Context context, List<SentMessage> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.fragment_sent, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
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