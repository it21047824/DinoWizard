package com.redstream.dinowizard.helpers;

import com.google.ar.sceneform.AnchorNode;

public class Item {

    public enum Type {
        REX,
        RAPTOR,
        DRAGONFLY
    }
    private AnchorNode anchorNode;
    private Type dinoType;

    public AnchorNode getAnchorNode() {
        return anchorNode;
    }

    public Type getDinoType() {
        return dinoType;
    }

    public Item(AnchorNode anchorNode, Type dinoType) {
        this.anchorNode = anchorNode;
        this.dinoType = dinoType;
    }


}
