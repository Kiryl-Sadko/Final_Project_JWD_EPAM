package by.sadko.training.entity;

public enum UserRole implements Entity {

    CEO(1L),
    CUSTOMER(2L),
    ADMIN(3L);

    private Long id;


    UserRole(Long id) {
        this.id = id;
    }

    public static UserRole fromString(String role) {

        final UserRole[] values = UserRole.values();
        for (UserRole userRole : values) {
            if (userRole.name().equalsIgnoreCase(role)) {
                return userRole;
            }
        }

        return null;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
