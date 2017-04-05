package models

import play.api.libs.json.{JsObject, Json}
import java.util.Date

case class Event(hitType: String, page: String, sessionId: String, created: Date, userAgent: String, ipAddress: String, ipDetails: JsObject)

object Event {
  implicit var eventFormatter = Json.format[Event]
}
