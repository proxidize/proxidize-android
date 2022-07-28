package com.legacy.android

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.*
import android.provider.Settings
import android.text.Html
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.legacy.android.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess
import frpclib.Frpclib as Conn


class LoginActivity : AppCompatActivity() {
    private var user: String? = null
    private var pwd: String? = null
    private var notificationManager: NotificationManagerCompat? = null
    private val mLabel = "copy"
    private var mText: String = ""
    private var mRandomPort = 0

    private var server: ProxyServer = ProxyServer.default()

    private lateinit var binding: ActivityLoginBinding
    private val preference by lazy { ServerPreference.getInstance(this) }

    @SuppressLint("BatteryLife")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                preference.SERVER.collect{
                    server = it
                }
            }
        }
        notificationManager = NotificationManagerCompat.from(this)
        val pm = getSystemService(PowerManager::class.java)
        if (!pm.isIgnoringBatteryOptimizations(packageName)) {
            val i = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                .setData(Uri.parse("package:$packageName"))
            startActivity(i)
        }
        binding.copyButton.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(mLabel, mText)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this@LoginActivity, "Copied", Toast.LENGTH_SHORT).show()
        }


        binding.stop.setOnClickListener {
            AlertDialog.Builder(this@LoginActivity)
                .setCancelable(false)
                .setTitle("Confirm Exit")
                .setMessage("Are you sure, You want to exit")
                .setPositiveButton("sure") { d, _ ->
                    stopService(Intent(applicationContext, NotificationService::class.java))
                    d.dismiss()
                    Handler(Looper.getMainLooper()).postDelayed({
                        exitProcess(0)
                    }, 100)
                }.setNegativeButton("NO", null)
                .create()
                .show()
        }
        binding.connect.setOnClickListener {
            writeToConfigFile(server)
            Toast.makeText(this@LoginActivity, "Connecting to proxy server", Toast.LENGTH_LONG)
                .show()
            startConnection()
            Handler(Looper.getMainLooper()).postDelayed({ checkIfPortUsed() }, 3000)
        }

        binding.customServer.setOnClickListener{
            startActivity(Intent(this, CustomServerActivity::class.java))
        }


    }

    private fun checkIfPortUsed(): Boolean {
        var connectionStatus = true
        val logfile = File(filesDir, MyApplication.LOGFILE)
        if (logfile.exists()) {
            try {
                val inputStream: InputStream = assets.open(MyApplication.LOGFILE)
                var line: String? = null
                val br = BufferedReader(FileReader(logfile))
                var isFileEmpty = true
                while ((br.readLine()?.also { line = it }) != null) {
                    isFileEmpty = false
                    if (line?.contains("port already used") == true) {
                        connectionStatus = false
                        break
                    }
                }
                if (!connectionStatus) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Port is already used. Try again",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (isFileEmpty) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Not connected. Restart app and connect again",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    showConnectionDetails()
                    val bundle = Bundle().also {
                        it.putInt(PORT, mRandomPort)
                        it.putString(USER, user)
                        it.putString(PASS, pwd)
                    }
                    Toast.makeText(this@LoginActivity, "connection success", Toast.LENGTH_LONG)
                        .show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        val sdf = SimpleDateFormat("HH:mm:ss z")
                        val currentTime = sdf.format(Date())
                        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.logo)
                        val intent1 = Intent(this, NotificationService::class.java)
                        intent1.action = "close_service"
                        var pIntent: PendingIntent? = null
                        pIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            PendingIntent.getService(this, 0, intent1, FLAG_MUTABLE)
                        } else PendingIntent.getService(this, 0, intent1, 0)

                        val p2Inten = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            PendingIntent.getActivity(
                                this,
                                2,
                                Intent(this, LoginActivity::class.java),
                                FLAG_MUTABLE
                            )
                        } else {
                            PendingIntent.getActivity(
                                this,
                                2,
                                Intent(this, LoginActivity::class.java),
                                0
                            )
                        }
                        val notification = NotificationCompat.Builder(
                            applicationContext,
                            MyApplication.channel_1_id
                        )
                            .setSmallIcon(R.drawable.bill)
                            .setLargeIcon(largeIcon)
                            .setStyle(NotificationCompat.BigPictureStyle())
                            .setStyle(NotificationCompat.BigTextStyle())
                            .setContentIntent(p2Inten)
                            .setContentTitle("Connected")
                            .setExtras(bundle)
                            .addAction(
                                NotificationCompat.Action(
                                    R.drawable.ic_baseline_close_24,
                                    "Close",
                                    pIntent
                                )
                            )
                            .setContentText(
                                "You are Connected to (${server.host}:$mRandomPort:$user:$pwd)On time$currentTime"
                            )
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setCategory(NotificationCompat.CATEGORY_ALARM)
                            .setColor(Color.YELLOW).build()
                        notificationManager?.notify(NOTIFICATION_ID, notification)
                    }, 2000)
                    val servicesIntent = Intent(applicationContext, NotificationService::class.java)
                    servicesIntent.putExtra("stat", "Proxidize Android is running!!")
                    startService(servicesIntent)
                }
                br.close()
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return connectionStatus

    }

    override fun onStart() {
        super.onStart()
        val notification = getActiveNotification() ?: return
        val extras = notification.extras
        pwd = extras.getString(PASS)
        user = extras.getString(USER)
        mRandomPort = extras.getInt(PORT)
        showConnectionDetails()

    }

    private fun showConnectionDetails() {
        mText = "${server.host}:$mRandomPort:$user:$pwd"
        val details =
            "<b>IP</b> : ${server.host}<br><b>Port</b> : $mRandomPort<br><b>Username</b> : $user<br><b>Password</b> : $pwd"
        binding.connection.text = mText
        binding.connectionTextView.text = Html.fromHtml(details)
        binding.connect.text = getString(R.string.connected)
    }

    private fun startConnection() {
        CoroutineScope(Dispatchers.IO).launch {
            Conn.touch()
            Conn.run("$filesDir/config.ini")
        }
    }

    private fun getActiveNotification(): Notification? {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val barNotifications = notificationManager.activeNotifications
        for (notification in barNotifications) {
            if (notification.id == NOTIFICATION_ID) {
                return notification.notification
            }
        }
        return null
    }

    private fun writeToConfigFile(server: ProxyServer) {
        // re-create connection.log file
        val logFile = File(filesDir, MyApplication.LOGFILE)
        if (logFile.exists()) {
            logFile.delete()
        }
        if (!logFile.exists()) {
            try {
                logFile.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        var out: FileOutputStream? = null
        val file = File(filesDir, MyApplication.FILENAME)
        if (file.exists()) {
            file.delete()
        }
        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        try {
            out = FileOutputStream(file, true)
            mRandomPort = getRandomPort()
            user = getAlphaNumericString()
            pwd = getAlphaNumericString()
            out.write("[common]\r\n".toByteArray())
            out.write("server_addr = ${server.host}\r\n".toByteArray())
            out.write("server_port = ${server.port}\r\n".toByteArray())
            out.write("token = ${server.token}\r\n".toByteArray())
            out.write("admin_addr = 0.0.0.0\r\n".toByteArray())
            out.write("admin_port = 7400\r\n".toByteArray())
            out.write("admin_user = admin\r\n".toByteArray())
            out.write("admin_passwd = admin\r\n".toByteArray())
            out.write("log_file = ${logFile.absolutePath}\r\n".toByteArray())
            out.write("log_level = info\r\n".toByteArray())
            out.write("log_max_days = 3\r\n".toByteArray())
            out.write("pool_count = 5\r\n".toByteArray())
            out.write("tcp_mux = true\r\n".toByteArray())
            out.write("login_fail_exit = true\r\n".toByteArray())
            out.write("protocol = tcp\r\n".toByteArray())
            out.write("[android_proxy_$mRandomPort]\r\n".toByteArray())
            out.write("type=tcp\r\n".toByteArray())
            out.write("remote_port=$mRandomPort\r\n".toByteArray())
            out.write("plugin=http_proxy\r\n".toByteArray())
            out.write("plugin_http_user=$user\r\n".toByteArray())
            out.write("plugin_http_passwd=$pwd\r\n".toByteArray())
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                out?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    companion object {
        const val NOTIFICATION_ID = 20340
        const val IP = "IP"
        const val PORT = "PORT"
        const val USER = "USER"
        const val PASS = "PASS"
    }
}