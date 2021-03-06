package com.example.a7minuteworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minuteworkoutapp.databinding.ActivityBmiactivityBinding
import kotlinx.android.synthetic.main.activity_bmiactivity.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }

    private var currentVisibleView : String = METRIC_UNITS_VIEW

    private var binding :ActivityBmiactivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiactivityBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)

        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }

        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        makeVisibleMetricUnitsView()
        binding?.rgUnits?.setOnCheckedChangeListener{_,checkedId:Int->
            if(checkedId == R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }else{
                makeVisibleUsUnitsView()
            }
        }


        binding?.btnCalculateUnits?.setOnClickListener {

            if (currentVisibleView.equals(METRIC_UNITS_VIEW)) {
                if (validateMetricUnits()) {

                    val heightValue: Float = binding?.etMetricUnitHeight?.text.toString().toFloat() / 100

                    val weightValue: Float = binding?.etMetricUnitWeight?.text.toString().toFloat()

                    val bmi = weightValue / (heightValue * heightValue)

                    displayBMIResult(bmi)
                } else {
                    Toast.makeText(
                        this@BMIActivity,
                        "Please enter valid values.",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            } else {

                if (validateUsUnits()) {

                    val usUnitHeightValueFeet: String =
                        binding?.etUsUnitHeightFeet?.text.toString()
                    val usUnitHeightValueInch: String =
                        binding?.etUsUnitHeightInch?.text.toString()
                    val usUnitWeightValue: Float = binding?.etUsUnitWeight?.text.toString()
                        .toFloat()
                    val heightValue = usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12

                    val bmi = 703 * (usUnitWeightValue / (heightValue * heightValue))

                    displayBMIResult(bmi)
                } else {
                    Toast.makeText(
                        this@BMIActivity,
                        "Please enter valid values.",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

    private fun makeVisibleMetricUnitsView(){
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.llMetricUnitsView?.visibility = View.VISIBLE
        binding?.llUsUnitsView?.visibility = View.GONE

        binding?.etMetricUnitHeight?.text!!.clear()
        binding?.etMetricUnitWeight?.text!!.clear()

        binding?.llDiplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun makeVisibleUsUnitsView() {
        currentVisibleView = US_UNITS_VIEW
        binding?.llMetricUnitsView?.visibility = View.GONE
        binding?.llUsUnitsView?.visibility = View.VISIBLE

        binding?.etUsUnitWeight?.text!!.clear()
        binding?.etUsUnitHeightFeet?.text!!.clear()
        binding?.etUsUnitHeightInch?.text!!.clear()

        binding?.llDiplayBMIResult?.visibility = View.INVISIBLE
    }


    private fun displayBMIResult(bmi:Float){

        val bmiLabel : String
        val bmiDescription : String

        if (java.lang.Float.compare(bmi, 15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (java.lang.Float.compare(bmi, 15f) > 0 && java.lang.Float.compare(
                bmi,
                16f
            ) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (java.lang.Float.compare(bmi, 16f) > 0 && java.lang.Float.compare(
                bmi,
                18.5f
            ) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (java.lang.Float.compare(bmi, 18.5f) > 0 && java.lang.Float.compare(
                bmi,
                25f
            ) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (java.lang.Float.compare(bmi, 30f) > 0 && java.lang.Float.compare(
                bmi,
                35f
            ) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (java.lang.Float.compare(bmi, 35f) > 0 && java.lang.Float.compare(
                bmi,
                40f
            ) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()

        binding?.llDiplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription

    }

    private fun validateMetricUnits():Boolean{
        var isValid = true

        if(binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isValid = false
        }
        else if(binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            isValid = false
        }

        return isValid
    }

    private fun validateUsUnits(): Boolean {
        var isValid = true

        if (binding?.etUsUnitWeight?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.etUsUnitHeightFeet?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.etUsUnitHeightInch?.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }

}