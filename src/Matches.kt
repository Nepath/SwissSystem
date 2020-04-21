import kotlin.random.Random

class Matches(val listofPlayer: ArrayList<Player>, val numberofPlayers: Int) {

    var roundNumber: Int = 1
    var medianRankFIDE: Float = 0f
    var completedDuals = mutableListOf<Duals>()
    var currentRoundDuals = mutableListOf<Duals>()
    var rankList = listofPlayer // aktualny ranking punktowy

    init {
        listofPlayer.sortByDescending { it.rankFIDE }
        println("Gracze: ")
        listofPlayer.forEach {
            println("${it.Name} - ${it.rankFIDE}")
        }
        val middlePlayer1Rank = listofPlayer[numberofPlayers / 2].rankFIDE
        val middlePlayer2Rank = listofPlayer[(numberofPlayers / 2) - 1].rankFIDE

        if (middlePlayer1Rank == middlePlayer2Rank) {
            listofPlayer[(numberofPlayers / 2) - 1].rankFIDE++
        }

        medianRankFIDE = (middlePlayer2Rank + middlePlayer1Rank) / 2f
    }

    fun addPoints() {
        currentRoundDuals.forEach {
            if (listofPlayer.contains(it.winner)) {

            }
        }
    }

    fun round1() {
        /* currentRoundDuals.forEach{
             var random = Random.nextInt(1,3)

             if(it.playerIDone.Name == "Bot"){
                 random = 2
             }
             else if(it.playerIDtwo.Name == "Bot"){
                 random = 1
             }

             when(random) {
                 1 -> it.winner = it.playerIDone
                 2 -> it.winner = it.playerIDtwo
                 3 -> it.winner = null
             }
             completedDuals.add(it)
         }*/
        println("Wprowadz wyniki dla wylosowanych par: 1 - wygrana gracza pierwszego, 2 - wygrana gracza drugiego, 3 - remis")
        currentRoundDuals.forEach {
            if (it.playerIDtwo.Name == "Bot") {
                it.winner = it.playerIDone
                it.playerIDone.points += 3
                it.playerIDone.withWho = listOf("Bot")  //to nie jest git ale jakos sobie chce ogarnac jak ma to dzialac by bylo zapisane kto z kim gral
            } else if (it.playerIDone.Name == "Bot") {
                it.winner = it.playerIDtwo
                it.playerIDtwo.points += 3
                it.playerIDtwo.withWho = listOf("Bot") //tak samo
            } else {
                println("${it.playerIDone.Name} vs ${it.playerIDtwo.Name}: ")
                var score = readLine()!!.toInt()
                when (score) {
                    1 -> {
                        it.winner = it.playerIDone
                        it.playerIDone.points += 3
                        it.playerIDone.withWho = listOf(it.playerIDtwo.Name)
                        it.playerIDtwo.withWho = listOf(it.playerIDone.Name)
                    }
                    2 -> {
                        it.winner = it.playerIDtwo
                        it.playerIDtwo.points += 3
                        it.playerIDone.withWho = listOf(it.playerIDtwo.Name)
                        it.playerIDtwo.withWho = listOf(it.playerIDone.Name)
                    }
                    3 -> {
                        it.winner = null
                        it.playerIDone.points += 1
                        it.playerIDtwo.points += 1
                        it.playerIDone.withWho = listOf(it.playerIDtwo.Name)
                        it.playerIDtwo.withWho = listOf(it.playerIDone.Name)
                    }
                }
                completedDuals.add(it)
            }
        }
        println("Kto z kim:") //tylko sprawdzam czy lista zadzialala
        listofPlayer.forEach {
            println("${it.Name} gral z ${it.withWho}")
        }
    }

    fun score() {
        rankList.sortByDescending { it.points }
        println("Aktualny ranking: ")
        rankList.forEach {
            println("${it.Name} posiada ${it.points} punktow")
        }
    }

    fun drawGame1() {
        val pool1 = mutableListOf<Player>()
        val pool2 = mutableListOf<Player>()
        val poolNumbers = mutableListOf<Int>()

        listofPlayer.forEach {
            if (it.rankFIDE < medianRankFIDE) {
                it.blackAmount++
                pool2.add(it)
            } else {
                it.whiteAmount++
                pool1.add(it)
            }
        }
        for (i in 0 until pool1.size) {
            poolNumbers.add(i)
        }
        //poolNumbers.shuffle() //W systemie szwajcarskim pierwsza runka nie jest losowo tylko po podziale. Zapomnialem o tym. Czyli
        //jak masz 8 zawodnikow to ustalasz liste wdg rankingu elo. Potem dzielisz ja na pol i gracz 1 gra z 5. Czyli najlepszy z pierwszej grupy z najlepszym w drugiej.
        //Losowanie jest potem jak np. 4 graczy ma tyle samo pkt to kto z kim gra.
        pool1.sortByDescending { it.rankFIDE }
        pool2.sortByDescending { it.rankFIDE }

        pool1.forEach {
            println("Pool1: ${it.Name}")
        }
        pool2.forEach {
            println("Pool2: ${it.Name}")
        }

        var i = 0
        pool1.forEach { player1 ->
            currentRoundDuals.add(Duals(player1, pool2[poolNumbers[i]]))
            i++
        }
        //pool1.forEach { player1 ->
        //    currentRoundDuals.add(Duals(player1, pool2[poolNumbers[i]]))
        //    i++
        //}
    }

    fun drawGame(){
        //zjebczylo sie i pierdykam ta funkcje
    }
}