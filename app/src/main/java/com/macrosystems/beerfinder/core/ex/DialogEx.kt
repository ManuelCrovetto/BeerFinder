package com.macrosystems.beerfinder.core.ex

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.macrosystems.beerfinder.core.dialog.launcher.DialogFragmentLauncher

fun DialogFragment.showDialog(dialogFragmentLauncher: DialogFragmentLauncher, activity: FragmentActivity){
    dialogFragmentLauncher.show(this, activity)
}