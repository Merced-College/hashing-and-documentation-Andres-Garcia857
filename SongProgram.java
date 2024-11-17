/**
* SongProgram.java
* @author Andres Garcia
* @since 11-12-2024
* This class reads from a SongRecord hashmap with and without a GUI
*/

//package hashingAndDocumentation;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SongProgram {

    // HashMap to store SongRecords with the song's ID as the key
    private HashMap<String, SongRecord> songMap;

    // Constructor
    public SongProgram() {
        songMap = new HashMap<>();
    }

    /**
     * Method to load songs from a CSV file
     * Preconditions: Must have an existing CSV file to read
     * Postconditions: Reads in the given CSV file
     * @param filePath Name of file
    */
    public void loadSongsFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            //read in first line and do nothing with it
            br.readLine();
            
            while ((line = br.readLine()) != null) {
            	
            	//System.out.println(line);//for testing
                // Create a SongRecord from the line and add it to the map
                SongRecord song = new SongRecord(line);
                songMap.put(song.getId(), song);
            }
            System.out.println("Songs successfully loaded from CSV.");
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }

    /**
     * Method to retrieve a SongRecord from hashmap with its ID
     * Preconditions: SongRecords must exist within the hashmap
     * Postconditions: The SongRecord object with the given ID is returned
     * @param id The ID of the requested SongRecord
    */
    public SongRecord getSongById(String id) {
        return songMap.get(id);
    }

    /**
     * Method to print all songs (for debugging or display purposes)
     * Preconditions: songMap must have a list of SongRecord objects
     * Postconditions: The name of every song in songMap is printed out
    */
    public void printAllSongs() {
        for (SongRecord song : songMap.values()) {
            System.out.println(song);
        }
    }
    
    /**
     * GUI method to search for a song by ID
     * Preconditions: GUI must be enabled on the device this program runs on
     * Postconditions: The requested song is returned within a GUI
    */
    public void openSearchGui() {
        // Create the main frame
        JFrame frame = new JFrame("Song Lookup");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel to hold input and button
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Label, Text Field, and Button
        JLabel label = new JLabel("Enter Song ID:");
        JTextField idField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        // Add label, text field, and button to panel
        panel.add(label);
        panel.add(idField);
        panel.add(searchButton);

        // Result area to display song details
        JTextArea resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        panel.add(scrollPane);

        // Add action listener for the search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                SongRecord song = getSongById(id);
                if (song != null) {
                    resultArea.setText("Song Found:\n" + song.toString());
                } else {
                    resultArea.setText("Song with ID " + id + " not found.");
                }
            }
        });

        // Add panel to frame
        frame.add(panel);
        frame.setVisible(true);
    }

    /**
     * Main method to demonstrate functionality and open GUI
     * Preconditions: GUI must be enabled on the device this program runs on
     * Postconditions: Uses GUI format to search for SongRecords
    */
    public static void main2(String[] args) {
        SongProgram program = new SongProgram();

        // Load songs from a CSV file
        String filePath = "data.csv";  // replace with actual file path
        program.loadSongsFromCSV(filePath);

        // Open GUI for searching songs by ID
        program.openSearchGui();
    }

    /**
     * Main method to demonstrate functionality
     * Preconditions: Requires CSV file to load songs from
     * Postconditions: Uses ID to find a song in songMap
    */
    public static void main(String[] args) {
        SongProgram program = new SongProgram();

        // Load songs from a CSV file
        String filePath = "data.csv";  // replace with actual file path
        program.loadSongsFromCSV(filePath);

        // Demonstrate retrieving a song by ID
        String testId = "4BJqT0PrAfrxzMOxytFOIz";  // replace with an actual ID from your file
        SongRecord song = program.getSongById(testId);
        if (song != null) {
            System.out.println("Retrieved song: " + song);
        } else {
            System.out.println("Song with ID " + testId + " not found.");
        }

        // Print all songs
        //program.printAllSongs();
    }
}

