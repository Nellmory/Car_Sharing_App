package com.example.myapp2.client

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
import com.example.myapp2.Client
import com.example.myapp2.R
import com.example.myapp2.TableAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [ClientsList.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClientsList : Fragment(), ClientAdapter.OnItemClickedDB,
    ClientAdapter.OnSaveClick {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var repository: TableAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var clientAdapter: ClientAdapter

    private val vm by viewModels<ClientsListVM>()


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
        val view = inflater.inflate(R.layout.fragment_clients_list, container, false)
        val addButton: Button = view.findViewById(R.id.addClientButton)
        val goBackButton: Button = view.findViewById(R.id.goBack)

        recyclerView = view.findViewById(R.id.recyclerViewClients)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        vm.client.observe(viewLifecycleOwner) {
            clientAdapter = ClientAdapter(it)
            clientAdapter.setOnClickDB(this@ClientsList)
            clientAdapter.setOnSaveClick(this@ClientsList)
            recyclerView.adapter = clientAdapter
        }

        vm.updateList()

        addButton.setOnClickListener {
            findNavController().navigate(R.id.action_clientsList_to_addClientForm)
        }
        goBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_clientsList_to_activityHub)
        }

        return view
    }

    override fun onDeleteButtonClick(id: Int) {
        vm.removeClient(id)
    }

    override fun onSaveClick(id: Int, client: Client) {
        vm.editClient(id,client)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClientsList.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClientsList().apply {
                /*arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }*/
            }
    }
}