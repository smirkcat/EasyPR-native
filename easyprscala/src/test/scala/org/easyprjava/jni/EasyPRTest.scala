package org.easyprjava.jni

import org.smirkcat.loaddll.JarDllScala
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

class EasyPRTest {

}
object EasyPRTest {
  def main(args: Array[String]) {
    //java可以获取到test-classes目录,scala无法获取,此处暂时只能替换
    val path = (JarDllScala.rootPath(EasyPRTest.getClass).replace("/classes", "/test-classes"))
    println(path)
    val fun = EasyPR(path + "model")
    val img=setImageToByteArray(path+"test.jpg")
    
    val result=fun.plateRecognize(img)

    println(s"c++返回结果为: $result")
    
    fun.delete()

    println(s"执行完毕")
  }
  /**
   * 图片文件目录转化为二进制
   *
   * @param fileName
   * @return byte[]
   */
  def setImageToByteArray(fileName: String): Array[Byte] = {
    val file = new File(fileName);
    setImageToByteArray(file);
  }

  /**
   * 图片文件转化为二进制
   *
   * @param file
   * @return
   */
  def setImageToByteArray(file: File): Array[Byte] = {
    var fis: FileInputStream = null;
    var filebuff:Array[Byte]=null;
    try {
      fis = new FileInputStream(file)
      filebuff = new Array[Byte](fis.available())
      fis.read(filebuff)
      fis.close()
    } catch {
      case e: FileNotFoundException => {
        println("文件没找到,错误信息：\n")
        e.printStackTrace()
      }
      case e: IOException => {
        println("io错误,错误信息：\n")
        e.printStackTrace()
      }
    }
    filebuff
  }
}