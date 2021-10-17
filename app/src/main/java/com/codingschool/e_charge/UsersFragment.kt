package com.codingschool.e_charge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingschool.e_charge.data.room.AppDataBase
import com.codingschool.e_charge.data.room.Type
import com.codingschool.e_charge.data.room.UserAndStations
import com.codingschool.e_charge.databinding.FragmentUsersBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class UsersFragment : Fragment() {

    private val database: AppDataBase get() = (requireActivity().application as MyApplication).database
    private lateinit var binding: FragmentUsersBinding
    private lateinit var recyclerview: RecyclerView
    private lateinit var adapter: UserListAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var userAndStations: List<UserAndStations>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // on first app start, no types exist -> we create initial plug types for demo purposes
        val types = database.typeDao().getAllTypes()
        if (types.isEmpty()) {
            Toast.makeText(
                requireActivity(),
                "Generating plug types car, bike, boat, plane...",
                Toast.LENGTH_LONG
            )
                .show()
            database.typeDao().insertType(Type("car"))
            database.typeDao().insertType(Type("bike"))
            database.typeDao().insertType(Type("boat"))
            database.typeDao().insertType(Type("plane"))
        }

        userAndStations = database.userDao().getUsersWithStations()

        // no users yet ?
        if (userAndStations.isEmpty()) binding.tvNoUsers.visibility = View.VISIBLE

        adapter = UserListAdapter()
        adapter.setData(userAndStations)

        adapter.addClickListener { user_id ->
            // show stations for user with user_id
            val action = UsersFragmentDirections.toStationsFragment(user_id)
            NavHostFragment.findNavController(this).navigate(action)
        }

        adapter.addLongClickListener { user_id ->
            // open delete dialog
            deleteUserDialog(user_id)
        }

        layoutManager = LinearLayoutManager(activity)
        recyclerview = binding.rvUsers
        recyclerview.layoutManager = layoutManager
        recyclerview.adapter = adapter

        binding.fabAddUser.setOnClickListener {
            findNavController().navigate(R.id.toAddUserFragment)
        }
    }

    private fun deleteUserDialog(user_id: Int) {
        MaterialAlertDialogBuilder(
            requireActivity(),
            R.style.materialDialog
        )
            .setTitle("Are you sure ?")
            .setMessage("Deleting the user will also delete the stations and plugs belonging to this user")
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("DELETE") { dialog, _ ->
                // will also delete plugs, stations with onDelete CASCADE
                database.userDao().deleteUserById(user_id)
                dialog.dismiss()
                refreshAdapter()
            }
            .show()
    }

    private fun refreshAdapter() {
        userAndStations = database.userDao().getUsersWithStations()
        adapter.setData(userAndStations)
    }
}