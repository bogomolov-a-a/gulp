const val RESET = "\u001B[0m"
const val BLACK = "\u001B[30m"
const val RED = "\u001B[31m"
const val GREEN = "\u001B[32m"
const val YELLOW = "\u001B[33m"
const val BLUE = "\u001B[34m"
const val PURPLE = "\u001B[35m"
const val CYAN = "\u001B[36m"
const val WHITE = "\u001B[37m"
const val BLACK_BACKGROUND = "\u001B[40m"
const val RED_BACKGROUND = "\u001B[41m"
const val GREEN_BACKGROUND = "\u001B[42m"
const val YELLOW_BACKGROUND = "\u001B[43m"
const val BLUE_BACKGROUND = "\u001B[44m"
const val PURPLE_BACKGROUND = "\u001B[45m"
const val CYAN_BACKGROUND = "\u001B[46m"
const val WHITE_BACKGROUND = "\u001B[47m"

val colors =
    mapOf(
        "Black" to BLACK,
        "Red" to RED,
        "Green" to GREEN,
        "Yellow" to YELLOW,
        "Blue" to BLUE,
        "Purple" to PURPLE,
        "Cyan" to CYAN,
        "White" to WHITE,
        "Default" to RESET
    )
val background =
    mapOf(
        "Black" to BLACK_BACKGROUND,
        "Red" to RED_BACKGROUND,
        "Green" to GREEN_BACKGROUND,
        "Yellow" to YELLOW_BACKGROUND,
        "Blue" to BLUE_BACKGROUND,
        "Purple" to PURPLE_BACKGROUND,
        "Cyan" to CYAN_BACKGROUND,
        "White" to WHITE_BACKGROUND,
        "Default" to RESET
    )

fun paint(text: String, color: String, back: String) = (colors[color] ?: error("No such text color")) + (background[back] ?: error("No such background color")) + text + RESET