package com.example.tiptime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    // Di sini kita melakukan inheritance objek binding
    // keyword lateinit adalah jaminan bahwa kode kita akan melakukan
    // inisialisasi variabel sebelum menggunakannya, jika tidak maka akan error
    // nama class AcitivityMainBinding berasal dari activity_main.xml
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Baris ini melakukan inisialisasi objek binding yang akan kita gunakan
        // untuk mengakses Views di tata letak activity_main.xml
        binding = ActivityMainBinding.inflate(layoutInflater)


        // Tetapkan tampilan konten activity ini, bukan dengan cara meneruskan
        // id resource tata letak, R.layout.activity_main, namun ini menentukan
        // root hirarki tampilasn aplikasi kita, binding.root

        // Root ini terhubung ke semua tampilan
        // Objek binding secara otomatis menentukan referensi untuk setiap View
        // dalam aplikasi kita yang memiliki ID, dan kelebihan objek binding adalah
        // kita tidak memerlukan variabel untuk menyimpan referensi pada View tertentu
        // kita bisa mengakses @id/text_view dengan cara binding.text_view
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculateTip() }
    }

    private fun calculateTip() {
        val stringInTextField = binding.costOfService.text.toString()
        val cost = stringInTextField.toDoubleOrNull()

        // digunakan untuk menangani null jika user tidak memasukkan input apapun
        // atau jika sudah melakukan perhitungan, namun biaya dihapus lalu langsung
        // menekan tombol calculate lagi, maka nila tipResult yang sebelumnya
        // harus dihilangkan
        if (cost == null) {
            binding.tipResult.text = ""
            return
        }

        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        // nilai tip dapat dibulatkan atau diubah, maka keyword yang digunakan var bukan val
        var tip = tipPercentage * cost

        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }
        val formatedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formatedTip)
    }
}