package models

import play.api.libs.json.Json
import utils.UnitSpec

class ModelsTest extends UnitSpec {
  "Activity" should "be able to serialised to json" in {
    val venue = Venue(line1 = "line1", line2 = None, city = "London", postcode = "w1", lat = 1.1, lng = 2.2)
    val contact = Contact (landline = "123", mobile = "123", website = None, facebook = None, twitter = None)
    val schedule = Schedule(venue = venue, monday = true)
    val activity = Activity (name = "activity", description = "description", schedules = List(schedule), tags = List("tag1"), category = "cat1", contact = contact)
    implicit val activityFormat = JsonFormats.activityFormat
    val expectJson = """{"name":"activity","description":"description","schedules":[{"venue":{"line1":"line1","city":"London","postcode":"w1","lat":1.1,"lng":2.2},"monday":true}],"tags":["tag1"],"category":"cat1","contact":{"landline":"123","mobile":"123"}}""".stripMargin
    Json.toJson(activity).toString should be (expectJson)
  }

  "Activity json" should "be able to deserialised to activity object" in {
    val venue = Venue(line1 = "line1", line2 = None, city = "London", postcode = "w1", lat = 1.1, lng = 2.2)
    val contact = Contact (landline = "123", mobile = "123", website = None, facebook = None, twitter = None)
    val schedule = Schedule(venue = venue, monday = true)
    val activity = Activity (name = "activity", description = "description", schedules = List(schedule), tags = List("tag1"), category = "cat1", contact = contact)
    implicit val activityFormat = JsonFormats.activityFormat
    val activityJson = """{"name":"activity","description":"description","schedules":[{"venue":{"line1":"line1","city":"London","postcode":"w1","lat":1.1,"lng":2.2},"monday":true}],"tags":["tag1"],"category":"cat1","contact":{"landline":"123","mobile":"123"}}""".stripMargin
    activityFormat.reads(Json.parse(activityJson)).asOpt.get should be (activity)
  }

}
