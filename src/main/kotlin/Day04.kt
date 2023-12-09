import utils.readAocInput

class ScratchCard(val winningNumbers: Set<Int>, val myNumbers: Set<Int>, var copies: Int = 1){
  fun countWinningNumbers(): Int = winningNumbers.count { myNumbers.contains(it) }

  fun calculatePower(): Int {
    val count = countWinningNumbers()
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
    }.toList()

  val part1 =
    cards
      .map { it.calculatePower() }
      .sum()

  println(part1)

  cards.mapIndexed{ index, scratchCard ->
    (index + 1..index + scratchCard.countWinningNumbers()).forEach { cards[it].copies += scratchCard.copies }
  }
  println(cards.sumOf { it.copies })
}
