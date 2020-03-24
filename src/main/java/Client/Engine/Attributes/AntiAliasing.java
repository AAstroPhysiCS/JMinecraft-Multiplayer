package Client.Engine.Attributes;

public enum AntiAliasing {
    ANTI_ALIASING_ON(4), ANTI_ALIASING_OFF();
    private int level;

    AntiAliasing(int level) {
        this.level = level;
    }
    AntiAliasing(){

    }

    public int getLevel() {
        return level;
    }
}
