package com.macrosystems.beerfinder.core.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.fragment.app.DialogFragment
import com.macrosystems.beerfinder.databinding.ErrorDialogBinding

class ErrorDialog: DialogFragment() {

    private var description: String = ""
    private var isDialogCancelable: Boolean = true
    private var positiveAction: Action = Action.Empty

    companion object{
        fun create(
        description: String = "",
        isDialogCancelable: Boolean = true,
        positiveAction: Action = Action.Empty): ErrorDialog = ErrorDialog().apply {
            this.description = description
            this.isDialogCancelable = isDialogCancelable
            this.positiveAction = positiveAction
        }
    }

    override fun onStart() {
        super.onStart()
        val window = dialog?.window ?: return

        window.setLayout(WRAP_CONTENT, WRAP_CONTENT)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = ErrorDialogBinding.inflate(requireActivity().layoutInflater)

        binding.tvDescriptionError.text = description

        binding.btnOkErrorDialog.setOnClickListener { positiveAction.onClickListener(this) }

        isCancelable = isDialogCancelable

        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .setCancelable(isDialogCancelable)
            .create()
    }

    data class Action(val text: String, val onClickListener: (ErrorDialog) -> Unit){
        companion object{
            val Empty = Action("") {}
        }
    }
}