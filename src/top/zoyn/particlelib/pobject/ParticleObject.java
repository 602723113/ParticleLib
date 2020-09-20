package top.zoyn.particlelib.pobject;

/**
 * 特效对象
 *
 * @author Zoyn
 */
public abstract class ParticleObject {

    public abstract void show();

    public abstract void alwaysShow();

    public abstract void alwaysShowAsync();

    public abstract void turnOffTask();
}
