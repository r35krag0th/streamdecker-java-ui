package net.r35.streamdecker.guiswing.ui.view;

import net.r35.streamdecker.guiswing.HeroFragment;
import net.r35.streamdecker.guiswing.lib.HeroBuild;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class MainFrame extends JFrame {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private JPanel mainPanel;
    private JComboBox heroPicker;
    private JList buildPicker;
    private JLabel topTalentLabel;
    private JLabel middleTalentLabel;
    private JLabel bottomTalentLabel;

    private DefaultListModel buildPickerModel;

    public MainFrame() {
        setSize(WIDTH, HEIGHT);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);

        buildPickerModel = new DefaultListModel();

        buildPicker.setModel(buildPickerModel);
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
        System.out.println("*** Resetting BuildPicker Options");
//        DefaultListModel listModel = (DefaultListModel) buildPicker.getModel();
//        buildPickerModel.removeAllElements();
//        buildPicker.setModel(new DefaultListModel());
//        buildPicker.getModel().clear();
    }

    public void resetCurrentBuildLines() {
        topTalentLabel.setText("");
        middleTalentLabel.setText("");
        bottomTalentLabel.setText("");
    }

    public void setBuildsPickerOptions(Set<String> buildNames) {
        resetBuildPickerOptions();
        buildPicker.setListData(buildNames.toArray());
//        buildPickerModel.a
//        buildPickerModel.addAll(Collections.unmodifiableSet(buildNames));
//        for (String name : buildNames) {
//            buildPickerModel.addElement(name);
//        }
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
