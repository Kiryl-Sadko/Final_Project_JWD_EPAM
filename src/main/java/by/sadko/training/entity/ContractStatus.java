package by.sadko.training.entity;

public enum ContractStatus {

    DONE("done"),
    IN_PROGRESS("in progress"),
    NOT_PAYED("not payed"),
    PAYED("payed");

    private final String status;

    ContractStatus(String status) {
        this.status = status;
    }

    public static ContractStatus fromString(String status) {
        ContractStatus[] values = ContractStatus.values();
        for (ContractStatus contractStatus : values) {
            if (contractStatus.name().equalsIgnoreCase(status)
                    || contractStatus.getStatus().equalsIgnoreCase(status)) {
                return contractStatus;
            }
        }
        return NOT_PAYED;
    }

    public String getStatus() {
        return status;
    }
}
