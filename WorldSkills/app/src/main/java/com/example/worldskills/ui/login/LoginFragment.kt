package com.example.worldskills.ui.login

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.worldskills.MainActivity
import com.example.worldskills.R
import com.example.worldskills.databinding.FragmentLoginBinding
import com.example.worldskills.utils.DataStore
import com.example.worldskills.utils.SnackBar
import com.example.worldskills.utils.Storage
import com.example.worldskills.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var dataStore: Storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStore = Storage(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater)

       // if (dataStore.getToken().isNotEmpty()){
          //  requireActivity().finish()
          //  val i = Intent(requireContext(), MainActivity::class.java)
            //startActivity(i)
       // }


/*        lifecycleScope.launch {
            dataStore.token.asLiveData().observe(viewLifecycleOwner, {
                if (it.isNullOrEmpty()) {
                }else {
                }
            })
        }*/

        binding.btnLoginLogin.setOnClickListener {
            when {
                binding.edtEnterPhoneLogin.text.isNullOrEmpty() -> {
                    SnackBar.addSnackBar("Please Fill All Amount", requireView())
                }
                binding.edtEnterPhoneLogin.text!!.length < 11 -> {
                    SnackBar.addSnackBar("Phone Number can not be less than 11", requireView())
                }
                else -> {
                    sendSms(binding.edtEnterPhoneLogin.text.toString())
                }
            }
        }

        return binding.root
    }

    private fun sendSms(phone: String) {
        mainViewModel.sendSms(phone)
        mainViewModel.smsResponse.observe(viewLifecycleOwner, { response ->
            SnackBar.addSnackBar(response, requireView())
            if (response.contains("Code")) {
                getCode(phone)
            } else {
                SnackBar.addSnackBar("Please Check Every Things", requireView())
            }
        })
    }

    private fun getCode(phone: String) {
        mainViewModel.getCode(phone)
        mainViewModel.codeResponse.observe(viewLifecycleOwner, { code ->
            val action =
                LoginFragmentDirections.actionLoginFragmentToVerifyFragment(code!!, phone)
            findNavController().navigate(action)
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}