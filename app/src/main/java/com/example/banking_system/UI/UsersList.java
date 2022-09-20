package com.example.banking_system.UI;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banking_system.DB.UserContract;
import com.example.banking_system.DB.UserHelper;
import com.example.banking_system.Data.User;
import com.example.banking_system.ListAdapters.UserListAdapter;
import com.example.banking_system.R;

import java.util.ArrayList;

public class UsersList extends AppCompatActivity {
    // RecyclerView
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<User> userArrayList;

    // Database
    private UserHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        // Create ArrayList of Users
        userArrayList = new ArrayList<User>();

        // Create Table in the Database
        dbHelper = new UserHelper(this);

        // Read Data from DataBase
        displayDatabaseInfo();

        // Show list of items
        recyclerView = findViewById(R.id.all_users_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        myAdapter = new UserListAdapter(this, userArrayList);
        recyclerView.setAdapter(myAdapter);
    }

    private void displayDatabaseInfo() {
        userArrayList.clear();

        Cursor cursor = new UserHelper(this).readAllData();

        int phoneNoColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_PHONE_NO);
        int emailColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_EMAIL);
        int ifscCodeColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_IFSC_CODE);
        int accountNumberColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_ACCOUNT_NUMBER);
        int nameColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_NAME);
        int accountBalanceColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_ACCOUNT_BALANCE);

        while (cursor.moveToNext()){
            String currentName = cursor.getString(nameColumnIndex);
            int accountNumber = cursor.getInt(accountNumberColumnIndex);
            String email = cursor.getString(emailColumnIndex);
            String phoneNumber = cursor.getString(phoneNoColumnIndex);
            String ifscCode = cursor.getString(ifscCodeColumnIndex);
            int accountBalance = cursor.getInt(accountBalanceColumnIndex);

            // Display the values from each column of the current row in the cursor in the TextView
            userArrayList.add(new User(currentName, accountNumber, phoneNumber, ifscCode, accountBalance, email));
        }
    }
}