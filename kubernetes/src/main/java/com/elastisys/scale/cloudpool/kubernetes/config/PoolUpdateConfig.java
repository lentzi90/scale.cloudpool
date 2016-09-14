package com.elastisys.scale.cloudpool.kubernetes.config;

import static com.google.common.base.Preconditions.checkArgument;

import com.elastisys.scale.cloudpool.api.CloudPool;
import com.elastisys.scale.cloudpool.api.types.MachinePool;
import com.elastisys.scale.commons.json.JsonUtils;
import com.elastisys.scale.commons.json.types.TimeInterval;
import com.google.common.base.Objects;

/**
 * Controls the {@link CloudPool}'s behavior with respect to how often to
 * attempt to update the size of the {@link MachinePool} to match the desired
 * size.
 */
public class PoolUpdateConfig {

    /**
     * The time interval between periodical pool size updates.
     */
    private final TimeInterval updateInterval;

    /**
     * Constructs a new {@link PoolUpdateConfig}.
     *
     * @param updateInterval
     *            The time interval between periodical pool size updates. May be
     *            <code>null</code>. Default: 60 seconds.
     */
    public PoolUpdateConfig(TimeInterval updateInterval) {
        this.updateInterval = updateInterval;
    }

    /**
     * Time interval between cloud pool size updates.
     *
     * @return
     */
    public TimeInterval getUpdateInterval() {
        return this.updateInterval;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.updateInterval);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PoolUpdateConfig) {
            PoolUpdateConfig that = (PoolUpdateConfig) obj;
            return Objects.equal(this.updateInterval, that.updateInterval);
        }
        return false;
    }

    @Override
    public String toString() {
        return JsonUtils.toPrettyString(JsonUtils.toJson(this));
    }

    public void validate() throws IllegalArgumentException {
        checkArgument(this.updateInterval != null, "poolUpdate: updateInterval missing");
        this.updateInterval.validate();
    }
}
