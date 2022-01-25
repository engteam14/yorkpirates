package com.engteam14.yorkpirates;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class Projectile extends GameObject{

    private float distanceTravelled;
    private static float maxDistance; // Projectile movement speed.

    private final float dx;
    private final float dy;
    private static final float SPEED = 150f; // Projectile movement speed.
    private static final float DAMAGE = 20f; // Projectile damage.

    /**
     * Generates a projectile object within the game with animated frame(s) and a hitbox.
     * @param frames    The animation frames, or a single sprite.
     * @param fps       The number of frames to be displayed per second.
     * @param start_x   The x coordinate within the map to initialise the object at.
     * @param start_y   The y coordinate within the map to initialise the object at.
     * @param goal_x    The x coordinate within the map the object is moving towards.
     * @param goal_y    The y coordinate within the map the object is moving towards.
     */
    public Projectile(Array<Texture> frames, float fps, float start_x, float start_y, float goal_x, float goal_y, YorkPirates game) {
        super(frames, fps, start_x, start_y,5f,5f);

        float changeInX = goal_x - start_x;
        float changeInY = goal_y - start_y;
        float scaleFactor = max(abs(changeInX),abs(changeInY));
        dx = changeInX / scaleFactor;
        dy = changeInY / scaleFactor;

        distanceTravelled = 0;
        maxDistance = 15000f;
                //max(game.camera.viewportWidth, game.camera.viewportHeight);
    }

    /**
     * Called once per frame. Used to perform calculations such as projectile movement and collision detection.
     * @param screen    The main game screen.
     */
    @Override
    public void update(GameScreen screen, OrthographicCamera camera){
        // Movement Calculations
        move(SPEED*dx, SPEED*dy);
        distanceTravelled += SPEED;

        for(int i = 0; i < screen.colleges.size; i++) {
            if (overlaps(screen.colleges.get(i).hitbox)){
                screen.colleges.get(i).hit(screen,DAMAGE);
                destroy(screen);
            }
        }

        if(distanceTravelled > maxDistance){
            destroy(screen);
        }
    }

    /**
     * Called when the projectile needs to be destroyed.
     * @param screen    The main game screen.
     */
    private void destroy(GameScreen screen){
        screen.projectiles.removeValue(this,true);
    }
}
