package com.example.practicv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    MainFragment mainFragment = new MainFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    CollectionsFragment collectionsFragment = new CollectionsFragment();
    ComplationFragment compilationFragment = new ComplationFragment();
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.mobiled_navigation);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mainFragment:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, mainFragment).commit();
                return true;
            case R.id.compilationFragment:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, compilationFragment).commit();
                return true;
            case R.id.collectionsFragment:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, collectionsFragment).commit();
                return true;
            case R.id.profileFragment:
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, profileFragment).commit();
                return true;
        }
        return false;
    }

    public void goDiscussions(View view) {
        Intent intent = new Intent(MainActivity.this, DiscussionScreen.class);
        startActivity(intent);
    }

    public void moveToLoginScreen(View view) {
        Intent i = new Intent(getApplicationContext(), SignActivity.class);
        finish();
        startActivity(i);
    }

    public void goCreateCollections(View view) {
        Intent intent = new Intent(MainActivity.this, CreateCollections.class);
        startActivity(intent);
    }
}