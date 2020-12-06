package com.geekteck.androidhomework3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {
    private Button btnSend;
    private Button btnOpen;
    private ImageView imageView;
    private TextView textView;
    String text;
    Uri img;
//    String img;
    public static final int REQUEST_CODE = 1;
    public static final String KEY_INTENT = "key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        clickListener();
    }

    @SuppressLint("IntentReset")
    private void clickListener() {
        btnOpen.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra(KEY_INTENT, "edit text");
            startActivityForResult(intent, REQUEST_CODE);
        });
        btnSend.setOnClickListener(v -> {
            Intent email = new Intent(Intent.ACTION_SENDTO);
            email.setType("text/plain");
            email.setType(String.valueOf(Uri.parse("image/*")));
            email.setData(Uri.parse("mailto:your.email@gmail.com"));
            email.putExtra(Intent.EXTRA_SUBJECT, "How you doing?");
            email.putExtra(Intent.EXTRA_TEXT, text +" from user");
            email.putExtra(Intent.EXTRA_STREAM, img);
            startActivity(email);
        });
    }

    private void init() {
        btnSend = findViewById(R.id.button);
        btnOpen = findViewById(R.id.button2);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            text = data.getStringExtra("text");
            img = Uri.parse(data.getStringExtra("image"));
            imageView.setImageURI(img);
            textView.setText(text);
            Glide.with(this).load(img).into(imageView);
            Toast.makeText(this, "Successful!", Toast.LENGTH_LONG).show();
        }
    }
}