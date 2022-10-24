package com.Dither.cropprotection.Statistics;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.Dither.cropprotection.R;

import java.util.ArrayList;
import java.util.List;

    public class StatisticsRecyclerViewAdapter extends RecyclerView.Adapter<com.Dither.cropprotection.Statistics.StatisticsRecyclerViewAdapter.ViewHolder> implements Filterable {

        private Context context;
        private List<SentStatistics> list;
        private List<SentStatistics> statisticsListFiltered;
        private stastisticsAdapterListener listener;

        public StatisticsRecyclerViewAdapter(Context context, List<SentStatistics> list) {
            this.context = context;
            this.list = list;
            this.listener = listener;
            this.statisticsListFiltered = list;
        }

        @Override
        public com.Dither.cropprotection.Statistics.StatisticsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.content_statistics, parent, false);
            return new com.Dither.cropprotection.Statistics.StatisticsRecyclerViewAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(com.Dither.cropprotection.Statistics.StatisticsRecyclerViewAdapter.ViewHolder holder, int position) {
            SentStatistics SentStatistics = statisticsListFiltered.get(position);

            holder.Subject.setText(SentStatistics.getSubject());
            holder.timesent.setText(String.valueOf(SentStatistics.gettimesent()));
            holder.Sender.setText(String.valueOf(SentStatistics.getSender()));
            holder.adviseid.setText(String.valueOf(SentStatistics.getAttachmentid()));
            final String  a = holder.adviseid.getText().toString();
            holder.Subject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try{
                        Intent intent = new Intent(view.getContext(), StatisticsDetail.class);
                        intent.putExtra("adviseid",a);
                        view.getContext().startActivity(intent);
                    } catch (Exception EX) {
                        Toast.makeText(view.getContext(), EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.timesent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try{
                        Intent intent = new Intent(view.getContext(), StatisticsDetail.class);
                        intent.putExtra("adviseid",a);
                        view.getContext().startActivity(intent);
                    } catch (Exception EX) {
                        Toast.makeText(view.getContext(), EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.Sender.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try{
                        Intent intent = new Intent(view.getContext(),StatisticsDetail.class);
                        intent.putExtra("adviseid",a);
                        view.getContext().startActivity(intent);
                    } catch (Exception EX) {
                        Toast.makeText(view.getContext(), EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.adviseid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try{
                        Intent intent = new Intent(view.getContext(), StatisticsDetail.class);
                        intent.putExtra("adviseid",a);
                        view.getContext().startActivity(intent);
                    } catch (Exception EX) {
                        Toast.makeText(view.getContext(), EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return statisticsListFiltered.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView Subject, Message, timesent, Sender,adviseid;

            public ViewHolder(View itemView) {
                super(itemView);

                Subject = itemView.findViewById(R.id.advisesubject);
                timesent = itemView.findViewById(R.id.datesenttext);
                Sender = itemView.findViewById(R.id.sentby);
                adviseid = itemView.findViewById(R.id.adviseids);
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
                        statisticsListFiltered = list;
                    } else {
                        List<SentStatistics> filteredList = new ArrayList<>();
                        for (SentStatistics row : list) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getSender().toLowerCase().contains(charString.toLowerCase()) || row.getMessage().contains(charSequence)|| row.getSubject().toLowerCase().contains(charSequence)) {
                                filteredList.add(row);
                            }
                        }

                        statisticsListFiltered = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values =statisticsListFiltered;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    statisticsListFiltered= (ArrayList<SentStatistics>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

        public interface stastisticsAdapterListener {
            void onContactSelected(SentStatistics SentStatistics);
        }
    }


