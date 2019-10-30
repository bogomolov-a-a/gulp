class Text(private val events: List<Event>) {
    fun show() {
        for (e in events)
            when (e) {
                is Message ->
                    print(paint(e.text, e.color, e.back))
                is Option -> {
                    e.sound?.run { play() }
                    Thread.sleep(e.pause)
                }
            }
    }
}

sealed class Event
data class Message(val text: String, val color: String, val back: String) : Event()
data class Option(val pause: Long = 0, val sound: Sound? = null) : Event()