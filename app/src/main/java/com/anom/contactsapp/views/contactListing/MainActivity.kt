package com.anom.contactsapp.views.contactListing

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anom.contactsapp.R
import com.anom.contactsapp.model.Contact
import com.anom.contactsapp.utility.Constants.CONTACT_DETAIL
import com.anom.contactsapp.utility.Constants.PERMISSIONS_REQUEST_READ_CONTACTS
import com.anom.contactsapp.utility.Constants.PERMISSION_READ_CONTACTS
import com.anom.contactsapp.utility.Constants.SELECTED_CONTACT
import com.anom.contactsapp.viewModel.ContactsListViewModel
import com.anom.contactsapp.viewModel.ViewModelFactory
import com.anom.contactsapp.views.contactDetails.ContactDetailsActivity
import kotlinx.android.synthetic.main.activity_main.recyclerview
import java.util.*

class MainActivity : AppCompatActivity(), ContactSelectionListener {

    private var contactsArrayList = ArrayList<Contact>()

    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ContactsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contactsArrayList.clear()

        viewModelFactory = ViewModelFactory(application)

        viewModel = ViewModelProvider(
            this, viewModelFactory
        )[ContactsListViewModel::class.java]

        // check permission and load contacts()
        checkPermission()
    }

    // Function to check and request permission.
    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this, PERMISSION_READ_CONTACTS
            ) == PackageManager.PERMISSION_DENIED
        ) {
            // Requesting the permission
            ActivityCompat.requestPermissions(
                this, arrayOf(PERMISSION_READ_CONTACTS), PERMISSIONS_REQUEST_READ_CONTACTS
            )
        } else {
            val contactsObserver = Observer<ArrayList<Contact>> { newContactsList ->
                // Update the UI
                if (newContactsList.isNotEmpty()) {
                    recyclerview.adapter = ContactAdapter(newContactsList, this)
                } else {
                    Toast.makeText(this, "No contacts to show", Toast.LENGTH_SHORT)
                        .show()
                }

            }
            // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
            viewModel.contactsArrayList.observe(this, contactsObserver)

            viewModel.getContacts()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPermission()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.grant_permission_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun didSelectContact(contact: Contact) {
        val bundle = Bundle()
        bundle.putParcelable(SELECTED_CONTACT, contact)

        startActivity(
            Intent(this, ContactDetailsActivity::class.java)
                .putExtra(CONTACT_DETAIL, bundle)
        )
    }
}

