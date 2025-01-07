package com.example.myapp2.rent

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapp2.R
import com.example.myapp2.Rent
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * A simple [Fragment] subclass.
 * Use the [AddRentForm.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddRentForm : Fragment() {
    val vm by viewModels<RentListVM>(ownerProducer = { requireParentFragment().childFragmentManager.primaryNavigationFragment!! })

    private lateinit var startET: EditText
    private lateinit var finishET: EditText
    private lateinit var tariffET: EditText
    private lateinit var carET: EditText
    private lateinit var clientET: EditText

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
        startET = view.findViewById(R.id.editTextStart)
        finishET = view.findViewById(R.id.editTextFinish)
        tariffET = view.findViewById(R.id.editTextTariff)
        carET = view.findViewById(R.id.editTextCarId)
        clientET = view.findViewById(R.id.editTextClientId)
        val addButton: Button = view.findViewById(R.id.addRentButton)
        val goBackButton: Button = view.findViewById(R.id.goBackButtonR)

        addButton.setOnClickListener {
            if (!validateInput()) {
                return@setOnClickListener
            }
            val newRent = createRentFromInput()
            vm.addRent(newRent)
        }

        goBackButton.setOnClickListener { findNavController().navigate(R.id.action_addRentForm_to_rentList) }

        vm.addRentResult.observe(viewLifecycleOwner) { result ->
            if (result.isSuccess) {
                findNavController().navigate(R.id.action_addRentForm_to_rentList)
            } else {
                result.exceptionOrNull()?.let {
                    showErrorDialog("Ошибка добавления аренды")
                }
            }
        }

        return view
    }

    private fun createRentFromInput(): Rent {
        val inputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())

        val startDate = inputFormat.parse(startET.text.toString())
        val finishDate = inputFormat.parse(finishET.text.toString())

        val start_date = outputFormat.format(startDate)
        val finish_date = outputFormat.format(finishDate)

        val tariff = tariffET.text.toString().toIntOrNull() ?: 0
        val car_id = carET.text.toString().toIntOrNull() ?: 0
        val client_id = clientET.text.toString().toIntOrNull() ?: 0
        return Rent(
            id = 0,
            start_date = start_date,
            finish_date = finish_date,
            tariff = tariff,
            car_id = car_id,
            client_id = client_id
        )
    }

    private fun validateInput(): Boolean {
        val startDateStr = startET.text.toString().trim()
        val finishDateStr = finishET.text.toString().trim()
        val tariffStr = tariffET.text.toString().trim()
        val carIdStr = carET.text.toString().trim()
        val clientIdStr = clientET.text.toString().trim()
        if (startDateStr.isBlank() || finishDateStr.isBlank() || tariffStr.isBlank() || carIdStr.isBlank() || clientIdStr.isBlank()) {
            showErrorDialog("Все поля должны быть заполнены.")
            return false
        }
        if (!isValidDate(startDateStr) || !isValidDate(finishDateStr)) {
            showErrorDialog("Неверный формат даты. Используйте формат dd.mm.yyyy")
            return false
        }
        if (!isValidNumber(tariffStr) || !isValidNumber(carIdStr) || !isValidNumber(clientIdStr)) {
            showErrorDialog("Тариф, ID машины и ID клиента должны быть целыми числами.")
            return false
        }
        return true
    }

    private fun isValidDate(dateStr: String): Boolean {
        return try {
            val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            format.isLenient = false
            format.parse(dateStr)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun isValidNumber(input: String): Boolean {
        return input.matches(Regex("^[0-9]+$"))
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Ошибка ввода")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
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