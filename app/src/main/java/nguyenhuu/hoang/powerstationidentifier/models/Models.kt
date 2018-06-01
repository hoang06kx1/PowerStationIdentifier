package nguyenhuu.hoang.powerstationidentifier.models

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.evrencoskun.tableview.adapter.AbstractTableAdapter
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import nguyenhuu.hoang.powerstationidentifier.R
import android.view.LayoutInflater
import java.text.Normalizer
import java.util.regex.Pattern


/**
 * Created by Hoang on 6/1/2018.
 */
data class Station(var LineName: String, var Position: String, var Ward: String, var District: String, var Province: String, var Latitude: String, var Longtitude: String, var PowerLevel: String, var Zone: String, var Team: String, var Height: Float, var ColumnType: String, var Box: String, var Note: String)

data class Distance(var LineName: String, var Position: String, var LineNameNoAccent: String, var PositionNoAccent: String, var Distance: Int, var Incremental: Int, var ReversedIncremental: Int)

class TableViewAdapter(val context: Context) : AbstractTableAdapter<String, String, String>(context) {
    override fun onCreateColumnHeaderViewHolder(parent: ViewGroup?, viewType: Int): AbstractViewHolder {
        val layout = LayoutInflater.from(context).inflate(R.layout.table_view_column_header_layout,
                parent, false)
        // Create a Custom ViewHolder for a Cell item.
        return ColumnHeaderViewHolder(layout)
    }

    override fun onBindColumnHeaderViewHolder(holder: AbstractViewHolder?, columnHeaderItemModel: Any?, columnPosition: Int) {
        val holder = holder as ColumnHeaderViewHolder
        holder.cellTextview.text = mColumnHeaderItems[columnPosition]
//        holder.cellTextview.requestLayout()
    }

    override fun onBindRowHeaderViewHolder(holder: AbstractViewHolder?, rowHeaderItemModel: Any?, rowPosition: Int) {
        val holder = holder as RowHeaderViewHolder
        holder.cellTextview.text = mRowHeaderItems[rowPosition]
    }

    override fun onCreateRowHeaderViewHolder(parent: ViewGroup?, viewType: Int): AbstractViewHolder {
        val layout = LayoutInflater.from(context).inflate(R.layout.table_view_row_header_layout,
                parent, false)
        return RowHeaderViewHolder(layout)
    }

    override fun getCellItemViewType(position: Int): Int {
        return 0
    }

    override fun onCreateCellViewHolder(parent: ViewGroup?, viewType: Int): AbstractViewHolder {
        val layout = LayoutInflater.from(context).inflate(R.layout.table_view_cell_layout,
                parent, false)
        // Create a Custom ViewHolder for a Cell item.
        return CellViewHolder(layout)
    }

    override fun onCreateCornerView(): View {
        return LayoutInflater.from(context).inflate(R.layout.table_view_corner_layout, null)
    }

    override fun onBindCellViewHolder(holder: AbstractViewHolder?, cellItemModel: Any?, columnPosition: Int, rowPosition: Int) {
        val holder = holder as CellViewHolder
        holder.cellTextview.text = (cellItemModel as String?) ?: ""
//        holder.cellTextview.requestLayout()
    }

    override fun getColumnHeaderItemViewType(position: Int): Int {
        return 0
    }

    override fun getRowHeaderItemViewType(position: Int): Int {
        return 0
    }


}

class CellViewHolder(itemView: View) : AbstractViewHolder(itemView) {
    var cellTextview: TextView = itemView.findViewById(R.id.cell_data)
}

class ColumnHeaderViewHolder(itemView: View) : AbstractViewHolder(itemView) {
    var cellTextview: TextView = itemView.findViewById(R.id.column_header_textView)
}

class RowHeaderViewHolder(itemView: View) : AbstractViewHolder(itemView) {
    var cellTextview: TextView = itemView.findViewById(R.id.row_header_textview)
}

class Utils {
    companion object {
        fun removeAccent(s: String): String {
            val temp = Normalizer.normalize(s, Normalizer.Form.NFD)
            val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
            return pattern.matcher(temp).replaceAll("").replace('đ', 'd').replace('Đ', 'D')
        }
    }
}