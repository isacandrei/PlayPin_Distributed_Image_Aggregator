package utils

import com.ecwid.consul.v1.ConsulClient
import scala.concurrent.Future


/**
  * Created by Mac on 03/10/2016.
  */
object Tools {

    def uuid() = java.util.UUID.randomUUID.toString

    def isFileSupported(extension: String): Boolean = {
        val supportedExtensions = Array ("jpg", "JPG", "jpeg", "png", "gif", "img")
        return supportedExtensions.contains(extension)
    }

    def getCassandraList : Seq[String] = {
        val client = new ConsulClient("localhost")
        return client.getKVValue("cassandra").getValue.getDecodedValue.split(" ")
    }
}
