package utils

import kotlin.math.abs

fun extendRanges(ranges: Iterable<IntRange>) = ranges.minOf { it.first }..ranges.maxOf { it.last }

data class Position(val x: Int, val y: Int) {
  fun cartesianLineTo(other: Position): List<Position>? {
    return if (x == other.x) {
      (y.coerceAtMost(other.y)..y.coerceAtLeast(other.y)).map { i ->
        Position(x, i)
      }
    } else if (y == other.y) {
      (x.coerceAtMost(other.x)..x.coerceAtLeast(other.x)).map { i ->
        Position(i, y)
      }
    } else {
      null
    }
  }

  fun forNeighborsInRange(
    range: Range2D,
    action: (Position) -> Unit,
  ) {
    Direction.entries
      .map { this + it }
      .filter { range.contains(it) }
      .forEach(action)
  }

  fun forDiagonalNeighborsInRange(
    range: Range2D,
    action: (Position) -> Unit,
  ) {
    DirectionWithDiagonals.entries
      .map { this + it }
      .filter { range.contains(it) }
      .forEach(action)
  }

  fun manhattanDistance(other: Position) = abs(x - other.x) + abs(y - other.y)

  fun pointsAtManhattanDistance(distance: Int): Set<Position> {
    val rangeX = -distance..distance
    return rangeX.flatMap { deltaX ->
      val distanceY = distance - abs(deltaX)
      listOf(Position(x + deltaX, y - distanceY), Position(x + deltaX, y + distanceY))
    }.toSet()
  }

  operator fun plus(other: Position): Position {
    return Position(x + other.x, y + other.y)
  }

  operator fun plus(direction: Direction): Position {
    return Position(x + direction.deltaX, y + direction.deltaY)
  }

  operator fun plus(other: DirectionWithDiagonals): Position {
    return Position(x + other.deltaX, y + other.deltaY)
  }
}
