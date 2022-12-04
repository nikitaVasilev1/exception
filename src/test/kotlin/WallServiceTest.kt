import org.junit.Assert.*
import org.junit.Test

class WallServiceTest {
    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
        val postId = 1
        val comment = Comment(1, 3, 123456, "text")
        WallService.createComment(postId, comment)
    }
    @Test(expected = PostNotFoundException::class)
    fun createComment() {
        val postId = 2
        val comment = Comment(1, 3, 123456, "text")
        WallService.createComment(postId, comment)
    }

}
