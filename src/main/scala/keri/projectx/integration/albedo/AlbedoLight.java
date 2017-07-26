/*
 * Copyright (c) 2017 KitsuneAlex & Adam8234. All rights reserved!
 * Do not distribute or redistribute without the explicit permission
 * of the developer!
 */

package keri.projectx.integration.albedo;

import codechicken.lib.colour.Colour;
import codechicken.lib.vec.Vector3;

public class AlbedoLight {

    private Vector3 position;
    private Colour color;
    private float radius;

    public AlbedoLight(Vector3 position, Colour color, float radius){
        this.position = position;
        this.color = color;
        this.radius = radius;
    }

    public void setPosition(Vector3 position){
        this.position = position;
    }

    public void setColor(Colour color){
        this.color = color;
    }

    public void setRadius(float radius){
        this.radius = radius;
    }

    public Vector3 getPosition(){
        return this.position;
    }

    public Colour getColor(){
        return this.color;
    }

    public float getRadius(){
        return this.radius;
    }

}
