package com.example.memorycheck

import android.os.Bundle
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.WindowManager
import android.widget.GridView
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import java.util.ArrayList
import kotlin.math.roundToInt
import android.content.DialogInterface
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




@Suppress("DEPRECATION")
class GameProcess : Activity() {

    private lateinit var grid: GridView
    private lateinit var adapter: GridAdapter
    private lateinit var pictures: ArrayList<String>
    private lateinit var moves: TextView
    var GRID_COLUMN = 0
    var GRID_ROW = 3
    var movesNumber = 0.0


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_process)
        val level = intent.getIntExtra("level", -1)
        moves = findViewById<TextView>(R.id.moves)
        when (level) {
            0 -> {
                GRID_COLUMN = 4
                movesNumber = 15.0
            }
            1 -> {
                GRID_COLUMN = 6
                movesNumber = 20.0
            }
            2 -> {
                GRID_COLUMN = 8
                movesNumber = 30.0
            }
        }

        grid = findViewById<GridView>(R.id.field)
        grid.numColumns = GRID_COLUMN
        grid.isEnabled = true

        moves.text = movesNumber.toInt().toString()
        val display =
            (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val displayHeight = display.height
        val height = displayHeight / 4
        adapter = GridAdapter(this, GRID_COLUMN, GRID_ROW, height)
        pictures = adapter.picturesInGame
        grid.adapter = adapter

        grid.onItemClickListener =
            OnItemClickListener { parent, v, position, id ->
                adapter.checkOpenCells()
                if (adapter.openCell(position)) {
                    movesNumber -= 0.5
                    moves.text = movesNumber.roundToInt().toString()
                }
                if (movesNumber == 0.0) {
                    endGame(R.string.end_lose)
                }
                if (adapter.checkGameOver()) {
                    endGame(R.string.end_win)
                }
            }
    }

    fun endGame(message: Int) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(message)
        alertDialog.setPositiveButton(getString(R.string.repeat_game)) { dialog, which ->
            finish()
            startActivity(intent)
        }

        alertDialog.setNegativeButton(getString(R.string.exit)) { dialog, which ->
            finish()
        }
        alertDialog.show()
    }
    override fun onBackPressed() {
        pressBack()
    }

    fun pressBack() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(getString(R.string.sure))
        alertDialog.setPositiveButton(getString(R.string.yes)) { dialog, which ->
            finish()
        }

        alertDialog.setNegativeButton(getString(R.string.no)) { dialog, which ->
            dialog.dismiss()
        }
        alertDialog.show()
    }


}


