package com.example.memorycheck

import android.os.Bundle
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.WindowManager
import android.widget.GridView
import android.widget.AdapterView.OnItemClickListener
import java.util.ArrayList


@Suppress("DEPRECATION")
class GameProcess : Activity() {

    private lateinit var grid: GridView
    private lateinit var adapter: GridAdapter
    private lateinit var pictures: ArrayList<String>
    var GRID_COLUMN = 0
    var GRID_ROW = 3


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_process)
        val level = intent.getIntExtra("level", -1)
        when (level) {
            0 -> GRID_COLUMN = 4
            1 -> GRID_COLUMN = 6
            2 -> GRID_COLUMN = 8
        }

        grid = findViewById<GridView>(R.id.field)
        grid.numColumns = GRID_COLUMN
        grid.isEnabled = true

        val display = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val displayHeight = display.height
        val height = displayHeight / 4
        adapter = GridAdapter(this, GRID_COLUMN, GRID_ROW, height)
        pictures = adapter.picturesInGame
        grid.adapter = adapter

        grid.onItemClickListener =
            OnItemClickListener { parent, v, position, id ->
                adapter.checkOpenCells()
                adapter.openCell(position)

                if (adapter.checkGameOver()) {
                    val alertDialog = AlertDialog.Builder(this)
                    alertDialog.setTitle(getString(R.string.end))
                    alertDialog.setPositiveButton(getString(R.string.repeat_game)) { dialog, which ->
                        finish()
                        startActivity(intent)
                    }

                    alertDialog.setNegativeButton(getString(R.string.exit)) { dialog, which ->
                        finish()
                    }
                    alertDialog.show()
                }
            }
    }
}

