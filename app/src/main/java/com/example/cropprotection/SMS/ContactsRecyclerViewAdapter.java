package com.example.cropprotection.SMS;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cropprotection.R;

import java.util.List;

    public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<com.example.cropprotection.SMS.ContactsRecyclerViewAdapter.ViewHolder> {

        private Context context;
        private MessageFragment mess;
        private List<SentContacts> list;
        private EditText mesagescontact;
        public FragmentManager f_manager;
        public ContactsRecyclerViewAdapter(Context context, List<SentContacts> list, FragmentManager f_manager) {
            this.context = context;
            this.list = list;
            this.f_manager = f_manager;
        }


        @Override
        public com.example.cropprotection.SMS.ContactsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.contactsfit, parent, false);
            return new com.example.cropprotection.SMS.ContactsRecyclerViewAdapter.ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(com.example.cropprotection.SMS.ContactsRecyclerViewAdapter.ViewHolder holder, int position) {
            SentContacts SentContacts = list.get(position);

            holder.textnames.setText(SentContacts.getnames());
            holder.texttelephone.setText(String.valueOf(SentContacts.gettelephone()));
            Glide.with(context).load(SentContacts.getprofilepic()).apply(RequestOptions.circleCropTransform()).into(holder.providerimage);
            final String  a = holder.texttelephone.getText().toString();
            holder.providerimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try{
                        Bundle bundle = new Bundle();
                        bundle.putString("contact", a);
                        MessageFragment mf = new MessageFragment();
                        mf.setArguments(bundle);
                        f_manager.beginTransaction().replace(R.id.fragment_container, mf).commit();

                    } catch (Exception EX) {
                        Toast.makeText(view.getContext(), EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.texttelephone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try{
                        Bundle bundle = new Bundle();
                        bundle.putString("contact", a);
                        MessageFragment mf = new MessageFragment();
                        mf.setArguments(bundle);
                        f_manager.beginTransaction().replace(R.id.fragment_container, mf).commit();
                    } catch (Exception EX) {
                        Toast.makeText(view.getContext(), EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.textnames.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try{
                        Bundle bundle = new Bundle();
                        bundle.putString("contact", a);
                        MessageFragment mf = new MessageFragment();
                        mf.setArguments(bundle);
                        f_manager.beginTransaction().replace(R.id.fragment_container, mf).commit();
                    } catch (Exception EX) {
                        Toast.makeText(view.getContext(), EX.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textnames, texttelephone;
            public ImageView providerimage;
            public ViewHolder(View itemView) {
                super(itemView);

                textnames = itemView.findViewById(R.id.contactname);
                texttelephone = itemView.findViewById(R.id.contact);
                providerimage = itemView.findViewById(R.id.thumbnail);
                mess = new MessageFragment();
            }
        }
    }

