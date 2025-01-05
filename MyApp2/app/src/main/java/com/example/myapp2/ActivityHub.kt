package com.example.myapp2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ActivityHub.newInstance] factory method to
 * create an instance of this fragment.
 */
class ActivityHub : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_activity_hub, container, false)
        val carsButton: Button = view.findViewById(R.id.carsButton)
        val brandButton: Button = view.findViewById(R.id.brandButton)
        val tariffButton: Button = view.findViewById(R.id.tariffsButton)
        val clintsButton: Button = view.findViewById(R.id.clientsButton)
        val violationsButton: Button = view.findViewById(R.id.violationsButton)
        val rentsButton: Button = view.findViewById(R.id.rentsButton)
        val rentViolationButton: Button = view.findViewById(R.id.rentViolationButton)

        carsButton.setOnClickListener {
            findNavController().navigate(R.id.action_activityHub_to_car_list)
        }
        brandButton.setOnClickListener {
            findNavController().navigate(R.id.action_activityHub_to_brand_list)
        }
        tariffButton.setOnClickListener {
            findNavController().navigate(R.id.action_activityHub_to_tariffList)
        }
        clintsButton.setOnClickListener {
            findNavController().navigate(R.id.action_activityHub_to_clientsList)
        }
        violationsButton.setOnClickListener {
            findNavController().navigate(R.id.action_activityHub_to_violationsList)
        }
        rentsButton.setOnClickListener {
            findNavController().navigate(R.id.action_activityHub_to_rentList)
        }
        rentViolationButton.setOnClickListener {
            findNavController().navigate(R.id.action_activityHub_to_rentViolationList)
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
         * @return A new instance of fragment ActivityHub.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ActivityHub().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}