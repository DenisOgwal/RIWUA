package com.example.cropprotection.CropDiseases;

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

    public class CropDiseasesRecyclerViewAdapter extends RecyclerView.Adapter<com.example.cropprotection.CropDiseases.CropDiseasesRecyclerViewAdapter.ViewHolder> implements Filterable {

        private Context context;
        private List<SentDiseases> list;
        private List<SentDiseases> diseaseListFiltered;
        private diseaseAdapterListener listener;

        public CropDiseasesRecyclerViewAdapter(Context context, List<SentDiseases> list) {
            this.context = context;
            this.list = list;
            this.listener = listener;
            this.diseaseListFiltered = list;
        }

        @Override
        public com.example.cropprotection.CropDiseases.CropDiseasesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.content_crop_diseases, parent, false);
            return new com.example.cropprotection.CropDiseases.CropDiseasesRecyclerViewAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(com.example.cropprotection.CropDiseases.CropDiseasesRecyclerViewAdapter.ViewHolder holder, int position) {
            SentDiseases SentDiseases = diseaseListFiltered.get(position);

            holder.Subject.setText(SentDiseases.getSubject());
            holder.timesent.setText(String.valueOf(SentDiseases.gettimesent()));
            holder.Sender.setText(String.valueOf(SentDiseases.getSender()));
            holder.adviseid.setText(String.valueOf(SentDiseases.getAttachmentid()));
            final String  a = holder.adviseid.getText().toString();
            holder.Subject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try{
                        Intent intent = new Intent(view.getContext(), CropDiseaseDetail.class);
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
                        Intent intent = new Intent(view.getContext(), CropDiseaseDetail.class);
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
                        Intent intent = new Intent(view.getContext(),CropDiseaseDetail.class);
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
                        Intent intent = new Intent(view.getContext(), CropDiseaseDetail.class);
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
            return diseaseListFiltered.size();
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
                        diseaseListFiltered = list;
                    } else {
                        List<SentDiseases> filteredList = new ArrayList<>();
                        for (SentDiseases row : list) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getSender().toLowerCase().contains(charString.toLowerCase()) || row.getMessage().contains(charSequence)|| row.getSubject().toLowerCase().contains(charSequence)) {
                                filteredList.add(row);
                            }
                        }

                        diseaseListFiltered= filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values =diseaseListFiltered;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    diseaseListFiltered = (ArrayList<SentDiseases>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

        public interface diseaseAdapterListener {
            void onContactSelected(SentDiseases SentDiseases);
        }
    }
