package trainstatus.trainbooking.pnrstatus.clicklistner

import trainstatus.trainbooking.pnrstatus.model.PopularListModel

interface OfflineSeatClick {

    fun click(photo: PopularListModel)
}