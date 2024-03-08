package com.example.machinetest1

import UserListAdapter
import WeatherResponse
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.machinetest1.databinding.SigninBinding
import com.example.machinetest1.ui.theme.ApiInterface
import com.example.machinetest1.ui.theme.ForcastSuccessResponse
import com.example.machinetest1.ui.theme.ListData
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TreeMap

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: SigninBinding
    private val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    private val API_KEY = "9b8cb8c7f11c077f8c4e217974d9ee40"

    val weatherApiService: ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
    val forcastApiService: ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.signin)

        if (isNetworkConnected(this)) {
            // Make API call
            // ...
            Handler(Looper.getMainLooper()).postDelayed({
                fetchWeatherData("Bengaluru")
                fetchForcastData("Bengaluru")
            }, 3000)

        } else {
            // Show error message and retry button

            showErrorSnackbar()
        }
        // Example: Call the API when the activity is created
    }

    private fun fetchWeatherData(cityName: String) {
        mBinding.loader.visibility = View.VISIBLE


        val call = weatherApiService.getWeatherData(cityName, API_KEY)

        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                mBinding.loader.visibility = View.GONE

                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    updateUI(weatherResponse)
                } else {
                    showErrorSnackbar()
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                mBinding.loader.visibility = View.GONE
                // Handle failure
            }
        })
    }

    private fun fetchForcastData(cityName: String) {
        mBinding.loader.visibility = View.VISIBLE


        val call = forcastApiService.forecast(cityName, API_KEY)

        call.enqueue(object : Callback<ForcastSuccessResponse> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<ForcastSuccessResponse>,
                response: Response<ForcastSuccessResponse>
            ) {
                mBinding.loader.visibility = View.GONE

                if (response.isSuccessful) {
                    val forcastResponse = response.body()
                    updateUIForcast(forcastResponse)
                } else {
                    showErrorSnackbar()
                }
            }

            override fun onFailure(call: Call<ForcastSuccessResponse>, t: Throwable) {
                mBinding.loader.visibility = View.GONE
                // Handle failure
            }
        })
    }

    private fun updateUI(weatherResponse: WeatherResponse?) {
        if (weatherResponse != null) {
            mBinding.tvCity.text = weatherResponse.name
            val temperatureText = weatherResponse.main?.temp?.let { kelvinToCelsius(it) }
            val intvalue = temperatureText?.toInt().toString()
            Log.e("invalue", intvalue)
            mBinding.tvtemp.text = "$intvalue" + "°C"
        } else {
            // Handle failure
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateUIForcast(forcastSuccessResponse: ForcastSuccessResponse?) {
        if (forcastSuccessResponse != null) {
            recyclerListViewAdapter(forcastSuccessResponse)

//            mBinding.tvLogin.text = temperatureText

        } else {
            // Handle failure
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun recyclerListViewAdapter(userList: ForcastSuccessResponse) {


        val datapass = addDistinctByDate(userList)
        val layoutManager = LinearLayoutManager(this)
        mBinding.recyclerView.layoutManager = layoutManager

        val userAdapter = UserListAdapter(datapass, userList)
        mBinding.recyclerView.adapter = userAdapter

        val animation = AnimationUtils.loadAnimation(this, R.anim.slide_from_bottom)
        mBinding.recyclerView.startAnimation(animation)

    }

    fun kelvinToCelsius(kelvin: Double): Double {
        return kelvin - 273.15
    }


    fun addDistinctByDate(forcastSuccessResponse: ForcastSuccessResponse?): ArrayList<ListData> {
        val distinctMap = TreeMap<String, ListData>() // TreeMap to automatically sort by keys
        val distinctList = ArrayList<ListData>()

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        forcastSuccessResponse?.list?.forEach { item ->
            val date = item.dtTxt?.substring(0, 10) // Extract date part
            if (date != null && !distinctMap.containsKey(date)) {
                distinctMap[date] = item
            }
        }

        distinctList.addAll(distinctMap.values)
        return distinctList
    }

    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun showErrorSnackbar() {
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            "Something went wrong",
            Snackbar.LENGTH_INDEFINITE
        )

        snackbar.setAction("Retry") {
            fetchWeatherData("Bengaluru")
            fetchForcastData("Bengaluru")
            Toast.makeText(this, "please check your internet connection", Toast.LENGTH_SHORT).show()
        }

        // Set the action text color
        snackbar.setActionTextColor(resources.getColor(android.R.color.white))

        // Set the background color of the Snackbar
        snackbar.view.setBackgroundColor(resources.getColor(R.color.black)) // Replace with your desired color
        snackbar.setActionTextColor(resources.getColor(R.color.red))

        // Show the Snackbar
        snackbar.show()
    }

    override fun onResume() {
        super.onResume()

        if (isNetworkConnected(this)) {
            // Make API call
            // ...
            Handler(Looper.getMainLooper()).postDelayed({
                fetchWeatherData("Bengaluru")
                fetchForcastData("Bengaluru")
            }, 3000)

        } else {
            // Show error message and retry button

            showErrorSnackbar()
        }
    }
}