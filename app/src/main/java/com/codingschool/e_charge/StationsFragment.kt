package com.codingschool.e_charge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codingschool.e_charge.data.room.AppDataBase
import com.codingschool.e_charge.data.room.StationAndPlugs
import com.codingschool.e_charge.data.room.Type
import com.codingschool.e_charge.databinding.FragmentStationsBinding

class StationsFragment : Fragment() {

    private val database: AppDataBase get() = (requireActivity().application as MyApplication).database
    private lateinit var binding: FragmentStationsBinding
    private lateinit var recyclerview: RecyclerView
    private lateinit var adapter: StationListAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var currentUserId = 0

    private lateinit var stationsAndPlugs: List<StationAndPlugs>
    private lateinit var plugTypeList: List<Type>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments?.let { StationsFragmentArgs.fromBundle(it) }
        currentUserId = args?.userId ?: 0

        val userName = database.userDao().getUserById(currentUserId).user_name

        stationsAndPlugs = database.stationDao().getStationsWithPlugsForUserWithId(currentUserId)

        // no stations yet ?
        if (stationsAndPlugs.isEmpty()) {
            binding.tvNoStations.text =
                "User \"$userName\" has no stations yet. Just click the + button below to add the first station."
            binding.tvNoStations.visibility = View.VISIBLE
        } else {
            Toast.makeText(
                requireActivity(),
                "Showing stations for user: $userName",
                Toast.LENGTH_LONG
            )
                .show()
        }

        adapter = StationListAdapter()

        plugTypeList = database.typeDao().getAllTypes()

        adapter = StationListAdapter()
        adapter.setData(stationsAndPlugs, plugTypeList)
        layoutManager = LinearLayoutManager(activity)
        recyclerview = binding.rvStations
        recyclerview.layoutManager = layoutManager
        recyclerview.adapter = adapter

        binding.fabAddStation.setOnClickListener {
            val action = StationsFragmentDirections.toNewStationFragment(currentUserId)
            NavHostFragment.findNavController(requireParentFragment()).navigate(action)
        }
    }

}