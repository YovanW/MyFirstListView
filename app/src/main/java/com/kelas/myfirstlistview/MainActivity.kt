package com.kelas.myfirstlistview

import android.app.AlertDialog
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kelas.myfirstlistview.ui.theme.MyFirstListViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var data = mutableListOf<String>()

        data.addAll(listOf("1","2","3","4","5"))

        val lvAdapter : ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            data
        )

        val _lv1 = findViewById<ListView>(R.id.lv1)
        _lv1.adapter = lvAdapter

        val _btnTambah = findViewById<Button>(R.id.btnTambah)
        _btnTambah.setOnClickListener {
            var dtAkhir = Integer.parseInt(data.get(data.size-1)) + 1
            data.add("$dtAkhir")
            lvAdapter.notifyDataSetChanged()
        }

        _lv1.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(
                this,
                data[position],
                Toast.LENGTH_SHORT
            ).show()
        }

        val gestureDetector = GestureDetector(
            this,
            object : GestureDetector.SimpleOnGestureListener() {
                override fun onDoubleTap(e: MotionEvent): Boolean {
                    val position = _lv1.pointToPosition(e.x.toInt(), e.y.toInt())
                    if (position != ListView.INVALID_POSITION){
                        val selectedItem = data[position]
                        showActionDialog(position, selectedItem, data, lvAdapter)
                    }
                    return true
                }
            }
        )

        _lv1.setOnTouchListener { view, event ->
            gestureDetector.onTouchEvent(event)
            false
        }

    }

    private fun showActionDialog(position: Int, selectedItem: String, data: MutableList<String>, adapter: ArrayAdapter<String>) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("ITEM $selectedItem")
        builder.setMessage("Pilih tindakan yang ingin dilakukan: ")

        builder.setPositiveButton("Update") { _, _ ->
            showUpdateDialog(position, selectedItem, data, adapter)
            Toast.makeText(
                this,
                "Updating Item $selectedItem",
                Toast.LENGTH_SHORT
            ).show()
        }

        builder.setNegativeButton("Hapus") { _, _ ->
            data.removeAt(position)
            adapter.notifyDataSetChanged()
            Toast.makeText(
                this,
                "Hapus Item $selectedItem",
                Toast.LENGTH_SHORT
            ).show()
        }

        builder.setNeutralButton("Batal"){ dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()

    }

    private fun showUpdateDialog(
        position: Int,
        oldValue: String,
        data: MutableList<String>,
        adapter: ArrayAdapter<String>
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Update Data")

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50,40,50,10)

        val tvOld = TextView(this)
        tvOld.text = "Old Value: $oldValue"
        tvOld.textSize = 16f

        val etNew = EditText(this)
        etNew.hint = "New Value: "
        etNew.setText(oldValue)

        layout.addView(tvOld)
        layout.addView(etNew)

        builder.setView(layout)

        builder.setPositiveButton("Simpan") {dialog, _ ->
            val newValue = etNew.text.toString().trim()
            if (newValue.isNotEmpty()) {
                data[position] = newValue
                adapter.notifyDataSetChanged()
                Toast.makeText(
                    this,
                    "Item $oldValue berhasil diupdate menjadi $newValue",
                    Toast.LENGTH_SHORT
                ).show()
            }else {
                Toast.makeText(
                    this,
                    "New Value tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Batal") {dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()

    }

}
