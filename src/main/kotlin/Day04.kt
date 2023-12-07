import utils.readAocInput

data class ScratchCard(val winningNumbers: Set<Int>, val myNumbers: Set<Int>){
  fun calculatePower(): Int {
    val count = winningNumbers.count { myNumbers.contains(it) }
    return if (count == 0) {
      0
    } else {
      (0 until count - 1).map { 2}.fold(1) { result, next -> result * next}
    }
  }
}

fun main() {
  val cards =
    readAocInput(4, 1).map { s ->
      s.substringAfter(':')
        .split(" | ")
        .map {
          it.split(Regex(" +")).filterNot { it.isEmpty() }
            .map { it.toInt() }.toSet()
        }
        .let { ScratchCard(it[0], it[1]) }
    }

  val part1 =
    cards
      .map { it.calculatePower() }
      .also { println(it) }
      .sum()
  println(part1)
}
