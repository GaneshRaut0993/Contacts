package com.anom.contactsapp.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory (private var application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactsListViewModel::class.java)) {
            return ContactsListViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}