package com.crudretrofit.View.Activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.crudretrofit.View.Adapter.ContactAdapter;
import com.crudretrofit.contract.ContactContract;
import com.crudretrofit.datasource.ContactRestAPIAdapter;
import com.crudretrofit.model.ContactModel;
import com.crudretrofit.model.ContactRepository;
import com.crudretrofit.model.LocalContactModel;
import com.crudretrofit.model.LocalContactRepository;
import com.crudretrofit.object.ContactPerson;
import com.crudretrofit.R;
import com.crudretrofit.presenter.ContactPresenter;
import com.crudretrofit.utils.RealmDB;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class ContactActivity extends AppCompatActivity implements ContactContract.ContactView{

    @BindView(R.id.rv_user)
    RecyclerView recyclerView;
    @BindView(R.id.btn_register)
    Button btnRegister;

    private ContactAdapter adapter;
    public static ContactActivity ma;
    private ContactContract.Presenter mPresenter;
    private ContactModel contactModel;
    private LocalContactModel localContactModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ContactActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ContactAdapter(ContactActivity.this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter = new ContactPresenter(this,this,new RealmDB());
        contactModel = new ContactRepository(ContactRestAPIAdapter.getContactRestApi());
        localContactModel = new LocalContactRepository();
}
    @Override
    protected void onResume() {
        super.onResume();
        initContact();
    }

    private void initContact() {
        mPresenter.getAllContact(contactModel,localContactModel);
    }

    public void detailContact(ContactPerson contactPerson){
        Intent intent = new Intent(this, DetailContactActivity.class);
        intent.putExtra("contact", Parcels.wrap(ContactPerson.class, contactPerson));
        startActivity(intent);
    }

    @OnClick(R.id.btn_register)
    public void onViewClicked(){
        startActivity(new Intent(ContactActivity.this,DetailContactActivity.class));
    }

    @Override
    public void doShowContact(List<ContactPerson> contactPersonList) {
        if(contactPersonList.size()>0){
            adapter.refreshContactList(contactPersonList);
        }else{
            adapter.clearContactList();
            Toast.makeText(ContactActivity.this, "Tidak ada kontak", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void doOnError(String message) {
        Toast.makeText(ContactActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    public void noConnection(){
        Snackbar.make(recyclerView,"Tidak ada koneksi internet",Snackbar.LENGTH_SHORT).show();
    }
}
