package amymialee.peculiarpieces.util;

public class RedstoneInstance {
    private boolean strong = false;
    private int lifetime = 20;
    private int power = 15;

    public RedstoneInstance setLifetime(int lifetime) {
        this.lifetime = lifetime;
        return this;
    }

    public RedstoneInstance setPower(int power) {
        this.power = power;
        return this;
    }

    public RedstoneInstance setStrong() {
        this.strong = true;
        return this;
    }

    public RedstoneInstance setWeak() {
        this.strong = false;
        return this;
    }

    public boolean tick() {
        this.lifetime--;
        return this.lifetime <= 0;
    }

    public int getLifetime() {
        return lifetime;
    }

    public int getPower() {
        return power;
    }

    public boolean isStrong() {
        return strong;
    }
}