package com.nouroeddinne.truecaller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nouroeddinne.truecaller.databinding.ActivityContactsBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ContactsActivity extends AppCompatActivity {
    ActivityContactsBinding binding;
    public static final int REQUEST_PERMISSION = 1;
    private RecyclerView recyclerView;
    private ArrayList<Model> listitems;
    private ArrayList<ModelSMS> listsms;
    private RecyclerView.Adapter adapter;
    private TextView textC,textS;
    private ImageView imgC,imgS;

    LinearLayout layoutMessages,layoutContacts;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contacts);

        binding = ActivityContactsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = findViewById(R.id.recyclerView);
        layoutContacts = findViewById(R.id.layoutContacts);
        layoutMessages = findViewById(R.id.layoutMessages);
        textC = findViewById(R.id.textC);
        textS = findViewById(R.id.textS);
        imgC = findViewById(R.id.imgC);
        imgS = findViewById(R.id.imgS);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (ContextCompat.checkSelfPermission(ContactsActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ContactsActivity.this, new String[]{Manifest.permission.READ_CONTACTS},REQUEST_PERMISSION);
        }else {
            loadData();
            populateDataIntoRecyclerview(listitems);
        }

        if (ContextCompat.checkSelfPermission(ContactsActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ContactsActivity.this, new String[]{Manifest.permission.READ_SMS},REQUEST_PERMISSION);
        }else {
            loadSMS();
        }


        layoutContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateDataIntoRecyclerview(listitems);

                layoutMessages.setBackgroundResource(R.color.white);
                textC.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                imgC.setImageResource(R.drawable.baseline_contacts_24_white);

                layoutContacts.setBackgroundResource(R.color.blue);
                textS.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blue));
                imgS.setImageResource(R.drawable.baseline_message_24);
            }
        });


        layoutMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateSMSIntoRecyclerview(listsms);

                layoutMessages.setBackgroundResource(R.color.blue);
                textC.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blue));
                imgC.setImageResource(R.drawable.baseline_contacts_24);

                layoutContacts.setBackgroundResource(R.color.white);
                textS.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                imgS.setImageResource(R.drawable.baseline_message_24_white);


            }
        });
































    }

    public void loadData(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                readContacts();
            }
        });
        thread.run();

    }

    public void loadSMS(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                readSms();
            }
        });
        thread.run();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0){
            loadData();
        }
    }


    @SuppressLint("Range")
    private void readContacts(){

        listitems = new ArrayList();

        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri,null,null, null,ContactsContract.Contacts.DISPLAY_NAME+" ASC");
        if (cursor.moveToFirst()){
            do {
                long contactId = cursor.getLong(cursor.getColumnIndex("_ID"));
                Uri uriContact = ContactsContract.Data.CONTENT_URI;
                Cursor cursorContact = getContentResolver().query(uriContact,null,ContactsContract.Data.CONTACT_ID+" =? ",new String[]{String.valueOf(contactId)},null);

                String name="";
                String nickname="";
                String homeEmail="";
                String workEmail="";
                String email="";
                String phoneNumber = "";
                String workPhoneNumber="";
                String homePhoneNumber="";
                byte[] photoBayt=null;
                String others="";

                if (cursorContact.moveToFirst()){
                    name = cursorContact.getString(cursorContact.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    do {
                        if (cursorContact.getString(cursorContact.getColumnIndex("mimetype")).equals(ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE)){
                            nickname= cursorContact.getString(cursorContact.getColumnIndex("data1"));
                        }
                        if (cursorContact.getString(cursorContact.getColumnIndex("mimetype")).equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)){

                            switch (cursorContact.getInt(cursorContact.getColumnIndex("data2"))){
                                case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                    homePhoneNumber = cursorContact.getString(cursorContact.getColumnIndex("data1"));
                                    break;
                                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                    workPhoneNumber = cursorContact.getString(cursorContact.getColumnIndex("data1"));
                                    break;
                                case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                    phoneNumber = cursorContact.getString(cursorContact.getColumnIndex("data1"));
                                    break;
                                default:
                                    phoneNumber = "\nPhone: " + cursorContact.getString(cursorContact.getColumnIndex("data1"));
                                    break;
                            }
                        }
                        if (cursorContact.getString(cursorContact.getColumnIndex("mimetype")).equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)){
                            switch (cursorContact.getInt(cursorContact.getColumnIndex("data2"))){
                                case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                                    homeEmail = cursorContact.getString(cursorContact.getColumnIndex("data1"));
                                    break;
                                case ContactsContract.CommonDataKinds.Email.TYPE_MOBILE:
                                    workEmail = cursorContact.getString(cursorContact.getColumnIndex("data1"));
                                    break;
                                case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                                    email = cursorContact.getString(cursorContact.getColumnIndex("data1"));
                                    break;
                                default:
                                    email = "\nEmail: " + cursorContact.getString(cursorContact.getColumnIndex("data1"));
                                    break;
                            }
                        }
                        if (cursorContact.getString(cursorContact.getColumnIndex("mimetype")).equals(ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)){
                            photoBayt= cursorContact.getBlob(cursorContact.getColumnIndex("data15"));
                        }
                    }while (cursorContact.moveToNext());
                }
                if (cursorContact.moveToFirst()){
                    listitems.add(new Model(name, phoneNumber, nickname, homeEmail, workEmail, email, workPhoneNumber, homePhoneNumber, others, photoBayt));
                }
            }while (cursor.moveToNext());
        }

    }

    @SuppressLint("Range")
    public void readSms(){

        listsms = new ArrayList<>();
        Uri uri = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String address = cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS));
                String body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));
                String date = cursor.getString(cursor.getColumnIndex(Telephony.Sms.DATE_SENT));
                Date dates = convertStringToDate(date);
                String data = formatDate(dates);
                listsms.add(new ModelSMS(address,body,data));

            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    private Date convertStringToDate(String dateStr) {
        try {
            long timestamp = Long.parseLong(dateStr);
            return new Date(timestamp);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm", Locale.getDefault());
        return dateFormat.format(date);
    }


    private void populateDataIntoRecyclerview(ArrayList<Model> list){

        adapter = new MyAdapter(this,list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void populateSMSIntoRecyclerview(ArrayList<ModelSMS> list){

        adapter = new AdapterSMS(this,list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }





















}