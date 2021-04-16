package com.example.rocketreserver.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.rocketreserver.databinding.ErrorViewLayoutBinding

class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: ErrorViewLayoutBinding = ErrorViewLayoutBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    var onRetryClicked: (() -> Unit)? = null

    init {
        binding.retryButton.setOnClickListener { onRetryClicked?.invoke() }
    }
}
