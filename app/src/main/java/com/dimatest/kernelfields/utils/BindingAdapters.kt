package com.dimatest.kernelfields.utils


import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["text", "titleRes"], requireAll = false)
fun setTextWithTitle(textView: TextView, text: String?, titleRes: String?) {
    safeLet(text, titleRes) { txt, title ->
        textView.setVisibleOrGone(true)
        textView.text = String.format(title, txt)
    } ?: textView.setVisibleOrGone(false)
}

@BindingAdapter("filterList")
fun setFilterList(textView: TextView, filterList: List<String>?) {
    textView.text = if (filterList.isNullOrEmpty()) ""
                    else filterList.joinToString()
}

@BindingAdapter("animateVisibility")
fun animateVisibility(view: View, visible: Boolean) {
    if (visible) {
        view.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate().alpha(1f).duration = FADE_ANIMATION_DURATION
        }
    } else {
        view.animate().alpha(0f).duration = FADE_ANIMATION_DURATION
        view.visibility = View.GONE
    }
}