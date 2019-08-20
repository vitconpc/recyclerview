package vn.com.example.recyclerviewcontact.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.com.example.recyclerviewcontact.R;
import vn.com.example.recyclerviewcontact.custom.ContactAdapter;
import vn.com.example.recyclerviewcontact.custom.ContactEvent;
import vn.com.example.recyclerviewcontact.custom.SimpleItemTouchHelperCallback;
import vn.com.example.recyclerviewcontact.data.Contact;

public class MainActivity extends AppCompatActivity implements ContactEvent, CompoundButton.OnCheckedChangeListener{

    private Switch swGrid;
    private RecyclerView recyclerContact;
    private List<Contact> contacts;
    private ContactAdapter contactAdapter;
    private LinearLayoutManager linnearlayout;
    private GridLayoutManager gridlayout;

    private AlertDialog AddDialog;
    private AlertDialog UpdateDialog;
    private EditText userName;
    private EditText numberContact;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        setEvent();
    }

    private void setEvent() {
        swGrid.setOnCheckedChangeListener(this);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(contactAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerContact);
    }

    private void initData() {
        swGrid.setChecked(false);
        contacts.add(new Contact("0348472270","Việt"));
        contacts.add(new Contact("0348472270","Linh"));
        contacts.add(new Contact("0348472270","Phúc"));
        contacts.add(new Contact("0348472270","Thúy"));
        contacts.add(new Contact("0348472270","Kid"));
        contactAdapter.notifyDataSetChanged();
    }

    private void initView() {
        swGrid = findViewById(R.id.sw_grid);
        contacts = new ArrayList<>();
        recyclerContact = findViewById(R.id.recycler_contact);
        linnearlayout = new LinearLayoutManager(this);
        gridlayout = new GridLayoutManager(this,2);
        recyclerContact.setLayoutManager(linnearlayout);
        contactAdapter = new ContactAdapter(this,contacts,this);
        recyclerContact.setAdapter(contactAdapter);
    }


    @Override
    public void eventClick(final int position) {
        contact = contacts.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View viewDialog  = LayoutInflater.from(this).inflate(R.layout.custom_dialog_item,null);
        builder.setView(viewDialog);
        UpdateDialog = builder.create();
        userName =  viewDialog.findViewById(R.id.edt_name);
        numberContact =  viewDialog.findViewById(R.id.edt_number);
        Button btnDelete = viewDialog.findViewById(R.id.btn_delete);
        Button btnUpdate = viewDialog.findViewById(R.id.btn_update);

        userName.setText(contact.getContactName());
        numberContact.setText(contact.getContactNumber());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contacts.remove(position);
                contactAdapter.notifyDataSetChanged();
                UpdateDialog.dismiss();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(userName.getText().toString())){
                    userName.setError(getString(R.string.not_enter_username));
                    return;
                }
                if (TextUtils.isEmpty(numberContact.getText().toString())){
                    numberContact.setError(getString(R.string.not_enter_contact));
                    return;
                }

                contact.setContactName(userName.getText().toString().trim());
                contact.setContactNumber(numberContact.getText().toString().trim());
                contactAdapter.notifyDataSetChanged();
                UpdateDialog.dismiss();
            }
        });
        UpdateDialog.setCancelable(false);
        UpdateDialog.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if ((isChecked)){
            recyclerContact.setLayoutManager(gridlayout);
            return;
        }
        recyclerContact.setLayoutManager(linnearlayout);
    }

    public void addContact(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View viewDialog  = LayoutInflater.from(this).inflate(R.layout.custom_dialog,null);
        builder.setView(viewDialog);
        final EditText userName =  viewDialog.findViewById(R.id.edt_name);
        final EditText numberContact =  viewDialog.findViewById(R.id.edt_number);
        Button btnClose = viewDialog.findViewById(R.id.btn_close);
        Button btnSave = viewDialog.findViewById(R.id.btn_save);
        AddDialog = builder.create();
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDialog.dismiss();
            }
        });
         btnSave.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (TextUtils.isEmpty(userName.getText().toString())){
                     userName.setError(getString(R.string.not_enter_username));
                     return;
                 }
                 if (TextUtils.isEmpty(numberContact.getText().toString())){
                     numberContact.setError(getString(R.string.not_enter_contact));
                     return;
                 }

                 Contact contact = new Contact(numberContact.getText().toString().trim()
                         ,userName.getText().toString().trim());
                 contacts.add(0,contact);
                 contactAdapter.notifyDataSetChanged();
                 AddDialog.dismiss();
             }
         });
        AddDialog.setCancelable(false);
        AddDialog.show();
    }
}
