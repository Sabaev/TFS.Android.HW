package com.funtik.fintech.contacts

import android.app.IntentService
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.funtik.fintech.App


class ContactIntentService: IntentService("ContactIntentService") {
    companion object {
        const val RESULT_MSG = "com.funtik.fintech.contacts"
    }


    override fun onHandleIntent(intent: Intent?) {



        Log.e(App.TAG, "Start IntentService")

        SystemClock.sleep(5000) // 5 seconds

        Log.e(App.TAG, "Finish IntentService")

        val broadcastIntent = Intent(ContactActivity.TAKE_CONTACTS_ACTION)
        broadcastIntent.putExtra(RESULT_MSG,"Hello from intentService")
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)

    }

    // этот метод вызывается при stopService, но onHandlerIntent запускается в своём потоке
    override fun onDestroy() {
        Log.e(App.TAG,"onDestroy method started")
        super.onDestroy()

    }
}