package top.zoyn.particlelib.pobject;

/**
 * 特效对象
 *
 * @author Zoyn
 */
public abstract class ParticleObject {

    private ShowType showType = ShowType.NONE;

    public abstract void show();

    public abstract void alwaysShow();

    public abstract void alwaysShowAsync();

    public abstract void turnOffTask();

    public ShowType getShowType() {
        return showType;
    }

    public void setShowType(ShowType showType) {
        this.showType = showType;
    }

}
