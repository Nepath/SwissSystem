import java.io.BufferedReader
import java.io.Reader
import kotlin.random.Random

fun main() {
    println("Podaj liczbę graczy: ")
    var numberOfPlayers = readLine()!!.toInt()
    while (numberOfPlayers < 4 || numberOfPlayers > 30) {
        println("Liczba graczy ma sie miescic w przedziale 4-30. Wprowadz liczbe jeszcze raz: ")
        numberOfPlayers = readLine()!!.toInt()
    }

    var listofPlayers = mutableListOf<Player>()

    for (i in 1..numberOfPlayers) {
        val name = "Gracz $i"
        val rankFide = Random.nextInt(1000, 2000)
        listofPlayers.add(Player(i, name, rankFide, 0, 0, 0,false, listOf("")))
    }

    if (numberOfPlayers % 2 != 0) {
        listofPlayers.add(Player(numberOfPlayers + 1, "Bot", 0, 0, 0, 0,false,  listOf("")))
        numberOfPlayers++
    }

    println("Podaj liczbę rund: ")
    var numberOfRounds = readLine()!!.toInt()
    while (numberOfRounds < 1 || numberOfRounds > numberOfPlayers) {
        println("Liczba rund musi byc dodatnia oraz mniejsza od liczby graczy. Wprowadz jeszcze raz: ")
        numberOfRounds = readLine()!!.toInt()
    }

    val matches = Matches(ArrayList(listofPlayers), numberOfPlayers)
    matches.drawGame1()
    matches.round1()
    matches.score()
}
