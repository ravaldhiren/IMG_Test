import MatchState._

import scala.beans.{BeanProperty, BooleanBeanProperty}


object MatchState {

  private def extractIntFromBinaryString(binaryStr: String,
                                         startIndex: Int,
                                         length: Int): Int =
    java.lang.Integer.parseInt(
      binaryStr.substring(binaryStr.length - startIndex - length,
        binaryStr.length - startIndex),
      2)

  private def hexToBinary(hex: String): String = {
    val binaryString: String =
      java.lang.Integer.toBinaryString(java.lang.Integer.decode(hex))
    padLeft(binaryString, 32 - binaryString.length)
  }

  private def padLeft(s: String, n: Int): String =
    String.format("%0" + n + "d", 0) + s

}

class MatchState(hex: String) {

  @BeanProperty
  var hexInput: String = hex

  @BeanProperty
  var binaryInput: String = _

  @BeanProperty
  var pointsScored: Int = _

  @BeanProperty
  var teamScored: Int = _

  @BeanProperty
  var team1TotalPoints: Int = _

  @BeanProperty
  var team2TotalPoints: Int = _

  @BeanProperty
  var elapsedTime: Int = _

  initializeMatchState()

  def initializeMatchState(): Unit = {
    this.binaryInput = hexToBinary(this.hexInput)
    this.pointsScored = extractPointsScored()
    this.teamScored = extractTeamScored()
    this.team1TotalPoints = extractTeam1TotalPoints()
    this.team2TotalPoints = extractTeam2TotalPoints()
    this.elapsedTime = extractElapsedTimeInSeconds()
  }

  private def extractPointsScored(): Int =
    extractIntFromBinaryString(this.binaryInput, 0, 2)

  private def extractTeamScored(): Int =
    1 + extractIntFromBinaryString(this.binaryInput, 2, 1)

  private def extractTeam2TotalPoints(): Int =
    extractIntFromBinaryString(this.binaryInput, 3, 8)

  private def extractTeam1TotalPoints(): Int =
    extractIntFromBinaryString(this.binaryInput, 11, 8)

  private def extractElapsedTimeInSeconds(): Int =
    extractIntFromBinaryString(this.binaryInput, 19, 12)

  override def toString(): String =
    "MatchState [hexInput=" + hexInput + ", binaryInput=" +
      binaryInput +
      ", pointsScored=" +
      pointsScored +
      ", teamScored=" +
      teamScored +
      ", team1TotalPoints=" +
      team1TotalPoints +
      ", team2TotalPoints=" +
      team2TotalPoints +
      ", elapsedTime=" +
      elapsedTime +
      "]"

}
