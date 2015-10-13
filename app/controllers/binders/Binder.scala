package controllers.binders

import play.api.mvc.PathBindable
import reactivemongo.bson.BSONObjectID

import scala.util.{Failure, Success}


object Binder {

  implicit def bsonIdBinder(implicit stringBinder: PathBindable[String]) = new PathBindable[BSONObjectID] {
    def bind(key: String, value: String): Either[String, BSONObjectID] = stringBinder.bind(key, value) match {
      case Left(msg) => Left(msg)
      case Right(id) => BSONObjectID.parse(id) match {
        case Success(boid) => Right(boid)
        case Failure(_) => Left(s"ID $id was invalid")
      }
    }

    def unbind(key: String, value: BSONObjectID): String = value.stringify
  }
}
