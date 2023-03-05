package top.zoyn.particlelib.pobject;

import com.google.common.collect.Lists;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import top.zoyn.particlelib.utils.matrix.Matrix;
import top.zoyn.particlelib.utils.matrix.Matrixs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 代表一个特效组
 * <p>
 * 如果你要使用 EffectGroup#scale 这样的方法, 我不建议你将 2D 的特效和 3D 的特效放在一起
 * <p>
 * EffectGroup 的矩阵默认使用 3行3列 的矩阵进行变换
 *
 * @author Zoyn IceCold
 */
public class EffectGroup {

    /**
     * 特效表
     */
    private final List<ParticleObject> effectList;

    public EffectGroup() {
        effectList = Lists.newArrayList();
    }

    public EffectGroup(ParticleObject... effects) {
        effectList = Lists.newArrayList(effects);
    }

    /**
     * 利用给定的特效列表构造出一个特效组
     *
     * @param effectList 特效列表
     */
    public EffectGroup(List<ParticleObject> effectList) {
        this.effectList = effectList;
    }

    /**
     * 往特效组添加一项特效
     *
     * @param particleObj 特效对象
     * @return {@link EffectGroup}
     */
    public EffectGroup addEffect(ParticleObject particleObj) {
        effectList.add(particleObj);
        return this;
    }

    /**
     * 往特效组添加一堆特效
     *
     * @param particleObj 一堆特效对象
     * @return {@link EffectGroup}
     */
    public EffectGroup addEffect(ParticleObject... particleObj) {
        effectList.addAll(Arrays.asList(particleObj));
        return this;
    }

    public List<Location> calculateLocations() {
        return effectList.stream()
                .flatMap(p -> p.calculateLocations().stream())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * 利用给定的下标, 将特效组里的第 index-1 个特效进行删除
     *
     * @param index 下标
     * @return {@link EffectGroup}
     */
    public EffectGroup removeEffect(int index) {
        effectList.remove(index);
        return this;
    }

    /**
     * 将特效组内的特效进行缩小或扩大
     *
     * @param value 缩小或扩大的倍率
     * @return {@link EffectGroup}
     */
    public EffectGroup scale(double value) {
        effectList.forEach(effect -> effect.addMatrix(Matrixs.scale(3, 3, value)));
        return this;
    }

    /**
     * 将特效组内的特效绕 X 轴进行旋转
     *
     * @param angle 旋转角度
     * @return {@link EffectGroup}
     */
    public EffectGroup rotateAroundXAxis(double angle) {
        effectList.forEach(effect -> effect.addMatrix(Matrixs.rotateAroundXAxis(angle)));
        return this;
    }

    /**
     * 将特效组内的特效绕 Y 轴进行旋转
     *
     * @param angle 旋转角度
     * @return {@link EffectGroup}
     */
    public EffectGroup rotateAroundYAxis(double angle) {
        effectList.forEach(effect -> effect.addMatrix(Matrixs.rotateAroundYAxis(angle)));
        return this;
    }

    /**
     * 将特效组内的特效绕 Z 轴进行旋转
     *
     * @param angle 旋转角度
     * @return {@link EffectGroup}
     */
    public EffectGroup rotateAroundZAxis(double angle) {
        effectList.forEach(effect -> effect.addMatrix(Matrixs.rotateAroundZAxis(angle)));
        return this;
    }

    /**
     * 将特效组内的特效增加一个矩阵
     * <p>
     * 建议请把矩阵设定为 3行3列 的矩阵, 以支持 2D 和 3D 的特效
     * </p>
     *
     * @param matrix 指定的矩阵
     * @return {@link EffectGroup}
     */
    public EffectGroup addMatrix(Matrix matrix) {
        effectList.forEach(effect -> effect.addMatrix(matrix));
        return this;
    }

    /**
     * 将特效组内的特效的矩阵全部移除
     *
     * @return {@link EffectGroup}
     */
    public EffectGroup removeMatrix() {
        effectList.forEach(ParticleObject::removeMatrix);
        return this;
    }

    public EffectGroup attachEntity(Entity entity) {
        effectList.forEach(effect -> effect.attachEntity(entity));
        return this;
    }

    public EffectGroup setOrigin(Location origin) {
        effectList.forEach(effect -> effect.setOrigin(origin));
        return this;
    }

    public EffectGroup setParticle(Particle particle) {
        effectList.forEach(effect -> effect.setParticle(particle));
        return this;
    }

    public EffectGroup setCount(int count) {
        effectList.forEach(effect -> effect.setCount(count));
        return this;
    }

    public EffectGroup setOffsetX(double offsetX) {
        effectList.forEach(effect -> effect.setOffsetX(offsetX));
        return this;
    }

    public EffectGroup setOffsetY(double offsetY) {
        effectList.forEach(effect -> effect.setOffsetX(offsetY));
        return this;
    }

    public EffectGroup setOffsetZ(double offsetZ) {
        effectList.forEach(effect -> effect.setOffsetX(offsetZ));
        return this;
    }

    public EffectGroup setExtra(double extra) {
        effectList.forEach(effect -> effect.setExtra(extra));
        return this;
    }

    public EffectGroup setData(Object data) {
        effectList.forEach(effect -> effect.setData(data));
        return this;
    }

    public EffectGroup setIncrementX(double incrementX) {
        effectList.forEach(effect -> effect.setIncrementX(incrementX));
        return this;
    }

    public EffectGroup setIncrementY(double incrementY) {
        effectList.forEach(effect -> effect.setIncrementX(incrementY));
        return this;
    }

    public EffectGroup setIncrementZ(double incrementZ) {
        effectList.forEach(effect -> effect.setIncrementX(incrementZ));
        return this;
    }

    /**
     * 利用给定的数字, 设置每一个特效的循环 tick
     *
     * @param period 循环tick
     * @return {@link EffectGroup}
     */
    public EffectGroup setPeriod(long period) {
        effectList.forEach(effect -> effect.setPeriod(period));
        return this;
    }

    public EffectGroup setColor(Color color) {
        effectList.forEach(effect -> effect.setColor(color));
        return this;
    }

    /**
     * 将特效组内的特效一次性展现出来
     *
     * @return {@link EffectGroup}
     */
    public EffectGroup show() {
        effectList.forEach(ParticleObject::show);
        return this;
    }

    /**
     * 将特效组内的特效一直地展现出来
     *
     * @return {@link EffectGroup}
     */
    public EffectGroup alwaysShow() {
        effectList.forEach(ParticleObject::alwaysShow);
        return this;
    }

    /**
     * 将特效组内的特效一直且异步地展现出来
     *
     * @return {@link EffectGroup}
     */
    public EffectGroup alwaysShowAsync() {
        effectList.forEach(ParticleObject::alwaysShowAsync);
        return this;
    }

    /**
     * 将特效组内的特效同时播放出来
     *
     * @return {@link EffectGroup}
     */
    public EffectGroup play() {
        for (ParticleObject pObj : effectList) {
            if (pObj instanceof Playable) {
                Playable playable = (Playable) pObj;
                playable.play();
            }
        }
        return this;
    }

    /**
     * 将特效组内的特效一直地同时播放出来
     *
     * @return {@link EffectGroup}
     */
    public EffectGroup alwaysPlay() {
        for (ParticleObject pObj : effectList) {
            if (pObj instanceof Playable) {
                pObj.alwaysPlay();
            }
        }
        return this;
    }

    /**
     * 将特效组内的特效一直且异步地同时播放出来
     *
     * @return {@link EffectGroup}
     */
    public EffectGroup alwaysPlayAsync() {
        for (ParticleObject pObj : effectList) {
            if (pObj instanceof Playable) {
                pObj.alwaysPlayAsync();
            }
        }
        return this;
    }

    /**
     * 获取特效列表
     *
     * @return {@link List}
     */
    public List<ParticleObject> getEffectList() {
        return effectList;
    }

}
