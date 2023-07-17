import com.example.luckycardgame.R
import com.example.luckycardgame.model.Card
import com.example.luckycardgame.model.CardType
import com.example.luckycardgame.model.LuckyGame
import com.example.luckycardgame.model.Participant
import com.example.luckycardgame.repository.GameRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LuckyGameTest {
    private lateinit var game: LuckyGame

    @Mock
    private lateinit var cardRepository: GameRepository

    /***
     * 테스트 클래스에서 game 참가자를 따로 설정 해주지 않으면 최소 인원 수를 3명으로 설정
     */
    @Before
    @Test
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Mockito.`when`(cardRepository.getAllCards(false)).thenReturn(getMockCards())

        // 가짜 객체 생성
        val mockParticipant = Participant("Participant 0",
            mutableListOf(
                Card(CardType.Dog, 1, 1, false),
                Card(CardType.Dog, 1, 1, false),
                Card(CardType.Dog, 1, 1, false),
                Card(CardType.Dog, 2, 1, false),
                Card(CardType.Dog, 2, 1, false),
                Card(CardType.Dog, 2, 1, false),
                Card(CardType.Dog, 7, 1, false),
                Card(CardType.Dog, 8, 1, false),
            ),
            hashSetOf(1,2,3,4,5,6,7,8),
            0,7,0)

        // 해당 함수를 불렀을 떄 가짜 객체를 반환 하도록 설정
        Mockito.`when`(cardRepository.getParticipant("Participant 0")).thenReturn(mockParticipant)

        game = LuckyGame(3)
    }

    /***
     * 게임 참가자가 3명, 4명, 5명일때 카드를 올바르게 나누어 지는지 테스트
     */

    @Test
    fun game_cardDistribution_initialDataState_expectedResult() {
        // 참가자가 3명, 4명, 5명일 때 전부 테스트
        val participantCounts = listOf(3, 4, 5)
        val expectedCardCounts = listOf(8, 7, 6)

        participantCounts.forEachIndexed { index, count ->
            game = LuckyGame(count)
            assertEquals(count, game.getParticipants().size)

            game.getParticipants().forEach { participant ->
                val cards = game.getParticipantCards(participant)
                assertNotNull(cards)
                assertEquals(expectedCardCounts[index], cards?.size)
            }
        }
    }

    /***
     * 카드를 생성하고 1번부터 12번까지 3장씩 올바르게 생성되었는지 검증하는 테스트
     */

    @Test
    fun game_card_distribution_checkTripleCardsByType() {
        val participantCounts = listOf(3, 4, 5)
        val expectedCardCounts = listOf(8, 7, 6)

        participantCounts.forEachIndexed { index, participantCount ->
            // 1번부터 12번까지 각 카드를 3장씩 가진 리스트 생성
            val cards = mutableListOf<Card>()
            repeat(3) {
                for (i in 1..12) {
                    cards.add(
                        Card(
                            listOf(CardType.Cat, CardType.Cow, CardType.Dog).random(),
                            i,
                            1,
                            false
                        )
                    )
                }
            }

            // 참가자 수가 3명일 경우 12번 카드 1장 제외
            if (participantCount == 3) {
                cards.removeIf { it.number == 12 }
            }

            // 카드를 섞음
            cards.shuffle()

            // Act
            game = LuckyGame(participantCount)

            // Assert
            game.getParticipants().forEach { participant ->
                val participantCards = game.getParticipantCards(participant)
                assertNotNull(participantCards)
                assertEquals(expectedCardCounts[index], participantCards?.size)

                val cardCounts = mutableMapOf<Int, Int>()
                participantCards?.forEach { card ->
                    cardCounts[card.number] = cardCounts.getOrDefault(card.number, 0) + 1
                }

                // 각 번호별 카드 수가 3장을 초과하지 않는지 확인
                for (i in 1..12) {
                    if (i == 12 && participantCount == 3) {
                        break
                    } else {
                        assertTrue(cardCounts.getOrDefault(i, 0) <= 3)
                    }
                }
            }
        }
    }

    /***
     *  게임 참가자 수가 3명일 때(테스트 클래스의 Default 참가자수) 참가자 카드 리스트가 오름차순 정렬이 잘 되는지 확인
     */
    @Test
    fun game_sortParticipantCards_checkAscendingSorting() {
        val participants = listOf("Participant 0", "Participant 1", "Participant 2")

        participants.forEach { participant ->
            assertNotNull(game.getParticipantCards(participant))

            // Act
            game.service.sortParticipantCards(participant, false)

            // Assert
            val sortedCards = game.getParticipantCards(participant)
            assertNotNull(sortedCards)

            for (i in 0 until sortedCards!!.size - 1) {
                assertTrue(sortedCards[i].number <= sortedCards[i + 1].number)
            }
        }
    }

    /***
     *  참가자 카드 리스트가 오름차순 정렬이 잘 되는지 확인
     */
    @Test
    fun game_sortParticipantCard_checkAscendingSorting() {

        val participantCard = game.getParticipantCards("Participant 0")

        game.service.participantsCardPrint(participantCard as MutableList<Card>)

        game.service.sortParticipantCards("Participant 0", false)
        val sortedCards = game.getParticipantCards("Participant 0")

        println()
        assertNotNull(sortedCards)
        game.service.participantsCardPrint(sortedCards as MutableList<Card>)

        for (i in 0 until sortedCards!!.size - 1) {
            assertTrue(sortedCards[i].number <= sortedCards[i + 1].number)
        }


    }

    /***
     *  게임 참가자 수가 3명일 때(테스트 클래스의 Default 참가자수) 참가자 카드 리스트가 내림차순 정렬이 잘 되는지 확인
     */
    @Test
    fun game_sortParticipantCards_checkDescendingSorting() {
        val participants = listOf("Participant 0", "Participant 1", "Participant 2")

        participants.forEach { participant ->
            assertNotNull(game.getParticipantCards(participant))

            // Act
            game.service.sortParticipantCards(participant, reverse = true)

            // Assert
            val sortedCards = game.getParticipantCards(participant)
            assertNotNull(sortedCards)

            for (i in 0 until sortedCards!!.size - 1) {
                assertTrue(sortedCards[i].number >= sortedCards[i + 1].number)
            }
        }
    }

    /**
     *  게임 참가자 수가 3명일 때(테스트 클래스의 Default 참가자수) 바닥 카드 리스트가 오름차순 정렬이 잘 되는지 확인
     */
    @Test
    fun game_sortFloorCards_checkAscendingSorting() {
        // Act
        game.service.sortFloorCards(false)

        // Assert
        val sortedCards = game.getFloorCards()
        assertNotNull(sortedCards)

        for (i in 0 until sortedCards!!.size - 1) {
            assertTrue(sortedCards[i].number <= sortedCards[i + 1].number)
        }
    }


    /**
     *  게임 참가자 카드 중에서 세 장이 동일한 숫자인 카드가 존재하는지 확인하는 테스트입니다.
     *  카드를 랜덤으로 나누어 주기 때문에 결과는 항상 다름
     */
    @Test
    fun game_isTripleMatchPresent_checkTripleMatch() {
        val isTriplePresent = game.service.isTripleMatchPresent()
        assertTrue(isTriplePresent)
    }

    /**
     * 특정 참가자와 해당 참가자 카드 중에 가장 낮은 숫자 또는 가장 높은 숫자,
     *  바닥 카드 중 아무거나를 선택해서 3개가 같은지 판단할 수 있어야 한다.
     *  카드의 숫자는 랜덤으로 배분하기 때문에 결과는 항상 다름
     */
    @Test
    fun game_HasTripleMatchWithFloorCard_GivenParticipantsWithMatchingCards_ReturnsTrue() {
        // 테스트에 필요한 데이터 설정
        val participant1 = "Participant 0"
        val participant2 = "Participant 1"

        // 테스트 시작
        val hasTripleMatch = game.service.hasTripleMatchWithFloorCard(participant1, participant2)

        // 결과 검증
        assertTrue(hasTripleMatch)
    }

    /**
     * 참가자들의 모든 턴이 끝나면 참가자들이 선택한 카드 중에서 같은 번호를 추출하는 메소드가 올바르게 동작하는지
     * 검증하는 메소드
     */
    @Test
    fun game_getTripleSelectedCard_ReturnTrue(){
        val participant = cardRepository.getParticipant("Participant 0")

        game.getParticipantsTripleSelectedCardNum()

//        val tripleCardSet = cardRepository.getTripleSelectedCards()
//
//        tripleCardSet["Participant 0"]?.let { assertEquals(1, it[0]) }
//        tripleCardSet["Participant 0"]?.let { assertEquals(2, it[0]) }
    }


    private fun getMockCards(): MutableList<Card> {
        val cards = mutableListOf<Card>()

        for (number in 1..12) {
            repeat(3) {
                cards.add(Card(listOf(CardType.Dog, CardType.Cat, CardType.Cow).random(), number, R.drawable.logo, false))
            }
        }
        return cards.shuffled().toMutableList()
    }



}
