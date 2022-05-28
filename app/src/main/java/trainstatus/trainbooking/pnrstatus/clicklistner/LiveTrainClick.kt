package trainstatus.trainbooking.pnrstatus.clicklistner

interface LiveTrainClick {

    fun click(pos: Int, stationName: String?, departureTime: String?, delay: Int)
}