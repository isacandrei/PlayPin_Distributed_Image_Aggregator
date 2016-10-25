package models

import com.websudos.phantom.builder.query.CreateQuery
import com.websudos.phantom.connectors.KeySpace
import com.websudos.phantom.dsl._

import scala.concurrent.duration._


object Defaults {

    val hosts = Seq("10.2.1.1","10.2.1.2","10.2.1.3")
    val connector = ContactPoints(hosts).keySpace("playpin", autoinit = true)
}

class AppDatabase(val keyspace: KeySpaceDef) extends Database(keyspace) {
    object boards extends ConcreteBoards with keyspace.Connector{
        override def autocreate(keySpace: KeySpace): CreateQuery.Default[ConcreteBoards, Board] = {
            create.ifNotExists().`with`(default_time_to_live eqs 10)
                    .and(gc_grace_seconds eqs 10.seconds)
                    .and(read_repair_chance eqs 0.2)
        }
    }
    object pins extends ConcretePins with keyspace.Connector{
        override def autocreate(keySpace: KeySpace): CreateQuery.Default[ConcretePins, Pin] = {
            create.ifNotExists().`with`(default_time_to_live eqs 10)
                    .and(gc_grace_seconds eqs 10.seconds)
                    .and(read_repair_chance eqs 0.2)
        }
    }
}

object AppDatabase extends AppDatabase(Defaults.connector)


//http://outworkers.com/blog/docker pspost/a-series-on-phantom-part-1-getting-started-with-phantom