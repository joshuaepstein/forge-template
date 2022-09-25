package joshuaepstein.template_mod.events;

public enum ActiveFlags {
    IS_AOE_MINING,
    IS_FORTUNE_MINING,
    IS_DOT_ATTACKING,
    IS_LEECHING,
    IS_AOE_ATTACKING,
    IS_REFLECT_ATTACKING;

    ActiveFlags() {
        this.activeReferences = 0;
    }

    private int activeReferences;

    public boolean isSet() {
        return (this.activeReferences > 0);
    }

    public synchronized void runIfNotSet(Runnable run) {
        if (!isSet()) {
            this.activeReferences++;
            try {
                run.run();
            } finally {
                this.activeReferences--;
            }
        }
    }
}
