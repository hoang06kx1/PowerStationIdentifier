package nguyenhuu.hoang.powerstationidentifier.models

/**
 * Created by Hoang on 6/1/2018.
 */
data class Station(var LineName: String, var Position: String, var Ward: String, var District: String, var Province: String, var Latitude: String, var Longtitude: String, var PowerLevel: String, var Zone: String, var Team: String, var Height: Float, var ColumnType: String, var Box: String, var Note: String)
data class Distance(var LineName: String, var Position: String, var Distance: Int, var Incremental: Int, var ReversedIncremental: Int)