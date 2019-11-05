import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val text = String(Files.readAllBytes(Paths.get("Config.txt")), StandardCharsets.UTF_8)
    val pathSource = text.substringAfter("[SOURCE_FILE]=").substringBefore('\n')
    val source = File(pathSource)
    val pathOut = text.substringAfter("[OUT_GV_FILE]=").substringBefore('\n')
    val pathOutPng = text.substringAfter("[OUT_PNG_FILE]=").substringBefore('\n')
    val outGV = File(pathOut)
    val outPNG = File(pathOutPng)
    generateGV(source, outGV, outPNG)
}