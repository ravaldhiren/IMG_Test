
  import org.apache.spark.sql.{DataFrame, SQLContext, SparkSession}
  import com.imgtest.hexToBinary
  import org.apache.spark.sql.functions.udf
  import com.imgtest.MatchState


  object Mainmethod extends App {


    val Spark = SparkSession.builder.master("local").appName("IMG").getOrCreate()


    val data = Spark.read.option("header",false).textFile("/Users/dhirenc/Dhiren/IMG_Gaming/IMG_Gaming_Programming_Test_Scala/sample1.txt")


    import data.sparkSession.implicits._


  // def hextoLong = udf((hex: String) =>  new MatchState(hex).toString())//new hexToBinary(hex.trim).hexToBinary(hex.trim))

  def hexToBinary = udf((hex: String) =>   java.lang.Integer.toBinaryString(java.lang.Integer.decode(hex)))
  def addPadding = udf((binaryString:String) => padLeft(binaryString, 32 - binaryString.length))

  private def padLeft(s: String, n: Int): String = String.format("%0" + n + "d", Byte.box(0)) + s

  def extractElaspedTimeInSeconds = udf((binaryStr:String) =>  extractIntFromBinaryString(binaryStr,19,12))


  def extractPointScored = udf((binaryStr: String) => extractIntFromBinaryString(binaryStr,0,2))

  def extractTeamScored  =  udf((binaryStr: String) => 1+ extractIntFromBinaryString(binaryStr,2,1))

  def extractTeam2TotalPoints  = udf((binaryStr: String) => extractIntFromBinaryString(binaryStr,3,8))

  def extractTeam1TotalPoints = udf((binaryStr: String) => extractIntFromBinaryString(binaryStr,11,8))
  //private def extractElaspedTimeInSeconds(): Int = extractIntFromBinaryString(this.binaryInput,19,12)



  private def extractIntFromBinaryString(binaryStr:String, startIndex: Int, lengh: Int): Int = {

    java.lang.Integer.parseInt(binaryStr.substring(binaryStr.length - startIndex-lengh,binaryStr.length - startIndex),2)
  }

   //val decim = data.withColumn("value", )

  val decim = data.withColumn("value", hexToBinary($"value"))
    .withColumn("value", addPadding($"value"))
    .withColumn("extractPointScores", extractPointScored($"value"))
    .withColumn("extractTeamScored",extractTeamScored($"value"))
    .withColumn("extractTeam1TotalPoints",extractTeam1TotalPoints($"value"))
    .withColumn("extractTeam2TotalPoints",extractTeam2TotalPoints($"value"))
    .withColumn("elapsedTime", extractElaspedTimeInSeconds($"value"))

  val df = decim.select("value","extractPointScores","extractTeamScored","extractTeam1TotalPoints","extractTeam2TotalPoints","elapsedTime")
    .sort("elapsedTime")

   df.show()


}
