package com.codingschool.e_charge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.codingschool.e_charge.data.room.AppDataBase
import com.codingschool.e_charge.data.room.Plug
import com.codingschool.e_charge.data.room.Station
import com.codingschool.e_charge.databinding.FragmentAddStationBinding

class NewStationFragment : Fragment() {
    private val database: AppDataBase get() = (requireActivity().application as MyApplication).database
    private lateinit var binding: FragmentAddStationBinding
    private var currentUserId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddStationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments?.let { NewStationFragmentArgs.fromBundle(it) }
        currentUserId = args?.userId ?: 0

        val userName = database.userDao().getUserById(currentUserId).user_name

        Toast.makeText(requireActivity(), "Adding a station for user: $userName", Toast.LENGTH_LONG)
            .show()

        binding.npCarPlugs.minValue = 0
        binding.npCarPlugs.maxValue = 10
        binding.npBikePlugs.minValue = 0
        binding.npBikePlugs.maxValue = 10
        binding.npBoatPlugs.minValue = 0
        binding.npBoatPlugs.maxValue = 10
        binding.npPlanePlugs.minValue = 0
        binding.npPlanePlugs.maxValue = 10

        binding.btRegister.setOnClickListener {
            val carPlugs = binding.npCarPlugs.value
            val bikePlugs = binding.npBikePlugs.value
            val boatPlugs = binding.npBoatPlugs.value
            val planePlugs = binding.npPlanePlugs.value
            val totalPlugs = carPlugs+bikePlugs+boatPlugs+planePlugs
            when {
                binding.etStreet.text.isNullOrBlank() -> binding.tilStreet.error =
                    "Please enter a street"
                binding.etNumber.text.isNullOrBlank() -> binding.tilNumber.error =
                    "Please enter the street number"
                binding.etZip.text.isNullOrBlank() -> binding.tilZip.error = "Please enter a ZIP"
                binding.etTown.text.isNullOrBlank() -> binding.tilTown.error =
                    "Please enter the town"
                binding.etCountry.text.isNullOrBlank() -> binding.tilTown.error =
                    "Please enter the country"
                totalPlugs == 0 ->  Toast.makeText(requireActivity(), "You need to add add least one plug to add the new station", Toast.LENGTH_LONG)
                    .show()
                database.stationDao().findExistingAddress(binding.etStreet.text.toString(),
                    binding.etNumber.text.toString(),
                    binding.etZip.text.toString(),
                    binding.etTown.text.toString(),
                    binding.etCountry.text.toString()) > 0 -> Toast.makeText(requireActivity(), "A station has already been registered on this address.", Toast.LENGTH_LONG)
                    .show()
                else -> {
                    val station = Station(
                        binding.etStreet.text.toString(),
                        binding.etNumber.text.toString(),
                        binding.etZip.text.toString(),
                        binding.etTown.text.toString(),
                        binding.etCountry.text.toString(),
                        currentUserId
                    )
                    val newStationId = registerStation(station)
                    Toast.makeText(requireActivity(), "station added successfully!", Toast.LENGTH_LONG)
                        .show()
                    registerPlugs(carPlugs,bikePlugs,boatPlugs,planePlugs, newStationId)

                }
            }
        }
    }

    private fun registerStation(station: Station): Int {
        return database.stationDao().insertStation(station).toInt()
    }

    private fun registerPlugs(
        carPlugs: Int,
        bikePlugs: Int,
        boatPlugs: Int,
        planePlugs: Int,
        stationId: Int
    ) {
        val carTypeId = database.typeDao().getIdByName("car")
        val bikeTypeId = database.typeDao().getIdByName("bike")
        val boatTypeId = database.typeDao().getIdByName("boat")
        val planeTypeId = database.typeDao().getIdByName("plane")

        for (p in 1..carPlugs) {
            val plug = Plug(stationId,carTypeId)
            database.plugDao().insertPlug(plug)
        }

        for (p in 1..bikePlugs) {
            val plug = Plug(stationId,bikeTypeId)
            database.plugDao().insertPlug(plug)
        }

        for (p in 1..boatPlugs) {
            val plug = Plug(stationId,boatTypeId)
            database.plugDao().insertPlug(plug)
        }

        for (p in 1..planePlugs) {
            val plug = Plug(stationId,planeTypeId)
            database.plugDao().insertPlug(plug)
        }
    }
}