import java.io.BufferedReader
import java.io.Reader
import kotlin.random.Random

fun main(){
    println("Podaj liczbę graczy:")
    var numberOfPlayers = readLine()!!.toInt()
    var listofPlayers = mutableListOf<Player>()

    for(i in 1..numberOfPlayers){
        val name = "Gracz $i"
        val rankFide= Random.nextInt(1000,2000)
        listofPlayers.add(Player(i, name, rankFide, 0,0,0))
    }
    if(numberOfPlayers%2!=0){
        listofPlayers.add(Player(numberOfPlayers+1, "Bot", 0, 0, 0, 0))
        numberOfPlayers++
    }
    println("Podaj liczbę rund:")
    val numberOfRounds = readLine().toString()

    val matches= Matches(ArrayList(listofPlayers), numberOfPlayers)
    matches.drawGame1()
}