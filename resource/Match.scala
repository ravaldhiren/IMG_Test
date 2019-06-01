object MatchState {
    

  private def extractIntFromBinaryString(binaryStr: String,                                            startIndex: Int,                                            length: Int): Int =       java.lang.Integer.parseInt(        binaryStr.substring(binaryStr.length - startIndex - length,                             binaryStr.length - startIndex),         2)

       private

  def hexToBinary(hex: String): String = {
          val binaryString: String =         java.lang.Integer.toBinaryString(java.lang.Integer.decode(hex))       padLeft(binaryString, 32 - binaryString.length)
     
  }

       private

  def padLeft(s: String, n: Int): String =       String.format("%0" + n + "d", 0) + s

    
}

  

class MatchState(hex: String) {
    
  @BeanProperty     var hexInput: String = hex
      @BeanProperty
      var binaryInput: String
  = _
      @BeanProperty
      var pointsScored: Int
  = _
      @BeanProperty
      var teamScored: Int
  =

 