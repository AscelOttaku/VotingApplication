package models;

import java.util.Comparator;

public class Candidate implements Comparable<Candidate> {
    private static long idCount;
    private final long id;
    private final String name;
    private final String photoUrl;
    private long votesQuantity;

    public Candidate(Builder builder) {
        this.id = builder.id != 0 ? builder.id : getIdCount();
        this.name = builder.name;
        this.photoUrl = builder.photoUrl;
        this.votesQuantity = builder.votesQuantity;
    }

    @Override
    public int compareTo(Candidate o) {
        if (votesQuantity == o.votesQuantity)
            return name.compareTo(o.name);

        return Long.compare(votesQuantity, o.votesQuantity);
    }

    public static class Builder {
        private long id;
        private String name;
        private String photoUrl;
        private long votesQuantity;

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

        public Builder votesQuantity(long votesQuantity) {
            this.votesQuantity = votesQuantity;
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

    public long getVotesQuantity() {
        return votesQuantity;
    }

    public void setVotesQuantity(long votesQuantity) {
        this.votesQuantity = votesQuantity;
    }
}
