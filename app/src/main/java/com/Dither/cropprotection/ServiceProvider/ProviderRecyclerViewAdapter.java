package com.Dither.cropprotection.ServiceProvider;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.Dither.cropprotection.R;

import java.util.ArrayList;
import java.util.List;

    public class ProviderRecyclerViewAdapter extends RecyclerView.Adapter<com.Dither.cropprotection.ServiceProvider.ProviderRecyclerViewAdapter.ViewHolder> implements Filterable {

        private Context context;
        private List<SentProviders> list;
        private List<SentProviders> providerListFiltered;
        private providerAdapterListener listener;


        public ProviderRecyclerViewAdapter(Context context, List<SentProviders> list) {
            this.context = context;
            this.list = list;
            this.listener = listener;
            this.providerListFiltered = list;
        }

        @Override
        public com.Dither.cropprotection.ServiceProvider.ProviderRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.content_providers, parent, false);
            return new com.Dither.cropprotection.ServiceProvider.ProviderRecyclerViewAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(com.Dither.cropprotection.ServiceProvider.ProviderRecyclerViewAdapter.ViewHolder holder, int position) {
            try{
            SentProviders SentProviders = providerListFiltered.get(position);
            holder.providername.setText(SentProviders.getprovidername());
            holder.providerphone.setText(String.valueOf(SentProviders.getcontact()));
            holder.provideremail.setText(String.valueOf(SentProviders.getemail()));
            holder.providerid.setText(String.valueOf(SentProviders.getproviderid()));
            Glide.with(context).load(SentProviders.getimageurl()).apply(RequestOptions.circleCropTransform()).into(holder.providerimage);
                //holder.descriptions.contentEquals(String.valueOf(SentProviders.getdescription()));
                //holder.servicess.contentEquals(String.valueOf(SentProviders.getservices()));
               // holder.timeregistered.contentEquals(String.valueOf(SentProviders.gettimeregistered()));

            final String  a = holder.providerid.getText().toString();
            holder.providerimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try{
                    Intent intent = new Intent(view.getContext(), ProviderDetail.class);
                    intent.putExtra("providerid",a);
                        view.getContext().startActivity(intent);
                    } catch (Exception EX) {
                        Toast.makeText(view.getContext(), EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.providername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try{
                        Intent intent = new Intent(view.getContext(), ProviderDetail.class);
                        intent.putExtra("providerid",a);
                        view.getContext().startActivity(intent);
                    } catch (Exception EX) {
                        Toast.makeText(view.getContext(), EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.providerphone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try{
                        Intent intent = new Intent(view.getContext(), ProviderDetail.class);
                        intent.putExtra("providerid",a);
                        view.getContext().startActivity(intent);
                    } catch (Exception EX) {
                        Toast.makeText(view.getContext(), EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.provideremail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try{
                        Intent intent = new Intent(view.getContext(), ProviderDetail.class);
                        intent.putExtra("providerid",a);
                        view.getContext().startActivity(intent);
                    } catch (Exception EX) {
                        Toast.makeText(view.getContext(), EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch(Exception ex){

        }
        }

        @Override
        public int getItemCount()
        {
            return providerListFiltered.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView providername, providerphone, provideremail,providerid;
            public ImageView providerimage;
            public String descriptions,servicess,timeregistered;

            public ViewHolder(View itemView) {
                super(itemView);

                providername = itemView.findViewById(R.id.providername);
                providerphone = itemView.findViewById(R.id.providercontact);
                provideremail = itemView.findViewById(R.id.provideremail);
                providerimage = itemView.findViewById(R.id.thumbnail);
                providerid = itemView.findViewById(R.id.providerid);
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
                        providerListFiltered = list;
                    } else {
                        try{
                        List<SentProviders> filteredList = new ArrayList<>();
                        for (SentProviders row : list) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getprovidername().toLowerCase().contains(charString.toLowerCase()) || row.getcontact().contains(charSequence)|| row.getemail().toLowerCase().contains(charSequence)|| row.getservices().toLowerCase().contains(charSequence)|| row.getdescription().toLowerCase().contains(charSequence)) {
                                filteredList.add(row);
                            }
                        }

                        providerListFiltered = filteredList;
                        }catch(Exception ex){
                            //Toast.makeText(get, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values =providerListFiltered;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    try{
                    providerListFiltered = (ArrayList<SentProviders>) filterResults.values;
                    notifyDataSetChanged();
                }catch(Exception ex){

                }
                }
            };
        }
public interface providerAdapterListener {
    void onContactSelected(SentProviders SentProviders);
}
    }

