package com.elastisys.scale.cloudadapters.commons.scaledown;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.elastisys.scale.cloudadapters.commons.scaledown.strategies.ClosestToInstanceHourVictimSelectionStrategy;
import com.elastisys.scale.cloudadapters.commons.scaledown.strategies.NewestInstanceVictimSelectionStrategy;
import com.elastisys.scale.cloudadapters.commons.scaledown.strategies.OldestInstanceVictimSelectionStrategy;

/**
 * Exercises the {@link VictimSelectionPolicy} class.
 * 
 * 
 * 
 */
public class TestVictimSelectionPolicy {

	@Test
	public void testPolicyStrategyAssociations() {
		assertThat(
				VictimSelectionPolicy.CLOSEST_TO_INSTANCE_HOUR
						.getVictimSelectionStrategy(),
				is(ClosestToInstanceHourVictimSelectionStrategy.class));

		assertThat(
				VictimSelectionPolicy.NEWEST_INSTANCE
						.getVictimSelectionStrategy(),
				is(NewestInstanceVictimSelectionStrategy.class));

		assertThat(
				VictimSelectionPolicy.OLDEST_INSTANCE
						.getVictimSelectionStrategy(),
				is(OldestInstanceVictimSelectionStrategy.class));

	}
}
