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

    fun round() {
        if (roundNumber == 1) {

        }
    }

    fun drawGame1() {
        val pool1 = mutableListOf<Player>()
        val pool2 = mutableListOf<Player>()
        val poolNumbers = mutableListOf<Int>()

        listofPlayer.forEach {
            if (it.rankFIDE < medianRankFIDE) {
                pool2.add(it)
            } else {
                pool1.add(it)
            }
        }
        for (i in 0 until pool1.size) {
            poolNumbers.add(i)
        }
        poolNumbers.shuffle()
        var i = 0
        pool1.forEach { player1 ->
                completedDuals.add(Duals(player1, pool2[poolNumbers[i]]))

            i++
        }
        completedDuals.forEach {
            println(it.playerIDone.Name + it.playerIDtwo.Name)
        }
    }

}