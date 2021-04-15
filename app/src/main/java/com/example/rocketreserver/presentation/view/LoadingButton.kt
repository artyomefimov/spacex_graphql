package com.example.rocketreserver.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.rocketreserver.R
import com.example.rocketreserver.databinding.LoadingButtonLayoutBinding

class LoadingButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = LoadingButtonLayoutBinding.inflate(LayoutInflater.from(context), this)

    var buttonText: String = ""
        set(value) {
            field = value
            binding.buttonTextView.text = value
        }

    var isLoading: Boolean = false
        set(value) {
            field = value
            binding.buttonProgressBar.isVisible = value
            binding.buttonTextView.isVisible = value.not()
        }

    var onClickListener: (() -> Unit)? = null

    init {
        binding.root.setOnClickListener { onClickListener?.invoke() }
        binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
    }
}