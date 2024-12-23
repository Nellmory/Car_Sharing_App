package com.example.myapp2.client

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

/**
 * A simple [Fragment] subclass.
 * Use the [AddClientForm.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddClientForm : Fragment() {
    val vm by viewModels<ClientsListVM>(ownerProducer = { requireParentFragment().childFragmentManager.primaryNavigationFragment!! })

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
        val view = inflater.inflate(R.layout.fragment_add_client_form, container, false)
        var nameET: EditText = view.findViewById(R.id.editTextName)
        var surnameET: EditText = view.findViewById(R.id.editTextSurname)
        var telephoneET: EditText = view.findViewById(R.id.editTextTelephone)
        val addButton: Button = view.findViewById(R.id.addClientButton)

        addButton.setOnClickListener {
            val name = nameET.text.toString()
            val surname = surnameET.text.toString()
            val telephone = telephoneET.text.toString()

            vm.addClient(Client(0, name, surname, telephone))
            findNavController().navigate(R.id.action_addClientForm_to_clientsList)
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
            AddClientForm().apply {
                /*arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }*/
            }
    }
}