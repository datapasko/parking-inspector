package com.tapascodev.inspector.places.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class PlaceDecoration: RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)


        val column = (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex

        //if (column != 0)


        outRect.top = 19
        outRect.bottom = 19
        outRect.left = 19
        outRect.right = 19
    }
}