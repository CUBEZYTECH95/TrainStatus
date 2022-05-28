package trainstatus.trainbooking.pnrstatus.utils

import android.view.Gravity
import android.widget.Toast
import trainstatus.trainbooking.pnrstatus.application.TrainPays

class Toastlib {

    fun MakeToast(messageToDisplay: String?) {
        val toast = Toast.makeText(TrainPays.getContext(), messageToDisplay, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM, 0, 60)
        toast.show()
    }

    fun MakeToast(stringResource: Int) {
        val toast = Toast.makeText(TrainPays.getContext(), stringResource, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM, 0, 60)
        toast.show()
    }
}