package com.anom.contactsapp.views.contactDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anom.contactsapp.R
import com.anom.contactsapp.model.Contact
import com.anom.contactsapp.utility.Constants.CONTACT_DETAIL
import com.anom.contactsapp.utility.Constants.SELECTED_CONTACT
import kotlinx.android.synthetic.main.contact_view.textName
import kotlinx.android.synthetic.main.contact_view.textPhone

class ContactDetailsActivity : AppCompatActivity() {

    private lateinit var contact :Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_details)

        val bundle = intent.getBundleExtra(CONTACT_DETAIL)
        contact  = bundle?.getParcelable<Contact>(SELECTED_CONTACT) as Contact

        textName.text = contact.name
        textPhone.text = contact.phoneNumber
    }
}
