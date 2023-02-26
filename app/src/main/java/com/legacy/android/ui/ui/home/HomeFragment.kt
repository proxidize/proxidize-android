/*
 * Copyright © 2020-2023 Proxidize. All Rights Reserved.
 */
package com.legacy.android.ui.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.*
import android.provider.Settings
import android.text.method.LinkMovementMethod
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.legacy.android.*
import com.legacy.android.databinding.FragmentHomeBinding
import com.legacy.android.network.ProxyServer
import com.legacy.android.service.isDefaultCurrentAssist
import com.legacy.android.ui.NavDrawerActivity
import dagger.hilt.android.AndroidEntryPoint
import frpclib.Frpclib
import kotlinx.coroutines.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var user: String? = null
    private var pwd: String? = null
    private var notificationManager: NotificationManagerCompat? = null
    private var mRandomPortWeb = 0
    private var mRandomPortHttp = 0
    private var mRandomPortSocks = 0

    private var server: ProxyServer = ProxyServer.default()

    private val preference by lazy { ServerPreference.getInstance(requireContext()) }

    private var _binding: FragmentHomeBinding? = null

    private val viewModel by activityViewModels<HomeViewModel>()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            activity?.window?.statusBarColor =
                ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)
        }

        viewModel.currentServer.observe(viewLifecycleOwner) { proxyServer ->
            proxyServer?.let { server = it }
        }
        viewModel.servers.observe(viewLifecycleOwner) {
            if (getActiveNotification() == null && runBlocking { viewModel.getCurrentConfig() == "Auto" }) {
                it.data?.let { it1 -> viewModel.calculateServer(it1) }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                preference.SERVER.collect {
                    server = it
                }
            }
        }
        notificationManager = NotificationManagerCompat.from(requireContext())

        val pm = requireContext().getSystemService(PowerManager::class.java)
        if (!pm.isIgnoringBatteryOptimizations(requireContext().packageName)) {
            val i = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                .setData(Uri.parse("package:${requireContext().packageName}"))
            startActivity(i)
        }
        binding.copyHttp.setOnClickListener {
            val clipboard =
                requireContext().getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Htt Proxy", binding.connectionHttp.text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "Copied", Toast.LENGTH_SHORT).show()
        }
        binding.copySocks.setOnClickListener {
            val clipboard =
                requireContext().getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Socks Proxy", binding.connectionSocks.text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "Copied", Toast.LENGTH_SHORT).show()
        }
        binding.copyIpChange.setOnClickListener {
            val clipboard =
                requireContext().getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("IP Change", binding.connectionIp.text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "Copied", Toast.LENGTH_SHORT).show()
        }


        binding.stop.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setCancelable(false)
                .setTitle("Confirm Exit")
                .setMessage("Are you sure, You want to exit")
                .setPositiveButton("sure") { d, _ ->
                    requireContext().stopService(
                        Intent(
                            requireContext().applicationContext,
                            NotificationService::class.java
                        )
                    )
                    d.dismiss()
                    Handler(Looper.getMainLooper()).postDelayed({
                        exitProcess(0)
                    }, 100)
                }.setNegativeButton("NO", null)
                .create()
                .show()
        }
        binding.connect.setOnClickListener {
            writeToConfigFile(server, false)
            Toast.makeText(requireContext(), "Connecting to proxy server", Toast.LENGTH_LONG)
                .show()
            startConnection()
            Handler(Looper.getMainLooper()).postDelayed({ checkIfPortUsed() }, 3000)
//            context?.let { it1 -> createCredentialsFile(server, it1) }
        }
        binding.rotate.setOnClickListener {
            requireContext().startService(
                Intent(
                    requireContext().applicationContext,
                    NotificationService::class.java
                ).apply { action = ACTION_ROTATE })
        }
        binding.autoRotate.setOnClickListener {
            showAutoRotate()
        }
        binding.githubLink.movementMethod = LinkMovementMethod.getInstance()
    }


    private fun showAutoRotate() {
        val viewInflated: View = LayoutInflater.from(requireContext())
            .inflate(R.layout.input, binding.root as ViewGroup?, false)
        val input = viewInflated.findViewById<View>(R.id.input) as EditText

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Custom IP Rotation")
            .setMessage("Please input time in minutes for the auto IP rotation.")
            .setView(viewInflated)
            .setPositiveButton(
                "Set", null
            )
            .setNegativeButton(
                "Cancel"
            ) { _, _ -> }
            .create()
        dialog.setOnShowListener {
            val button: Button =
                (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
            button.setOnClickListener {
                val intervalString = input.text.toString()
                try {
                    val interval = intervalString.toInt()
                    if (interval > 0) {
                        runBlocking { preference.saveTimeInterval(interval) }
                        requireContext().setAlarm(SystemClock.elapsedRealtime() + interval * 60_000)
                        dialog.dismiss()
                    } else {
                        input.error = "Please input valid time interval"
                    }
                } catch (e: NumberFormatException) {
                    input.error = "Please input valid time interval"
                }
            }
        }
        dialog.show()
    }

    private fun checkIfPortUsed(): Boolean {
        var connectionStatus = true
        val logfile = File(requireContext().filesDir, MyApplication.LOGFILE)
        if (logfile.exists()) {
            try {
                val inputStream: InputStream = requireContext().assets.open(MyApplication.LOGFILE)
                var line: String? = null
                val br = BufferedReader(FileReader(logfile))
                var isFileEmpty = true
                while ((br.readLine()?.also { line = it }) != null) {
                    isFileEmpty = false
                    if (line?.contains("port already used") == true) {
                        connectionStatus = false
                        writeToConfigFile(server, true)
                        break
                    }
                }
                if (!connectionStatus) {
                    Toast.makeText(
                        requireContext(),
                        "Port is already used. Try again",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (isFileEmpty) {
                    Toast.makeText(
                        requireContext(),
                        "Not connected. Restart app and connect again",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    showConnectionDetails()
                    val bundle = Bundle().also {
                        it.putInt(PORT_HTTP, mRandomPortHttp)
                        it.putInt(PORT_IP, mRandomPortWeb)
                        it.putInt(PORT_SOCKS, mRandomPortSocks)
                        it.putString(USER, user)
                        it.putString(PASS, pwd)
                    }
                    Toast.makeText(requireContext(), "connection success", Toast.LENGTH_LONG)
                        .show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        val sdf = SimpleDateFormat("HH:mm:ss z")
                        val currentTime = sdf.format(Date())
                        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.logo)
                        val intent1 = Intent(requireContext(), NotificationService::class.java)
                        intent1.action = ACTION_CLOSE
                        var pIntent: PendingIntent? = null
                        pIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            PendingIntent.getService(
                                requireContext(),
                                0,
                                intent1,
                                PendingIntent.FLAG_MUTABLE
                            )
                        } else PendingIntent.getService(requireContext(), 0, intent1, 0)

                        val p2Inten = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            PendingIntent.getActivity(
                                requireContext(),
                                2,
                                Intent(requireContext(), NavDrawerActivity::class.java),
                                PendingIntent.FLAG_MUTABLE
                            )
                        } else {
                            PendingIntent.getActivity(
                                requireContext(),
                                2,
                                Intent(requireContext(), NavDrawerActivity::class.java),
                                0
                            )
                        }
                        val notification = NotificationCompat.Builder(
                            requireContext().applicationContext,
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
                                "You are Connected to (${server.IP}:$mRandomPortHttp:$user:$pwd)On time$currentTime"
                            )
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setCategory(NotificationCompat.CATEGORY_ALARM)
                            .setColor(Color.YELLOW).build()
                        notificationManager?.notify(NOTIFICATION_ID, notification)
                    }, 2000)
                    val servicesIntent =
                        Intent(requireContext().applicationContext, NotificationService::class.java)
                    servicesIntent.putExtra("stat", "Proxidize Android is running!!")
                    requireContext().startService(servicesIntent)
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
        showDefaultAssistant()
        val notification = getActiveNotification() ?: return
        val extras = notification.extras
        pwd = extras.getString(PASS)
        user = extras.getString(USER)
        mRandomPortHttp = extras.getInt(PORT_HTTP)
        mRandomPortSocks = extras.getInt(PORT_SOCKS)
        mRandomPortWeb = extras.getInt(PORT_IP)
        showConnectionDetails()

    }


    private fun showDefaultAssistant() {
        if (!isDefaultCurrentAssist(requireContext()))
            AlertDialog.Builder(requireContext())
                .setTitle("Default Assistant")
                .setMessage("Proxidize requires the default assistant permission to change the IP of the device.")
                .setPositiveButton("Set") { _, _ ->
                    startActivityForResult(
                        Intent(Settings.ACTION_VOICE_INPUT_SETTINGS),
                        0
                    )
                }
                .setNegativeButton("Cancel") { d, _ -> d.dismiss() }
                .show()
    }

    @SuppressLint("SetTextI18n")
    private fun showConnectionDetails() {
        binding.connectionGrp.isVisible = true
        binding.connectionHttp.text = "${server.IP}:$mRandomPortHttp:$user:$pwd"
        binding.connectionSocks.text = "${server.IP}:$mRandomPortSocks:$user:$pwd"
        binding.connectionIp.text = "${server.IP}:$mRandomPortWeb/change_ip?t=$user$pwd"
        binding.connect.text = getString(R.string.connected)
    }

    private fun startConnection() {
        CoroutineScope(Dispatchers.IO).launch {
            Frpclib.touch()
            Frpclib.run("${requireContext().filesDir}/config.ini")
        }
    }

    private fun getActiveNotification(): Notification? {
        val notificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val barNotifications = notificationManager.activeNotifications
        for (notification in barNotifications) {
            if (notification.id == NOTIFICATION_ID) {
                return notification.notification
            }
        }
        return null
    }
    //this method if we need these information for us
    private fun createCredentialsFile(server: ProxyServer,context: Context){
        val file = File(context.getExternalFilesDir(null),"CredentialsAndPortsDetails.txt")
        var out: FileOutputStream? = null

        try {
            out = FileOutputStream(file, true)
            out.write("Connection Details : \r\n".toByteArray())
            out.write("server_addr = ${server.IP}\r\n".toByteArray())
            out.write("server_port = ${server.port}\r\n".toByteArray())
            out.write("token = ${server.token}\r\n".toByteArray())
            out.write("log_level = info\r\n".toByteArray())
            out.write("log_max_days = 3\r\n".toByteArray())
            out.write("pool_count = 5\r\n".toByteArray())
            out.write("tcp_mux = true\r\n".toByteArray())
            out.write("login_fail_exit = true\r\n".toByteArray())
            out.write("protocol = tcp\r\n".toByteArray())
            out.write("[api_$mRandomPortWeb]\r\n".toByteArray())
            out.write("type=tcp\r\n".toByteArray())
            out.write("local_ip = 0.0.0.0\r\n".toByteArray())
            out.write("local_port = 8080\r\n".toByteArray())
            out.write("remote_port = $mRandomPortWeb\r\n".toByteArray())
            out.write("[android_http_proxy_$mRandomPortHttp]\r\n".toByteArray())
            out.write("type=tcp\r\n".toByteArray())
            out.write("remote_port=$mRandomPortHttp\r\n".toByteArray())
            out.write("plugin=http_proxy\r\n".toByteArray())
            out.write("plugin_http_user=$user\r\n".toByteArray())
            out.write("plugin_http_passwd=$pwd\r\n".toByteArray())
            out.write("[android_socks_proxy_$mRandomPortSocks]\r\n".toByteArray())
            out.write("type=tcp\r\n".toByteArray())
            out.write("remote_port=$mRandomPortSocks\r\n".toByteArray())
            out.write("plugin=socks5\r\n".toByteArray())
            out.write("plugin_http_user=$user\r\n".toByteArray())
            out.write("plugin_http_passwd=$pwd\r\n".toByteArray())
            out.write("------------------------------------------------------------------------------\n".toByteArray())
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                out?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

    }}

    private fun writeToConfigFile(server: ProxyServer, resetPorts: Boolean) {
        // re-create connection.log file
        val logFile = File(requireContext().filesDir, MyApplication.LOGFILE)
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
        val file = File(requireContext().filesDir, MyApplication.FILENAME)
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
            initializePorts(resetPorts)
            out.write("[common]\r\n".toByteArray())
            out.write("server_addr = ${server.IP}\r\n".toByteArray())
            out.write("server_port = ${server.port}\r\n".toByteArray())
            out.write("token = ${server.token}\r\n".toByteArray())
            out.write("log_file = ${logFile.absolutePath}\r\n".toByteArray())
            out.write("log_level = info\r\n".toByteArray())
            out.write("log_max_days = 3\r\n".toByteArray())
            out.write("pool_count = 5\r\n".toByteArray())
            out.write("tcp_mux = true\r\n".toByteArray())
            out.write("login_fail_exit = true\r\n".toByteArray())
            out.write("protocol = tcp\r\n".toByteArray())
            out.write("[api_$mRandomPortWeb]\r\n".toByteArray())
            out.write("type=tcp\r\n".toByteArray())
            out.write("local_ip = 0.0.0.0\r\n".toByteArray())
            out.write("local_port = 8080\r\n".toByteArray())
            out.write("remote_port = $mRandomPortWeb\r\n".toByteArray())
            out.write("[android_http_proxy_$mRandomPortHttp]\r\n".toByteArray())
            out.write("type=tcp\r\n".toByteArray())
            out.write("remote_port=$mRandomPortHttp\r\n".toByteArray())
            out.write("plugin=http_proxy\r\n".toByteArray())
            out.write("plugin_http_user=$user\r\n".toByteArray())
            out.write("plugin_http_passwd=$pwd\r\n".toByteArray())
            out.write("[android_socks_proxy_$mRandomPortSocks]\r\n".toByteArray())
            out.write("type=tcp\r\n".toByteArray())
            out.write("remote_port=$mRandomPortSocks\r\n".toByteArray())
            out.write("plugin=socks5\r\n".toByteArray())
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

    private fun initializePorts(resetPorts: Boolean) {
        val (port1, port2, port3) = runBlocking { preference.retrievePorts() }
        val portEmpty = port1 == 0 || port2 == 0 || port3 == 0
        if (resetPorts || portEmpty) {
            mRandomPortHttp = getRandomPort()
            mRandomPortWeb = getRandomPort()
            mRandomPortSocks = getRandomPort()
            lifecycleScope.launch {
                preference.savePorts(
                    mRandomPortHttp,
                    mRandomPortWeb,
                    mRandomPortSocks
                )
            }
        } else {
            mRandomPortHttp = port1
            mRandomPortWeb = port2
            mRandomPortSocks = port3
        }

        user = getAlphaNumericString()
        pwd = getAlphaNumericString()
        lifecycleScope.launch { preference.saveUserAndPassword("$user$pwd") }
        println(user)
        println(pwd)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val NOTIFICATION_ID = 20340
        const val IP = "IP"
        const val PORT_HTTP = "PORT_HTTP"
        const val PORT_IP = "PORT_IP"
        const val PORT_SOCKS = "PORT_SOCKS"
        var USER = "USER"
        var PASS = "PASS"
    }

}