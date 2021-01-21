/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegathering.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import magicthegathering.game.CreatureCard;
import magicthegathering.game.Game;
import magicthegathering.game.Generator;
import magicthegathering.game.Player;

/**
 * Implementation of Game interface.
 * Ensure the play of the game, control its action.
 * 
 * @author Jiri Ceska
 */
public class GameImpl implements Game {
    private final Player p1;
    private final Player p2;
    private boolean firstPlayerIsPlaying;
    
    /**
     * Creates new game for provided two players
     * @param p1 First player - he will start
     * @param p2 Second player
     */
    public GameImpl(Player p1, Player p2) {
        if (p1 == null || p2 == null) {
            throw new IllegalArgumentException("Both players must be provided");
        }
        this.p1 = p1;
        this.p2 = p2;
        firstPlayerIsPlaying = true;
    }
    
    @Override
    public void initGame() {
        p1.initCards(Generator.generateCards());
        p2.initCards(Generator.generateCards());
    }

    @Override
    public void changePlayer() {
        firstPlayerIsPlaying = !firstPlayerIsPlaying;
    }

    @Override
    public void prepareCurrentPlayerForTurn() {
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.untapAllCards();
        currentPlayer.prepareAllCreatures();
    }

    @Override
    public Player getCurrentPlayer() {
        return firstPlayerIsPlaying ? p1 : p2;
    }

    @Override
    public Player getSecondPlayer() {
        return firstPlayerIsPlaying ? p2 : p1;
    }

    @Override
    public void performAttack(List<CreatureCard> creatures) {
        if (isCreaturesAttackValid(creatures)) {
            for (CreatureCard c : creatures) {
                c.tap();
            }
        }
    }

    @Override
    public boolean isCreaturesAttackValid(List<CreatureCard> attackingCreatures) {
        if (containsDuplicates(attackingCreatures)) {
            return false;
        }
        for (CreatureCard c : attackingCreatures) {
            if (c.isTapped() || 
                    !c.isOnTable() ||
                    c.hasSummoningSickness() ||
                    !getCurrentPlayer().getCreaturesOnTable().contains(c)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isCreaturesBlockValid(List<CreatureCard> attackingCreatures, List<CreatureCard> blockingCreatures) {
        List<CreatureCard> attackingCreaturesCopy = new ArrayList<>(attackingCreatures);
        List<CreatureCard> blockingCreaturesCopy = new ArrayList<>(blockingCreatures);
        attackingCreaturesCopy.removeAll(Collections.singleton(null));
        blockingCreaturesCopy.removeAll(Collections.singleton(null));
        
        return !containsDuplicates(attackingCreaturesCopy) &&
               !containsDuplicates(blockingCreaturesCopy) &&
               attackingCreatures.size() == blockingCreatures.size() &&
               usedCreatureCardsAreValid(attackingCreaturesCopy, getCurrentPlayer()) &&
               usedCreatureCardsAreValid(blockingCreaturesCopy, getSecondPlayer());
    }

    @Override
    public void performBlockAndDamage(List<CreatureCard> attackingCreatures, List<CreatureCard> blockingCreatures) {
        for (int i = 0; i < attackingCreatures.size(); ++i) {
            CreatureCard attackingCreature = attackingCreatures.get(i);
            CreatureCard blockingCreature = blockingCreatures.get(i);

            if (blockingCreature == null) {
                getSecondPlayer().subtractLives(attackingCreature.getPower());
            } else {
                if (attackingCreature.getPower() >= blockingCreature.getToughness()) {
                    getSecondPlayer().destroyCreature(blockingCreature);
                }
                if (attackingCreature.getToughness() <= blockingCreature.getPower()) {
                    getCurrentPlayer().destroyCreature(attackingCreature);
                }
            }
        }
    }
    
    /**
     * Check if list of CreatureCards contain duplicates
     * @param cards List of CreatureCards to check
     * @return True if list contains duplicates, false otherwise
     */
    private boolean containsDuplicates(List<CreatureCard> cards) {
        Set<CreatureCard> cardsSet = new HashSet<>(cards);

        return cards.size() != cardsSet.size();
    }
    
    /**
     * Check if all cards belong to player who is trying to used them.
     * If the player is blocking, check if no creature is tapped
     * @param cards Cards to check
     * @param p Player who is trying to use this cards to perform attack/block
     * @return True if cards belong to player (and are not tapped in case of blocking), False otherwise
     */
    private boolean usedCreatureCardsAreValid(List<CreatureCard> cards, Player p) {
        for (CreatureCard c : cards) {
            if (!p.getCreaturesOnTable().contains(c) ||
                    (p == getSecondPlayer() && c.isTapped())) {
                return false;
            }
        }
        
        return true;
    }
}
