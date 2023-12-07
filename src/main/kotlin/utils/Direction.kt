package utils

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
