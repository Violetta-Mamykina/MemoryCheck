package com.example.memorycheck

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.*


internal class GridAdapter(
    private val contextGame: Context,
    private val columnsNumber: Int,
    private val rowsNumber: Int,
    var displayHeight: Int
) : BaseAdapter() {
    val picturesInGame: ArrayList<String> = ArrayList()
    private val prefixPictures: String
    private val resourcesGame: Resources
    private val statusCells: ArrayList<status> = ArrayList()

    private enum class status {
        CELL_OPEN, CELL_CLOSE, CELL_DELETE
    }

    init {
        prefixPictures = contextGame.getString(R.string.prefix)
        resourcesGame = contextGame.resources

        //randomly creating pairs of pictures
        picturesInGame.clear()
        for (i in 1..columnsNumber * rowsNumber / 2) {
            picturesInGame.add(prefixPictures + i)
            picturesInGame.add(prefixPictures + i)
        }
        picturesInGame.shuffle()

        //all cells are closed in the start game
        statusCells.clear()
        for (i in 0 until columnsNumber * rowsNumber)
            statusCells.add(status.CELL_CLOSE)
    }

    override fun getCount(): Int {
        return columnsNumber * rowsNumber
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view: ImageView = if (convertView == null)
            ImageView(contextGame)
        else
            (convertView as ImageView?)!!

        when (statusCells[position]) {
            status.CELL_OPEN -> {
                val drawableId = resourcesGame.getIdentifier(
                    picturesInGame[position], "drawable", contextGame.packageName
                )
                view.setImageResource(drawableId)
            }
            status.CELL_CLOSE -> view.setImageResource(R.drawable.ic_launcher_background)
            else -> view.setImageResource(R.drawable.ic_launcher_foreground)
        }
        view.layoutParams = AbsListView.LayoutParams(GridView.AUTO_FIT, displayHeight)
        return view
    }

    fun checkOpenCells() {
        val first = statusCells.indexOf(status.CELL_OPEN)
        val second = statusCells.lastIndexOf(status.CELL_OPEN)
        if (first == second)
            return
        if (picturesInGame[first] == picturesInGame[second]) {
            statusCells[first] = status.CELL_DELETE
            statusCells[second] = status.CELL_DELETE
        } else {
            statusCells[first] = status.CELL_CLOSE
            statusCells[second] = status.CELL_CLOSE
        }
        return
    }

    fun openCell(position: Int) {
        if (statusCells[position] != status.CELL_DELETE)
            statusCells[position] = status.CELL_OPEN

        notifyDataSetChanged()
        return
    }

    fun checkGameOver(): Boolean {
        return statusCells.indexOf(status.CELL_CLOSE) < 0
    }
}