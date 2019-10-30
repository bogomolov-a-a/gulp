data class Item(val name: String)

class Requirement(private val items: Map<Item, Int>) {
    operator fun plus(newItems: Requirement) = Requirement((items.asSequence() + newItems.items.asSequence())
        .distinct()
        .groupBy({ it.key }, { it.value })
        .mapValues { (_, values) -> values.sum() })

    fun can(other: Requirement) = items.all { (k, v) -> other.items.getOrDefault(k, 0) >= v }
}

fun emptyRequirement() = Requirement(mapOf())