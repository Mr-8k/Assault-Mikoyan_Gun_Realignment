package data.scripts.weapons;

import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.combat.listeners.ApplyDamageResultAPI;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;

/**
 * A simple on-hit effect for HE guns like the Howler
 *
 * @author Eliza Weisman
 */
public class DMEGR_HeavyLinearOnHit implements OnHitEffectPlugin {

    // Declare important values as constants so that our
    // code isn't littered with magic numbers. If we want to
    // re-use this effect, we can easily just copy this class
    // and tweak some of these constants to get a similar effect.
    // minimum amount of extra damage
    // I took this from the 'core' color of the Howler projectile.
    // It can be changed
    // private static final Color EXPLOSION_COLOR = new Color(155,225,255,255);
    private static final Color EXPLOSION_COLOR = new Color(125, 175, 255, 255);


    @Override
    public void onHit(DamagingProjectileAPI projectile, CombatEntityAPI target, Vector2f point, boolean shieldHit, ApplyDamageResultAPI damageResult, CombatEngineAPI engine) {

        // check whether or not we want to apply critical damage
        final WeaponAPI weapon = projectile.getWeapon();
        final float EXTRA_DAMAGE = (weapon.getDamage().getDamage()/3.75f);

        if (target instanceof ShipAPI && !shieldHit) {

            engine.applyDamage(target, point,
                    EXTRA_DAMAGE,
                    DamageType.ENERGY,
                    0f,
                    false,
                    false,
                    projectile.getSource());

            // get the target's velocity to render the crit FX
            Vector2f v_target = new Vector2f(target.getVelocity());

            // do visual effects
            engine.spawnExplosion(point, v_target,
                    EXPLOSION_COLOR, // color of the explosion
                    45f, // sets the size of the explosion
                    0.6f // how long the explosion lingers for
            );
        }
    }

}
