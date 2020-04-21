import kotlin.random.Random

class Matches(val listofPlayer: ArrayList<Player>, val numberofPlayers: Int) {

    var roundNumber: Int = 1
    var medianRankFIDE: Float = 0f
    var completedDuals = mutableListOf<Duals>()
    var currentRoundDuals = mutableListOf<Duals>()

    init {
        listofPlayer.sortByDescending { it.rankFIDE }
        val middlePlayer1Rank = listofPlayer[numberofPlayers / 2].rankFIDE
        val middlePlayer2Rank = listofPlayer[(numberofPlayers / 2) - 1].rankFIDE

        if (middlePlayer1Rank == middlePlayer2Rank) {
            listofPlayer[(numberofPlayers / 2) - 1].rankFIDE++
        }

        medianRankFIDE = (middlePlayer2Rank + middlePlayer1Rank) / 2f
    }

    fun addPoints(){
        currentRoundDuals.forEach{
            if (listofPlayer.contains(it.winner))
            {
                
            }
        }
    }

    fun round() {
        currentRoundDuals.forEach{
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
        poolNumbers.shuffle()
        var i = 0
        pool1.forEach { player1 ->
            currentRoundDuals.add(Duals(player1, pool2[poolNumbers[i]]))
            i++
        }
    }




}