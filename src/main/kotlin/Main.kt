data class Post(
    val id: Int,
    val ownerId: Int,
    val fromId: Int,
    val createdBy: Int,
    val like: Int?,
    val text: String,
    val replyOwnerId: Int,
    val replyPostId: Int?,
    val friendsOnly: Boolean = true,
    val isPinned: Boolean = true,
    val date: Long,
    val attachments: Array<Attachment> = emptyArray()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Post

        if (id != other.id) return false
        if (ownerId != other.ownerId) return false
        if (fromId != other.fromId) return false
        if (createdBy != other.createdBy) return false
        if (like != other.like) return false
        if (text != other.text) return false
        if (replyOwnerId != other.replyOwnerId) return false
        if (replyPostId != other.replyPostId) return false
        if (friendsOnly != other.friendsOnly) return false
        if (isPinned != other.isPinned) return false
        if (date != other.date) return false
        if (!attachments.contentEquals(other.attachments)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + ownerId
        result = 31 * result + fromId
        result = 31 * result + createdBy
        result = 31 * result + (like ?: 0)
        result = 31 * result + text.hashCode()
        result = 31 * result + replyOwnerId
        result = 31 * result + (replyPostId ?: 0)
        result = 31 * result + friendsOnly.hashCode()
        result = 31 * result + isPinned.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + attachments.contentHashCode()
        return result
    }
}

interface Attachment {
    val id: Int
    val ownerId: Int
    val title: String
    val description: String
    val duration: String
    val tipe: String
}

open class VideoAttachment(
    override val id: Int = 1,
    override val ownerId: Int = 1,
    override val title: String = "title",
    override val description: String = "description",
    override val duration: String = "duration",
    override val tipe: String = "Видео"
) : Attachment {
}

open class AudioAttachment(
    override val id: Int = 1,
    override val ownerId: Int = 1,
    override val title: String = "title",
    override val description: String = "description",
    override val duration: String = "duration",
    override val tipe: String = "Аудио",
) : Attachment {}

data class Video(
    override val id: Int = 1,
    override val ownerId: Int = 1,
    override val title: String = "title",
    override val description: String = "description",
    override val duration: String = "duration",
    override val tipe: String = "Видео"
) : VideoAttachment() {}

data class Audio(
    override val id: Int = 1,
    override val ownerId: Int = 1,
    override val title: String = "title",
    override val description: String = "description",
    override val duration: String = "duration",
    override val tipe: String = "Аудио"
) : AudioAttachment() {}

data class Likes(
    val count: Int,
    val userLikes: Boolean,
    val canLike: Boolean,
    val canPublish: Boolean
) {}

class PostNotFoundException : RuntimeException() {}

data class Comment(
    val id: Int,
    val fromId: Int,
    val date: Long,
    val text: String
) {}

object WallService {
    private var posts = emptyArray<Post>()
    private var nextId = 1
    private var videos = emptyArray<Video>()
    private var audios = emptyArray<Audio>()
    private var comment = emptyArray<Comment>()

    fun add(post: Post): Post {
        posts += post.copy(id = nextId++)
        return posts.last()
    }

    fun addVideo(video: Video): Video {
        videos += video.copy(id = nextId++)
        return videos.last()
    }

    fun addAudio(audio: Audio): Audio {
        audios += audio.copy(id = nextId++)
        return audios.last()
    }

    fun update(newPost: Post): Boolean {
        for ((index, post) in posts.withIndex()) {
            if (post.id == newPost.id) {
                posts[index] = newPost.copy(
                    ownerId = post.ownerId,
                    date = post.date
                )
                return true
            }
        }
        return false
    }

    fun createComment(postId: Int, newComment: Comment)  {

        try {
            if (postId == newComment.id) {
                comment += newComment.copy(id = nextId++)
            }
            println("Пост найден")
        } catch (e: PostNotFoundException) {
            println("Пост не найден")
        }
    }
}

fun main(args: Array<String>) {
    var videos = Video(1, 2, "title", "description", "duration")
    WallService.addVideo(videos)
    var audios = Audio(1, 2, "title", "description", "duration")
    WallService.addAudio(audios)
    var post = Post(3, 15, 10, 12, 10, "hello", 1, 1, date = 16688636)
    var posts = Post(4, 15, 10, 12, 10, "hello", 1, 1, date = 116688637)
    var comment = Comment(2, 3, 123456, "text")
    WallService.createComment(2, comment)
    println(WallService.add(posts))
    println(WallService.update(posts))
}
