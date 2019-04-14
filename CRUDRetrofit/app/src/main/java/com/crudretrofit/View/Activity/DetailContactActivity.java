package com.crudretrofit.View.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.crudretrofit.R;
import com.crudretrofit.contract.ContactContract;
import com.crudretrofit.datasource.ContactRestAPIAdapter;
import com.crudretrofit.model.ContactModel;
import com.crudretrofit.model.ContactRepository;
import com.crudretrofit.model.LocalContactModel;
import com.crudretrofit.model.LocalContactRepository;
import com.crudretrofit.object.ResultList;
import com.crudretrofit.object.ContactPerson;
import com.crudretrofit.datasource.ContactRestAPI;
import com.crudretrofit.presenter.ContactPresenter;
import com.crudretrofit.utils.RealmDB;
import com.google.gson.Gson;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ArLoJi on 09/02/2018.
 */

public class DetailContactActivity extends AppCompatActivity implements ContactContract.DetailContactView{

    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_address)
    EditText editAddress;
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.edit_mail)
    EditText editMail;
    @BindView(R.id.btn_create_update)
    Button btnCreatUpdate;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    private ContactPerson contactPerson;
    private boolean isEdit = false;
    private ContactContract.Presenter mPresenter;
    private ContactModel contactModel;
    private LocalContactModel localContactModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);
        ButterKnife.bind(this);

        contactPerson = Parcels.unwrap(getIntent().getParcelableExtra("contact"));
        if (contactPerson != null) {
            editName.setText(contactPerson.getName());
            editAddress.setText(contactPerson.getAddress());
            editPhone.setText(contactPerson.getPhone());
            editMail.setText(contactPerson.getEmail());
            btnCreatUpdate.setText("UPDATE");
            isEdit = true;
        }else{
            btnDelete.setVisibility(View.GONE);
            isEdit = false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter = new ContactPresenter(this,DetailContactActivity.this,new RealmDB());
        contactModel = new ContactRepository(ContactRestAPIAdapter.getContactRestApi());
        localContactModel = new LocalContactRepository();
    }

    @OnClick({R.id.btn_delete, R.id.btn_create_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_delete:
                mPresenter.deleteContact(contactPerson.getId(),contactModel,localContactModel);
                finish();
                break;
            case R.id.btn_create_update:
                ContactPerson mContact = new ContactPerson(editName.getText().toString(),
                        editAddress.getText().toString(), editPhone.getText().toString(), editMail.getText().toString());
                if (isEdit) {
                    mContact.setVersion(contactPerson.getVersion());
                    mPresenter.editContact(contactPerson.getId(),mContact,contactModel,localContactModel);
                    finish();
                } else {
                    mPresenter.addContact(mContact,contactModel,localContactModel);
                    finish();
                }
                break;
        }
    }

    @Override
    public void doOnError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
