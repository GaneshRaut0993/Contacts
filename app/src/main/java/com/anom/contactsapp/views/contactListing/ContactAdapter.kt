package com.anom.contactsapp.views.contactListing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anom.contactsapp.R
import com.anom.contactsapp.model.Contact
import kotlinx.android.synthetic.main.contact_view.view.textName
import kotlinx.android.synthetic.main.contact_view.view.textPhone
import java.util.*

class ContactAdapter(
    private val contactsArrayList: ArrayList<Contact>, private val listener: ContactSelectionListener
): RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_view, parent, false)

        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.itemView.textName.text = contactsArrayList[position].name
        holder.itemView.textPhone.text = contactsArrayList[position].phoneNumber

        holder.itemView.setOnClickListener {
            listener.didSelectContact(contactsArrayList[position])
        }
    }

    override fun getItemCount(): Int {
        return contactsArrayList.size
    }
}

interface ContactSelectionListener {
    fun didSelectContact(contact: Contact)
}