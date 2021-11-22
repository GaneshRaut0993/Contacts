package com.anom.contactsapp.repository

import android.annotation.SuppressLint
import android.content.Context
import android.provider.ContactsContract
import com.anom.contactsapp.model.Contact

class ContactRepository(private var context: Context) {

    @SuppressLint("Range")
    fun fetchContacts(): ArrayList<Contact> {
        val contacts = ArrayList<Contact>()

        val cursor = context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI, null, null, null,
            null
        )

        if (cursor != null && cursor.count > 0) {
            while (cursor.moveToNext()) {

                val id =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID))
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phoneNumber: Int = Integer.parseInt(
                    (cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
                    ))
                )

                if (phoneNumber > 0) {
                    val cursorPhone = context.contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                        arrayOf(id),
                        null
                    )

                    if (cursorPhone != null && cursorPhone.count > 0) {
                        while (cursorPhone.moveToNext()) {
                            val phoneNumValue = cursorPhone.getString(
                                cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            )
                            contacts.add(Contact(name, phoneNumValue))
                            // restrict to add only one phone number if has multiple
                            break
                        }
                    }
                    cursorPhone?.close()
                }
            }
        }
        cursor?.close()
        return contacts
    }
}