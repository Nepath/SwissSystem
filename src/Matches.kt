import kotlin.random.Random

class Matches(val listofPlayer: ArrayList<Player>, val numberofPlayers: Int) {

    var roundNumber: Int = 0
    var medianRankFIDE: Float = 0f
    var completedDuals = mutableListOf<Duals>()
    var pool = ArrayList<ArrayList<Player>>()
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
        currentRoundDuals.forEach {match->
            listofPlayer.forEach{player->
                if(match.winner==null && match.playerIDone == player){
                    player.points+=1
                }
                else if(match.winner==null && match.playerIDtwo == player){
                    player.points+=1
                }
                else if(match.winner==player){
                    player.points+=3
                }
            }
        }
        completedDuals.plusAssign(currentRoundDuals)
        currentRoundDuals.clear()
        listofPlayer.sortBy { it.points }

        listofPlayer.forEach {
            it.hasMatch=false
        }
    }

    fun score() {
        rankList.sortByDescending { it.points }
  //      println("Aktualny ranking: ")
        rankList.forEach {
        //    println("${it.Name} posiada ${it.points} punktow")
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

//        pool1.forEach {
//            println("Pool1: ${it.Name}")
//        }
//        pool2.forEach {
//            println("Pool2: ${it.Name}")
//        }

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

    fun setPools(){
        roundNumber++
        var highestScore = roundNumber*3
        val amountOfLoop = highestScore
        var currentPool= mutableListOf<Player>()
        for(i in 0..amountOfLoop) {
            listofPlayer.forEach {
                if(it.points==highestScore){
                    currentPool.add(it)
                }
            }
            highestScore--
            if(currentPool.isNotEmpty()) {
                pool.add(ArrayList(currentPool))
            }
            currentPool.clear()
        }
        var numberofCurrentPool =0
        pool.forEach { list ->
            if(list.size%2!=0){
                //list.add(pool[numberofCurrentPool+1].first() )
                pool[numberofCurrentPool+1].indexOfFirst {playerToSwitch->
                    list.add(playerToSwitch)
                }
                pool[numberofCurrentPool+1].removeAt(0)
            }
            numberofCurrentPool++
        }

//        var test =1
//        pool.forEach { list ->
//            println("koszyk $test :")
//            list.forEach { player ->
//                println("${player.Name}, punkty: ${player.points}")
//            }
//            test++
//        }

    }


    fun drawGame(){
        setPools()
        var waitList = mutableListOf<Player>()
        var searchForOpponent = true

        pool.forEach { list ->
            var playersInCurrentPool = mutableListOf<Int>()
            for (i in 0 until list.size) {
                playersInCurrentPool.add(i)
            }
            playersInCurrentPool.shuffle()
            for(i in 0 until playersInCurrentPool.size-1){
                var j = i+1
                if(list[playersInCurrentPool[i]].hasMatch==false){
                while (searchForOpponent){
                    searchForOpponent= checkIfMatchHadPlace(list[playersInCurrentPool[i]].id, list[playersInCurrentPool[j]].id )
                    if(searchForOpponent && j==playersInCurrentPool.size-1){
                        waitList.add(list[playersInCurrentPool[i]]) //tutaj uzupelnij wiesz jak
                        searchForOpponent=false
                    }
                    else if(!searchForOpponent){
                        currentRoundDuals.add(Duals(list[playersInCurrentPool[i]], list[playersInCurrentPool[j]] ))
                        list[playersInCurrentPool[i]].hasMatch=true
                        list[playersInCurrentPool[j]].hasMatch=true
                    }
                    j++
                }
                searchForOpponent=true
            }
            }

        }

        currentRoundDuals.forEach {
            println("Aktualny mecz:")
            println("${it.playerIDone.Name} punktów: ${it.playerIDone.points}")
            println("${it.playerIDtwo.Name} punktów: ${it.playerIDtwo.points}")
        }
        sideSelection()
        pool.clear()

    }

    fun setScore(){
        currentRoundDuals.forEach {
            println("Wpisz wynik dla meczu ${it.playerIDone.Name} vs. ${it.playerIDtwo.Name} " )
            println("1- wygrał ${it.playerIDone.Name} // 2- wygrał ${it.playerIDtwo.Name} // 3- remis" )
            val input = readLine()!!.toInt()
            when(input){
                1-> it.winner = it.playerIDone
                2-> it.winner = it.playerIDtwo
            }
        }
    }

    fun checkIfMatchHadPlace(playerOneId:Int, playerTwoId:Int):Boolean{
        var hadPlace=false
        completedDuals.forEach{completedDual ->
            if(!hadPlace) {
                hadPlace =
                    (completedDual.playerIDone.id == playerOneId && completedDual.playerIDtwo.id == playerTwoId) || (completedDual.playerIDtwo.id == playerOneId && completedDual.playerIDone.id == playerTwoId)
            }
            }
        return hadPlace
    }

    fun sideSelection(){
        // po ktorej stronie gra... nalezy dac bialego na lewo i dodac mu liczbe bialych. temu po prawej liczbe po czarnych
    }

}