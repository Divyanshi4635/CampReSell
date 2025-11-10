package com.example.buyandsell.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "customers")
public class CustomerEntity {

    @PrimaryKey
    @NonNull
    public String id; // University registration number

    public String name;

    public String email; // auto-generated from id

    public String mobileNumber;

    public String password;

    public boolean isAdmin;
}


