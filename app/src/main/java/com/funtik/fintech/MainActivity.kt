package com.funtik.fintech

import android.Manifest
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.funtik.fintech.contacts.ContactActivity
import android.content.pm.PackageManager
import android.widget.ArrayAdapter
import android.os.Build
import android.widget.ListView


class MainActivity : AppCompatActivity(), View.OnClickListener {


    // Request code for READ_CONTACTS. It can be any number > 0.
    private val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    private val CONTACT_REQUEST_CODE = 1

    // view
    private lateinit var btnToSecondActivity: Button
    private lateinit var lstNames: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(R.string.main_activity_title)

        btnToSecondActivity = findViewById(R.id.btnToSecondActivity)
        btnToSecondActivity.setOnClickListener(this)

        // Find the list view
        lstNames = findViewById(R.id.lstNames)

    }


    override fun onClick(p0: View?) {
        loadContactsFromSecondActivity(this)
    }


    /**
     * load the contacts from the second Activity.
     */
    private fun loadContactsFromSecondActivity(activity: Activity) {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), PERMISSIONS_REQUEST_READ_CONTACTS)
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            val intent = Intent(activity, ContactActivity::class.java)
            startActivityForResult(intent, CONTACT_REQUEST_CODE)
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                loadContactsFromSecondActivity(this)
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            CONTACT_REQUEST_CODE -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        val result = data?.getSerializableExtra(ContactActivity.EXTRA_CONFIRM_DATA)

                        Toast.makeText(this, getString(R.string.contactsAreLoaded), Toast.LENGTH_SHORT).show()
                        @Suppress("UNCHECKED_CAST") val adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,result as ArrayList<String>)
                        lstNames.adapter = adapter

                    }
                    Activity.RESULT_CANCELED -> {
                        Toast.makeText(this, R.string.data_IsNotReceived, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


}
