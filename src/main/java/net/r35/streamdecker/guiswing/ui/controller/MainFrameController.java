package net.r35.streamdecker.guiswing.ui.controller;

import net.r35.streamdecker.guiswing.HeroFragment;
import net.r35.streamdecker.guiswing.lib.Hero;
import net.r35.streamdecker.guiswing.lib.HeroBuild;
import net.r35.streamdecker.guiswing.lib.HeroBuildsCollection;
import net.r35.streamdecker.guiswing.lib.StreamDeckerApi;
import net.r35.streamdecker.guiswing.ui.view.MainFrame;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import javax.swing.JButton;
//import javax.swing.JTextArea;

public class MainFrameController {
    private MainFrame mainFrame;
    private StreamDeckerApi api = new StreamDeckerApi();
    private Map<String, Hero> knownHeroes;

    public MainFrameController() {
        knownHeroes = new HashMap<>();

        initComponents();
        initListeners();
        initHeroesList();
    }

    private void initHeroesList() {
        try {
            List<HeroFragment> response = api.getHeroes();

            // Get the hero names from the response
            String[] output = response.stream()
                    .map(p -> p.name)
                    .toArray(size -> new String[response.size()]);

            // Store the hero names and slugs for later uses
            for (HeroFragment hero : response) {
                System.out.println(String.format("[InitHeroesList] Checking name=%s, slug=%s", hero.name, hero.slug));
                if (knownHeroes.containsKey(hero.name)) {
                    System.out.println(String.format("--> KnownHeroes HAS %s", hero.name));
                    continue;
                }

                System.out.println(String.format("--> KnownHeroes does not have %s", hero.name));
                knownHeroes.put(hero.name, new Hero(hero.name, hero.slug));

            }

            System.out.println(String.format("==V==[ KNOWN HEROES ] ==V=="));
            System.out.println(knownHeroes);

            mainFrame.setHeroesPickerOptions(output);

//            System.out.println(response);
        } catch (IOException thrown_exception) {
            System.out.println(">>> Failed to get initial heroes data...");
            System.out.println(thrown_exception.getMessage());
        }
    }

    public void showMainFrameWindow() {
        mainFrame.setVisible(true);
    }

    private void initComponents() {
        mainFrame = new MainFrame();
    }

    private void initListeners() {
        mainFrame.addHeroPickerListener(e -> {
            JComboBox comboBox = (JComboBox)e.getSource();
            String newSelection = (String)comboBox.getSelectedItem();
            System.out.println(String.format("--> [SELECTED-HERO] %s", newSelection));

            Hero hero = knownHeroes.get(newSelection);
            System.out.println(String.format("[Hero] selected=%s | name=%s, slug=%s", newSelection, hero.name, hero.slug));

            if (hero.hasBuilds()) {
                System.out.println("--> HERO HAS BUILDS ALREADY");
                mainFrame.setBuildsPickerOptions(hero.builds.getAllBuildNames());
                return;
            }

            // Pull Build for Hero
            try {
                System.out.println(String.format("--> Pulling builds for %s", newSelection));
                HeroBuildsCollection builds = api.getBuilds(knownHeroes.get(newSelection));

                hero.builds.merge(builds);
                mainFrame.setBuildsPickerOptions(hero.builds.getAllBuildNames());
                System.out.println(builds);
            } catch (IOException e1) {
                System.out.println("--> Failed to pull builds");
                e1.printStackTrace();
            }
        });

        mainFrame.addBuildPicketListener(e -> {
            JList uiList = (JList)e.getSource();
            String newSelection = (String)uiList.getSelectedValue();
            System.out.println(String.format("--> [SELECTED-BUILD] %s", newSelection));

            if (newSelection == null) {
                mainFrame.resetCurrentBuildLines();
                return;
            }

            Hero hero = knownHeroes.get(mainFrame.getCurrentHeroName());
            HeroBuild build = hero.builds.getByName(newSelection);
            mainFrame.setBuildLineLabels(build);

            try {
                api.setAsCurrentBuild(hero, newSelection);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }
}
