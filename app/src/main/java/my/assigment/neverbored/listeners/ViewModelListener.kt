package m.otpremnica.android.utility.listeners

interface ViewModelListener {
    fun onStarted(message : String? = null)
    fun onSuccess(message : String? = null)
    fun onFailure(message : String? = null)
}