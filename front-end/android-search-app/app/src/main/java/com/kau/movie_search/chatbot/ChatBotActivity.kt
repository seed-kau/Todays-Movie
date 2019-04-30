package com.kau.movie_search.chatbot

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.telephony.TelephonyManager
import android.util.Log
import com.kau.movie_search.R
import com.kau.movie_search.server.ServerHandler
import kotlinx.android.synthetic.main.activity_chatbot.*
import java.util.*

class ChatBotActivity : AppCompatActivity() {

    lateinit var chatAdapter : ChatAdapter
    lateinit var chatLayoutManager : RecyclerView.LayoutManager
    var chats = ArrayList<ChatData> ()
    private lateinit var deviceUuid : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)

        initModel()
        initRecycler()
        aboutView()

        setupPermission()


    }

    private fun initModel () {
    }

    private fun initRecycler () {
        chatAdapter = ChatAdapter(chats, this)
        recyclerChat.adapter = chatAdapter

        chatLayoutManager = LinearLayoutManager(this)
        recyclerChat.layoutManager = chatLayoutManager

        recyclerChat.setHasFixedSize(true)
    }

    private fun aboutView () {
        btnChat.setOnClickListener {
//            val chatData = ChatData(deviceUuid, editChat.text.toString())
            val chatData = ChatData(deviceUuid, "한글 메시지")
            chats.add(chatData)
            editChat.setText("")
            val serverHandler = ServerHandler()
            serverHandler.postChat(chatData, this)
        }
    }

    private fun setupPermission () {
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            deviceUuid = getUniqueId(this)
        } else {
            makePermissionRequest()
        }
    }

    private fun makePermissionRequest() {
        requestPermissions( arrayOf(Manifest.permission.READ_PHONE_STATE), 1)
    }

    @SuppressLint("MissingPermission", "HardwareIds")
    private fun getUniqueId (context : Context) : String {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val tmDevice : String
        val tmSerial : String
        val androidId : String = "" + android.provider.Settings.Secure.getString(context.contentResolver, android.provider.Settings.Secure.ANDROID_ID)
        tmDevice = "" + tm.deviceId
        tmSerial = "" + tm.simSerialNumber

        val deviceUuid = UUID(androidId.hashCode().toLong(), tmDevice.hashCode().toLong() or tmSerial.hashCode().toLong())
        return deviceUuid.toString()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i("v", "Permission has been denied by user")
                } else {
                    deviceUuid = getUniqueId(this)
                }
            }
        }
    }
}