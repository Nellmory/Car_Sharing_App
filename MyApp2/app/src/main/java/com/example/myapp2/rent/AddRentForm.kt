package com.example.myapp2.rent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapp2.Client
import com.example.myapp2.R
import com.example.myapp2.Rent
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * A simple [Fragment] subclass.
 * Use the [AddRentForm.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddRentForm : Fragment() {
    val vm by viewModels<RentListVM>(ownerProducer = { requireParentFragment().childFragmentManager.primaryNavigationFragment!! })

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_rent_form, container, false)
        var startET: EditText = view.findViewById(R.id.editTextStart)
        var finishET: EditText = view.findViewById(R.id.editTextFinish)
        var tariffET: EditText = view.findViewById(R.id.editTextTariff)
        var carET: EditText = view.findViewById(R.id.editTextCarId)
        var clientET: EditText = view.findViewById(R.id.editTextClientId)
        val addButton: Button = view.findViewById(R.id.addRentButton)

        addButton.setOnClickListener {
            val inputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())

            val startDate = inputFormat.parse(startET.text.toString())
            val finishDate = inputFormat.parse(finishET.text.toString())

            val start_date = outputFormat.format(startDate)
            val finish_date = outputFormat.format(finishDate)

            val tariff = tariffET.text.toString().toIntOrNull() ?: 0
            val car_id = carET.text.toString().toIntOrNull() ?: 0
            val client_id = clientET.text.toString().toIntOrNull() ?: 0

            val newRent = Rent(
                id = 0,
                start_date = start_date,
                finish_date = finish_date,
                tariff = tariff,
                car_id = car_id,
                client_id = client_id
            )

            vm.addRent(newRent)
            findNavController().navigate(R.id.action_addRentForm_to_rentList)
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddClientForm.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddRentForm().apply {
                /*arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }*/
            }
    }
}