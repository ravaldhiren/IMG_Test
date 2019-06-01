import java.io.File

import com.imgtest.MatchState
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, Row, SQLContext, SparkSession}

import scala.io.Source


object RddMain extends App {

  val conf = new SparkConf().setAppName("rddrout").setMaster("local")

  val sc = new SparkContext(conf)

  val path = getClass().getResource("/Input")
  val inputFolder = new File(path.getPath)
  if (inputFolder.exists() && inputFolder.isDirectory()) {
    inputFolder.listFiles().toList.foreach(file => {
      print("File : " + file)
    })
  }
  /*
  val scsession = SparkSession.builder().appName("Sparksessionapp").master("local[*]").getOrCreate()


  val rddfile = sc.textFile("C:\\IMG_Test\\IMG_Gaming_Programming_Test_Scala\\sample2.txt")

  case class schema( hexinput : String, elapsedTime: Int, binaryinput: String, pointscored: Int, teamscored: Int, team1TotalPoint: Int, team2TotalPoint: Int)

  val rddfn = rddfile.filter(x => x != null && !x.isEmpty) //.map(x => addPadding(java.lang.Integer.toBinaryString(java.lang.Integer.decode(x.trim))))
      //.map(x =>extractPointScored(x) )

  var globalTeam1Score = 0
  var globalTeam2Score = 0
  var counter = 0
  def applyMatchState(row : Row) :Boolean =  {
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
    if (teamScored == 1 && team1Points  == globalTeam1Score) {
      return false
    }
    if (teamScored == 2 && team2Points == globalTeam2Score) {
      return false
    }

    globalTeam1Score = team1Points
    globalTeam2Score = team2Points
    println("global1 :" + globalTeam1Score + " global2:" + globalTeam2Score + " row:" + row.toString())
    return true
    //counter += 1
    //if (counter % 2 == 0) return true else return false
  }

  //val rddfn = rddf.map(x => List(x, extractPointScored(x), extractElaspedTimeInSeconds(x))).filter(x => (x.apply(2).asInstanceOf[Int]) > 30)

import scsession.sqlContext.implicits._


  val dd = rddfn.map(x => new MatchState(x))
    .map(x => schema(x.hexInput,x.elapsedTime, x.binaryInput,x.pointsScored,x.teamScored,x.team1TotalPoints,x.team1Tota2Points))
    .toDF().orderBy($"elapsedTime" ) // .dropDuplicates("elapsedTime").filter(x => applyMatchState(x))

  //val dd = rddfn.map(x => new MatchState(x)).map(x => schema(x.hexInput,x.binaryInput,x.pointsScored,x.teamScored,x.team1TotalPoints,x.team1Tota2Points,x.elapsedTime))
    //.sortBy(x=>x.elapsedTime).filter(x => applyMatchState(x.elapsedTime)).toDF().dropDuplicates("elapsedTime")
    // .sortWithinPartitions("elapsedTime").filter(x => applyMatchState(x))

  val df = dd.show(100)

  //.filter(x => (x.apply(2).asInstanceOf[Int]) > 30)

// 1 3 4 7 2
  // 1 2 3 4 7

  //val rddth = rddf.cartesian(rddfn).distinct




  /*val mrgrdd = List (rddf,rddfn)

  val dcrdd

  //val struct = dcrdd.map(x => matchdata(x._1,x._2)).toDF()

*/



  rddfn.foreach(println)

 println( dd.schema)

  //dd.sort("elapsedTime").show()


  //df.show()


*/
}
