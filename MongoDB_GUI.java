package edu.umn.d.cs4322;

import com.mongodb.client.*;
import org.bson.Document;
import static com.mongodb.client.model.Filters.*;

import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class QuickStart extends javax.swing.JFrame {

    public QuickStart() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMovies = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtActor = new javax.swing.JTextField();
        btnViewPlot = new javax.swing.JButton();
        lblExplanation = new javax.swing.JLabel();


        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Enter Actor Name:");

        btnSearch.setText("Search Movies");
        btnSearch.addActionListener(evt -> searchMovies());

        tblMovies.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {}, new String[] { "Title" }
        ));
        jScrollPane1.setViewportView(tblMovies);

        btnViewPlot.setText("View Plot");
        btnViewPlot.addActionListener(evt -> showPlot());

        lblExplanation.setText("<html>This app lets you search for movies from a given actor.<br>After searching, select a movie from the list to view its plot.</html>");
        lblExplanation.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblExplanation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtActor))
                                        .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnViewPlot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblExplanation)
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(txtActor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSearch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnViewPlot)
                                .addContainerGap())
        );

        pack();
    }

    private void searchMovies() {
        String actor = txtActor.getText().trim();
        if (actor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an actor's name.");
            return;
        }

        try (MongoClient mongoClient = MongoClients.create(MONGO_URI)) {
            MongoDatabase database = mongoClient.getDatabase("mongodb-mtext");
            MongoCollection<Document> collection = database.getCollection("movies");

            List<Document> results = collection.find(eq("cast", actor)).into(new ArrayList<>());
            movieList = results;

            String[] columnNames = { "Title" };
            String[][] data = results.stream()
                    .map(doc -> new String[]{ doc.getString("title") })
                    .toArray(String[][]::new);

            DefaultTableModel model = new DefaultTableModel(data, columnNames) {
                public boolean isCellEditable(int row, int col) { return false; }
            };

            tblMovies.setModel(model);

            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No movies found for actor: " + actor);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to connect to MongoDB: " + e.getMessage());
        }
    }

    private void showPlot() {
        int selectedRow = tblMovies.getSelectedRow();
        if (selectedRow == -1 || selectedRow >= movieList.size()) {
            JOptionPane.showMessageDialog(this, "Please select a movie to view its plot.");
            return;
        }

        Document movie = movieList.get(selectedRow);
        String plot = movie.getString("plot");
        JOptionPane.showMessageDialog(this, (plot != null && !plot.isEmpty()) ? plot : "No plot available.");
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new QuickStart().setVisible(true));
    }

    private final String MONGO_URI = "mongodb://gode0028:fnrbdplp@localhost:63334/?authSource=mongodb-mtext";

    private List<Document> movieList = new ArrayList<>();
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnViewPlot;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblMovies;
    private javax.swing.JTextField txtActor;
    private javax.swing.JLabel lblExplanation;

}

