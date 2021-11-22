package com.anom.contactsapp.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.provider.ContactsContract
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anom.contactsapp.model.Contact
import com.anom.contactsapp.repository.ContactRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ContactsListViewModel(application: Application) : ViewModel() {

    // Create a LiveData with a String
    val contactsArrayList: MutableLiveData<ArrayList<Contact>> by lazy {
        MutableLiveData()
    }

    private val context by lazy {
        application.applicationContext
    }

    private val repository = ContactRepository(context)

    fun getContacts() {
        viewModelScope.launch {
            val contactsAsync = async { repository.fetchContacts()}
            val contacts = contactsAsync.await()
            contactsArrayList.postValue(contacts)
        }
    }
}