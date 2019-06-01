import java.io.File
import java.util.Scanner



object HexParser {

  def main(args: Array[String]): Unit = {
    val file: File = new File("/home/syam/Downloads/sample2.txt")
    val sc: Scanner = new Scanner(file)
    while (sc.hasNext) {
      val hex: String = sc.nextLine()
      println("hex : " + hex + " val : " + hexToBinary(hex))
    }
    sc.close()
  }

  def hexToBinary(hex: String): String = {
    val binaryString: String =
      java.lang.Integer.toBinaryString(java.lang.Integer.decode(hex))
    padLeft(binaryString, 32 - binaryString.length)
  }

  private def padLeft(s: String, n: Int): String =
    String.format("%0" + n + "d", 0) + s

  private def getPointsScored(binaryStr: String): Int =
    java.lang.Integer.parseInt(binaryStr.substring(0, 2), 2)

}
