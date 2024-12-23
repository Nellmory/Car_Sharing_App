package com.example.myapp2.tariff

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp2.R
import com.example.myapp2.TableAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [TariffList.newInstance] factory method to
 * create an instance of this fragment.
 */
class TariffList : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var repository: TableAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var tariffAdapter: TariffAdapter

    private val vm by viewModels<TariffListVM>()


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
        val view = inflater.inflate(R.layout.fragment_tariff_list, container, false)
        val goBackButton: Button = view.findViewById(R.id.goBackT)

        repository = TableAdapter()
        recyclerView = view.findViewById(R.id.recyclerViewTariffs)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        vm.client.observe(viewLifecycleOwner) {
            tariffAdapter = TariffAdapter(it)
            recyclerView.adapter = tariffAdapter
        }

        vm.updateList()

        goBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_tariffList_to_activityHub)
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
         * @return A new instance of fragment TariffList.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TariffList().apply {
                /*arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }*/
            }
    }
}