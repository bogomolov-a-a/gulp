import guru.nidi.graphviz.engine.Format
import guru.nidi.graphviz.engine.Graphviz
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.File
import java.io.FileReader
import java.io.PrintWriter

fun generateGV(inFile: File, outFileGv: File, outFilePNG: File) {
    val graph = HashMap<String, List<Pair<String, Long>>>()
    val reader = FileReader(inFile)
    val json = JSONParser().parse(reader) as JSONObject
    val game = (json["game"] ?: error("No game")) as JSONObject
    for ((k, v) in game) {
        k as String
        v as JSONObject
        val connect = ((v["edges"] as JSONArray).map { it as JSONObject }).map {
            (it["nextScene"] as String) to (it.getOrDefault(
                "priority",
                0L
            ) as Long)
        }
        graph[k] = connect
    }
    val pw = PrintWriter(outFileGv)
    pw.println("digraph {")
    for (name in graph.keys) {
        pw.println("    ${name.hashCode()}[label=$name]")
    }
    for ((name, edges) in graph) {
        for ((dst, prio) in edges) {
            pw.println("    ${name.hashCode()} -> ${dst.hashCode()}[label=$prio]")
        }
    }
    pw.println("}")
    pw.close()
//    Graphviz.useEngine(listOf(GraphvizV8Engine()))
    Graphviz.fromFile(outFileGv).render(Format.PNG).toFile(outFilePNG)
}
