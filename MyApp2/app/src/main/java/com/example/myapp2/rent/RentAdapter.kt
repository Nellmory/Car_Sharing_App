package com.example.myapp2.rent

import android.app.AlertDialog
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp2.R
import com.example.myapp2.Rent
import java.text.SimpleDateFormat
import java.util.Locale

class RentAdapter(private val rents: List<Rent>) :
    RecyclerView.Adapter<RentAdapter.RentViewHolder>() {

    private var onClickDB: OnItemClickedDB? = null
    private var onSaveClick: OnSaveClick? = null

    class RentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textId: EditText = itemView.findViewById(R.id.textIdR)
        val textStartDate: EditText = itemView.findViewById(R.id.textStartDate)
        val textFinishDate: EditText = itemView.findViewById(R.id.textFinishDate)
        val textTariff: EditText = itemView.findViewById(R.id.textTariff)
        val textCarId: EditText = itemView.findViewById(R.id.textCarId)
        val textClientId: EditText = itemView.findViewById(R.id.textClientId)
        val editButton: Button = itemView.findViewById(R.id.editButton)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        val saveButton: Button = itemView.findViewById(R.id.saveButton)
        val cancelButton: Button = itemView.findViewById(R.id.cancelButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rent, parent, false)
        return RentViewHolder(view)
    }

    override fun onBindViewHolder(holder: RentViewHolder, position: Int) {
        val rent = rents[position]

        val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val inputFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
        val startDate = inputFormat.parse(rent.start_date)
        val finishDate = inputFormat.parse(rent.finish_date)

        holder.textId.setText(rent.id.toString())
        holder.textStartDate.text =
            Editable.Factory.getInstance().newEditable(outputFormat.format(startDate))
        holder.textFinishDate.text =
            Editable.Factory.getInstance().newEditable(outputFormat.format(finishDate))
        holder.textTariff.setText(rent.tariff.toString())
        holder.textCarId.setText(rent.car_id.toString())
        holder.textClientId.setText(rent.client_id.toString())


        holder.editButton.setOnClickListener {
            holder.textId.isEnabled = true
            holder.textStartDate.isEnabled = true
            holder.textFinishDate.isEnabled = true
            holder.textTariff.isEnabled = true
            holder.textCarId.isEnabled = true
            holder.textClientId.isEnabled = true
            holder.editButton.visibility = View.GONE
            holder.deleteButton.visibility = View.GONE
            holder.saveButton.visibility = View.VISIBLE
            holder.cancelButton.visibility = View.VISIBLE
        }

        holder.saveButton.setOnClickListener {
            val inputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())

            // Преобразуем введенные строки в даты
            val startDate = inputFormat.parse(holder.textStartDate.text.toString())
            val finishDate = inputFormat.parse(holder.textFinishDate.text.toString())

            // Форматируем даты в нужный формат для отправки на сервер
            val formattedStartDate = outputFormat.format(startDate)
            val formattedFinishDate = outputFormat.format(finishDate)

            val updatedRent = Rent(
                id = holder.textId.text.toString().toInt(),
                start_date = formattedStartDate,
                finish_date = formattedFinishDate,
                tariff = holder.textTariff.text.toString().toInt(),
                car_id = holder.textCarId.text.toString().toInt(),
                client_id = holder.textClientId.text.toString().toInt()
            )

            onSaveClick?.onSaveClick(rent.id, updatedRent)

            holder.textId.isEnabled = false
            holder.textStartDate.isEnabled = false
            holder.textFinishDate.isEnabled = false
            holder.textTariff.isEnabled = false
            holder.textCarId.isEnabled = false
            holder.textClientId.isEnabled = false
            holder.editButton.visibility = View.VISIBLE
            holder.deleteButton.visibility = View.VISIBLE
            holder.saveButton.visibility = View.GONE
            holder.cancelButton.visibility = View.GONE
        }

        holder.cancelButton.setOnClickListener {
            holder.textId.isEnabled = false
            holder.textStartDate.isEnabled = false
            holder.textFinishDate.isEnabled = false
            holder.textTariff.isEnabled = false
            holder.textCarId.isEnabled = false
            holder.textClientId.isEnabled = false
            holder.editButton.visibility = View.VISIBLE
            holder.deleteButton.visibility = View.VISIBLE
            holder.saveButton.visibility = View.GONE
            holder.cancelButton.visibility = View.GONE
        }

        holder.deleteButton.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Подтверждение удаления")
                .setMessage("Вы действительно хотите удалить запись об аренде?")
                .setPositiveButton("Удалить") { dialog, _ ->
                    onClickDB?.onDeleteButtonClick(rent.id)
                    dialog.dismiss()
                }
                .setNegativeButton("Отмена") { dialog, _ ->
                    dialog.cancel()
                }
                .show()
        }
    }

    override fun getItemCount(): Int {
        return rents.size
    }

    fun setOnClickDB(onClick: OnItemClickedDB) {
        this.onClickDB = onClick
    }

    fun setOnSaveClick(onSaveClick: OnSaveClick) {
        this.onSaveClick = onSaveClick
    }

    interface OnItemClickedDB {
        fun onDeleteButtonClick(id: Int)
    }

    interface OnSaveClick {
        fun onSaveClick(id: Int, rent: Rent)
    }
}