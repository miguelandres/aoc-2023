import utils.minPositive
import utils.readAocInput
import kotlin.math.max

val digitMap =
  mapOf(
    1 to "one",
    2 to "two",
    3 to "three",
    4 to "four",
    5 to "five",
    6 to "six",
    7 to "seven",
    8 to "eight",
    9 to "nine",
    0 to "0",
  )

fun main() {
  val lines = readAocInput(1, 2)
  val part1 =
    lines
      .map { s -> s.filter { it.isDigit() } }
      .map { it.first() + "" + it.last() }
      .map { it.toInt() }
      .sum()
  println(part1)

  val part2 =
    readAocInput(1, 2)
      .map { line ->
        (0..9).map {
          (
            minPositive(
              line.indexOf(it.toString()),
              line.indexOf(digitMap.getValue(it)),
            )
              to
              max(line.lastIndexOf(it.toString()), line.lastIndexOf(digitMap.getValue(it)))
          )
        }.let { positions ->
          val (firstPos, lastPos) = positions.unzip()
          val lastDigit =
            lastPos
              .withIndex()
              .maxBy { it.value }
              .index
          val firstDigit =
            firstPos
              .withIndex()
              .filterNot { it.value == null }
              .minBy { it.value!! }
              .index
          firstDigit * 10 + lastDigit
        }
      }
      .sum()

  println(part2)
}
