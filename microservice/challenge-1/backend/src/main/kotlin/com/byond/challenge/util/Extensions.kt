package com.byond.challenge.util

inline fun <reified R, reified T> R.toDto(): T {
    val klass = T::class.java
    val constructor = klass.getConstructor(R::class.java)
    return constructor.newInstance(this) as T
}

inline fun <T : Any, R> T?.ifNotNull(callback: (T) -> R): R? {
    return this?.let(callback)
}

fun <E> List<E>.applyPagination(limit: Int, offset: Int): List<E> {
    return this
        .windowed(size = limit, step = limit, partialWindows = true)
        .elementAtOrElse(offset) { emptyList() }
}
