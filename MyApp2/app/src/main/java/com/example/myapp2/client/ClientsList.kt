package com.example.myapp2.client

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
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
    private val clientList = mutableListOf<Client>()
    private var isLoading = false
    private var hasMoreData = true
    private var currentPage = 1

    private lateinit var progressBar: ProgressBar
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

        progressBar = view.findViewById(R.id.progressBar)

        recyclerView = view.findViewById(R.id.recyclerViewClients)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        clientAdapter = ClientAdapter(clientList)
        clientAdapter.setOnClickDB(this@ClientsList)
        clientAdapter.setOnSaveClick(this@ClientsList)
        recyclerView.adapter = clientAdapter

        loadClients()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && hasMoreData) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        loadClients()
                    }
                }
            }
        })

        /*vm.client.observe(viewLifecycleOwner) {
            clientAdapter = ClientAdapter(it)
            clientAdapter.setOnClickDB(this@ClientsList)
            clientAdapter.setOnSaveClick(this@ClientsList)
            recyclerView.adapter = clientAdapter
        }

        vm.updateList()*/

        addButton.setOnClickListener {
            findNavController().navigate(R.id.action_clientsList_to_addClientForm)
        }
        goBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_clientsList_to_activityHub)
        }

        return view
    }

    private fun loadClients() {
        isLoading = true
        progressBar.visibility = View.VISIBLE

        vm.getClients(currentPage).observe(viewLifecycleOwner) { response ->
            if (response != null) {
                val newClients = response.clients
                if (newClients.isEmpty()) {
                    hasMoreData = false
                } else {
                    clientList.addAll(newClients)
                    //Log.d("ClientsList", "ClientList size before notify: ${clientList.size}")
                    clientAdapter.notifyDataSetChanged()
                    currentPage++
                    //Log.d("ClientsList", "Current page after update: $currentPage")
                }
                hasMoreData = response.has_next
                Log.d("API", "Current page: " + response.current_page)
                Log.d("API", "Total pages: " + response.total_pages)
                Log.d("API", "Has next: " + response.has_next)
                Log.d("API", "Has prev: " + response.has_prev)

            }
            isLoading = false
            progressBar.visibility = View.GONE
        }
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