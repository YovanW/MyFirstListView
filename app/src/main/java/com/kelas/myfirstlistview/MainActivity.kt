package com.kelas.myfirstlistview

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
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

    }
}
