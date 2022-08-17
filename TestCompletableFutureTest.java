import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TestCompletableFutureTest {



    @Test
    void testCacheIsWorking() {
        SongCache cache = new SongCacheImpl();

        cache.recordSongPlays("ID-1", 3);
        cache.recordSongPlays("ID-1", 1);
        cache.recordSongPlays("ID-2", 2);
        cache.recordSongPlays("ID-3", 5);


//        try
//        {
//            Thread.sleep(1000);
//        }
//        catch(InterruptedException ex)
//        {
//            Thread.currentThread().interrupt();
//        }

        assertEquals(cache.getPlaysForSong("ID-1"), 4);
        assertEquals(cache.getPlaysForSong("ID-9"), -1);

        assertEquals(cache.getTopNSongsPlayed(2), Arrays.asList("ID-3", "ID-1"));
        assertEquals(cache.getTopNSongsPlayed(0), Arrays.asList());

//        cache.check();


    }
}