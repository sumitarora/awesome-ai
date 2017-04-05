package utils

import models.Event
import play.api.libs.json.{JsObject, Json}
import utils.Data

// "training-online" => cta1
// "training-inoffice" => cta2
// "contact-us" => cta3

object Predict {

  def predictCta3(p0: Double, p1: Double, p2: Double, p3: Double, p4: Double, p5: Double, p6: Double): Double = {
    var prediction = 0.0
    if (p4 <= 0.0) {
      prediction = 0.5002
      if (p3 <= 0.0) {
        prediction = 0.7814
        if (p5 <= 0.0) {
          prediction = 0.9374
          if (p6 <= 0.0) {
            prediction = 0.9879
            if (p1 <= 0.0) {
              prediction = 0.9871
            } else if (p1 > 0.0) {
              prediction = 0.129
            }
          } else if (p6 > 0.0) {
            prediction = 0.121
          }
        } else if (p5 > 0.0) {
          prediction = 0.626
          if (p2 <= 0.0) {
            prediction = 0.7400
            if (p1 <= 0.0) {
              prediction = 0.8240
            } else if (p1 > 0.0) {
              prediction = 0.1760
            }
          } else if (p2 > 0.0) {
            prediction = 0.2600
            if (p1 <= 0.0) {
              prediction = 0.5301
            } else if (p1 > 0.0) {
              prediction = 0.4699
            }
          }
        }
      } else if (p3 > 0.0) {
        prediction = 0.2186
        if (p6 <= 0.0) {
          prediction = 0.8834
        }
      }
    }
    prediction
  }

  def predictCta2(p0: Double, p1: Double, p2: Double, p3: Double, p4: Double, p5: Double, p6: Double): Double = {
    var prediction = 0.0
    if (p5 <= 0.0) {
      prediction = 0.7503
      if (p4 <= 0.0) {
        prediction = 0.8927
        if (p3 <= 0.0) {
          prediction = 0.9708
          if (p1 <= 0.0) {
            prediction = 0.9936
            if (p6 <= 0.0) {
              prediction = 0.9961
            } else if (p6 > 0.0) {
              prediction = 0.39
            }
          } else if (p1 > 0.0) {
            prediction = 0.64
            if (p2 <= 0.0) {
              prediction = 0.9921
            } else if(p2 > 0.0){
              prediction =  0.79
            }
          }
        } else if (p3 > 0.0) {
          prediction = 0.292
          if (p6 <= 0.0) {
            prediction = 0.6465
            if (p2 <= 0.0) {
              prediction = 0.6667
            } else if (p2 > 0.0) {
              prediction = 0.3333
            }
          } else if (p6 > 0.0) {
            prediction = 0.3535
          }
        }
      } else if (p4 > 0.0) {
        prediction = 0.1073
        if (p6 <= 0.0) {
          prediction = 0.5647
        }
      }
    }
    prediction
  }

  def predictCta1(p0: Double, p1: Double, p2: Double, p3: Double, p4: Double, p5: Double, p6: Double): Double = {
    var prediction = 0.0
    if (p4 <= 0.0) {
       prediction = 0.7499
      if (p3 <= 0.0) {
        prediction = 0.8933
        if (p5 <= 0.0) {
          prediction = 0.9709
          if (p6 <= 0.0) {
            prediction = 0.9943
            if (p1 <= 0.0) {
              prediction = 0.9939
            } else if (p1 > 0.0) {
              prediction = 0.61
            }
          } else if (p6 > 0.0) {
            prediction = 0.57
          }
        } else if (p5 > 0.0) {
          prediction = 0.291
          if (p2 <= 0.0) {
            prediction = 0.6571
            if (p6 <= 0.0) {
              prediction = 0.6180
            } else if (p6 > 0.0) {
              prediction = 0.3820
            }
          } else if (p2 > 0.0) {
            prediction = 0.3429
            if (p1 <= 0.0) {
              prediction = 0.7831
            } else if (p1 > 0.0) {
              prediction = 0.2169
            }
          }
        }
      } else if (p3 > 0.0) {
        prediction = 0.1067
        if (p6 <= 0.0) {
          prediction = 0.5625
        }
      }
    }
    prediction
  }

  def parseAndPredict(events: List[Event], countryCode: String): JsObject = {
    var p1, p2, p3, p4, p5, p6, country = 0.0
    events.foreach{ event =>
      if (event.hitType == "pageview") {
        event.page match {
          case "/services" => p1 += 1.0
          case "/process" => p2 += 1.0
          case "/work" => p3 += 1.0
          case "/resources" => p4 += 1.0
          case "/tech-stack" => p5 += 1.0
          case "/blog" => p6 += 1.0
          case "/" =>
        }
      }
    }

    p1 = if (p1 > 0.0) 1.0 else 0.0
    p2 = if (p2 > 0.0) 1.0 else 0.0
    p3 = if (p3 > 0.0) 1.0 else 0.0
    p4 = if (p4 > 0.0) 1.0 else 0.0
    p5 = if (p5 > 0.0) 1.0 else 0.0
    p6 = if (p6 > 0.0) 1.0 else 0.0
    country = if (countryCode == "US" || countryCode == "CA") 1.0 else 0.0;

    var response = Json.obj(
      "cta1" -> 0.0,
      "cta2" -> 0.0,
      "cta3" -> 0.0
    )

    // "training-online" => cta1
    // "training-inoffice" => cta2
    // "contact-us" => cta3



    return Json.obj(
      "cta1" -> predictCta1(p1,p2,p3,p4,p5,p6, country),
      "cta2" -> predictCta2(p1,p2,p3,p4,p5,p6, country),
      "cta3" -> predictCta3(p1,p2,p3,p4,p5,p6, country)
    )
  }

}