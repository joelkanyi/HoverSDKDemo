package com.kanyideveloper.hoversdkdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.hover.sdk.api.Hover
import com.hover.sdk.api.HoverParameters
import com.kanyideveloper.hoversdkdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Hover.initialize(this)

        binding.buttonSend.setOnClickListener {
            if (binding.editTextPhoneNumber.text.toString()
                    .isEmpty() || binding.editTextAmount.text.toString().isEmpty()
            ) {
                Toast.makeText(
                    this,
                    "Please Input both the phone Number and Amount",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            try {
                val intent = HoverParameters.Builder(this)
                    .request("14c45f2e")  //Action ID
                    .extra("phoneNumber", binding.editTextPhoneNumber.text.toString().trim())
                    .extra("amount", binding.editTextAmount.text.toString().trim())
                    .buildIntent()

                startActivityForResult(intent, 0)
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Hover Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == 0 && resultCode == RESULT_OK) {
                Toast.makeText(
                    this,
                    "You will receive an MPESA Confirmation message shortly",
                    Toast.LENGTH_SHORT
                ).show()

            } else if (requestCode == 0 && resultCode == RESULT_CANCELED) {

                Toast.makeText(this, data?.getStringExtra("error"), Toast.LENGTH_SHORT).show()
                Log.d(TAG, "onActivityResult: ${data?.getStringExtra("error")}")
            }
        } catch (e: Exception) {
            Log.d(TAG, "onActivityResult: ${e.localizedMessage}")
        }
    }
}

