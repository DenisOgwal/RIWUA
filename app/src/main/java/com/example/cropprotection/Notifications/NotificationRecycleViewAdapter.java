package com.example.cropprotection.Notifications;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.cropprotection.R;

import java.util.ArrayList;
import java.util.List;

    public class NotificationRecycleViewAdapter extends RecyclerView.Adapter<com.example.cropprotection.Notifications.NotificationRecycleViewAdapter.ViewHolder> implements Filterable {

        private Context context;
        private List<SentNotification> list;
        private List<SentNotification> notificationListFiltered;
        private notificationAdapterListener listener;

        public NotificationRecycleViewAdapter(Context context, List<SentNotification> list) {
            this.context = context;
            this.list = list;
            this.listener = listener;
            this.notificationListFiltered= list;
        }

        @Override
        public NotificationRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.content_notifications, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(NotificationRecycleViewAdapter.ViewHolder holder, int position) {
            SentNotification SentNotification = notificationListFiltered.get(position);

            holder.Subject.setText(SentNotification.getSubject());
            holder.Message.setText(String.valueOf(SentNotification.getMessage()));
            holder.timesent.setText(String.valueOf(SentNotification.gettimesent()));
            holder.Sender.setText(String.valueOf(SentNotification.getSender()));
        }

        @Override
        public int getItemCount()
        {
            return notificationListFiltered.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView Subject, Message, timesent, Sender;

            public ViewHolder(View itemView) {
                super(itemView);

                Subject = itemView.findViewById(R.id.notificationsubject);
                Message = itemView.findViewById(R.id.notificationmessage);
                timesent = itemView.findViewById(R.id.datesenttext);
                Sender = itemView.findViewById(R.id.sentby);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // send selected contact in callback
                        //listener.onContactSelected(adviseAdapterListener.get(getAdapterPosition()));
                    }
                });
            }
        }
        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        notificationListFiltered = list;
                    } else {
                        List<SentNotification> filteredList = new ArrayList<>();
                        for (SentNotification row : list) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getSender().toLowerCase().contains(charString.toLowerCase()) || row.getMessage().contains(charSequence)|| row.getSubject().toLowerCase().contains(charSequence)) {
                                filteredList.add(row);
                            }
                        }

                        notificationListFiltered = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values =notificationListFiltered;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    notificationListFiltered = (ArrayList<SentNotification>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

        public interface notificationAdapterListener {
            void onContactSelected(SentNotification SentNotification);
        }
    }
