package it.unibo.oop.lab.streams;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Stream;

/**
 *
 */
public final class MusicGroupImpl implements MusicGroup {

    private final Map<String, Integer> albums = new HashMap<>();
    private final Set<Song> songs = new HashSet<>();

    @Override
    public void addAlbum(final String albumName, final int year) {
        this.albums.put(albumName, year);
    }

    @Override
    public void addSong(final String songName, final Optional<String> albumName, final double duration) {
        if (albumName.isPresent() && !this.albums.containsKey(albumName.get())) {
            throw new IllegalArgumentException("invalid album name");
        }
        this.songs.add(new MusicGroupImpl.Song(songName, albumName, duration));
    }

    /*Usare la map ogni volta che si vuole applicare una trasformazione agli elementi di uno stream
     * già esistente. Ogni Collection ha un metodo stream().
     */
    @Override
    public Stream<String> orderedSongNames() {
        return this.songs.stream()
            .map(e -> e.getSongName())
            .sorted();
    }

    @Override
    public Stream<String> albumNames() {
        return this.albums.keySet().stream();
    }

    /*Il metodo entrySet delle mappe restituisce un Set di coppie
     * chiave-valore.
     */
    @Override
    public Stream<String> albumInYear(final int year) {
        return this.albums.entrySet()
            .stream()
            .filter(elem -> elem.getValue() == year)
            .map(elem -> elem.getKey());
    }

    /*Il metodo count purtroppo restituisce un long; decido di fare un 
     * cast a int per rispettare l'implementazione del prof.
     */
    @Override
    public int countSongs(final String albumName) {
        int result = 0;
        result += (int) this.songs.stream()
            .filter(elem -> elem.getAlbumName().get().equals(albumName))
            .count();
        return result;
    }

    @Override
    public int countSongsInNoAlbum() {
        return -1;
    }

    @Override
    public OptionalDouble averageDurationOfSongs(final String albumName) {
        return null;
    }

    @Override
    public Optional<String> longestSong() {
        return null;
    }

    @Override
    public Optional<String> longestAlbum() {
        return null;
    }

    private static final class Song {

        private final String songName;
        private final Optional<String> albumName;
        private final double duration;
        private int hash;

        Song(final String name, final Optional<String> album, final double len) {
            super();
            this.songName = name;
            this.albumName = album;
            this.duration = len;
        }

        public String getSongName() {
            return songName;
        }

        public Optional<String> getAlbumName() {
            return albumName;
        }

        public double getDuration() {
            return duration;
        }

        @Override
        public int hashCode() {
            if (hash == 0) {
                hash = songName.hashCode() ^ albumName.hashCode() ^ Double.hashCode(duration);
            }
            return hash;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof Song) {
                final Song other = (Song) obj;
                return albumName.equals(other.albumName) && songName.equals(other.songName)
                        && duration == other.duration;
            }
            return false;
        }

        @Override
        public String toString() {
            return "Song [songName=" + songName + ", albumName=" + albumName + ", duration=" + duration + "]";
        }

    }

}
