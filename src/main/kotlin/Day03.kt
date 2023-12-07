import utils.Map2D
import utils.readAocInput
import java.util.regex.Pattern

sealed interface EngineSchematicComponent
class PartNumber(val number: Int, var touched: Boolean = false) : EngineSchematicComponent {
  override fun toString(): String = "PartNumber $number${if (touched) " touched" else ""}"
}

data object EmptySpace : EngineSchematicComponent
class Symbol(val symbol: Char, val nearbyNumbers: MutableSet<PartNumber> = mutableSetOf()) : EngineSchematicComponent

fun main() {
  val map = readAocInput(3, 1).map { line ->
    val numberStrings = line.split(Pattern.compile("""\D+""")).filterNot { it.isEmpty() }
    val numberQueue = numberStrings.flatMap { number ->
        val partNumber = PartNumber(number.toInt())
        number.indices.map { partNumber }
      }.toMutableList()
    line.map {
      if (it.isDigit()) {
        numberQueue.removeFirst()
      } else if (it == '.') {
        EmptySpace
      } else {
        Symbol(it)
      }
    }.toTypedArray()
  }.toTypedArray().let { Map2D(it) }

  // Touch all part numbers
  map.forEachWithPosition { pos, value ->
    if (value is Symbol) {
      pos.forDiagonalNeighborsInRange(map.range) {
        (map[it] as? PartNumber)?.also { partNumber ->
          value.nearbyNumbers.add(partNumber)
          partNumber.touched = true
        }
      }
    }
  }

  val part1 = map.filterIsInstance<PartNumber>().filter { it.touched }.toSet().sumOf { it.number }
  println(part1)

  val part2 = map.filterIsInstance<Symbol>().filter { it.symbol == '*' && it.nearbyNumbers.size == 2 }
    .map { it.nearbyNumbers.fold(1) { result, next -> result * next.number } }.sum()

  println(part2)
}
