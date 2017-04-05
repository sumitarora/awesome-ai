import org.apache.spark.sql.SparkSession
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.mllib.tree.model.{Node, Split}
import org.apache.spark.mllib.tree.configuration.FeatureType._
import org.apache.log4j._
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.tree.DecisionTree

// Set log level to Error
Logger.getLogger("org").setLevel(Level.ERROR)

// Differentiate between tech and non-tech pages
val nonTechPages = List("/", "/services" , "/process", "/work")
val techPages = List("/resources" , "/tech-stack", "/blog")

// Print hello to see start of program
print("hello from scala & spark")

// Create Spark Session 
val spark = SparkSession.builder().getOrCreate()
// Read data from json file exported from old data
val dataFrame = spark.read.json("datasets/data.json")

// Print all the nodes of DecisionTree with probabilities
def subtreeToString(rootNode: Node, indentFactor: Int = 0): String = {
  def splitToString(split: Split, left: Boolean): String = {
    split.featureType match {
      case Continuous => if (left) {
        s"(feature ${split.feature} <= ${split.threshold})"
      } else {
        s"(feature ${split.feature} > ${split.threshold})"
      }
      case Categorical => if (left) {
        s"(feature ${split.feature} in ${split.categories.mkString("{", ",", "}")})"
      } else {
        s"(feature ${split.feature} not in ${split.categories.mkString("{", ",", "}")})"
      }
    }
  }
  val prefix: String = " " * indentFactor
  if (rootNode.isLeaf) {
    prefix + s"Predict: ${rootNode.predict.predict} \n"
  } else {
    val prob = rootNode.predict.prob*100D
    prefix + s"If ${splitToString(rootNode.split.get, left = true)} " + f"(Prob: $prob%04.2f %%)" + "\n" +
      subtreeToString(rootNode.leftNode.get, indentFactor + 1) +
      prefix + s"Else ${splitToString(rootNode.split.get, left = false)} " + f"(Prob: ${100-prob}%04.2f %%)" + "\n" +
      subtreeToString(rootNode.rightNode.get, indentFactor + 1)
  }
}

// Generate train data from data loaded in dataframe
val trainingData = dataFrame.rdd.map(row => {
  val hitType = row.get(2).toString
  val userSession = row.get(6).toString
  val page = row.get(5).toString
  val ipDetails = row.get(4).toString

  val data = ipDetails.substring(1, ipDetails.length - 1).split(",")
  val country = data(3)

  (userSession, hitType, country, page)
}).groupBy(row => row._1).map(row => {
  var p1, p2, p3, p4, p5, p6, cta1, cta2, cta3 = 0.0
  // Map resources to numerical features
  row._2.map(r => {
    if (r._2 == "pageview") {
      r._4 match {
        case "/services" => p1+=1
        case "/process" => p2+=1
        case "/work" => p3+=1
        case "/resources" => p4+=1
        case "/tech-stack" => p5+=1
        case "/blog" => p6+=1
        case "/" =>
      }
    } else {
      r._4 match {
        case "training-online" => cta1+=1
        case "training-inoffice" => cta2+=1
        case "contact-us" => cta3+=1
      }
    }
  })
  var countryCode = 0.0
  val code = row._2.toList(0)._3
  if (code == "Canada" || code == "United States") {
    countryCode = 1.0
  }

  p1 = if (p1 > 0.0) 1.0 else 0.0
  p2 = if (p2 > 0.0) 1.0 else 0.0
  p3 = if (p3 > 0.0) 1.0 else 0.0
  p4 = if (p4 > 0.0) 1.0 else 0.0
  p5 = if (p5 > 0.0) 1.0 else 0.0
  p6 = if (p6 > 0.0) 1.0 else 0.0

  // Create vector for features 
  val vector = Vectors.dense(countryCode, p1, p2, p3, p4, p5, p6)

  // Get LabeledPoint point corresponding to each outcome and list of features
  if (cta3 > 0) {
    new LabeledPoint(1.0, vector)
  } else {
    new LabeledPoint(0.0, vector)
  }
})

// Create configuration for DecisionTree Classifier
val numClasses = 2
val categoricalFeaturesInfo = Map[Int, Int]()
var impurity = "gini"
val maxDepth = 5
val maxBins = 64

// Train Classifier on trainingData
val dtClassifierModel = DecisionTree.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo, impurity, maxDepth, maxBins)

// Print DecisionTree with nodes
subtreeToString(dtClassifierModel.topNode)
