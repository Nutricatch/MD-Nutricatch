package com.nutricatch.dev.views.navigation.camera

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class SuccessScanFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction.
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Nasi Padang")
                .setPositiveButton("OK") { dialog, id ->
                    /// intent to detail result
                }
                .setNegativeButton("Re-Take") { dialog, id ->
                    /// Cancel
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}