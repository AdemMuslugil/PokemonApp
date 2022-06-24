package com.adem.pokemonapp.view

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.adem.pokemonapp.databinding.ActivityOverlayBinding

class OverlayActivity : AppCompatActivity() {

    private var _binding : ActivityOverlayBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOverlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        goSettings()

    }



    override fun onResume() {
        super.onResume()
        if (Settings.canDrawOverlays(this)) {
            val action = Intent(this,MainActivity::class.java)
            action.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(action)
            finish()
        }
    }


    fun goSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (Settings.canDrawOverlays(this)) {
                val action = Intent(this,MainActivity::class.java)
                action.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(action)
                finish()
            }
            else {
                binding.Button.setOnClickListener {
                    val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                    Uri.parse("package:" + this.packageName)
                    startActivity(intent)
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}