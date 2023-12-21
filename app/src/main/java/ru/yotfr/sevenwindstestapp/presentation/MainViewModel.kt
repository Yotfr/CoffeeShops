package ru.yotfr.sevenwindstestapp.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {


    val visiblePermissionDialogQueue = mutableStateListOf<String>()
    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }
    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if (!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        }
    }


}