/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package magicthegathering.impl;

import java.util.Objects;
import magicthegathering.game.AbstractCard;
import magicthegathering.game.LandCard;
import magicthegathering.game.LandCardType;
import magicthegathering.game.ManaType;

/**
 * Implementation of LandCard interface.
 * 
 * @author Jiri Ceska
 */
public class LandCardImpl extends AbstractCard implements LandCard {
    private LandCardType landType;

    /**
     * Creates new land card, in game will be provide mana for summoning creature
     * @param landType 
     */
    public LandCardImpl(LandCardType landType) {
        if (landType == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        this.landType = landType;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Land ");
        sb.append(landType.toString().toLowerCase())
          .append(", ")
          .append(getManaType().toString());
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.landType);
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
        final LandCardImpl other = (LandCardImpl) obj;
        
        return this.landType == other.landType;
    }

    @Override
    public LandCardType getLandType() {
        return landType;
    }

    @Override
    public ManaType getManaType() {
        return ManaType.values()[landType.ordinal()];
    }
}
