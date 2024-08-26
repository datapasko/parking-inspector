package com.tapascodev.inspector.lines.ui.create

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.tapascodev.inspector.R
import com.tapascodev.inspector.databinding.ActivityCreateLineBinding
import com.tapascodev.inspector.lines.ui.LineViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateLineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateLineBinding
    private val viewModel by viewModels<LineViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateLineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {

        /*binding.apply {
            supportActionBar?.title = "Create Line"

            btCreateLine.setOnClickListener {
                val line = LineModel(
                    null,
                    etName.editText?.text.toString().trim(),
                    findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.trim().toString(),
                    acZones.text.toString().trim()
                )
                viewModel.creationLine(line)
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.creation.collect{
                    when(it){
                        is Resource.Success -> {
                            binding.lpCreateLine.visible(false)
                            finish()
                            startNewActivity(LineActivity::class.java)
                        }

                        is Resource.Failure -> {
                            it.errorBody?.let { it1 -> Log.d("messi", it1) }
                        }

                        is Resource.Loading -> {
                            binding.lpCreateLine.visible(true)
                        }
                    }
                }
            }
        }
*/
        // set array for zones options
        val priorities = resources.getStringArray(R.array.zones)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item,priorities)
        binding.acZones.setAdapter(arrayAdapter)

    }
}