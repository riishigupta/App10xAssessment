import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.compose.ui.text.TextStyle
import androidx.recyclerview.widget.RecyclerView
import com.example.machinetest1.R
import com.example.machinetest1.ui.theme.ForcastSuccessResponse
import com.example.machinetest1.ui.theme.ListData
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class UserListAdapter(
    private val userList: ArrayList<ListData>,
    private val userForcast: ForcastSuccessResponse
) :
    RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {
    // Extension function to rotate a list
    private fun <T> List<T>.rotate(n: Int): List<T> = this.subList(n % this.size, this.size) + this.subList(0, n % this.size)

    private var distinctDays: List<String> = emptyList()

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDays: TextView = itemView.findViewById(R.id.tvDays)
        val tvTemp: TextView = itemView.findViewById(R.id.tvTemp)
    }

    private fun init(position: Int) {
        updateDistinctDays(position)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateDistinctDays(position: Int) {


        val dateString = userList.get(position).dtTxt

        // Define the date-time formatter
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        // Parse the string to LocalDateTime
        val dateTime = LocalDateTime.parse(dateString, formatter)

        // Extract the day as a string
        dateTime.dayOfWeek.toString()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_list_view, parent, false)
        return UserViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val temperatureText = userForcast.list.get(position).main?.temp.let {
            kelvinToCelsius(
                it ?: 0.0
            )
        }
        val intvalue = temperatureText.toInt().toString()
        Log.e("invalue", intvalue)
        init(position)
        holder.tvTemp.text = intvalue + "°C"
        holder.tvDays.text = userList[position].dtTxt?.let { convertToDayOfWeek(it) }

    }

/*
    @RequiresApi(Build.VERSION_CODES.O)
    private fun theDayOnDate(
        forcastSuccessResponse: ArrayList<ListData>,
        position: Int
    ): String {
        val dateString = forcastSuccessResponse.get(position).dtTxt
        // Define the date-time formatter
        val dateFormated = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        // Parse the string to LocalDateTime
        val dateTime = LocalDateTime.parse(dateString, dateFormated)
        Log.e("dateTime", dateTime.toString())
        // Extract the day as a string
        val dayOfWeek = dateTime.dayOfWeek.toString()
        return (dayOfWeek)
    }

*/

    fun convertToDayOfWeek(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date: Date = inputFormat.parse(dateString) ?: return ""

        val outputFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        return outputFormat.format(date)
    }
    fun kelvinToCelsius(kelvin: Double): Double {
        return kelvin - 273.15
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}
