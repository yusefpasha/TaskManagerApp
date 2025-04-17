package ir.yusefpasha.taskmanagerapp.domain.service

interface NotificationService {

    fun showSimple(
        id: Int,
        title: String,
        subtitle: String
    )

}