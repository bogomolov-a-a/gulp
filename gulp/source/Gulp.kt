import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths


fun play(v: Scene, inventory: Requirement) {
    val newInventory = inventory + v.newItems
    v.text?.run {
        show()
        println()
    }
    val possibleEdges = v.edges.filter { it.requirements.can(inventory) }
    val maxPriority = possibleEdges.maxBy { it.priority }?.priority
    val outEdges = possibleEdges.filter { it.priority == maxPriority }
    if (outEdges.size == 1) {
        outEdges.first().text?.run {
            show()
            println()
        }
        play(outEdges.first().nextScene, newInventory)
    } else {
        for ((i, e) in outEdges.withIndex()) {
            if (outEdges.size > 1)
                print("${i + 1}. ")
            e.text?.run {
                show()
                println()
            }
        }
        val choice = read(1..possibleEdges.size) - 1
        play(outEdges[choice].nextScene, newInventory)
    }
}

fun main() {
    val text = String(Files.readAllBytes(Paths.get("Config.txt")), StandardCharsets.UTF_8)
    text.substringAfter("[SOURCE_FILE]=")
    val path = text.substringAfter("[SOURCE_FILE]=").substringBefore('\n')
    val file = File(path)
    play(parse(file), emptyRequirement())
}