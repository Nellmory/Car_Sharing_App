package com.example.myapp2.car

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
import com.example.myapp2.client.ClientAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [CarList.newInstance] factory method to
 * create an instance of this fragment.
 */
class CarList : Fragment() {

    private lateinit var repository: TableAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var carAdapter: CarAdapter

    private val vm by viewModels<CarListVM>()

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
        val view = inflater.inflate(R.layout.fragment_car_list, container, false)
        val goBackButton: Button = view.findViewById(R.id.goBack)
        /*val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service: ApiService = retrofit.create(ApiService::class.java)

        val testB: Button = view.findViewById(R.id.testB)
        val textSukuna: TextView = view.findViewById(R.id.test)

        testB.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val hellogojo = service.hello().await()
                textSukuna.text = hellogojo.result
            }
        }*/

        repository = TableAdapter()
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        vm.client.observe(viewLifecycleOwner) {
            carAdapter = CarAdapter(it)
            recyclerView.adapter = carAdapter
        }

        vm.updateList()

        goBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_car_list_to_activityHub)
        }

        return view
    }

    companion object {
        /*/**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment car_list.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CarList().apply {
                /*arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }*/
            }*/
    }
}