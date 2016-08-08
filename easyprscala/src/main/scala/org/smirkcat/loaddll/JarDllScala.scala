package org.smirkcat.loaddll

import java.io._
import java.util.ArrayList
import java.net.URISyntaxException
import scala.util.control._
import scala.collection.mutable.ListBuffer
import util.Try
import util.Properties


object JarDllScala {
  val tempDir: File = {
    val tmpdir = new File(Properties.tmpDir)
    val loop = new Breaks
    var tempDirf: File = null
    loop.breakable {
      for (i <- 0 to 1000) {
        val f = new File(tmpdir, "dll" + System.nanoTime())
        if (f.mkdir()) {
          tempDirf = f
          tempDirf.deleteOnExit()
          loop.break
        }
      }
    }
    tempDirf
  }
  val systemType: String = Properties.osName
  val libExtension: String = {
    val osName = systemType.toLowerCase()
    if (osName.indexOf("win") != -1) ".dll"
    else if (osName.indexOf("mac") != -1) ".dylib"
    else ".so"
  }
  def rootPath(cls: Class[_]): String = {
    var rootPath = cls.getResource("/").getFile()
    if ((systemType.toLowerCase().indexOf("win") != -1)) {
      rootPath = rootPath.substring(1, rootPath.length())
    }
    rootPath
  }
  def loadLib(libName: String, dllpath: String, cls: Class[_]) {
    val libFullName = dllpath + libName + libExtension
    val in = cls.getResourceAsStream(libFullName)
    if (in == null) return
    val reader = new BufferedInputStream(in)
    val filepath = tempDir.getAbsolutePath() + "/" + libName + libExtension
    var writer: FileOutputStream = null
    var extractedLibFile = new File(filepath)
    if (!extractedLibFile.exists()) {
      try {
        writer = new FileOutputStream(extractedLibFile)
        var buf = ListBuffer[Byte]()
        var b: Int = reader.read()
        while (b != -1) {
          buf.append(b.byteValue)
          b = reader.read()
        }
        writer.write(buf.toArray)
        in.close()
        reader.close()
        writer.close()
        System.load(extractedLibFile.toString())
      } catch {
        case e: IOException => {
          if (extractedLibFile.exists()) {
            extractedLibFile.delete()
          }
          throw e
        }
      } finally {
        if (in!= null)
          in.close()
        if (reader!= null)
          reader.close()
        if (writer != null)
          writer.close()
        if (extractedLibFile.exists()) {
          extractedLibFile.deleteOnExit()
        }
      }
    }
  }
  //暂时没有用到
  def main(args: Array[String]): Unit = Try[Unit] {
    val tmpdir = new File(System.getProperty("java.io.tmpdir"));
    val tempDir = new File(args(0));
    if (!tmpdir.equals(tempDir.getParentFile()) || !tempDir.getName().startsWith("dll")) {
      return
    }
    tempDir.listFiles().foreach(
      file => {
        while (file.exists() && !file.delete()) {
          Thread.sleep(100)
        }
      })
    tempDir.delete()
  }
  //抽象控制  
  def runInThread(block: => Unit) = {
    new Thread {
      override def run() { block }
    }
  }
  
  Runtime.getRuntime().addShutdownHook(runInThread { () =>
    if (tempDir != null && tempDir.exists()) {
      if (libExtension == ".dll") {
        try {
          var command = new ArrayList[String]();
          command.add(Properties.javaHome + "/bin/java");
          command.add("-classpath");
          command.add((new File(
            JarDllScala.getClass.getProtectionDomain().getCodeSource().getLocation().toURI())).toString())
          command.add(JarDllScala.getClass.getName.replace("Scala$", "Java")) //暂时只能调用java的main函数来删除tempDir
          command.add(tempDir.getAbsolutePath()) //args(0)
          new ProcessBuilder(command).start()
        } catch {
          case e: IOException =>
            throw new RuntimeException(e)
          case e: URISyntaxException =>
            throw new RuntimeException(e)
        }
      }
    }
  })
}

