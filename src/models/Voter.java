package models;

public class Voter {
    private final String firstName;
    private final String lastName;
    private final String middleName;
    private final String email;
    private final String password;
    private String cookieIdentity;
    private boolean isVoted;

    public Voter(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.middleName = builder.middleName;
        this.email = builder.email;
        this.password = builder.password;
        this.isVoted = builder.isVoted;
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private String middleName;
        private String email;
        private String password;
        private boolean isVoted;

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder middleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder isVoted(boolean isVoted) {
            this.isVoted = isVoted;
            return this;
        }

        public Voter build() {
            return new Voter(this);
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUniqueCookieIdentity() {
        return cookieIdentity != null ? cookieIdentity : "";
    }

    public boolean isVoted() {
        return isVoted;
    }

    public void setCookieIdentity(String cookieIdentity) {
        this.cookieIdentity = cookieIdentity;
    }

    public void setVoted(boolean voted) {
        isVoted = voted;
    }
}
