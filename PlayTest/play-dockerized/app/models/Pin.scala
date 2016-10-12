package models

import com.datastax.driver.core.PagingState

import scala.concurrent.Future
import com.websudos.phantom.dsl._
import com.websudos.phantom.reactivestreams._
import net.liftweb.json.{DefaultFormats, Extraction, JsonParser, compactRender}

case class PinJson(title: String, description: String, url: String)

case class Pin(
                       boardName: String,
                       date: DateTime,
                       pinId: UUID,
                       data: PinJson
               )


class Pins extends CassandraTable[ConcretePins, Pin] {

    object boardName extends StringColumn(this) with PartitionKey[String]
    object date extends DateTimeColumn(this) with ClusteringOrder[DateTime] with Descending
    object pinId extends UUIDColumn(this) with PrimaryKey[UUID] with ClusteringOrder[UUID] with Descending
    object data extends JsonColumn[PinJson](this) {
        override def fromJson(obj: String): PinJson = {
            implicit val formats = net.liftweb.json.DefaultFormats
            JsonParser.parse(obj).extract[PinJson]
        }

        override def toJson(obj: PinJson): String = {
            implicit val formats = net.liftweb.json.DefaultFormats
            compactRender(Extraction.decompose(obj))
        }
    }


    def fromRow(row: Row): Pin = {
        Pin(
            boardName(row),
            date(row),
            pinId(row),
            data(row)
        )
    }
}

abstract class ConcretePins extends Pins with RootConnector {

    def createTable(): Future[ResultSet] = {
        create.ifNotExists().future()
    }

    def store(pin: Pin): Future[ResultSet] = {
        insert.value(_.boardName, pin.boardName)
                .value(_.date, pin.date)
                .value(_.pinId, pin.pinId)
                .value(_.data, pin.data)
                .consistencyLevel_=(ConsistencyLevel.ALL)
                .future()
    }

    def getRecords(start: Int, limit: Int): Future[Iterator[Pin]] = {
        select.fetchEnumerator run Iteratee.slice(start, limit)
    }

    def getAll(board: String): Future[List[Pin]] = {
        select.where(_.boardName eqs board)fetch()
    }

    def getPage(limit: Int, paging: Option[PagingState] = None): Future[ListResult[Pin]] = {
        val l = select.limit(limit).fetchRecord(paging)
        l
    }
}
