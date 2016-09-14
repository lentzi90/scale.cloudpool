package com.elastisys.scale.cloudpool.api.types;

import java.util.List;
import java.util.regex.Pattern;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

/**
 * Describes static properties about the cloud pool itself and the cloud it
 * manages.
 */
public class CloudPoolMetadata {

    /**
     * Unique identifier for the cloud that this cloud pool manages.
     */
    private final String poolIdentifier;

    /**
     * List of supported API versions.
     */
    private final List<String> supportedApiVersions;

    /**
     * Ensure that we have "at least one digit, optionally followed by a dot and
     * a non-empty sequence of digits" as version numbers.
     */
    private final static Pattern apiVersionPattern = Pattern.compile("\\d+(\\.\\d)?");

    /**
     * Creates a new instance describing the cloud pool and the cloud it
     * manages.
     *
     * @param poolIdentifier
     *            The unique identifier for the cloud infrastructure managed by
     *            this cloud pool.
     * @param supportedApiVersions
     *            List of supported API versions.
     */
    public CloudPoolMetadata(String poolIdentifier, List<String> supportedApiVersions) {
        Preconditions.checkNotNull(poolIdentifier, "poolIdentifier cannot be null");
        Preconditions.checkNotNull(supportedApiVersions, "supportedApiVersions cannot be null");
        Preconditions.checkState(!supportedApiVersions.isEmpty(), "supportedApiVersion cannot be empty");
        for (String apiVersion : supportedApiVersions) {
            Preconditions.checkState(apiVersionPattern.matcher(apiVersion).matches(),
                    String.format("%s is not a valid API version", apiVersion));
        }

        this.poolIdentifier = poolIdentifier;
        this.supportedApiVersions = ImmutableList.copyOf(supportedApiVersions);
    }

    /**
     * @return The unique identifier for the cloud infrastructure managed by
     *         this cloud pool.
     */
    public String poolIdentifier() {
        return this.poolIdentifier;
    }

    /**
     * @return List of supported API versions.
     */
    public List<String> supportedApiVersions() {
        return this.supportedApiVersions;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(this.poolIdentifier, this.supportedApiVersions);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CloudPoolMetadata) {
            CloudPoolMetadata that = (CloudPoolMetadata) obj;
            return Objects.equal(this.poolIdentifier, that.poolIdentifier)
                    && Objects.equal(this.supportedApiVersions, that.supportedApiVersions);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("poolIdentifier", this.poolIdentifier)
                .add("supportedApiVersions", this.supportedApiVersions).toString();
    }
}
