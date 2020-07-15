package com.example.inclass_08;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ExpenseFragment.OnFragmentInteractionListener,AddExpFragment.OnFragmentInteractionListener {

    String[] categories_selection = {"Groceries", "Invoice", "Transportation", "Shopping", "Rent", "Trips", "Utilities", "Other"};
    private static final String EXP_NAME_KEY = "Expense Name";
    Button addButton;
    FirebaseDatabase mDatabase1 = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = mDatabase1.getReference("expenses");
    ArrayList<Expense> displayList = new ArrayList<Expense>();
    int count = 0;
    public static String key_of_obj="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.main_container,new ExpenseFragment(),"ExpenseFragment").commit();


    }








    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected()) {
            Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onFragmentInteraction() {
        Log.d("status","Database got cleared");

        Log.d("status","called Mainactivity from expense fragment");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                displayList.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    Expense e1 = (Expense) child.getValue(Expense.class);
                    System.out.println("object_keys"+e1.obj_key);
                    count+=1;
                    displayList.add(e1);


                }
                Log.d("size1234","IN MainActivity");
                //Log.d("last item",displayList.get(displayList.size()-1).expName);

                Bundle bundle = new Bundle();
                bundle.putSerializable("data",displayList);
                ExpenseFragment ef = new ExpenseFragment();
                Log.d("status","sent the arguments");
                ef.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container,ef,"ExpenseFragment").commit();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onFragmentInteraction(Expense expense) {
        if (((expense.obj_key).equals("key"))){
            Log.d("edit",expense.obj_key);
            String obj_key = mDatabase.push().getKey();
            expense.obj_key = obj_key;
            Log.d("status","addding the data into database with key"+obj_key);
            mDatabase.child(obj_key).setValue(expense);}

        else{

            String obj_key = expense.obj_key;
            mDatabase.child(obj_key).setValue(expense);

        //getFragmentManager().beginTransaction().replace(R.id.main_container, new ExpenseFragment()).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new ExpenseFragment(),"ExpenseFragment").commit();
    }
}
    }
