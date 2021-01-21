/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegathering.impl;

import java.util.ArrayList;
import java.util.List;
import magicthegathering.game.Card;
import magicthegathering.game.CreatureCard;
import magicthegathering.game.LandCard;
import magicthegathering.game.ManaType;
import magicthegathering.game.Player;

/**
 *
 * @author Jiri Ceska
 */
public class PlayerImpl implements Player {
    private String name;
    private int lives = INIT_LIVES;
    private List<Card> cards;
    
    /**
     * Create new player who can participate in game.
     * @param name Name of player
     */
    public PlayerImpl(String name) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Player name cannot be empty!");
        }
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name + "(" + lives + ")";
    }

    @Override
    public String getName() {
       return name;
    }

    @Override
    public int getLife() {
        return lives;
    }

    @Override
    public void subtractLives(int lives) {
        this.lives -= lives;
    }

    @Override
    public boolean isDead() {
        return lives <= 0;
    }

    @Override
    public void initCards(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    @Override
    public List<Card> getCardsInHand() {
        List<Card> cardsInHand = new ArrayList<>();
        for (Card c : cards) {
            if (!c.isOnTable()) {
                cardsInHand.add(c);
            }
        }
        return cardsInHand;
    }

    @Override
    public List<Card> getCardsOnTable() {
        List<Card> cardsOnTable = new ArrayList<>();
        for (Card c : cards) {
            if (c.isOnTable()) {
                cardsOnTable.add(c);
            }
        }
        return cardsOnTable;
    }

    @Override
    public List<LandCard> getLandsOnTable() {
        List<LandCard> landsOnTable = new ArrayList<>();
        for (Card c : getCardsOnTable()) {
            if (c instanceof LandCard) {
                landsOnTable.add((LandCard) c);
            }
        }
        return landsOnTable;
    }

    @Override
    public List<CreatureCard> getCreaturesOnTable() {
        List<CreatureCard> creaturesOnTable = new ArrayList<>();
        for (Card c : getCardsOnTable()) {
            if (c instanceof CreatureCard) {
                creaturesOnTable.add((CreatureCard) c);
            }
        }
        return creaturesOnTable;
    }

    @Override
    public List<LandCard> getLandsInHand() {
        List<LandCard> landsInHand = new ArrayList<>();
        for (Card c : getCardsInHand()) {
            if (c instanceof LandCard) {
                landsInHand.add((LandCard) c);
            }
        }
        return landsInHand;
    }

    @Override
    public List<CreatureCard> getCreaturesInHand() {
        List<CreatureCard> creaturesInHand = new ArrayList<>();
        for (Card c : getCardsInHand()) {
            if (c instanceof CreatureCard) {
                creaturesInHand.add((CreatureCard) c);
            }
        }
        return creaturesInHand;
    }

    @Override
    public void untapAllCards() {
        for (Card c : getCardsOnTable()) {
            c.untap();
        }
    }

    @Override
    public void prepareAllCreatures() {
        for (CreatureCard c : getCreaturesOnTable()) {
            c.unsetSummoningSickness();
        }
    }

    @Override
    public boolean putLandOnTable(LandCard landCard) {
        if (landCard.isOnTable() || 
                !getLandsInHand().contains(landCard)) {
            return false;
        }
        landCard.putOnTable();
        return true;
    }

    @Override
    public boolean putCreatureOnTable(CreatureCard creatureCard) {
        if (creatureCard.isOnTable() 
                || !getCreaturesInHand().contains(creatureCard) 
                || !hasManaForCreature(creatureCard)) {
            return false;
        }
        tapManaForCreature(creatureCard);
        creatureCard.putOnTable();
        creatureCard.setSummoningSickness();
        return true;
    }

    @Override
    public boolean hasManaForCreature(CreatureCard creature) {
        int[] manaCost = new int[5];
        for (int i = 0; i < 5; ++i) {
            manaCost[i] = creature.getSpecialCost(ManaType.values()[i]);
        }
        int[] untappedLands = calculateUntappedLands();
        
        for (int i = 0; i < 5; ++i) {
            if (manaCost[i] > untappedLands[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int[] calculateUntappedLands() {
        int[] untappedLands = new int[5];
        for (LandCard c : getLandsOnTable()) {
            if (!c.isTapped()) {
                ++untappedLands[c.getManaType().ordinal()];
            }
        }
        return untappedLands;
    }

    @Override
    public void tapManaForCreature(CreatureCard creature) {
        int[] manaCost = new int[5];
        for (int i = 0; i < 5; ++i) {
            manaCost[i] = creature.getSpecialCost(ManaType.values()[i]);
        }
        for (LandCard c : getLandsOnTable()) {
            if (manaCost[c.getManaType().ordinal()] > 0) {
                c.tap();
                --manaCost[c.getManaType().ordinal()];
            }
        }
    }

    @Override
    public void destroyCreature(CreatureCard creature) {
        cards.remove(creature);
    }
}
