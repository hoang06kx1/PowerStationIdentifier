package nguyenhuu.hoang.powerstationidentifier

import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import nguyenhuu.hoang.powerstationidentifier.models.Distance
import nguyenhuu.hoang.powerstationidentifier.models.Station
import nguyenhuu.hoang.powerstationidentifier.models.TableViewAdapter
import nguyenhuu.hoang.powerstationidentifier.models.Utils

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // read json
        val inputStreamStations = assets.open("stations.json")
        val stationsJson = inputStreamStations.bufferedReader().use { it.readText() }
        val inputStreamDistances = assets.open("distances.json")
        val distancesJson = inputStreamDistances.bufferedReader().use { it.readText() }
        val stations: ArrayList<Station> = Gson().fromJson(stationsJson, object : TypeToken<ArrayList<Station>>() {}.type)
        val distances: ArrayList<Distance> = Gson().fromJson(distancesJson, object : TypeToken<ArrayList<Distance>>() {}.type)

        // remove accent
        distances.forEachIndexed { i, it ->
            it.LineNameNoAccent = Utils.removeAccent(it.LineName)
            it.PositionNoAccent = Utils.removeAccent(it.Position)
            if (i == 0) {
                it.Incremental = it.Distance
            }
            if (i > 0) {
                it.Incremental = distances[i - 1].Incremental + it.Distance
            }
        }

        // display on tableview
        val columnHeaderList = arrayListOf<String>("Đường dây", "Vị trí", "Khoảng cột", "Cộng dồn", "Cộng dồn ngược")
        val rowHeaderList = IntArray(distances.size, { i -> i + 1 }).map { i -> i.toString() }
        val cellList = initCellList(distances)
        val adapter = TableViewAdapter(this)
        table.adapter = adapter
        adapter.setAllItems(columnHeaderList, rowHeaderList, cellList)
    }

    fun initCellList(distances: ArrayList<Distance>): List<List<String>> {
        val result = emptyArray<List<String>>().toMutableList()
        distances.forEachIndexed { _, distance ->
            result.add(arrayOf<String>(distance.LineName, distance.Position, distance.Distance.toString(), distance.Incremental.toString(), distance.ReversedIncremental.toString()).toList())
        }
        return result
    }
}
