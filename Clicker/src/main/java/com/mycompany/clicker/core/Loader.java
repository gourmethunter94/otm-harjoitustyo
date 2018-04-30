/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clicker.core;

import com.mycompany.clicker.domain.Save;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author Olli K. Kärki
 */
public class Loader {

    private Game game;
    private Save save;
    private boolean loaded;
    private boolean simulated;

    public Loader(Game game, Save save) {
        this.game = game;
        this.save = save;
        this.loaded = false;
        this.simulated = false;
    }

    /**
     * Initializes all UI elements that belong to the Game class.
     */
    public void load() {
        if (!loaded) {
            loaded = true;
            game.loading = true;
            Service service = getLoadService();
            service.start();
        }
    }

    private Service getLoadService() {
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return getLoadTask();
            }
        };
        return service;
    }

    private Task<Void> getLoadTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                //Background work                       
                final CountDownLatch latch = new CountDownLatch(1);
                Platform.runLater(getLoadRunnable(latch));
                latch.await();
                //Keep with the background work
                return null;
            }
        };
    }

    private Runnable getLoadRunnable(CountDownLatch latch) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    game.initializeAllUI();
                    game.loading = false;
                } finally {
                    latch.countDown();
                }
            }
        };
    }

    /**
     * Simulates offline time of the game. Should only be called when the
     * program is started.
     */
    public void simulate() {
        long currentTime = System.currentTimeMillis();
        long simulationTime = currentTime - save.getLastPlayTime();
        if (simulationTime > 1000 && !simulated && save.getDamagePerSecond().compareTo(BigInteger.ZERO) > 0) {
            simulated = true;
            game.simulating = true;
            Service service = getSimulateService(simulationTime);
            service.start();

        }

    }

    private Service getSimulateService(long simulationTime) {
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return getSimulateTask(simulationTime);
            }
        };
        return service;
    }

    private Task<Void> getSimulateTask(long simulationTime) {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                //Background work                       
                final CountDownLatch latch = new CountDownLatch(1);
                Platform.runLater(getSimulationRunnable(simulationTime, latch));
                latch.await();
                //Keep with the background work
                return null;
            }
        };
    }

    private Runnable getSimulationRunnable(long simulationTime, CountDownLatch latch) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    simulation(simulationTime);
                    game.simulating = false;
                } catch (SQLException ex) {
                    Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    latch.countDown();
                }
            }
        };
    }

    private void simulation(long time) throws SQLException {

        int stageLimit = save.getStage();
        BigInteger damage = save.getDamagePerSecond();
        BigInteger limit = new BigInteger("30");
        int level = 1;

        for (int s = stageLimit; s > 1; s = s - 1) {
            BigInteger monsterHP = game.monsterHP(s);
            BigInteger seconds = monsterHP.divide(damage);
            if (seconds.compareTo(limit) <= 0) {
                level = s;
                break;
            }
        }

        int kTime = Math.max(1000, game.monsterHP(level).divide(damage).intValue() * 1000);
        int monsterLimit = 10;
        int activeMonster = save.getActiveMonster();
        BigInteger bounty = BigInteger.ZERO;
        int currentMonster = activeMonster;

        while (time >= kTime) { // if time remaining is larger than the time it takes to kill one monster

            if (level < stageLimit) { // if level being simulated is lower then the level limit
                long kills = time / kTime; // how many kills the simulation can do in the time
                time = 0; // set time to 0 to end the simulation
                bounty = bounty.add(game.monsterMoney(level).multiply(new BigInteger(kills + ""))); // add to the bounty gained
                currentMonster = save.getActiveMonster();
            } else {
                int monsters = (monsterLimit + 1) - currentMonster;
                if (monsters * kTime < time) { // can the simulation clear the stage
                    time -= (monsters * kTime); // remove required amount of time from the simulation
                    bounty = bounty.add(game.monsterMoney(level).multiply(new BigInteger(monsters + ""))); // add to the bounty
                    stageLimit++; // increase stageLimit
                    level++; // increase level
                    currentMonster = 1;
                } else {
                    long kills = time / kTime; // how many kills the simulation can do in the time
                    time = 0; // set time to 0 to end simulation
                    bounty = bounty.add(game.monsterMoney(level).multiply(new BigInteger(kills + ""))); // add to the bounty gained
                    currentMonster = (int) kills;
                }
            }

            kTime = Math.max(1000, game.monsterHP(level).divide(damage).intValue() * 1000); // calculate new kill time
            if (kTime > 30000) { // if the new kill time is larger than 30 seconds
                level--; // decrease level
                kTime = Math.max(1000, game.monsterHP(level).divide(damage).intValue() * 1000); // reset the kill time.
            }

        }

        int newLevel = Math.max(level, stageLimit);
        save.setStage(newLevel);
        save.setActiveMonster(currentMonster);
        save.setMoney(save.getMoney().add(bounty));
        game.saveGame(save);

    }

}
