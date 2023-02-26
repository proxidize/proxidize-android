/*
 * Copyright © 2020-2023 Proxidize. All Rights Reserved.
 */
package com.legacy.android.ui.ui.gallery

import android.content.ContentValues
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.legacy.android.*
import com.legacy.android.databinding.ActivityCustomServerBinding
import com.legacy.android.network.ProxyServer
import com.legacy.android.ui.ui.home.HomeFragment
import com.legacy.android.ui.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class ChangeServerFragment : Fragment() {
    private lateinit var dbHelper: CredentialsDatabase
    private var _binding: ActivityCustomServerBinding? = null

    private val binding get() = _binding!!

    private val viewModel by activityViewModels<HomeViewModel>()
    private val preference by lazy { ServerPreference.getInstance(requireContext()) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = ActivityCustomServerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Sever", "onViewCreated: shit is happening")
        val application = requireActivity().application as MyApplication
        binding.textField.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, p2, _ ->
                setSelectedServer()
            }
        viewModel.servers.observe(viewLifecycleOwner) { resource ->

            resource.data?.let { servers ->
                val list = mutableListOf("Auto", "Custom")
                list.addAll(servers.map { it.region })
                binding.textField.setAdapter(
                    ArrayAdapter(
                        requireContext(),
                        R.layout.list_item,
                        list
                    )
                )
                val config = runBlocking { viewModel.getCurrentConfig() }
                binding.textField.setText(
                    binding.textField.adapter.getItem(list.indexOf(config)).toString(),
                    false
                )
            }


        }
        binding.saveButton.setOnClickListener {
            if (isClear()) {
                Toast.makeText(requireContext(), "Saving server info", Toast.LENGTH_SHORT).show()
                lifecycleScope.launch {
                    viewModel.serverConfig("Custom")
                    viewModel.selectedServer(
                        ProxyServer(
                            binding.etHost.text.toString(),
                            "Custom",
                            binding.etPort.text.toString().toInt(),
                            binding.etToken.text.toString(),
                            0.0, 0.0
                        )
                    )
                    preference.saveProxyServer(
                        ProxyServer(
                            binding.etHost.text.toString(),
                            "Custom",
                            binding.etPort.text.toString().toInt(),
                            binding.etToken.text.toString(),
                            0.0, 0.0
                        )
                    )

                    val values = ContentValues().apply {
                        put("hostIp",binding.etHost.text.toString())
                        put("region","Custom")
                        put("port",binding.etPort.text.toString().toInt())
                        put("token",binding.etToken.text.toString())
                        put("latitude",0.0)
                        put("longitude",0.0)
                    }
                    val newRowId = CredentialsDatabase(requireContext()).writableDatabase.update("mycredentials",values,"hostIp = ?", arrayOf("0.0.0.0"))
                    showMessage()
                    requireActivity().onBackPressed()

                }
            }
        }

    }

    private fun showMessage() {
        Toast.makeText(
            requireContext(),
            "Changes saved, please restart the app",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setSelectedServer() {
        val item = binding.textField.text.toString()
        Log.d("FUCK", "setSelectedServer: $item")
        binding.group.isVisible = item == "Custom"
        if (item == "Custom")
            return
        if (item == "Auto") {
            showMessage()
            viewModel.servers.value?.data?.let { viewModel.calculateServer(it) }
        } else {
            val selected = viewModel.servers.value?.data?.findLast { it.region == item }
            selected?.let {
                showMessage()
                viewModel.serverConfig(selected.region)
                viewModel.selectedServer(it)
            }
        }

    }

    private fun isClear(): Boolean {
        var clear = true
        if (binding.etHost.text.isNullOrEmpty()) {
            binding.etHost.error = "Enter a valid Host"
            clear = false
        } else binding.etHost.error = null
        if (binding.etPort.text.isNullOrEmpty()) {
            binding.etPort.error = "Enter a valid Port"
            clear = false
        } else binding.etPort.error = null
        if (binding.etToken.text.isNullOrEmpty()) {
            binding.etToken.error = "Enter a valid token"
            clear = false
        } else binding.etToken.error = null
        return clear
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}