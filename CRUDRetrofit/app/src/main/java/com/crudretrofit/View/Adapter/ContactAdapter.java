package com.crudretrofit.View.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crudretrofit.R;
import com.crudretrofit.View.Activity.ContactActivity;
import com.crudretrofit.object.ContactPerson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Arloji
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.UserViewHolder> {

    private List<ContactPerson> dataList = new ArrayList<>();
    private ContactActivity contactActivity;

    public ContactAdapter(ContactActivity contactActivity) {
        this.contactActivity = contactActivity;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        if(dataList.size()>0){
            ((UserViewHolder) holder).loadBind(position);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_user_name)
        TextView txName;
        @BindView(R.id.txt_user_email)
        TextView txEmail;
        @BindView(R.id.txt_user_address)
        TextView txAddress;
        @BindView(R.id.txt_user_phone)
        TextView txPhone;
        private ContactPerson contactPerson;

        UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void loadBind(int position){
            contactPerson = dataList.get(position);
            txName.setText(contactPerson.getName());
            txAddress.setText(contactPerson.getAddress());
            txPhone.setText(contactPerson.getPhone());
            txEmail.setText(contactPerson.getEmail());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contactActivity.detailContact(contactPerson);
                }
            });
        }

    }

    public void refreshContactList(List<ContactPerson> contactPeople){
        dataList = new ArrayList<>();
        dataList.addAll(contactPeople);
        notifyDataSetChanged();
    }

    public void clearContactList(){
        dataList = new ArrayList<>();
        notifyDataSetChanged();
    }
}