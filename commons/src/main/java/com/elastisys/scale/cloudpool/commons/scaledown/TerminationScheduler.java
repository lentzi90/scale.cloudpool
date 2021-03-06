package com.elastisys.scale.cloudpool.commons.scaledown;

import static com.elastisys.scale.cloudpool.api.types.Machine.remainingInstanceHourTime;
import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;

import org.joda.time.DateTime;

import com.elastisys.scale.cloudpool.api.types.Machine;
import com.elastisys.scale.cloudpool.commons.termqueue.ScheduledTermination;
import com.elastisys.scale.commons.util.time.UtcTime;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/**
 * Schedules termination of machine instances so that machine instances are
 * terminated as close as possible to the end of the current instance hour (with
 * a certain safety margin so that termination has time to complete).
 */
public class TerminationScheduler {

    /**
     * How many seconds prior to the next instance hour machine instances are to
     * be scheduled for termination. This should be set to a conservative and
     * safe value to prevent machines from being billed for an additional hour.
     * A value of zero is used to specify immediate termination when a
     * scale-down is ordered.
     */
    private final long instanceHourMargin;

    /**
     * Constructs a new {@link TerminationScheduler}.
     *
     * @param instanceHourMargin
     *            How many seconds prior to the next instance hour machine
     *            instances are to be scheduled for termination. This should be
     *            set to a conservative and safe value to prevent machines from
     *            being billed for an additional hour. A value of zero is used
     *            to specify immediate termination when a scale-down is ordered.
     */
    public TerminationScheduler(long instanceHourMargin) {
        checkArgument(instanceHourMargin < 3600, "instanceHourMargin must be smaller than 3600 seconds");
        this.instanceHourMargin = instanceHourMargin;
    }

    /**
     * Schedules termination for a number of machine instances.
     *
     * @param victims
     *            The machine instances to be scheduled for termination.
     * @return A list of {@link ScheduledTermination}s, one for each victim.
     */
    public List<ScheduledTermination> scheduleEvictions(List<Machine> victims) {
        Preconditions.checkNotNull(victims, "null victims");
        List<ScheduledTermination> evictions = Lists.newArrayList();
        for (Machine victim : victims) {
            evictions.add(scheduleEviction(victim));
        }
        return evictions;
    }

    /**
     * Schedules termination for a single {@link Machine} instance.
     * <p/>
     * For cases where the {@link Machine} does not have a launch time set (it
     * may not have been possible to determine), the {@link Machine} is
     * scheduled for immediate termination.
     *
     * @param victim
     *            The machine instance to be scheduled for termination.
     * @return A list of {@link ScheduledTermination}s, one for each victim.
     */
    public ScheduledTermination scheduleEviction(Machine victim) {
        Preconditions.checkNotNull(victim, "null victim");

        if (victim.getLaunchTime() == null) {
            return new ScheduledTermination(victim, UtcTime.now());
        }

        DateTime terminationTime = calculateTerminationTime(victim, this.instanceHourMargin);
        return new ScheduledTermination(victim, terminationTime);
    }

    private DateTime calculateTerminationTime(Machine victim, long instanceHourMargin) {
        if (instanceHourMargin <= 0) {
            // non-positive margin implies immediate termination
            return UtcTime.now();
        }

        long secondsLeftOfInstanceHour = remainingInstanceHourTime().apply(victim);
        long secondsLeftOfInstanceHourWithMargin = secondsLeftOfInstanceHour - instanceHourMargin;
        // if time left of billing hour is less than margin, we terminate
        // immediately
        secondsLeftOfInstanceHourWithMargin = Math.max(secondsLeftOfInstanceHourWithMargin, 0);
        DateTime terminationTime = UtcTime.now().plusSeconds((int) secondsLeftOfInstanceHourWithMargin);
        return terminationTime;
    }

}
