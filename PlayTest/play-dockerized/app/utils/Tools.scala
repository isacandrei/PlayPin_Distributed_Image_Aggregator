package utils

/**
  * Created by Mac on 03/10/2016.
  */
object Tools {

    def uuid() = java.util.UUID.randomUUID.toString

    def isFileSupported(extension: String): Boolean = {
        val supportedExtensions = Array ("jpg", "JPG", "jpeg", "png", "gif", "img")
        return supportedExtensions.contains(extension)
    }
}
