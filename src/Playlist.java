import java.util.ArrayList;
import java.io.File;
public class Playlist {
    private String name;
    private ArrayList<File> songs;

    public Playlist(String name){
        this.name = name;
        this.songs = new ArrayList<>();
    }
    public void addSong(File song){
        songs.add(song); // append the the song list as it add songs to playlist
    }
    public ArrayList<File> getSongs(){
        return songs;
    }

}
