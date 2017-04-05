package controllers

import javax.inject.{Inject, Singleton}

import play.api.Logger
import play.api.mvc._
import play.modules.reactivemongo.ReactiveMongoApi
import play.api.libs.ws._
import play.modules.reactivemongo._

import scala.util.Random
import reactivemongo.api.ReadPreference
import reactivemongo.play.json._
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._


@Singleton
class DataController @Inject()(val reactiveMongoApi: ReactiveMongoApi, val ws: WSClient) extends Controller with MongoController with ReactiveMongoComponents  {

  def citiesFuture: Future[JSONCollection] = database.map(_.collection[JSONCollection]("event"))

  def generate() = Action {
    val wsd: Future[WSResponse] = ws.url("http://ipinfo.io/8.8.8.8/json").get()
    wsd.map { response =>
      println(response.body.toString)
    }

    for (i <- utils.Data.getNAIPS.indices) {
      val futureResponse: Future[WSResponse] = ws.url("http://ip-api.com/json/" + utils.Data.getNAIPS(i)).get()

      futureResponse.map { response =>
        for( a <- 1 to 30) {
          saveEventData(i, utils.Data.getNAIPS(i), response.body.toString, true)
        }
      }
      println("na: ", i)
      Thread.sleep(2000)
    }

    for (i <- utils.Data.getIPS.indices) {
      val futureResponse: Future[WSResponse] = ws.url("http://ip-api.com/json/" + utils.Data.getIPS(i)).get()

      futureResponse.map { response =>
        for( a <- 1 to 30) {
          saveEventData(i, utils.Data.getIPS(i), response.body.toString, false)
        }
      }
      println("aa: ", i)
      Thread.sleep(2000)
    }
    Ok("generated data")

  }

  def saveEventData(index: Int, ipAddress: String, ipDetails: String, isCountryNA: Boolean): Unit = {
    val eventsCount = (Math.random() * 6).toInt + 1
    val sessionId = java.util.UUID.randomUUID.toString
    val ctaClicked = eventsCount % 2 == 0 // Tech=true, NonTech=false
    var page = ""

    for (i <- 0 until eventsCount) {

      if (index < 150) {
        page = Random.shuffle(utils.Data.getNonTechPages).head
        if (ctaClicked) {
          page = Random.shuffle(utils.Data.getTechPages).head
        }
      } else {
        val allPages = utils.Data.getNonTechPages ++ utils.Data.getTechPages
        page = Random.shuffle(allPages).head
      }

      val obj = Json.obj(
        "ipDetails" -> Json.parse(ipDetails),
        "userAgent" -> utils.Data.USER_AGENT,
        "hitType" -> utils.Data.PAGE_VIEW,
        "page" -> page,
        "created" -> JsNumber(new java.util.Date().getTime()),
        "sessionId" -> sessionId,
        "ipAddress" -> ipAddress
      )
      for {
        events <- citiesFuture
        lastError <- events.insert(obj)
      }
        yield {
          Logger.debug(s"Successfully inserted with LastError: $lastError")
          Created("Created 1 person")
        }

    }
    if (isCountryNA) {
      if (ctaClicked) {
        page = utils.Data.TRAINING_INOFFICE
      } else {
        page = utils.Data.CONTACT_US
      }
    } else {
      if (ctaClicked) {
        page = utils.Data.TRAINING_ONLINE
      } else {
        page = utils.Data.CONTACT_US
      }
    }


    val obj = Json.obj(
      "ipDetails" -> Json.parse(ipDetails),
      "userAgent" -> utils.Data.USER_AGENT,
      "hitType" -> utils.Data.EVENT,
      "page" -> page ,
      "created" -> JsNumber(new java.util.Date().getTime()),
      "sessionId" -> sessionId,
      "ipAddress" -> ipAddress
    )
    for {
      events <- citiesFuture
      lastError <- events.insert(obj)
    }
      yield {
        Logger.debug(s"Successfully inserted with LastError: $lastError")
        Created("Created 1 person")
      }


    if (ctaClicked) {

    }
  }

}
