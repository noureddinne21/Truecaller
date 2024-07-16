package com.nouroeddinne.truecaller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ShowInformatinContactActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_informatin_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        Bundle extras = getIntent().getExtras();
        if (extras!=null){

            textView.setText("\nname "+extras.getString("name")
                    +"\nnumber "+extras.getString("number")
                    +"\nnickname "+extras.getString("nickname")
                    +"\nhomeEmail"+extras.getString("homeEmail")
                    +"\nworkEmail"+extras.getString("workEmail")
                    +"\nemail "+extras.getString("email")
                    +"\nworkPhoneNumber"+extras.getString("workPhoneNumber")
                    +"\nhomePhoneNumber"+extras.getString("homePhoneNumber")
                    +"\nothers "+extras.getString("others"));



            String imgDataString = getIntent().getStringExtra("img");
            if (imgDataString.length() == 0) {
                imageView.setImageResource(R.drawable.baseline_person_24);
            }else {
                imageView.setImageBitmap(stringToBitmap(imgDataString));
            }




        }






    }


    public static Bitmap stringToBitmap(String encodedString) {
        byte[] decodedString = Base64.decode(encodedString, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }













}