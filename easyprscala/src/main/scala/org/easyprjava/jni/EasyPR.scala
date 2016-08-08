package org.easyprjava.jni

import org.smirkcat.loaddll.JarDllScala
import java.io._

class EasyPR{

  @native protected def init(): Int
  @native protected def initPath(path: String): Int
  @native protected def plateRecognize(ptrNative: Int, img: Array[Byte]): String
  @native protected def delete(ptrNative: Int): Unit
  
  var ptrNative: Int = 0
  
  def plateRecognize(img: Array[Byte]): String = {
    plateRecognize(ptrNative, img)
  }

  def delete(): Unit = {
    delete(ptrNative)
    ptrNative=0;
  }
}

object EasyPR {
  def apply() = new EasyPR {
    ptrNative = init
  }

  def apply(path: String) = new EasyPR {
    ptrNative = initPath(path)
  }

  try {
    JarDllScala.loadLib("easyprjni", "/dll/", EasyPR.getClass)
  } catch {
    case e: IOException => {
      throw e
    }
  }
}