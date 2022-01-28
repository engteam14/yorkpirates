package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class GameObject {
    Texture sprite;
    Animation<Texture> anim;
    Rectangle hitbox;
    float x;
    float y;
    float width;
    float height;
    String team;
    int maxHealth;
    float currentHealth;

    /**
     * Generates a generic object within the game with animated frame(s) and a hitbox.
     * @param frames    The animation frames, or a single sprite.
     * @param fps       The number of frames to be displayed per second.
     * @param team      The team the object is on.
     */
    public GameObject(Array<Texture> frames, float fps, String team){
        this(frames,fps,0,0,team);
    }

    /**
     * Generates a generic object within the game with animated frame(s) and a hitbox.
     * @param frames    The animation frames, or a single sprite.
     * @param fps       The number of frames to be displayed per second.
     * @param x         The x coordinate within the map to initialise the object at.
     * @param y         The y coordinate within the map to initialise the object at.
     * @param team      The team the object is on.
     */
    public GameObject(Array<Texture> frames, float fps, float x, float y, String team){
        this(frames,fps,x,y,frames.get(0).getWidth(),frames.get(0).getHeight(),team);
    }

    /**
     * Generates a generic object within the game with animated frame(s) and a hitbox.
     * @param frames    The animation frames, or a single sprite.
     * @param fps       The number of frames to be displayed per second.
     * @param x         The x coordinate within the map to initialise the object at.
     * @param y         The y coordinate within the map to initialise the object at.
     * @param width     The size of the object in the x-axis.
     * @param height    The size of the object in the y-axis.
     * @param team      The team the object is on.
     */
    GameObject(Array<Texture> frames, float fps, float x, float y, float width, float height, String team){
        changeImage(frames,fps);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setHitbox();
        this.team = team;
    }

    void changeImage(Array<Texture> frames, float fps){
        sprite = frames.get(0);
        anim = new Animation<>(fps==0?0:(1f/fps), frames);
    }

    void setMaxHealth(int mh){
        maxHealth = mh;
        currentHealth = maxHealth;
    }

    /**
     * Called when a projectile hits the object.
     * @param screen            The main game screen.
     * @param damage            The damage dealt by the projectile.
     * @param projectileTeam    The team of the projectile.
     */
    void takeDamage(GameScreen screen, float damage, String projectileTeam){
        currentHealth -= damage;
    }

    /**
     * Moves the object within the x and y-axis of the game world.
     * @param x     The amount to move the object within the x-axis.
     * @param y     The amount to move the object within the y-axis.
     */
    void move(float x, float y){
        this.x += x * Gdx.graphics.getDeltaTime();
        this.y += y * Gdx.graphics.getDeltaTime();
    }

    /**
     * Sets the object's hitbox, based upon it's x, y, width and height values.
     */
    void setHitbox(){
        hitbox = new Rectangle();
        updateHitboxPos();
        hitbox.width = width;
        hitbox.height = height;
    }

    /**
     * Updates the object's hitbox location to match the object's rendered location.
     */
    void updateHitboxPos() {
        hitbox.x = x - width/2;
        hitbox.y = y - height/2;
    }

    /**
     * Checks if this object overlaps with another.
     * @param rect  The other object to be checked against.
     * @return      True if overlapping, false otherwise.
     */
    boolean overlaps(Rectangle rect){
        updateHitboxPos();
        return hitbox.overlaps(rect);
    }

    /**
     * Called when drawing the object.
     * @param batch         The batch to draw the object within.
     * @param elapsedTime   The current time the game has been running for.
     */
    void draw(SpriteBatch batch, float elapsedTime){
        batch.draw(anim.getKeyFrame(elapsedTime, true), x - width/2, y - height/2, width, height);
    }
}
