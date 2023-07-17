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

        val participant1 = Participant("Participant 1", collectionSet = setOf(1, 2, 3) as HashSet<Int>, leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)
        val participant2 = Participant("Participant 2", collectionSet = setOf(7,10,11) as HashSet<Int>,leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)

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

        val participant1 = Participant("Participant 1", collectionSet = setOf(2, 3) as HashSet<Int>, leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)
        val participant2 = Participant("Participant 2", collectionSet = setOf(10,11) as HashSet<Int>,leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)

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

        val participant1 = Participant("Participant 1", collectionSet = HashSet<Int>(), leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)
        val participant2 = Participant("Participant 2", collectionSet = HashSet<Int>(),leftSelectIndex = 0, rightSelectIndex = 7, selectCount = 0)

        `when`(gameRepository.getParticipant("Participant 1")).thenReturn(participant1)
        `when`(gameRepository.getParticipant("Participant 2")).thenReturn(participant2)
        `when`(gameRepository.getParticipants()).thenReturn(setOf("Participant 1", "Participant 2"))

        // When
        val result = gameService.findParticipantWithSeven()

        // Then
        assertEquals(result, "none")
    }
}
