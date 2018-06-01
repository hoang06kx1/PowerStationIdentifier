package nguyenhuu.hoang.powerstationidentifier

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import nguyenhuu.hoang.powerstationidentifier.models.Distance
import nguyenhuu.hoang.powerstationidentifier.models.Station

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
    }
}
