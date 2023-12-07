package utils

import kotlin.math.min

fun minOrNull(
  x: Int?,
  y: Int?,
): Int? {
  if (x == null && y == null) return null
  if (x == null) return y
  if (y == null) return x
  return min(x, y)
}

/**
 * Returns the positive minimum between the two integers, if both are negative returns null
 */
fun minPositive(
  x: Int,
  y: Int,
): Int? {
  if (x < 0 && y < 0) return null
  return minOrNull(if (x < 0) null else x, if (y < 0) null else y)
}
