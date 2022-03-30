package com.example.practicv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Collections;

public class CreateCollections extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_collections);
    }

    public void backCollectionScreen(View view) {
        Intent intent = new Intent(CreateCollections.this, CollectionsFragment.class);
        //startActivity(intent);
    }
}