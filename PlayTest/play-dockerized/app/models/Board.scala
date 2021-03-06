package models

import scala.concurrent.Future
import com.websudos.phantom.dsl._

case class Board(
                name: String,
                rowName: String = "boards"
              )

class Boards extends CassandraTable[ConcreteBoards, Board] {

    object rowName extends StringColumn(this) with PartitionKey[String]
    object name extends StringColumn(this) with PrimaryKey[String]

    def fromRow(row: Row): Board = {
        Board(
            name(row),
            rowName(row)
        )
    }
}

abstract class ConcreteBoards extends Boards with RootConnector {

    def createTable(): Future[ResultSet] = {
        create.ifNotExists().future()
    }

    def store(board: Board): Future[ResultSet] = {
        insert.value(_.rowName, board.rowName)
                .value(_.name, board.name)
                .consistencyLevel_=(ConsistencyLevel.ONE)
                .future()
    }

    def getAll(): Future[List[String]] = {
        select(_.name).fetch()
    }

}
