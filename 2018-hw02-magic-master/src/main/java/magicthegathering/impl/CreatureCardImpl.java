/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegathering.impl;

import java.util.List;
import java.util.Objects;
import magicthegathering.game.AbstractCard;
import magicthegathering.game.CreatureCard;
import magicthegathering.game.ManaType;

/**
 * Implementation of CreatureCard interface.
 * 
 * @author Jiri Ceska
 */
public class CreatureCardImpl extends AbstractCard implements CreatureCard {
    private String name;
    private List<ManaType> cost;
    private int power;
    private int toughness;
    private boolean justSummoned = false;

    /**
     * Creates new creature card, player can attack/block with this card
     * @param name Name of creature
     * @param cost List containing enum values - number of each ManaType means card cost of that mana
     * @param power Power of creature
     * @param toughness Toughness of creature
     */
    public CreatureCardImpl(String name, List<ManaType> cost, int power, int toughness) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Creature name cannot be empty!");
        }
        if (cost == null) {
            throw new IllegalArgumentException("Creature cost must be provided!");
        }
        if (power < 0 || toughness <= 0) {
            throw new IllegalArgumentException("Attributes of creature cannot be negative!");
        }
        this.name = name;
        this.cost = List.copyOf(cost);
        this.power = power;
        this.toughness = toughness;
    }
    
    @Override
    public String toString() {
        String description = name + " " + cost + " " + power + " / " + toughness;
        StringBuilder sb = new StringBuilder(description);
        if (!justSummoned) {
            sb.append(" can attack");
        }
        if (isTapped()) {
            sb.append(" TAPPED");
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Objects.hashCode(this.cost);
        hash = 37 * hash + this.power;
        hash = 37 * hash + this.toughness;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CreatureCardImpl other = (CreatureCardImpl) obj;
        
        return this.power == other.power &&
               this.toughness == other.toughness &&
               Objects.equals(this.name, other.name) &&
               Objects.equals(this.cost, other.cost);
    }

    @Override
    public int getTotalCost() {
        return cost.size();
    }

    @Override
    public int getSpecialCost(ManaType mana) {
        int counter = 0;
        for (ManaType m : cost) {
            if (m == mana) {
                ++counter;
            }
        }
        return counter;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPower() {
        return power;
    }

    @Override
    public int getToughness() {
        return toughness;
    }   

    @Override
    public boolean hasSummoningSickness() {
        return justSummoned;
    }

    @Override
    public void setSummoningSickness() {
        justSummoned = true;
    }

    @Override
    public void unsetSummoningSickness() {
        justSummoned = false;
    }
    
}
