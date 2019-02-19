package com.funtik.fintech

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.funtik.fintech.contacts.ContactActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var btnToSecondActivity: Button
    val CONTACT_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(R.string.main_activity_title)

        btnToSecondActivity = findViewById(R.id.btnToSecondActivity)
        btnToSecondActivity.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        startContactActivityForResult(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            CONTACT_REQUEST_CODE -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        val result = data?.getSerializableExtra(ContactActivity.EXTRA_CONFIRM_DATA)
                        Toast.makeText(this,result.toString(),Toast.LENGTH_SHORT).show()
                    }
                    Activity.RESULT_CANCELED -> { Toast.makeText(this,R.string.data_IsNotReceived,Toast.LENGTH_SHORT).show()}
                }
            }
        }
    }

    private fun startContactActivityForResult(activity: Activity) {
        val intent = Intent(activity, ContactActivity::class.java)
        activity.startActivityForResult(intent, CONTACT_REQUEST_CODE)
    }

}
