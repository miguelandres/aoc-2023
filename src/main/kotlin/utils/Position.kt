package utils

import kotlin.math.abs

fun extendRanges(ranges: Iterable<IntRange>) = ranges.minOf { it.first }..ranges.maxOf { it.last }

operator fun <T> List<List<T>>.get(pos: Position): T = this[pos.y][pos.x]

operator fun <T> Array<Array<T>>.get(pos: Position): T = this[pos.y][pos.x]

operator fun <T> Array<Array<T>>.set(pos: Position, value: T) {
  this[pos.y][pos.x] = value
}

fun <T> List<List<T>>.range(): Range2D = Range2D(this[0].indices, this.indices)
fun <T> List<List<T>>.dimensions(): Pair<Int, Int> = this.size to this[0].size

fun <T> Array<Array<T>>.range(): Range2D = Range2D(this[0].indices, this.indices)
fun <T> Array<Array<T>>.dimensions(): Pair<Int, Int> = this.size to this[0].size

enum class Direction(val deltaX: Int, val deltaY: Int) {
  LEFT(-1, 0),
  RIGHT(1, 0),
  DOWN(0, -1),
  UP(0, 1),
}

enum class DirectionWithDiagonals(val deltaX: Int, val deltaY: Int) {
  LEFT(-1, 0),
  DOWN_LEFT(-1, -1),
  UP_LEFT(-1, 1),
  RIGHT(1, 0),
  DOWN_RIGHT(1, -1),
  UP_RIGHT(1, 1),
  DOWN(0, -1),
  UP(0, 1),
}

data class Range2D(val rangeX: IntRange, val rangeY: IntRange) {
  fun forEach(action: (Position) -> Unit) {
    for (y in rangeY) {
      for (x in rangeX) {
        val position = Position(x, y)
        action(position)
      }
    }
  }

  fun <T> map(transform: (Position) -> T): Iterable<T> {
    val result = mutableListOf<T>()
    for (y in rangeY) {
      for (x in rangeX) {
        val position = Position(x, y)
        result.add(transform(position))
      }
    }
    return result
  }


  fun contains(position: Position): Boolean =
    rangeX.contains(position.x) && rangeY.contains(position.y)
}

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
