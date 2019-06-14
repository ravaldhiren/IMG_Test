import java.io.File

import com.imgtest.MatchState
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, Row, SQLContext, SparkSession}
import scala.io.Source


object RddMain extends App {

  case class schema( hexinput : String, elapsedTime: Int, binaryinput: String, pointscored: Int, teamscored: Int, team1TotalPoint: Int, team2TotalPoint: Int)
  val conf = new SparkConf().setAppName("rddrout").setMaster("local")
  val sc = new SparkContext(conf)

  processResourceFiles()
  def processResourceFiles() = {
      val path = getClass().getResource("sample3.txt")
      println("p1 : " + path)

      val inputFolder = new File(getClass().getResource("input").getPath)
      if (inputFolder.exists() && inputFolder.isDirectory()) {
        inputFolder.listFiles().toList.foreach(file => {
          println("Starting process for file : " + file.getAbsolutePath)
          processMatchState(file.getAbsolutePath)
          println("Finished processing for file : " + file.getAbsolutePath)
        })
      }
  }

  def createSparkSession(): SparkSession = SparkSession.builder().appName("Sparksessionapp").master("local[*]").getOrCreate()

  def processMatchState(file : String) = {

    val rddfile = sc.textFile(file)
    val rddfn = rddfile.filter(x => x != null && !x.isEmpty) //.map(x => addPadding(java.lang.Integer.toBinaryString(java.lang.Integer.decode(x.trim))))
    rddfn.foreach(println)

    var globalTeam1Score = 0
    var globalTeam2Score = 0
    var counter = 0
    def applyMatchState(row : Row) :Boolean = {
      //println("Applying match state : " + row.getInt(1))
      // Validate points scored
      val points = row.getInt(3)
      if (points < 1 || points > 3) {
        return false
      }

      // Validate team1 & team2 points
      val teamScored = row.get(4)
      val team1Points = row.getInt(5)
      val team2Points = row.getInt(6)

      // Both scores should be gte current score
      if (team1Points < globalTeam1Score || team2Points < globalTeam2Score) {
        return false
      }
      if (teamScored == 1 && team1Points == globalTeam1Score) {
        return false
      }
      if (teamScored == 2 && team2Points == globalTeam2Score) {
        return false
      }

      globalTeam1Score = team1Points
      globalTeam2Score = team2Points
      println("global1 :" + globalTeam1Score + " global2:" + globalTeam2Score + " row:" + row.toString())
      return true
    }

    val scsession = createSparkSession()
    import scsession.sqlContext.implicits._
    val dd = rddfn.map(x => new MatchState(x))
      .map(x => schema(x.hexInput,x.elapsedTime, x.binaryInput,x.pointsScored,x.teamScored,x.team1TotalPoints,x.team1Tota2Points))
      .toDF().orderBy($"elapsedTime" ).dropDuplicates("elapsedTime").filter(x => applyMatchState(x))
    dd.show(100)
    println( dd.schema)
  }
  /*

*/
}
