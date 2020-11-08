package com.example.finalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.finalapp.ui.chat.ChatFragment;
import com.example.finalapp.ui.home.HomeFragment;
import com.example.finalapp.ui.profile.ProfileFragment;
import com.example.finalapp.ui.profile.history.OrderHistoryAdapter;
import com.example.finalapp.ui.profile.history.OrderHistoryFragment;
import com.example.finalapp.ui.profile.history.PostHistoryFragment;
import com.example.finalapp.ui.shop.ShopFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navView;
    Fragment selectedFrag = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, new HomeFragment()).commit();
    }



    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            selectedFrag = new HomeFragment();
                            break;
                        case R.id.navigation_shop:
                            selectedFrag = new ShopFragment();
                            break;
                        case R.id.navigation_post:
                            selectedFrag = null;
                            startActivity(new Intent(MainActivity.this, PostActivity.class));
                            break;
                        case R.id.navigation_chat:
                            selectedFrag = new ChatFragment();
                            break;
                        case R.id.navigation_account:
                            selectedFrag = new ProfileFragment();
                            break;
                    }

                    if (selectedFrag != null) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.nav_host_fragment, selectedFrag).commit();
                    }

                    return true;
                }
            };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            assert data != null;
            String orderId = data.getStringExtra("orderId");
            final int position = data.getIntExtra("position", 0);
            db.collection("orders").document(orderId)
                    .update("buyerStatus", "Done")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
//                                ProfileFragment fragment = (ProfileFragment) getSupportFragmentManager().getFragments().get(4);
//                                OrderHistoryFragment fragment1 = (OrderHistoryFragment) fragment.getChildFragmentManager().getFragments().get(1);
//                                fragment1.update(position);
                            }
                        }
                    });

        }

        if (requestCode == 200 && resultCode == RESULT_OK) {

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            assert data != null;
            String orderId = data.getStringExtra("orderId");
            final int position = data.getIntExtra("position", 0);
            db.collection("orders").document(orderId)
                    .update("sellerStatus", "Done")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                 List<Fragment> list = getSupportFragmentManager().getFragments();
                                 OrderHistoryFragment fragment = (OrderHistoryFragment) list.get(3);
                                 fragment.update(position);
                            }
                        }
                    });

        }



    }
}
