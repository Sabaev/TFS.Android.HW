package com.funtik.fintech.contacts

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.database.CrossProcessCursor
import android.database.Cursor
import android.os.SystemClock
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.funtik.fintech.App
import android.provider.ContactsContract
import java.io.Serializable


class ContactIntentService: IntentService("ContactIntentService") {
    companion object {
        const val RESULT_MSG = "com.funtik.fintech.contacts"
    }


    override fun onHandleIntent(intent: Intent?) {

        Log.e(App.TAG, "Start IntentService")
        val contracts = getContacts()
        Log.e(App.TAG, "Finish IntentService")

        val broadcastIntent = Intent(ContactActivity.TAKE_CONTACTS_ACTION)
        broadcastIntent.putExtra(RESULT_MSG, contracts )
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)

    }

    /**
     * Read the name of all the contacts.
     *
     * @return a list of names.
     */
    private fun getContacts(): ArrayList<String> {
        val contacts = ArrayList<String>()
        // Get the ContentResolver
        val cr = contentResolver
        // Get the Cursor of all the contacts
        val cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)

        // Move the cursor to first. Also check whether the cursor is empty or not.
        if (cursor!!.moveToFirst()) {
            // Iterate through the cursor
            do {
                // Get the contacts name
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
//                val phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                contacts.add(name)
            } while (cursor.moveToNext())
        }
        // Close the cursor
        cursor.close()

        return contacts
    }


    // этот метод вызывается при stopService, но onHandlerIntent запускается в своём потоке
    override fun onDestroy() {
        Log.e(App.TAG,"onDestroy method started")
        super.onDestroy()

    }
}