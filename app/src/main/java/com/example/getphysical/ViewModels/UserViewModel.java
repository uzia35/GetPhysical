package com.example.getphysical.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.getphysical.Models.User;

public class UserViewModel extends ViewModel {
    private MutableLiveData<User> user = new MutableLiveData<>(); ;

    public LiveData<User> login(String username, String password) {
        return user ;
    }

    public MutableLiveData<User> getUser() {
        return user;
    }
}
