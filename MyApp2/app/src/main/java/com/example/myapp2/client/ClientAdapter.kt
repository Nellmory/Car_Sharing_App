package com.example.myapp2.client

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp2.Client
import com.example.myapp2.R

class ClientAdapter(private val clients: List<Client>) : RecyclerView.Adapter<ClientAdapter.ClientViewHolder>() {

        private var onClickDB: OnItemClickedDB? = null
    private var onSaveClick: OnSaveClick? = null

    class ClientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textId: EditText = itemView.findViewById(R.id.textIdCl)
        val textName: EditText = itemView.findViewById(R.id.textName)
        val textSurname: EditText = itemView.findViewById(R.id.textSurname)
        val textTelephone: EditText = itemView.findViewById(R.id.textTelephone)
        val editButton: Button = itemView.findViewById(R.id.editButton)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
        val saveButton: Button = itemView.findViewById(R.id.saveButton)
        val cancelButton: Button = itemView.findViewById(R.id.cancelButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_client, parent, false)
        return ClientViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        val client = clients[position]
        holder.textId.setText(client.id.toString())
        holder.textName.setText(client.name)
        holder.textSurname.setText(client.surname)
        holder.textTelephone.setText(client.telephone)

        holder.editButton.setOnClickListener {
            holder.textId.isEnabled = true
            holder.textName.isEnabled = true
            holder.textSurname.isEnabled = true
            holder.textTelephone.isEnabled = true
            holder.editButton.visibility = View.GONE
            holder.deleteButton.visibility = View.GONE
            holder.saveButton.visibility = View.VISIBLE
            holder.cancelButton.visibility = View.VISIBLE
        }

        holder.saveButton.setOnClickListener {
            val updatedClient = Client(
                id = holder.textId.text.toString().toInt(),
                name = holder.textName.text.toString(),
                surname = holder.textSurname.text.toString(),
                telephone = holder.textTelephone.text.toString()
            )
            onSaveClick?.onSaveClick(client.id, updatedClient)
            holder.textId.isEnabled = false
            holder.textName.isEnabled = false
            holder.textSurname.isEnabled = false
            holder.textTelephone.isEnabled = false
            holder.editButton.visibility = View.VISIBLE
            holder.deleteButton.visibility = View.VISIBLE
            holder.saveButton.visibility = View.GONE
            holder.cancelButton.visibility = View.GONE
        }

        holder.cancelButton.setOnClickListener {
            holder.textId.isEnabled = false
            holder.textName.isEnabled = false
            holder.textSurname.isEnabled = false
            holder.textTelephone.isEnabled = false
            holder.editButton.visibility = View.VISIBLE
            holder.deleteButton.visibility = View.VISIBLE
            holder.saveButton.visibility = View.GONE
            holder.cancelButton.visibility = View.GONE
        }

        holder.deleteButton.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Подтверждение удаления")
                .setMessage("Вы действительно хотите удалить клиента?")
                .setPositiveButton("Удалить") { dialog, _ ->
                    onClickDB?.onDeleteButtonClick(client.id)
                    dialog.dismiss()
                }
                .setNegativeButton("Отмена") { dialog, _ ->
                    dialog.cancel()
                }
                .show()
        }
    }

    override fun getItemCount(): Int {
        return clients.size
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
        fun onSaveClick(id: Int, client: Client)
    }
}
