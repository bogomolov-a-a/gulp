import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.File
import java.io.FileReader

fun read(range: IntRange): Int {
    val c = readLine()
    return try {
        val num = c?.toInt()
        if (num == null || num !in range) {
            println("I need number from ${range.first} to ${range.last}")
            read(range)
        } else
            num
    } catch (e: Exception) {
        println("I need number from ${range.first} to ${range.last}")
        read(range)
    }
}

fun parse(f: File): Scene {
    val reader = FileReader(f)
    val json = JSONParser().parse(reader) as JSONObject
    val game = (json["game"] ?: error(paint("No game", "black", "red")))  as JSONObject
    val scenes = mutableMapOf<String, Scene>()
    fun String.toItem() =
        Item(this)

    fun JSONObject.toRequirements() =
        Requirement(this.map { (it.key.toString().toItem()) to it.value.toString().toInt() }.toMap())

    fun JSONObject.toEvent(): Event =
        if (this.containsKey("text"))
            Message(
                this["msg"].toString(),
                this.getOrDefault("color", "Default").toString(),
                this.getOrDefault("back", "Default").toString()
            )
        else
            Option(
                this.getOrDefault("pause", 0) as Long,
                if (this["sound"] == null) null else Sound(File(this["sound"].toString()))
            )

    fun JSONArray.toText() = Text(this.map { (it as JSONObject).toEvent() })
    fun JSONObject.toEdge() = Edge(
        ((this.getOrDefault("text", null) as JSONArray?))?.toText(),
        scenes[(this["nextScene"] ?: error("No destination")).toString()] ?: error("No such scene"),
        (this.getOrDefault("requirements", JSONObject()) as JSONObject).toRequirements(),
        this.getOrDefault("priority", 0L) as Long
    )


    for ((k, v) in game) {
        k as String
        v as JSONObject
        scenes[k] = Scene(
            ((v.getOrDefault("text", null) as JSONArray?))?.toText(),
            listOf(),
            (v.getOrDefault("newItems", JSONObject()) as JSONObject).toRequirements()
        )
    }
    for ((k, v) in game) {
        k as String
        v as JSONObject
        scenes[k]!!.edges = (v.getOrDefault("edges", listOf<JSONObject>()) as List<JSONObject>).map { it.toEdge() }
    }
    return scenes[json["begin"]?.toString() ?: "No start point"] ?: error("No such start scene")
}