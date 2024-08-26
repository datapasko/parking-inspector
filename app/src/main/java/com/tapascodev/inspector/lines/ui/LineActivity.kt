package com.tapascodev.inspector.lines.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tapascodev.inspector.base.ui.startNewActivity
import com.tapascodev.inspector.databinding.ActivityLinesBinding
import com.tapascodev.inspector.lines.ui.create.CreateLineActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LineActivity : AppCompatActivity() {


    private lateinit var binding : ActivityLinesBinding
    private val viewModel by viewModels<LineViewModel>()

    private lateinit var lineAdapter: LineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLinesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        initUI()
    }

    private fun initRecyclerView() {
        lineAdapter = LineAdapter()
        binding.rvLines.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter= lineAdapter
        }
    }


    private fun initUI() {
        /*viewModel.getLines()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.lines.collect{
                    when(it) {
                        is Resource.Failure -> Log.d("error", "messi")
                        is Resource.Success -> {
                           lineAdapter.updateList(it.value)
                        }
                        Resource.Loading -> {


                        }
                    }
                }
            }
        }*/

        binding.apply {
            fbCreateLine.setOnClickListener {
                startNewActivity(CreateLineActivity::class.java)
            }
        }

    }
}