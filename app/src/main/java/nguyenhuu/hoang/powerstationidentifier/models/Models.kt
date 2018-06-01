package nguyenhuu.hoang.powerstationidentifier.models

/**
 * Created by Hoang on 6/1/2018.
 */
data class Station(var lineName: String, var position: String, var ward: String, var district: String, var province: String, var latitude: String, var longtitude: String, var powerLevel: String, var zone: String, var team: String, var height: Float, var columnType: String, var box: String, var note: String)
data class Distance(var lineName: String, var position: String, var distance: Int, var incremental: Int, var reversedIncremental: Int)