package com.example.myweatherapp

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.hide()
        val lat=intent.getStringExtra("lat")
        var long=intent.getStringExtra("long")

        window.statusBarColor = Color.parseColor("#1383C3")
        getJSONData(lat,long)

        searchbtn.setOnClickListener {
            val citytxt = citynamesearch.text.toString()
            getJSONDataByname(citytxt)

        }




    }



    private fun getJSONData(lat: String?, long: String?) {
        val textView = findViewById<TextView>(R.id.text)
        val API_KEY = "b8c4acbdc7b133df1986cb69732b5dac"


        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${API_KEY}"

        val JsonRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener { response ->
                setValues(response)
               // Toast.makeText(this,response.toString(),Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener {Toast.makeText(this,"ERROR",Toast.LENGTH_SHORT).show()})

        queue.add(JsonRequest)
    }

    private fun getJSONDataByname(cityname:String?) {

        val textView = findViewById<TextView>(R.id.text)
        val API_KEY = "b8c4acbdc7b133df1986cb69732b5dac"


        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/weather?q=${cityname}&appid=${API_KEY}"

        val JsonRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener { response ->
                setValues(response)
                // Toast.makeText(this,response.toString(),Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener {Toast.makeText(this,"ERROR",Toast.LENGTH_SHORT).show()})

        queue.add(JsonRequest)
    }


    private fun setValues(response: JSONObject){

        city.text=response.getString("name")
        var lat = response.getJSONObject("coord").getString("lat")
        var long=response.getJSONObject("coord").getString("lon")
        coordinates.text="${lat} , ${long}"

        val main = response.getJSONArray("weather").getJSONObject(0).getString("main")
        weather.text= main

//        if(main == "Mist"){
//            Picasso.get()
//                .load("@drawable/demo")
//                .into(icon)
//
//        }else if (main == "Clouds"){
//            Toast.makeText(this,"not cloudy",Toast.LENGTH_SHORT).show()
//        }


        var tempr=response.getJSONObject("main").getString("temp")
        tempr=((((tempr).toFloat()-273.15)).toInt()).toString()
        temp.text="${tempr}°C"


        var mintemp=response.getJSONObject("main").getString("temp_min")
        mintemp=((((mintemp).toFloat()-273.15)).toInt()).toString()
        min_temp.text=mintemp+"°C"

        var maxtemp=response.getJSONObject("main").getString("temp_max")
        maxtemp=((((maxtemp).toFloat()-273.15)).toInt()).toString()
        max_temp.text=maxtemp+"°C"

        pressure.text=response.getJSONObject("main").getString("pressure")
        humidity.text=response.getJSONObject("main").getString("humidity")+"%"
        wind.text=response.getJSONObject("wind").getString("speed")
        degree.text="Degree : "+response.getJSONObject("wind").getString("deg")+"°"
//        gust.text="Gust : "+response.getJSONObject("wind").getString("gust")
    }
}