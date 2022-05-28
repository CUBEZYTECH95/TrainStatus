package trainstatus.trainbooking.pnrstatus.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import trainstatus.trainbooking.pnrstatus.repository.MainRespository
import trainstatus.trainbooking.pnrstatus.viewmodel.MainViewModel

class ModelFactory(private val mainRespository: MainRespository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return MainViewModel(mainRespository) as T
    }
}