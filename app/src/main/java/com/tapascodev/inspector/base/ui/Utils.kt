package com.tapascodev.inspector.base.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.tapascodev.inspector.network.domain.Resource

    fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
        Intent(this, activity).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }

    fun <A : Activity> Fragment.startNewActivity(activity: Class<A>) {
        Intent(getActivity(), activity).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun Context.showKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view, 0)
    }

    fun Fragment.showKeyboard() {
        view?.let { activity?.showKeyboard(it) }
    }

    fun Activity.showKeyboard() {
        showKeyboard(currentFocus ?: View(this))
    }

    fun View.visible(isVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun View.snackbar(message: String, action: (() -> Unit)? = null) {
        val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        action?.let {
            snackbar.setAction("Retry") {
                it()
            }
        }
        snackbar.show()
    }

    fun Activity.handleError(
        failure: Resource.Failure,
        retry : (() -> Unit) ?= null
    ) {
        when {
            failure.isNetworkError -> window.decorView.snackbar(
                "Please check your internet connection",
                retry
            )

            else -> {
                val error = failure.errorBody
                error?.let { window.decorView.snackbar(it) }
            }
        }
    }

    fun Fragment.setTitle(title:String) {
        activity?.actionBar?.title = title
    }

    fun Fragment.handleError(
        failure: Resource.Failure,
        retry: (() -> Unit) ?= null
    ) {
        when {
            failure.isNetworkError -> requireView().snackbar(
                "Please check your internet connection",
                retry
            )

            else -> {
                val error = failure.errorBody
                error?.let { requireView().snackbar(it) }
            }
        }
    }
