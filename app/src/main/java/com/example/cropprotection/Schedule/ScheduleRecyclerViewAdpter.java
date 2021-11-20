package com.example.cropprotection.Schedule;

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


public class ScheduleRecyclerViewAdpter extends RecyclerView.Adapter<ScheduleRecyclerViewAdpter.ViewHolder> implements Filterable {

    private Context context;
    private List<SentSchedule> list;
    private List<SentSchedule> scheduleListFiltered;
    private ScheduleAdapterListener listener;

    public ScheduleRecyclerViewAdpter(Context context, List<SentSchedule> list) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        this.scheduleListFiltered = list;
    }

    @Override
    public ScheduleRecyclerViewAdpter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.content_schedule, parent, false);
        return new ScheduleRecyclerViewAdpter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ScheduleRecyclerViewAdpter.ViewHolder holder, int position) {
        SentSchedule SentSchedule = scheduleListFiltered.get(position);

        holder.Subject.setText(SentSchedule.getSubject());
        holder.venue.setText(String.valueOf(SentSchedule.getVenue()));
        holder.postdate.setText(String.valueOf(SentSchedule.getPostDate()));
        holder.ids.setText(String.valueOf(SentSchedule.getid()));
        final String  a = holder.ids.getText().toString();
        holder.Subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    Intent intent = new Intent(view.getContext(), ScheduleDetail.class);
                    intent.putExtra("ids",a);
                    view.getContext().startActivity(intent);
                } catch (Exception EX) {
                    Toast.makeText(view.getContext(), EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.postdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    Intent intent = new Intent(view.getContext(),ScheduleDetail.class);
                    intent.putExtra("ids",a);
                    view.getContext().startActivity(intent);
                } catch (Exception EX) {
                    Toast.makeText(view.getContext(), EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.ids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    Intent intent = new Intent(view.getContext(), ScheduleDetail.class);
                    intent.putExtra("ids",a);
                    view.getContext().startActivity(intent);
                } catch (Exception EX) {
                    Toast.makeText(view.getContext(), EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {

        return scheduleListFiltered.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Subject, venue, ids,postdate;

        public ViewHolder(View itemView) {
            super(itemView);

            Subject = itemView.findViewById(R.id.Subject);
            postdate = itemView.findViewById(R.id.postdate);
            venue = itemView.findViewById(R.id.venue);
            ids = itemView.findViewById(R.id.ids);
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
                    scheduleListFiltered = list;
                } else {
                    List<SentSchedule> filteredList = new ArrayList<>();
                    for (SentSchedule row : list) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if ( row.getPostDate().toLowerCase().contains(charString.toLowerCase()) || row.getDescription().toLowerCase().contains(charString.toLowerCase()) || row.getVenue().toLowerCase().contains(charString.toLowerCase())|| row.getSubject().toLowerCase().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    scheduleListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values =scheduleListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                scheduleListFiltered = (ArrayList<SentSchedule>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ScheduleAdapterListener {
        void onContactSelected(SentSchedule SentSchedule);
    }
}
