package utils

class Map2D<T>(private val map: Array<Array<T>>) : Collection<T> {
  val range: Range2D = Range2D(map[0].indices, map.indices)

  val dimensions: Position = Position(map[0].size, map.size)

  override val size: Int = dimensions.x * dimensions.y

  operator fun get(pos: Position): T = map[pos.y][pos.x]

  operator fun set(
    pos: Position,
    value: T,
  ) {
    map[pos.y][pos.x] = value
  }

  override fun isEmpty(): Boolean = map.isEmpty()

  inner class Map2DIterator : Iterator<T> {
    var iterator = range.iterator()

    override fun hasNext(): Boolean = iterator.hasNext()

    override fun next(): T = this@Map2D[iterator.next()]
  }

  override fun iterator(): Iterator<T> = Map2DIterator()

  override fun containsAll(elements: Collection<T>): Boolean {
    val searchSet = elements.toMutableSet()
    forEach {
      searchSet.remove(it)
    }
    return searchSet.isEmpty()
  }

  override fun contains(element: T): Boolean = this.any { it == element }

  fun forEachWithPosition(action: (pos: Position, value: T) -> Unit) {
    range.forEach { action(it, this[it]) }
  }

  fun <R> mapWithPosition(transform: (pos: Position, it: T) -> R): Iterable<R> = range.map { transform(it, this[it]) }

  fun <R> flatMapWithPosition(transform: (pos: Position, it: T) -> Iterable<R>): Iterable<R> =
    range.flatMap { transform(it, this[it]) }
}
