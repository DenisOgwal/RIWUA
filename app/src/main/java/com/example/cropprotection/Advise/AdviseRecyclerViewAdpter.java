package com.example.cropprotection.Advise;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cropprotection.R;

import java.util.ArrayList;
import java.util.List;


    public class AdviseRecyclerViewAdpter extends RecyclerView.Adapter<com.example.cropprotection.Advise.AdviseRecyclerViewAdpter.ViewHolder> implements Filterable {

        private Context context;
        private List<SentAdvise> list;
        private List<SentAdvise> adviseListFiltered;
        private adviseAdapterListener listener;

        public AdviseRecyclerViewAdpter(Context context, List<SentAdvise> list) {
            this.context = context;
            this.list = list;
            this.listener = listener;
            this.adviseListFiltered = list;
        }

        @Override
        public com.example.cropprotection.Advise.AdviseRecyclerViewAdpter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.content_advise, parent, false);
            return new com.example.cropprotection.Advise.AdviseRecyclerViewAdpter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(com.example.cropprotection.Advise.AdviseRecyclerViewAdpter.ViewHolder holder, int position) {
            SentAdvise SentAdvise = adviseListFiltered.get(position);

            holder.Subject.setText(SentAdvise.getSubject());
            holder.timesent.setText(String.valueOf(SentAdvise.gettimesent()));
            holder.Sender.setText(String.valueOf(SentAdvise.getSender()));
            holder.adviseid.setText(String.valueOf(SentAdvise.getAttachmentid()));
            final String  a = holder.adviseid.getText().toString();
            holder.Subject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try{
                        Intent intent = new Intent(view.getContext(), AdviseDetail.class);
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
                        Intent intent = new Intent(view.getContext(), AdviseDetail.class);
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
                        Intent intent = new Intent(view.getContext(), AdviseDetail.class);
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
                        Intent intent = new Intent(view.getContext(), AdviseDetail.class);
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

            return adviseListFiltered.size();
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
                        adviseListFiltered = list;
                    } else {
                        List<SentAdvise> filteredList = new ArrayList<>();
                        for (SentAdvise row : list) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getSender().toLowerCase().contains(charString.toLowerCase()) || row.getMessage().contains(charSequence)|| row.getSubject().toLowerCase().contains(charSequence)) {
                                filteredList.add(row);
                            }
                        }

                        adviseListFiltered = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values =adviseListFiltered;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    adviseListFiltered = (ArrayList<SentAdvise>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

        public interface adviseAdapterListener {
            void onContactSelected(SentAdvise SentAdvise);
        }
    }
