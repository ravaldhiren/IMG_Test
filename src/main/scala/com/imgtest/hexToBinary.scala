package com.imgtest

  import org.apache.spark.sql.functions.udf

   class hexToBinary(hex: String) {

    def hexToBinary(hex: String) :String =  {
      val binaryString: String = java.lang.Integer.toBinaryString(java.lang.Integer.decode(hex))
      return padLeft(binaryString, 32 - binaryString.length)
    }

    private def padLeft(s: String, n: Int): String = String.format("%0" + n + "d", Byte.box(0)) + s

    //def hextoLong = udf((hex: String) => hexToBinary(hex.trim))




}
