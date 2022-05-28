package trainstatus.trainbooking.pnrstatus.clicklistner

import trainstatus.trainbooking.pnrstatus.model.NameOrCodeModelItem

interface TrainSeatClick {

    fun click(photo: NameOrCodeModelItem)
}