package com.elastisys.scale.cloudadapters.commons.scaledown.strategies;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Comparator;
import java.util.List;

import com.elastisys.scale.cloudadapers.api.types.Machine;
import com.elastisys.scale.cloudadapters.commons.scaledown.VictimSelectionStrategy;

/**
 * A {@link VictimSelectionStrategy} that selects the most recently created
 * instance for termination.
 * 
 * 
 */
public enum NewestInstanceVictimSelectionStrategy implements
		VictimSelectionStrategy {

	/** The single instance of this class. */
	INSTANCE;

	@Override
	public Machine selectVictim(List<Machine> candidates)
			throws IllegalArgumentException {
		checkNotNull(candidates, "null candidate set");
		checkArgument(!candidates.isEmpty(), "empty candidate set");

		return Machine.sort(candidates, new NewestFirstOrder()).get(0);
	}

	/**
	 * {@link Comparator} that orders {@link Machine} instances in order of
	 * increasing age (youngest instance first).
	 * 
	 * 
	 * 
	 */
	public class NewestFirstOrder implements Comparator<Machine> {
		@Override
		public int compare(Machine instance1, Machine instance2) {
			return instance2.getLaunchtime().compareTo(
					instance1.getLaunchtime());
		}
	}
}