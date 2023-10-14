package com.example.utsp3b

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CalendarView
import com.example.utsp3b.databinding.PemesananTiketBinding
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: PemesananTiketBinding? = null
    private val binding get() = _binding!!
    companion object{
        const val EXTRA_ST_AWAL = "extra_st_awal"
        const val EXTRA_ST_AKHIR = "extra_st_akhir"
        const val EXTRA_TANGGAL_BRGKT = "extra_tanggal_brgkt"
        const val EXTRA_TANGGAL_KEMBALI = "extra_tanggal_kembali"
        const val EXTRA_KELAS = "extra_kelas"
        const val EXTRA_HARGA = "extra_harga"
        const val EXTRA_KURSI = "extra_kursi"
    }

    val spinnerStasiunAwal = arrayOf("Stasiun Yogyakarta - Yogyakarta", "Stasiun Solo Balapan - Solo", "Stasiun Poncol - Semarang", "Stasiun Pasar Senen - Jakarta")
    val spinnerKelasKereta = arrayOf("Ekonomi", "Eksekutif", "Bisnis")
    val spinnerKursi = arrayOf("A1", "A2", "A3", "A4", "A5","B1", "B2", "B3", "B4", "B5", "C1", "C2", "C3", "C4", "C5")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PemesananTiketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendarBrgkt = view.findViewById<CalendarView>(R.id.calendarViewBrgkt)
        val calendarKembali = view.findViewById<CalendarView>(R.id.calendarViewKembali)

        calendarBrgkt.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth-${month + 1}-$year"
            binding.tanggalBrgkt.text = selectedDate
        }

        calendarKembali.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth-${month + 1}-$year"
            binding.tanggalKembali.text = selectedDate
        }

        val adapterStasiun = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            spinnerStasiunAwal
        )
        adapterStasiun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapterStasiun

        binding.buttonSecond.setOnClickListener {
            val st_awal = spinnerStasiunAwal[binding.spinner.selectedItemPosition]
            val st_akhir = spinnerStasiunAwal[binding.spinner2.selectedItemPosition]
            val kelas = spinnerKelasKereta[binding.spinner3.selectedItemPosition]
            val kursi = spinnerKursi[binding.spinner4.selectedItemPosition]

            val tanggalBrgkt = binding.tanggalBrgkt.text.toString()
            val tanggalKembali = binding.tanggalKembali.text.toString()

            val harga = binding.harga.text.toString()

            val bundle = Bundle()
            bundle.putString(EXTRA_ST_AWAL, st_awal)
            bundle.putString(EXTRA_ST_AKHIR, st_akhir)
            bundle.putString(EXTRA_TANGGAL_BRGKT, tanggalBrgkt)
            bundle.putString(EXTRA_TANGGAL_KEMBALI, tanggalKembali)
            bundle.putString(EXTRA_KELAS, kelas)
            bundle.putString(EXTRA_HARGA, harga)
            bundle.putString(EXTRA_KURSI, kursi)


            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment, bundle)
        }


        // Buat adapter untuk spinner2 dengan opsi yang tidak termasuk yang dipilih di spinner
        val adapterSpinner2 = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            spinnerStasiunAwal.filter { it != spinnerStasiunAwal[binding.spinner.selectedItemPosition] }
        )
        adapterSpinner2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner2.adapter = adapterSpinner2

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Saat pilihan diubah di spinner, perbarui adapter untuk spinner2
                val selectedItem = spinnerStasiunAwal[position]
                val updatedOptions = spinnerStasiunAwal.filter { it != selectedItem }
                val adapterSpinner2 = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    updatedOptions
                )
                adapterSpinner2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinner2.adapter = adapterSpinner2
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val adapterKelas = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            spinnerKelasKereta
        )
        adapterKelas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner3.adapter = adapterKelas

        val adapterKursi = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            spinnerKursi
        )
        adapterKursi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner4.adapter = adapterKursi

        binding.spinner3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateHargaBasedOnKelas()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.tambahanAnak.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                updateHarga(50000)
            } else {
                updateHarga(-50000)
            }
        }

        binding.konsum.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                updateHarga(25000)
            } else {
                updateHarga(-25000)
            }
        }

        binding.internet.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                updateHarga(5000)
            } else {
                updateHarga(-5000)
            }
        }

        binding.bagasi.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                updateHarga(10000)
            } else {
                updateHarga(-10000)
            }
        }

        binding.porter.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                updateHarga(20000)
            } else {
                updateHarga(-20000)
            }
        }

        binding.pijat.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                updateHarga(15000)
            } else {
                updateHarga(-15000)
            }
        }

        binding.audio.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                updateHarga(35000)
            } else {
                updateHarga(-35000)
            }
        }

        updateHargaBasedOnKelas()

    }

    private fun updateHarga(hargaTambahan: Int) {
        val currentHarga = binding.harga.text.toString().toInt()
        val newHarga = currentHarga + hargaTambahan
        binding.harga.text = newHarga.toString()
    }

    private fun updateHargaBasedOnKelas() {
        val selectedKelas = binding.spinner3.selectedItem as String
        var hargaAwal = 0

        // Tentukan harga awal berdasarkan kelas yang dipilih
        when (selectedKelas) {
            "Ekonomi" -> hargaAwal = 75000
            "Eksekutif" -> hargaAwal = 150000
            "Bisnis" -> hargaAwal = 175000
        }

        // Anda dapat menambahkan logika tambahan di sini jika diperlukan

        // Perbarui TextView harga
        binding.harga.text = hargaAwal.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
