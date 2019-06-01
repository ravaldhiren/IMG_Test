package com.imgtest

import scala.beans.{BeanProperty, BooleanBeanProperty}

@SerialVersionUID(1231321L)
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
    var team1Tota2Points: Int = _

    @BeanProperty
    var elapsedTime: Int = _

    def hexToBinary(hex: String) :String =  {
      //println("Creating match state for hex : " + hex)
      val binaryString: String = java.lang.Integer.toBinaryString(java.lang.Integer.decode(hex))
      return padLeft(binaryString, 32 - binaryString.length)
    }

    private def padLeft(s: String, n: Int): String = String.format("%0" + n + "d", Byte.box(0)) + s

    initializedMatchState()

    def initializedMatchState(): Unit = {
      this.binaryInput = hexToBinary(hexInput)
      this.pointsScored = extractPointScored()
      this.teamScored = extractTeamScored()
      this.team1TotalPoints = extractTeam1TotalPoints()
      this.team1Tota2Points = extractTeam2TotalPoints()
      this.elapsedTime = extractElaspedTimeInSeconds()
      println("Match state initialized for time : " + this.elapsedTime)
    }

    private def extractPointScored(): Int = extractIntFromBinaryString(this.binaryInput,0,2)
    private def extractTeamScored(): Int =  1+ extractIntFromBinaryString(this.binaryInput,2,1)
    private def extractTeam2TotalPoints(): Int = extractIntFromBinaryString(this.binaryInput,3,8)
    private def extractTeam1TotalPoints(): Int = extractIntFromBinaryString(this.binaryInput,11,8)
    private def extractElaspedTimeInSeconds(): Int = extractIntFromBinaryString(this.binaryInput,19,12)


    private def extractIntFromBinaryString(binaryStr:String, startIndex: Int, lengh: Int): Int = {

      java.lang.Integer.parseInt(binaryStr.substring(binaryStr.length - startIndex-lengh,binaryStr.length - startIndex),2)
    }




    /*override def toString(): String =
    "MatchState [hexInput=" + hexInput + ",binaryInput=" +
    binaryInput +
    ",pointsScored=" +
    pointsScored +
    ",teamScored=" +
    teamScored +
    ",team1TotalPoints=" +
    team1TotalPoints +
    ",team2TotalPoints=" +
    team1Tota2Points +
    ",elapedTime=" +
    elapsedTime +
    "]"*/

    override def toString(): String = "" +
        hexInput + "," +
        binaryInput + "," +
        pointsScored + "," +
        teamScored + "," +
        team1TotalPoints + "," +
        team1Tota2Points + "," +
        elapsedTime + ""

  }
