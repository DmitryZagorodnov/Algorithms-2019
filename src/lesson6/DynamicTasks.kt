@file:Suppress("UNUSED_PARAMETER")

package lesson6

import java.io.File

/**
 * Наибольшая общая подпоследовательность.
 * Средняя
 *
 * Дано две строки, например "nematode knowledge" и "empty bottle".
 * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
 * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
 * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
 * Если общей подпоследовательности нет, вернуть пустую строку.
 * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
 * При сравнении подстрок, регистр символов *имеет* значение.
 */
fun longestCommonSubSequence(first: String, second: String): String {
    TODO()
}

/**
 * Наибольшая возрастающая подпоследовательность
 * Сложная
 *
 * Дан список целых чисел, например, [2 8 5 9 12 6].
 * Найти в нём самую длинную возрастающую подпоследовательность.
 * Элементы подпоследовательности не обязаны идти подряд,
 * но должны быть расположены в исходном списке в том же порядке.
 * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
 * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
 * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
 */
fun longestIncreasingSubSequence(list: List<Int>): List<Int> {
    if (list.isEmpty()) return emptyList()
    if (list.size == 1) return list
    val lengthArray = Array(list.size) {1}
    for (j in 1 until list.size) {
        for (k in 0 until j) {
            if (list[j] > list[k]) {
                if (lengthArray[j] <= lengthArray[k]) lengthArray[j] = lengthArray[k] + 1
            }
        }
    }
    var max = 0
    for (length in lengthArray) {
        max = maxOf(max, length)
    }
    val ind = lengthArray.indexOf(max)
    val newlist = MutableList<Int>(max) {-1}
    newlist[max - 1] = list[ind]
    for (i in ind - 1 downTo 0) {
        if (list[i] < newlist[lengthArray[i]]) newlist.add(list[i])
    }
    newlist.remove(-1)
    return newlist.reversed()
}

/**
 * Самый короткий маршрут на прямоугольном поле.
 * Средняя
 *
 * В файле с именем inputName задано прямоугольное поле:
 *
 * 0 2 3 2 4 1
 * 1 5 3 4 6 2
 * 2 6 2 5 1 3
 * 1 4 3 2 6 2
 * 4 2 3 1 5 0
 *
 * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
 * В каждой клетке записано некоторое натуральное число или нуль.
 * Необходимо попасть из верхней левой клетки в правую нижнюю.
 * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
 * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
 *
 * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
 */
//Быстродействие и ресурсоемкость O(n)
fun shortestPathOnField(inputName: String): Int {
    val file = File(inputName).readLines()
    val str = file.joinToString(separator = " ")
    val field = str.split(" ")
    val width = (field.lastIndex + 1) / file.size
    var ind = 0
    val ans = MutableList(field.size) { 0 }
    for (element in field) {
        ans[ind] = element.toInt()
        ind++
    }
    ind = 1
    while (ind != ans.lastIndex) {
        ans[ind] += when {
            width == 1 -> ans[ind - 1]
            ind % width == 0 -> ans[ind - width]
            ind > width -> minOf(ans[ind - 1], ans[ind - width], ans[ind - width - 1])
            else -> ans[ind - 1]
        }
        ind++
    }
    return when {
        width == 1 -> ans[ind - 1] + ans[ind]
        ind > width -> ans[ind] + minOf(ans[ind - 1], ans[ind - width], ans[ind - width - 1])
        else -> ans[ind] + ans[ind - 1]
    }
}


// Задачу "Максимальное независимое множество вершин в графе без циклов"
// смотрите в уроке 5