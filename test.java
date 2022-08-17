import java.sql.SQLOutput;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



interface SongCache {
    /**
     * Record number of plays for a song.
     */
    void recordSongPlays(String songId, int numPlays);
    /**
     * Fetch the number of plays for a song.
     *
     * @return the number of plays, or -1 if the
    song ID is unknown.
     */
    int getPlaysForSong(String songId);
    /**
     * Return the top N songs played, in descending
     order of number of plays.
     */
    List<String> getTopNSongsPlayed(int n);

//    void check();
}


class SongCacheImpl implements SongCache {

    private static final ExecutorService pool = Executors.newCachedThreadPool();
    private static final Hashtable<String, Integer> table = new Hashtable();
    CountDownLatch latch = new CountDownLatch(4);

    @Override
    public void recordSongPlays(String songId, int numPlays) {
        pool.submit( ()-> {
            try {
                System.out.println(songId + "  " + numPlays + "  " + Thread.currentThread().getName());
                table.put(songId, (table.getOrDefault(songId, 0) + numPlays));

            } catch (Exception e) {

            }
            finally {

                latch.countDown();

            }

        });

    }

    @Override
    public int getPlaysForSong(String songId){

        try {
            Thread.sleep(800);
        }
        catch (Exception e) {

        }

        pool.submit( ()-> {
            System.out.println(songId + "  " + Thread.currentThread().getName());
            try {
                latch.await();
            }
            catch (Exception e) {

            }
        });

        if (table.containsKey(songId))
            return table.get(songId);
        else
            return -1;
    }

    @Override
    public List<String> getTopNSongsPlayed(int n) {
        return table.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).limit(n).map(Map.Entry::getKey).collect(Collectors.toList());
    }

//    @Override
//    public void check() {
//        try {
//            latch.await();
//        }
//        catch (Exception e) {
//
//        }
//
//        for(String a : table.keySet()) {
//            System.out.println(a + "  " + table.get(a));
//        }
//    }
}
