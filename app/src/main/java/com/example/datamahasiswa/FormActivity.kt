package com.example.datamahasiswa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.datamahasiswa.databinding.ActivityFormBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvHuruf1.visibility = View.INVISIBLE
        binding.tvPredikat1.visibility = View.INVISIBLE

        data()

        binding.btnSaveData.setOnClickListener {
            data()
            databaseReference = FirebaseDatabase.getInstance().getReference("khs")
            val mhsId = databaseReference.push().key
            val data = Mahasiswa(
                    mhsId,
                    binding.inpKode.text.toString(),
                    binding.inpMatkul.text.toString(),
                    binding.inpSks.text.toString(),
                    binding.inpAngka.text.toString(),
                    binding.tvHuruf1.text.toString(),
                    binding.tvPredikat1.text.toString()
            )

//            if (mhsId != null) {
            if (mhsId != null) {
                databaseReference.child(mhsId).setValue(data)
                        .addOnSuccessListener {
                            Log.d("TAG", "Success to Save Data")
                            Toast.makeText(applicationContext, "Data Berhasil Disimpan", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, DetailActivity::class.java)
                            startActivity(intent)
                            finish()
                        }.addOnFailureListener {
                            Log.d("TAG", "Failed to Save Data")
                            Toast.makeText(applicationContext, "Data Gagal Disimpan", Toast.LENGTH_LONG).show()
                        }
            }
//            }else {
                Log.d("TAG", "Failed to Save Data")
                Toast.makeText(applicationContext, "Data Gagal Disimpan", Toast.LENGTH_LONG).show()
//            }
        }

        binding.btnCancel.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }
    }

    private fun data() {
        val kode = binding.inpKode.text.toString().trim()
        val matkul = binding.inpMatkul.text.toString().trim()
        val sks = binding.inpSks.text.toString().trim()
        val angka = binding.inpAngka.text.toString().trim()

        if (kode.isEmpty() && matkul.isEmpty() && sks.isEmpty() && angka.isEmpty()) {
            binding.inpKode.error = "Field Tidak Boleh Kosong"
            binding.tvHuruf1.visibility = View.INVISIBLE
            binding.tvPredikat1.visibility = View.INVISIBLE
        }else if (angka.isNotEmpty()) {
            binding.tvHuruf1.visibility = View.VISIBLE
            binding.tvPredikat1.visibility = View.VISIBLE
            when(angka.toInt()) {
                in 86..100 -> {
                    binding.tvHuruf1.visibility = View.VISIBLE
                    binding.tvHuruf1.text = "A"
                    binding.tvPredikat1.visibility = View.VISIBLE
                    binding.tvPredikat1.text = getString(R.string.sangat_baik)
                }
                in 76..85 -> {
                    binding.tvHuruf1.visibility = View.VISIBLE
                    binding.tvHuruf1.text = "B"
                    binding.tvPredikat1.visibility = View.VISIBLE
                    binding.tvPredikat1.text = getString(R.string.baik)
                }
                in 66..75 -> {
                    binding.tvHuruf1.visibility = View.VISIBLE
                    binding.tvHuruf1.text = "C"
                    binding.tvPredikat1.visibility = View.VISIBLE
                    binding.tvPredikat1.text = getString(R.string.cukup)
                }
                in 50..65 -> {
                    binding.tvHuruf1.visibility = View.VISIBLE
                    binding.tvHuruf1.text = "D"
                    binding.tvPredikat1.visibility = View.VISIBLE
                    binding.tvPredikat1.text = getString(R.string.jelek)
                }
                in 0..49 -> {
                    binding.tvHuruf1.visibility = View.VISIBLE
                    binding.tvHuruf1.text = "E"
                    binding.tvPredikat1.visibility = View.VISIBLE
                    binding.tvPredikat1.text = getString(R.string.sangat_jelek)
                }
            }
        }
    }

    private fun saveData() {
        data()
        databaseReference = FirebaseDatabase.getInstance().getReference("mahasiswa")
        val mhsId = databaseReference.push().key
        val mhs = Mahasiswa(mhsId,
            binding.inpKode.text.toString().trim(),
            binding.inpMatkul.text.toString().trim(),
            binding.inpSks.text.toString().trim(),
            binding.inpAngka.text.toString().trim(),
            binding.tvHuruf1.toString(),
            binding.tvPredikat1.toString())

        if (mhsId != null) {
            databaseReference.child(mhsId).setValue(mhs).addOnSuccessListener {
                Toast.makeText(applicationContext, "Data Berhasil Ditambahkan", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(applicationContext, "Data Gagal Ditambahkan", Toast.LENGTH_LONG).show()
            }
        }
    }
}