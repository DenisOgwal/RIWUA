package com.Dither.cropprotection.Chat;

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

public class ContactsChatRecyclerViewAdapter extends RecyclerView.Adapter<ContactsChatRecyclerViewAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<SentContactsChat> list;
    private List<SentContactsChat> contactcListFiltered;
    private contactcsAdapterListener listener;
    public ContactsChatRecyclerViewAdapter(Context context, List<SentContactsChat> list) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        this.contactcListFiltered = list;
    }


    @Override
    public ContactsChatRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contactschatfit, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ContactsChatRecyclerViewAdapter.ViewHolder holder, int position) {
        SentContactsChat SentContactsChat =contactcListFiltered.get(position);

        holder.textnames.setText(SentContactsChat.getnames());
        holder.texttelephone.setText(String.valueOf(SentContactsChat.gettelephone()));
        Glide.with(context).load(SentContactsChat.getprofilepic()).apply(RequestOptions.circleCropTransform()).into(holder.providerimage);
        final String  a = holder.textnames.getText().toString();
        holder.providerimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    Intent intent = new Intent(view.getContext(), ChatRoom.class);
                    intent.putExtra("adviseid",a);
                    view.getContext().startActivity(intent);

                } catch (Exception EX) {
                    Toast.makeText(view.getContext(), EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.texttelephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    Intent intent = new Intent(view.getContext(), ChatRoom.class);
                    intent.putExtra("adviseid",a);
                    view.getContext().startActivity(intent);

                } catch (Exception EX) {
                    Toast.makeText(view.getContext(), EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.textnames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    Intent intent = new Intent(view.getContext(), ChatRoom.class);
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

        return contactcListFiltered.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textnames, texttelephone;
        public ImageView providerimage;
        public ViewHolder(View itemView) {
            super(itemView);

            textnames = itemView.findViewById(R.id.contactname);
            texttelephone = itemView.findViewById(R.id.contact);
            providerimage = itemView.findViewById(R.id.thumbnail);
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
                    contactcListFiltered = list;
                } else {
                    List<SentContactsChat> filteredList = new ArrayList<>();
                    for (SentContactsChat row : list) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getnames().toLowerCase().contains(charString.toLowerCase()) || row.gettelephone().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    contactcListFiltered= filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values =contactcListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactcListFiltered = (ArrayList<SentContactsChat>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface contactcsAdapterListener {
        void onContactSelected(SentContactsChat SentContactsChat);
    }
}

