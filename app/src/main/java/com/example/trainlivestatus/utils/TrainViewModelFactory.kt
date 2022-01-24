package com.example.trainlivestatus.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trainlivestatus.repository.TrainRepository
import com.example.trainlivestatus.viewmodel.TrainViewModel

class TrainViewModelFactory(val trainRepository: TrainRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return TrainViewModel(trainRepository) as T
    }


}