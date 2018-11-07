package net.r35.streamdecker.guiswing.ui.view;

import net.r35.streamdecker.guiswing.HeroFragment;
import net.r35.streamdecker.guiswing.lib.HeroBuild;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainFrame extends JFrame {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private JPanel mainPanel;
    private JComboBox heroPicker;
    private JList buildPicker;
    private JLabel topTalentLabel;
    private JLabel middleTalentLabel;
    private JLabel bottomTalentLabel;

    public MainFrame() {
        setSize(WIDTH, HEIGHT);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);

    }

    public JComboBox getHeroPicker() {
        return heroPicker;
    }

    public String getCurrentHeroName() {
        return (String) heroPicker.getSelectedItem();
    }

    public JList getBuildPicker() {
        return buildPicker;
    }

    public JLabel getTopTalentLabel() {
        return topTalentLabel;
    }

    public JLabel getMiddleTalentLabel() {
        return middleTalentLabel;
    }

    public JLabel getBottomTalentLabel() {
        return bottomTalentLabel;
    }

    public void setHeroesPickerOptions(String[] heroNames) {
        heroPicker.removeAllItems();
        for (String name : heroNames) {
            heroPicker.addItem(name);
        }
    }

    public void resetBuildPickerOptions() {
        buildPicker.removeAll();
    }

    public void setBuildsPickerOptions(Set<String> buildNames) {
        resetBuildPickerOptions();
        buildPicker.setListData(buildNames.toArray());
    }

    public void setBuildLineLabels(HeroBuild build) {
        topTalentLabel.setText(build.getTopLineString());
        middleTalentLabel.setText(build.getMiddleLineString());
        bottomTalentLabel.setText(build.getBottomLineString());
    }

    public void addHeroPickerListener(ActionListener listener) {
        heroPicker.addActionListener(listener);
    }

    public void addBuildPicketListener(ListSelectionListener listener) {
        buildPicker.addListSelectionListener(listener);
    }
}
