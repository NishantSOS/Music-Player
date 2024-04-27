import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import javax.sound.sampled.*;
import java.util.Arrays;

public class Myframe extends JFrame{

    JMenuBar menuBar;
    JMenu songMenu;
    JMenu libraryMenu;
    JMenuItem loadSong;
    JMenuItem loadPlaylist;
    JMenuItem createPlaylist;
    ImageIcon player = new ImageIcon("muSic.png");
    ImageIcon preV = new ImageIcon("Previous.png");
    ImageIcon nexT = new ImageIcon("Next.png");
    ImageIcon plaY = new ImageIcon("Play.png");
    ImageIcon pausE = new ImageIcon("Pause.png");
    JButton play , pause , nextone , previous;
    JSlider Playbackslider = new JSlider(JSlider.HORIZONTAL, 0 , 100,0);
    File[] songs = {new File("song.wav"),new File("Nature.wav")};
    int currentSongIndex = 0;
    Clip clip;

    Myframe() throws LineUnavailableException, IOException, UnsupportedAudioFileException {

        JLabel label = new JLabel();
        //label.setText("LIBRARY");
        label.setVerticalAlignment(JLabel.TOP);
        label.setBounds(10,0,89,89);

        //add song title name
        JLabel songTitle = new JLabel("Song Title");
        songTitle.setHorizontalAlignment(SwingConstants.CENTER);
        songTitle.setFont(new Font("Dialog",Font.BOLD,25));
        songTitle.setBounds(0,314, getWidth()-10 , 30);
        //add song artist name
//        JLabel songArtist = new JLabel("Artist");
//        songTitle.setHorizontalAlignment(SwingConstants.CENTER);
//        songTitle.setFont(new Font("Dialog",Font.BOLD,19));
//        songArtist.setBounds(0, 344, getWidth() - 10, 30);
        //playbackslider
        //Playbackslider.setPreferredSize(new Dimension(150,400));

        //label for the music image
        JLabel imageLabel = new JLabel(player);

        JPanel greypanel = new JPanel();
        greypanel.setBackground(Color.gray);
        greypanel.setBounds(0,0,100,1000);
        //------------
        String[] songNames = {"$$ LIBRARY $$",  "song.wav","nature.wav"};
        Arrays.sort(songNames);

        //------------

        //----
        for (String songName : songNames) {
            JLabel songLabel = new JLabel(songName);
            greypanel.add(songLabel);
        }
        //----

        JPanel imagepanel = new JPanel();
        imagepanel.setBackground(Color.PINK);
        imagepanel.setBounds(100,0,500,500);


        imagepanel.add(songTitle);
        //imagepanel.add(songArtist);

        JPanel workingpanel = new JPanel();
        workingpanel.setBackground(Color.BLACK);
        workingpanel.setBounds(100,500,500,200);
        //workingpanel.setLayout(new BorderLayout());

        //My Buttons
        play = new JButton();
        pause = new JButton();
        nextone = new JButton();
        previous = new JButton();
        previous.setIcon(preV);
        nextone.setIcon(nexT);
        pause.setIcon(pausE);
        play.setIcon(plaY);
        //pause.setVisible(false);

        //play.setBorder(new EmptyBorder(0,0,0,0));

        play.setFocusable(false);
        pause.setFocusable(false);
        nextone.setFocusable(false);
        previous.setFocusable(false);


        JPanel buttonPanel = new JPanel();
        JPanel forPlayback = new JPanel(new BorderLayout());
        //Adding buttons into "workingpanel" =>
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(previous);
        buttonPanel.add(play);
        buttonPanel.add(pause);
        buttonPanel.add(nextone);
        //adding the buttonPanel to top of the workingpanel
        workingpanel.add(buttonPanel,BorderLayout.NORTH);
        //and this adds playbackslider to south of workingpanel
        Playbackslider.setPreferredSize(new Dimension(400, 40));
        Playbackslider.setBackground(Color.black);
        forPlayback.add(Playbackslider,BorderLayout.NORTH);
        workingpanel.add(forPlayback,BorderLayout.CENTER);


        this.add(label);
        this.setTitle("MUSIC_PLAYER");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(600,700);
        this.setLayout(null);

        //This is for panel
        this.add(greypanel);
        greypanel.add(label);
        this.add(imagepanel);
        imagepanel.add(imageLabel);
        this.add(workingpanel);


        //This is for menu bar
        menuBar = new JMenuBar();
        songMenu = new JMenu("Songs");
        libraryMenu = new JMenu("Library");

        //Parts of the main elements of the menu bar
        loadSong = new JMenuItem("Load");
        createPlaylist = new JMenuItem("Create Playlist");
        loadPlaylist = new JMenuItem("Load Playlist");


        songMenu.add(loadSong);
        libraryMenu.add(createPlaylist);
        libraryMenu.add(loadPlaylist);
        menuBar.add(songMenu);
        menuBar.add(libraryMenu);
        this.setJMenuBar(menuBar);
//----------------------------------------------
        //adding music
        try {
            loadSong(songs[currentSongIndex]);

            play.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clip.start();
                    String currentSongName = songs[currentSongIndex].getName(); // Get the name of the currently playing song
                    songTitle.setText(currentSongName); // Update the text of the songTitle label
                }
            });

            nextone.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentSongIndex = (currentSongIndex + 1) % songs.length; // Update current song index
                    loadSong(songs[currentSongIndex]);
                    clip.start();
                    String currentSongName = songs[currentSongIndex].getName(); // Get the name of the currently playing song
                    songTitle.setText(currentSongName); // Update the text of the songTitle label
                }
            });

            previous.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentSongIndex = (currentSongIndex - 1 + songs.length) % songs.length; // Update current song index
                    loadSong(songs[currentSongIndex]);
                    clip.start();
                    String currentSongName = songs[currentSongIndex].getName(); // Get the name of the currently playing song
                    songTitle.setText(currentSongName); // Update the text of the songTitle label
                }
            });

            pause.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clip.stop();
                }
            });

            Playbackslider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    int value = Playbackslider.getValue();
                    long position = (long) ((double) value / 100 * clip.getMicrosecondLength());
                    clip.setMicrosecondPosition(position);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }


//------------------------------------------------
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }

    private void loadSong(File songFile) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(songFile);
            if (clip != null) {
                clip.stop();
                clip.close();
            }
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        }
    }
}