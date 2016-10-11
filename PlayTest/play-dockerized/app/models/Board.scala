//package models
//
//import scala.concurrent.Future
//import com.websudos.phantom.dsl._
//
//case class Board(
//                       name: String,
//                       registrationDate: DateTime,
//                       data: String
//               )
//
//class Boards extends CassandraTable[ConcreteBoards, Board] {
//
//    object name extends StringColumn(this) with PartitionKey[String]
//    object registrationDate extends DateTimeColumn(this) with PrimaryKey[DateTime]
//    object data extends StringColumn(this)
//
//    def fromRow(row: Row): Board = {
//        Board(
//            name(row),
//            registrationDate(row),
//            data(row)
//        )
//    }
//}
//
//// The root connector comes from import com.websudos.phantom.dsl._
//abstract class ConcreteBoards extends Boards with RootConnector {
//
//    def createTable(): Future[ResultSet] = {
//        create.ifNotExists().future()
//    }
//
//    def store(board: Board): Future[ResultSet] = {
//        insert.value(_.name, board.name)
//                .value(_.registrationDate, board.registrationDate)
//                .value(_.data, board.data)
//                .consistencyLevel_=(ConsistencyLevel.ALL)
//                .future()
//    }
//
//}
