package com.vnat.chatwithfirebase.User.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vnat.chatwithfirebase.User.Model.User;

public class UserViewModel extends ViewModel {
    private MutableLiveData<User> userLiveData;
}