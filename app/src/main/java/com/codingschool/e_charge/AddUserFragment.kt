package com.codingschool.e_charge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.codingschool.e_charge.data.room.AppDataBase
import com.codingschool.e_charge.data.room.User
import com.codingschool.e_charge.databinding.FragmentAddUserBinding

class AddUserFragment : Fragment() {
    private val database: AppDataBase get() = (requireActivity().application as MyApplication).database
    private lateinit var binding: FragmentAddUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btRegister.setOnClickListener {
            when {
                binding.etUsername.text.isNullOrBlank() -> binding.tilUsername.error =
                    "Please enter a username"
                binding.etEmail.text.isNullOrBlank() -> binding.tilEmail.error =
                    "Please enter your e-mail"
                else -> registerUser(
                    binding.etUsername.text.toString(),
                    binding.etEmail.text.toString()
                )
            }
        }
    }

    private fun registerUser(name: String, email: String) {
        if (database.userDao().existsUserWithEmail(email) == 0) {
            val user = User(email, name, "test", "test", null)
            database.userDao().insertUser(user)
            Toast.makeText(
                requireActivity(),
                "User \"$name\" has been successfully added",
                Toast.LENGTH_SHORT
            )
                .show()
            findNavController().navigate(R.id.toFirstFragment)
        } else {
            binding.tilEmail.error = "E-mail address is already in use"
        }

    }
}