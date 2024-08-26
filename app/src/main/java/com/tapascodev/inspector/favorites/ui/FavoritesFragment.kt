package com.tapascodev.inspector.favorites.ui

import android.os.Bundle
import android.view.View
import com.tapascodev.inspector.base.ui.BaseFragment
import com.tapascodev.inspector.databinding.FragmentFavoritesBinding

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding> (
    FragmentFavoritesBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
    }

    private fun initUI() {

    }
}