package com.example.getphysical;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {
    private MutableLiveData<User> user;

    public LiveData<User> login(String username, String password) {
        return user ;
    }

    public MutableLiveData<User> getUser() {
        return user;
    }
}
