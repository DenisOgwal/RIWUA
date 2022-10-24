package com.Dither.cropprotection.WaterBills;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.Dither.cropprotection.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class WaterBillPaymentsRecyclerViewAdpter extends RecyclerView.Adapter<WaterBillPaymentsRecyclerViewAdpter.ViewHolder> implements Filterable {

    private Context context;
    private List<SentWaterBillPayments> list;
    private List<SentWaterBillPayments> WaterBillPaymentsListFiltered;
    private WaterBillPaymentAdapterListener listener;

    public WaterBillPaymentsRecyclerViewAdpter(Context context, List<SentWaterBillPayments> list) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        this.WaterBillPaymentsListFiltered = list;
    }

    @Override
    public WaterBillPaymentsRecyclerViewAdpter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.content_water_bill_payments, parent, false);
        return new WaterBillPaymentsRecyclerViewAdpter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(WaterBillPaymentsRecyclerViewAdpter.ViewHolder holder, int position) {
        SentWaterBillPayments SentWaterBillPayments =WaterBillPaymentsListFiltered.get(position);
        String paymentno="";
        String billno="";
        if(SentWaterBillPayments.getpaymentid()<10){
            paymentno="#00";
        }else  if(SentWaterBillPayments.getpaymentid()<100){
            paymentno="#0";
        }else{
            paymentno="#";
        }
        if(SentWaterBillPayments.getpaymentid()<10){
            billno="#00";
        }else  if(SentWaterBillPayments.getpaymentid()<100){
            billno="#0";
        }else{
            billno="#";
        }
        holder.paymentid.setText("Payment ID: "+paymentno+String.valueOf(SentWaterBillPayments.getpaymentid()));
        holder.billnumber.setText("Bill Number:   "+billno+String.valueOf(SentWaterBillPayments.getBillNumber()));
        holder.amountpaid.setText("Amount Paid: "+String.valueOf(SentWaterBillPayments.getamountpaid()));
        holder.paidby.setText("Paid By: "+String.valueOf(SentWaterBillPayments.getPaidBy()));
        holder.contact.setText("Contact:   "+String.valueOf(SentWaterBillPayments.getContact()));
        holder.paymentdate.setText("Payment Date: "+String.valueOf(SentWaterBillPayments.getPaymentDate()));
        final String  a = ""+SentWaterBillPayments.getpaymentid();
        /*holder.waterunits.setOnClickListener(new View.OnClickListener() {
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
        });*/
    }

    @Override
    public int getItemCount() {

        return WaterBillPaymentsListFiltered.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView paymentid,billnumber, amountpaid,paidby,contact,paymentdate;

        public ViewHolder(View itemView) {
            super(itemView);
            paymentid = itemView.findViewById(R.id.paymentid);
            billnumber = itemView.findViewById(R.id.billnumber);
            amountpaid = itemView.findViewById(R.id.amountpaid);
            paidby = itemView.findViewById(R.id.paidby);
            contact = itemView.findViewById(R.id.contact);
            paymentdate = itemView.findViewById(R.id.paymentdate);
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
                    WaterBillPaymentsListFiltered = list;
                } else {
                    List<SentWaterBillPayments> filteredList = new ArrayList<>();
                    for (SentWaterBillPayments row : list) {
                        String bills=Integer.toString(row.getBillNumber());
                        String payments=Integer.toString(row.getpaymentid());
                        if ( row.getPaymentDate().toLowerCase().contains(charString.toLowerCase()) ||  row.getPaidBy().toLowerCase().contains(charString.toLowerCase()) ||row.getContact().toLowerCase().contains(charString.toLowerCase()) ||bills.contains(charString.toLowerCase())||payments.contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    WaterBillPaymentsListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values =WaterBillPaymentsListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                WaterBillPaymentsListFiltered = (ArrayList<SentWaterBillPayments>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface WaterBillPaymentAdapterListener {
        void onContactSelected(SentWaterBillPayments SentWaterBillPayments);
    }
}
