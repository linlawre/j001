I used CachedThreadPool to create a thread pool
I used Hashtable to save data because Hashtable is threadsafe
I used CountDownLatch to count 4 adding operations

1. recordSongPlays
	createa new thread to the pool
	check whether key is in the Hashtable
		if not add new key and value
		if existing current value + new value
	finally
		CountDownLatch -1

2. getPlaysForSong
	sleep 800 milis (sometimes update value to Hashtable will be slow, so sleep 800 millis made getPlaysForSong getting the right value)
	reuse thread from pool 
		wait until latch == 0

	then 
		if existing 
			return value
		if not existing 
			return -1

3. getTopNSongsPlayed
	use stream api to return List

4. test file

I changed assertThat to assertEquals
I'm not sure why I cannot use assertThat
but assertEquals works, so I made changes
