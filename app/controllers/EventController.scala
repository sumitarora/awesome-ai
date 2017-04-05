package controllers

import javax.inject._

import play.api.Logger
import play.api.http.HttpRequestHandler
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._
import play.api.mvc._
import play.modules.reactivemongo._
import reactivemongo.api.Cursor
import reactivemongo.api.ReadPreference
import reactivemongo.play.json._
import reactivemongo.play.json.collection.JSONCollection
import play.api.libs.ws._
import utils.Predict
import models.Event
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class EventController @Inject()(val reactiveMongoApi: ReactiveMongoApi, val ws: WSClient)(implicit exec: ExecutionContext)  extends Controller with MongoController with ReactiveMongoComponents {

  def eventsFuture: Future[JSONCollection] = database.map(_.collection[JSONCollection]("event"))
  def eventsCollection: JSONCollection = db.collection[JSONCollection]("event")

  val transformer: Reads[JsObject] =
    Reads.jsPickBranch[JsString](__ \ "hitType") and
      Reads.jsPickBranch[JsString](__ \ "page") and
      Reads.jsPickBranch[JsString](__ \ "ip") and
      Reads.jsPickBranch[JsString](__ \ "sessionId") and
      Reads.jsPut(__ \ "created", JsNumber(new java.util.Date().getTime())) reduce

  def getAll() = Action.async {
    val cursor: Cursor[Event] = eventsCollection.find(Json.obj()).sort(Json.obj("created" -> -1)).cursor[Event]
    val future: Future[List[Event]] = cursor.collect[List](30)
    future.map { events =>
      Ok(Json.toJson(events))
    }
  }

  def getSessionDetail(sessionId: String): Future[List[Event]] = {
    val cursor: Cursor[Event] = eventsCollection.find(Json.obj("sessionId" -> sessionId)).cursor[Event]
    cursor.collect[List]()
  }

  def getIpDetails(request: Request[Any]): Tuple2[String, String] = {
    val userAgent = request.headers.get("User-Agent").get
    var ipAddress = ""
    if (request.headers.get("X-Forwarded-For").isDefined) {
      ipAddress = request.headers.get("X-Forwarded-For").get
    } else if (!request.remoteAddress.isEmpty) {
      ipAddress = request.remoteAddress.toString
    }
    (userAgent, ipAddress)
  }

  def create = Action.async(parse.json) { implicit request =>
    var ip = (request.body \ "ip").as[String]
    val parseHeader = getIpDetails(request)
    if (ip == null) {
      ip = parseHeader._2.toString
    }

    val futureResponse: Future[WSResponse] = ws.url("http://ip-api.com/json/" + ip).get()

    futureResponse.flatMap { response =>
      request.body.transform(transformer) match {
        case JsSuccess(person, _) =>
          val ipDetails = Json.parse(response.body.toString)
          val countryCode = (ipDetails \ "countryCode").as[String]
          var savePerson = person
          savePerson = savePerson.+("ipDetails", ipDetails)
          savePerson = savePerson.+("userAgent", JsString(parseHeader._1))
          savePerson = savePerson.+("ipAddress", JsString(parseHeader._2))
          val sessionId = (savePerson \ "sessionId").as[String]

          for {
            persons <- eventsFuture
            lastError <- persons.insert(savePerson)
            events <- getSessionDetail(sessionId.toString)
          } yield {
            Logger.debug(s"Successfully inserted with LastError: $lastError")
            val prediction = Predict.parseAndPredict(events, countryCode)
            Ok(prediction.toString())
          }
        case _ =>
          Future.successful(BadRequest("invalid json"))
      }
    }
  }

  def getIPDetails(ip: String) = Action.async {
    ws.url("http://ip-api.com/json/" + ip)
      .get()
      .map { ipDetails =>
        Ok(ipDetails.body.toString)
      }
  }

}


