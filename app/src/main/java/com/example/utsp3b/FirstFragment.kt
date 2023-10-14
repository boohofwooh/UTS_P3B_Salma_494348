package com.example.utsp3b

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.utsp3b.databinding.DashboardBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: DashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DashboardBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val st_awal = arguments?.getString(SecondFragment.EXTRA_ST_AWAL)
        val st_akhir = arguments?.getString(SecondFragment.EXTRA_ST_AKHIR)
        val tanggalBrgkt = arguments?.getString(SecondFragment.EXTRA_TANGGAL_BRGKT)
        val tanggalKembali = arguments?.getString(SecondFragment.EXTRA_TANGGAL_KEMBALI)
        val kelas = arguments?.getString(SecondFragment.EXTRA_KELAS)
        val harga = arguments?.getString(SecondFragment.EXTRA_HARGA)
        val kursi = arguments?.getString(SecondFragment.EXTRA_KURSI)

        binding.stasiunAsal.text = "$st_awal"
        binding.stasiunTujuan.text = "$st_akhir"
        binding.tglKeberangkatan.text = "$tanggalBrgkt"
        binding.tglKembali.text = "$tanggalKembali"
        binding.kelas.text = "$kelas"
        binding.harga.text = "$harga"
        binding.kursi.text = "$kursi"

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}