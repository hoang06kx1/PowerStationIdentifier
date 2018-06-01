package nguyenhuu.hoang.powerstationidentifier


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_station_info.*
import nguyenhuu.hoang.powerstationidentifier.models.Station


/**
 * A simple [Fragment] subclass.
 */
class StationInfoFragment : Fragment() {
    lateinit var station: Station
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            station = it.getSerializable("STATION") as Station
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_station_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_linename.text = station.LineName
        tv_position.text = station.Position
        tv_height.text = station.Height.toString()
        tv_type.text = station.ColumnType
        tv_box.text = station.Box
        tv_team.text = station.Team
        tv_ward.text = station.Ward
        tv_district.text = station.District
        tv_province.text = station.Province
    }

}// Required empty public constructor
