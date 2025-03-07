package models;

public class Candidate {
    private static long idCount;
    private final long id;
    private final String name;
    private final String photoUrl;

    public Candidate(Builder builder) {
        this.id = builder.id != 0 ? builder.id : getIdCount();
        this.name = builder.name;
        this.photoUrl = builder.photoUrl;
    }

    public static class Builder {
        private long id;
        private String name;
        private String photoUrl;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder photoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
            return this;
        }

        public Candidate build() {
            return new Candidate(this);
        }
    }

    public long getIdCount() {
        return ++idCount;
    }

    public String getName() {
        return name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public long getId() {
        return id;
    }
}
