package com.simcoder.novalauncherclone;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SettingsActivity extends AppCompatActivity {

    ImageView mHomeScreenImage;

    int REQUEST_CODE_IMAGE = 1;
    String PREFS_NAME = "NovaPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button mHomeScreenButton = findViewById(R.id.homeScreenButton);
        mHomeScreenImage = findViewById(R.id.homeScreenImage);

        mHomeScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        });

        getData();
    }

    private void getData(){
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String imageUri = sharedPreferences.getString("imageUri", null);

        if(imageUri != null)
            mHomeScreenImage.setImageURI(Uri.parse(imageUri));

    }

    private void saveData(Uri imageUri){
        SharedPreferences.Editor sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        sharedPreferences.putString("imageUri", imageUri.toString());
        sharedPreferences.apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK){
            Uri imageUri = data.getData();
            mHomeScreenImage.setImageURI(imageUri);
            saveData(imageUri);
        }
    }
}
