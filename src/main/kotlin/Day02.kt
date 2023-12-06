import utils.readAocInput
import utils.splitWithPrefix
import kotlin.math.max

val maxCubesPerColor = mapOf(
  "red" to 12,
  "green" to 13,
  "blue" to 14,
)

val colors = listOf("red", "green", "blue")

data class ColorCount(val color: String, val count: Int) {
  companion object {
    fun fromString(s: String): ColorCount {
      val parts = s.trim().split(" ")
      return ColorCount(parts[1], parts[0].toInt())
    }
  }
}

fun main() {
  val games = readAocInput(2, 1)
    .map { s ->
      val parts = s.splitWithPrefix("Game ", ":")
      val id = parts[0].toInt()
      val shows: List<Map<String, Int>> = parts[1].split(";").map {
        val colorCounts = it.split(",").map { ColorCount.fromString(it) }
        colorCounts.associate { it.color to it.count }
      }
      id to shows
    }
  val part1 = games
    .filter { it.second.all { show -> maxCubesPerColor.all { show.getOrDefault(it.key, 0) <= it.value } } }
    .sumOf { it.first }

  println(part1)

  val part2 = games
    .map {
      it.second.fold(colors.associateWith { 0 }) { result, show ->
        result.mapValues { entry -> max(entry.value, show[entry.key] ?: 0) }
      }
    }
    .map {
      it.entries.fold(1) { result, next ->
        result * next.value
      }
    }
    .sum()

  println(part2)

}
