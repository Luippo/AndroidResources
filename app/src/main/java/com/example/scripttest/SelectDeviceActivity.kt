package com.example.scripttest

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_select_device.*

class SelectDeviceActivity : AppCompatActivity() {

    var m_bluetoothAdapter: BluetoothAdapter? = null
    lateinit var m_pairedDevices: Set<BluetoothDevice>
    val REQUEST_ENABLE_BLUETOOTH = 1

    // definir variables para acceder desde otras clases.
    companion object{
        val EXTRA_ADDRESS: String = "Device_address" //
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_device)

        m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter() // New object
        if (m_bluetoothAdapter == null){
            Toast.makeText(this, "This device doesn't support bluetooth",Toast.LENGTH_SHORT).show()
            return
        }
        if (!m_bluetoothAdapter!!.isEnabled){
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
        }

        val buttonRefresh = findViewById<Button>(R.id.select_device_refresh)
        buttonRefresh.setOnClickListener { pairedDeviceList() }

    }

    private fun pairedDeviceList(){
        m_pairedDevices = m_bluetoothAdapter!!.bondedDevices
        val list : ArrayList<BluetoothDevice> = ArrayList()

        if(m_pairedDevices.isNotEmpty()){
            for(device: BluetoothDevice in m_pairedDevices){
                list.add(device)
                Log.i("device", ""+device)
            }
        }else{
            Toast.makeText(this, "No paired bluetooth devices found",Toast.LENGTH_SHORT).show()
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        select_device_list.adapter = adapter
        select_device_list.onItemClickListener = AdapterView.OnItemClickListener{ _, _, position, _ ->
            val device: BluetoothDevice = list[position]
            val address: String = device.address

            val intent = Intent(this, ControlActivity::class.java)
            intent.putExtra(EXTRA_ADDRESS,address)
            startActivity(intent)
        }
    }

    // para saber si el dispositivo esta habilitado o no
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BLUETOOTH){
            if (resultCode == Activity.RESULT_OK){
                if (m_bluetoothAdapter!!.isEnabled){
                    Toast.makeText(this, "Bluetooth has been enabled",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "Bluetooth has been disabled",Toast.LENGTH_SHORT).show()
                }
            }else if (resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this, "Bluetooth enableing has been canceled",Toast.LENGTH_SHORT).show()
            }
        }
    }
}