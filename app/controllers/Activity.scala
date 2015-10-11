package controllers

import javax.inject.Inject

import models.{JsonFormats}
import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import play.modules.reactivemongo.{ReactiveMongoComponents, MongoController, ReactiveMongoApi}
import reactivemongo.bson.BSONObjectID

import scala.concurrent.Future

// Reactive Mongo imports
import reactivemongo.api.Cursor

// BSON-JSON conversions/collection
import play.modules.reactivemongo.json._
import play.modules.reactivemongo.json.collection._
import scala.concurrent.ExecutionContext.Implicits.global

class Activity @Inject() (val reactiveMongoApi: ReactiveMongoApi)
  extends Controller with MongoController with ReactiveMongoComponents {

  def collection: JSONCollection = db.collection[JSONCollection]("activities")

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def create = Action.async(parse.json) { request =>
    /*
     * request.body is a JsValue.
     * There is an implicit Writes that turns this JsValue as a JsObject,
     * so you can call insert() with this JsValue.
     * (insert() takes a JsObject as parameter, or anything that can be
     * turned into a JsObject using a Writes.)
     */

    implicit val activityFormat = JsonFormats.activityFormat
    request.body.validate[models.Activity].map { activity =>
      collection.insert(activity).map { lastError =>
        Logger.debug(s"Successfully inserted with LastError: $lastError")
        Created
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def findById(id: String) = Action.async { request =>
    implicit val activityFormat = JsonFormats.activityFormat
    collection.find(Json.obj("id" -> id)).one[models.Activity]. map { activity =>
      Ok(Json.toJson(activity.get))
    }
  }

}