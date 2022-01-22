package com.example.a7minuteworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minuteworkoutapp.databinding.ActivityHistoryBinding
import kotlinx.android.synthetic.main.activity_history.*

class History : AppCompatActivity() {

    private var binding:ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarHistoryActivity)

        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setTitle("History")
        }

        binding?.toolbarHistoryActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        getAllCompletedDates()

    }

    private fun getAllCompletedDates(){
        val dbHandler = SqliteOpenHelper(this,null)
        val allCompletedDatesList = dbHandler.getAllCompleteDatesList()

        if(allCompletedDatesList.size>0){
            binding?.tvHistory?.visibility = View.VISIBLE
            binding?.rvHistory?.visibility = View.VISIBLE
            binding?.tvNoDataAvailable?.visibility = View.GONE

            binding?.rvHistory?.layoutManager = LinearLayoutManager(this)
            val historyAdapter = HistoryAdaptor(this,allCompletedDatesList)
            binding?.rvHistory?.adapter = historyAdapter
        }
        else{
            binding?.tvHistory?.visibility = View.GONE
            binding?.rvHistory?.visibility = View.GONE
            binding?.tvNoDataAvailable?.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}