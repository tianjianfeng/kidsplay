package models

import play.api.libs.json.Json

case class Venue (line1: String, line2: Option[String], city: String, postcode: String, lat: Double, lng: Double)

case class Contact (landline: String, mobile: String, website: Option[String], facebook: Option[String], twitter: Option[String])

case class Schedule (venue: Venue, monday: Boolean)

case class Activity (name: String, description: String, schedules: List[Schedule], tags: List[String], category: String, contact: Contact)

object JsonFormats {

  import play.api.libs.json.Json

  implicit val venueFormat = Json.format[Venue]
  implicit val contactFormat = Json.format[Contact]
  implicit val scheduleFormat = Json.format[Schedule]
  implicit val activityFormat = Json.format[Activity]
}


