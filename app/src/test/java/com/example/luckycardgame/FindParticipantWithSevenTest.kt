import com.example.luckycardgame.model.Card
import com.example.luckycardgame.model.CardType
import com.example.luckycardgame.model.Participant
import com.example.luckycardgame.repository.GameRepository
import com.example.luckycardgame.service.GameServiceImpl
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class FindParticipantWithSevenTest {
    private lateinit var gameService: GameServiceImpl
    private lateinit var gameRepository: GameRepository

    @Before
    @Test
    fun setup() {
        gameRepository = mock(GameRepository::class.java)
        gameService = GameServiceImpl(gameRepository, 2)
    }


    @Test
    fun findParticipantWithSeven_whenSevenInCollectionSet_shouldReturnParticipantId(){
        // Given
        val gameRepository = mock(GameRepository::class.java)
        val gameService = GameServiceImpl(gameRepository, 2)

        val card1 = Card(CardType.Dog, 1, 1 , false)
        val card2 = Card(CardType.Cat, 1, 1,false)
        val card3 = Card(CardType.Cow, 1, 1, false)
        val card4 = Card(CardType.Dog, 7, 1 , false)
        val card5 = Card(CardType.Cat, 7, 1,false)
        val card6 = Card(CardType.Cow, 7, 1, false)

        val participant1 = Participant("Participant 1", collectionSet = arrayListOf(card1, card2, card3), leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)
        val participant2 = Participant("Participant 2", collectionSet = arrayListOf(card4,card5, card6) ,leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)

        `when`(gameRepository.getParticipant("Participant 1")).thenReturn(participant1)
        `when`(gameRepository.getParticipant("Participant 2")).thenReturn(participant2)
        `when`(gameRepository.getParticipants()).thenReturn(setOf("Participant 1", "Participant 2"))

        // When
        val result = gameService.findParticipantWithSeven()

        // Then
        assertEquals(result, "Participant 2")
    }

    @Test
    fun findParticipantWithSeven_whenNoSevenInCollectionSet_shouldReturnNone(){
        // Given
        val gameRepository = mock(GameRepository::class.java)
        val gameService = GameServiceImpl(gameRepository, 2)

        val card1 = Card(CardType.Dog, 1, 1 , false)
        val card2 = Card(CardType.Cat, 1, 1,false)
        val card3 = Card(CardType.Cow, 1, 1, false)
        val card4 = Card(CardType.Dog, 5, 1 , false)
        val card5 = Card(CardType.Cat, 5, 1,false)
        val card6 = Card(CardType.Cow, 5, 1, false)

        val participant1 = Participant("Participant 1", collectionSet = arrayListOf(card1, card2, card3), leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)
        val participant2 = Participant("Participant 2", collectionSet = arrayListOf(card4,card5, card6) ,leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)

        `when`(gameRepository.getParticipant("Participant 1")).thenReturn(participant1)
        `when`(gameRepository.getParticipant("Participant 2")).thenReturn(participant2)
        `when`(gameRepository.getParticipants()).thenReturn(setOf("Participant 1", "Participant 2"))

        // When
        val result = gameService.findParticipantWithSeven()

        // Then
        assertEquals(result, "none")
    }

    @Test
    fun findParticipantWithSeven_whenCollectionisEmpty_shouldReturnNone(){
        // Given
        val gameRepository = mock(GameRepository::class.java)
        val gameService = GameServiceImpl(gameRepository, 2)

        val participant1 = Participant("Participant 1", collectionSet = arrayListOf(), leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)
        val participant2 = Participant("Participant 2", collectionSet = arrayListOf(),leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)

        `when`(gameRepository.getParticipant("Participant 1")).thenReturn(participant1)
        `when`(gameRepository.getParticipant("Participant 2")).thenReturn(participant2)
        `when`(gameRepository.getParticipants()).thenReturn(setOf("Participant 1", "Participant 2"))

        // When
        val result = gameService.findParticipantWithSeven()

        // Then
        assertEquals(result, "none")
    }
}
