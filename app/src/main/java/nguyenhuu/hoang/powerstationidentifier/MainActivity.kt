package nguyenhuu.hoang.powerstationidentifier

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.evrencoskun.tableview.listener.ITableViewListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jakewharton.rxbinding2.widget.textChanges
import kotlinx.android.synthetic.main.activity_main.*
import nguyenhuu.hoang.powerstationidentifier.models.Distance
import nguyenhuu.hoang.powerstationidentifier.models.Station
import nguyenhuu.hoang.powerstationidentifier.models.TableViewAdapter
import nguyenhuu.hoang.powerstationidentifier.models.Utils

class MainActivity : BaseActivity() {

    val adapter = TableViewAdapter(this)
    val columnHeaderList = arrayListOf("Đường dây", "Vị trí", "Khoảng cột", "Cộng dồn", "Cộng dồn ngược")
    var distances: ArrayList<Distance> = ArrayList()
    var stations: ArrayList<Station> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // read json
        val inputStreamStations = assets.open("stations.json")
        val stationsJson = inputStreamStations.bufferedReader().use { it.readText() }
        val inputStreamDistances = assets.open("distances.json")
        val distancesJson = inputStreamDistances.bufferedReader().use { it.readText() }
        stations = Gson().fromJson(stationsJson, object : TypeToken<ArrayList<Station>>() {}.type)
        distances = Gson().fromJson(distancesJson, object : TypeToken<ArrayList<Distance>>() {}.type)

        // remove accent
        distances.forEachIndexed { i, it ->
            it.LineNameNoAccent = Utils.removeAccent(it.LineName)
            it.PositionNoAccent = Utils.removeAccent(it.Position)
            if (i == 0) {
                it.Incremental = it.Distance
            } else {
                it.Incremental = distances[i - 1].Incremental + it.Distance
            }
        }

        // revertedIncremental
        val reversedDistance = distances.reversed()
        reversedDistance.forEachIndexed { i, it ->
            if (i == 0) {
                it.ReversedIncremental = 0
            } else {
                it.ReversedIncremental = reversedDistance[i - 1].ReversedIncremental + it.Distance
            }
        }

        // display on tableview
        val rowHeaderList = IntArray(distances.size, { i -> i + 1 }).map { i -> i.toString() }
        val cellList = initCellList(distances, "", "", -1, -1)
        table.adapter = adapter
        adapter.setAllItems(columnHeaderList, rowHeaderList, cellList)
        table.setHasFixedWidth(false)
        table.setColumnWidth(0, Utils.dpToPx(this, 280))
        table.setColumnWidth(1, Utils.dpToPx(this, 150))
        table.setColumnWidth(2, Utils.dpToPx(this, 100))
        table.setColumnWidth(3, Utils.dpToPx(this, 100))
        table.setColumnWidth(4, Utils.dpToPx(this, 100))
        table.tableViewListener = TableViewClickListener()

        // filter
        edt_linename.textChanges().skipInitialValue().subscribe { s ->
            applyFilter(distances, s.toString(), edt_position.text.toString(), getIncremental(), getRevertedIncremental())
        }

        edt_position.textChanges().skipInitialValue().subscribe { s ->
            applyFilter(distances, edt_linename.text.toString(), s.toString(), getIncremental(), getRevertedIncremental())
        }

        edt_incremental.textChanges().skipInitialValue().subscribe { s ->
            if (s.toString().toLongOrNull() != null || s.toString().isBlank()) {
                applyFilter(distances, edt_linename.text.toString(), edt_position.text.toString(), getIncremental(), getRevertedIncremental())

            }
        }

        edt_reversed_incremental.textChanges().skipInitialValue().subscribe { s ->
            if (s.toString().toLongOrNull() != null || s.toString().isBlank()) {
                applyFilter(distances, edt_linename.text.toString(), edt_position.text.toString(), getIncremental(), getRevertedIncremental())
            }
        }
    }

    fun applyFilter(distances: ArrayList<Distance>, name: String, position: String, incremental: Long, revertIncremental: Long) {
        val newCellList = initCellList(distances, name, position, incremental, revertIncremental)
        val newRowHeaderList = IntArray(newCellList.size, { i -> i + 1 }).map { i -> i.toString() }
        adapter.setAllItems(columnHeaderList, newRowHeaderList, newCellList)
    }

    fun getIncremental(): Long {
        return edt_incremental.text.toString().toLongOrNull() ?: -1
    }

    fun getRevertedIncremental(): Long {
        return edt_reversed_incremental.text.toString().toLongOrNull() ?: -1
    }

    fun initCellList(distances: ArrayList<Distance>, name: String, position: String, incremental: Long, reversedIncremental: Long): List<List<String>> {
        var result = emptyArray<List<String>>().toMutableList()
        val sName = Utils.removeAccent(name).trim()
        val sPosition = Utils.removeAccent(position).trim()
        distances.forEach {
            var satisfiedDistance: Distance? = null
            if ((it.LineNameNoAccent.contains(sName, true) || sName.isBlank()) && (it.PositionNoAccent.contains(sPosition, true) || sPosition.isBlank())) {
                satisfiedDistance = it
            }
            if (satisfiedDistance != null) {
                result.add(arrayOf<String>(satisfiedDistance.LineName, satisfiedDistance.Position, satisfiedDistance.Distance.toString(), satisfiedDistance.Incremental.toString(), satisfiedDistance.ReversedIncremental.toString()).toList())
            }
            if (incremental > -1) {
                result = result.filter { it[3].toLongOrNull() ?: -1 <= incremental }.takeLast(1).toMutableList()
            } else if (reversedIncremental > -1) {
                result = result.reversed().filter { it[4].toLongOrNull() ?: -1 <= reversedIncremental }.takeLast(1).toMutableList()
            }
        }
        return result
    }

    inner class TableViewClickListener : ITableViewListener {
        override fun onCellLongPressed(cellView: RecyclerView.ViewHolder, column: Int, row: Int) {
        }

        override fun onColumnHeaderLongPressed(columnHeaderView: RecyclerView.ViewHolder, column: Int) {
        }

        override fun onRowHeaderClicked(rowHeaderView: RecyclerView.ViewHolder, row: Int) {
        }

        override fun onColumnHeaderClicked(columnHeaderView: RecyclerView.ViewHolder, column: Int) {
        }

        override fun onCellClicked(cellView: RecyclerView.ViewHolder, column: Int, rowIndex: Int) {
            val row = adapter.getCellRowItems(rowIndex) as List<String>
            val station = stations.filter { it.LineName.trim().toLowerCase() == row[0].trim().toLowerCase() && it.Position.trim().toLowerCase() == row[1].trim().toLowerCase() }.firstOrNull()
            if (station != null) {
                val f = StationInfoFragment()
                val bundle = Bundle()
                bundle.putSerializable("STATION", station)
                f.arguments = bundle
                supportFragmentManager.beginTransaction().add(R.id.root_layout, f).addToBackStack(null).commit()
            }
        }

        override fun onRowHeaderLongPressed(rowHeaderView: RecyclerView.ViewHolder, row: Int) {
        }

    }
}
