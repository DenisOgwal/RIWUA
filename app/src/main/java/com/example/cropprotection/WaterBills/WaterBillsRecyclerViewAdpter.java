package com.example.cropprotection.WaterBills;

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

import com.example.cropprotection.Advise.AdviseDetail;
import com.example.cropprotection.Advise.SentAdvise;
import com.example.cropprotection.R;

import java.util.ArrayList;
import java.util.List;


public class WaterBillsRecyclerViewAdpter extends RecyclerView.Adapter<WaterBillsRecyclerViewAdpter.ViewHolder> implements Filterable {

    private Context context;
    private List<SentWaterBills> list;
    private List<SentWaterBills> WaterBillsListFiltered;
    private WaterBillsAdapterListener listener;

    public WaterBillsRecyclerViewAdpter(Context context, List<SentWaterBills> list) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        this.WaterBillsListFiltered = list;
    }

    @Override
    public WaterBillsRecyclerViewAdpter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.content_water_bills, parent, false);
        return new WaterBillsRecyclerViewAdpter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(WaterBillsRecyclerViewAdpter.ViewHolder holder, int position) {
        SentWaterBills SentWaterBills =WaterBillsListFiltered.get(position);
        String billno="";
        if(SentWaterBills.getBillNumber()<10){
            billno="#00";
        }else  if(SentWaterBills.getBillNumber()<100){
            billno="#0";
        }else{
            billno="#";
        }
        holder.billnumber.setText("Bill No: "+billno+String.valueOf(SentWaterBills.getBillNumber()));
        holder.waterunits.setText("Units:   "+String.valueOf(SentWaterBills.getWaterUnits()));
        holder.billingdate.setText("Bill Date: "+String.valueOf(SentWaterBills.getBillingDate()));
        final String  a = ""+SentWaterBills.getBillNumber();
        holder.waterunits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    Intent intent = new Intent(view.getContext(), WaterBillDetails.class);
                    intent.putExtra("billingid",a);
                    view.getContext().startActivity(intent);
                } catch (Exception EX) {
                    Toast.makeText(view.getContext(), EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.billnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    Intent intent = new Intent(view.getContext(), WaterBillDetails.class);
                    intent.putExtra("billingid",a);
                    view.getContext().startActivity(intent);
                } catch (Exception EX) {
                    Toast.makeText(view.getContext(), EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.billingdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    Intent intent = new Intent(view.getContext(), WaterBillDetails.class);
                    intent.putExtra("billingid",a);
                    view.getContext().startActivity(intent);
                } catch (Exception EX) {
                    Toast.makeText(view.getContext(), EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return WaterBillsListFiltered.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView billnumber, waterunits, billingdate;

        public ViewHolder(View itemView) {
            super(itemView);
            billnumber = itemView.findViewById(R.id.billnumber);
            waterunits = itemView.findViewById(R.id.waterunits);
            billingdate = itemView.findViewById(R.id.billingdate);
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
                    WaterBillsListFiltered = list;
                } else {
                    List<SentWaterBills> filteredList = new ArrayList<>();
                    for (SentWaterBills row : list) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        String S=Integer.toString(row.getBillNumber());

                        if ( row.getMeterNumber().toLowerCase().contains(charString.toLowerCase()) ||  row.getBillingDate().toLowerCase().contains(charString.toLowerCase()) || S.contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    WaterBillsListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values =WaterBillsListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                WaterBillsListFiltered = (ArrayList<SentWaterBills>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface WaterBillsAdapterListener {
        void onContactSelected(SentWaterBills SentWaterBills);
    }
}
