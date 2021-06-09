package by.sadko.training.entity;

public enum OperationType {

    CUTTING("C"),
    ROLLING("R"),
    STAMPING("S"),
    MILLING("M"),
    GRINDING("G"),
    TURNING("T"),
    HARDENING("H");

    private final String name;

    OperationType(String name) {
        this.name = name;
    }

    public static OperationType fromString(String processName) {
        OperationType[] values = OperationType.values();
        for (OperationType operationType : values) {
            if (operationType.name.equalsIgnoreCase(processName)
                    || operationType.name().equalsIgnoreCase(processName)) {
                return operationType;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
