package gym.model;

public enum PackageStatus {
    ACTIVE(),
    INACTIVE,
    EXPIRED;

    public String toString() {
        return this.name().toLowerCase();
    }

    public boolean isActive() {
        return this == ACTIVE;
    }

    public boolean isInactive() {
        return this == INACTIVE;
    }

    public boolean isExpired() {
        return this == EXPIRED;
    }
}
