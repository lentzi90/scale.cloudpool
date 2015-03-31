package com.elastisys.scale.cloudpool.openstack.driver.config;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Preconditions.checkArgument;

import com.elastisys.scale.cloudpool.openstack.driver.OpenStackPoolDriver;
import com.elastisys.scale.commons.json.JsonUtils;
import com.google.common.base.Objects;
import com.google.common.base.Optional;

/**
 * Configuration object for an {@link OpenStackPoolDriver}, which declares how
 * to authenticate and what OpenStack region to operate against.
 * <p/>
 * The {@link OpenStackPoolDriver} can be configured to use either use version 2
 * or version 3 of the <a
 * href="http://docs.openstack.org/developer/keystone/http-api.html#history"
 * >identity HTTP API</a>.
 */
public class OpenStackPoolDriverConfig {

	/**
	 * Declares how to authenticate with the OpenStack identity service
	 * (Keystone).
	 */
	private final AuthConfig auth;

	/**
	 * The particular OpenStack region (out of the ones available in Keystone's
	 * service catalog) to connect to. For example, {@code RegionOne}.
	 */
	private final String region;

	/**
	 * Set to <code>true</code> if a floating IP address should be allocated to
	 * launched servers. Default: <code>true</code>.
	 */
	private final Boolean assignFloatingIp;

	/**
	 * Creates a new {@link OpenStackPoolDriverConfig}.
	 *
	 * @param auth
	 *            Declares how to authenticate with the OpenStack identity
	 *            service (Keystone).
	 * @param region
	 *            The particular OpenStack region (out of the ones available in
	 *            Keystone's service catalog) to connect to. For example,
	 *            {@code RegionOne}.
	 * @param assignFloatingIp
	 *            Set to <code>true</code> if a floating IP address should be
	 *            allocated to launched servers. Default: <code>true</code>.
	 */
	public OpenStackPoolDriverConfig(AuthConfig auth, String region,
			Boolean assignFloatingIp) {
		this.auth = auth;
		this.region = region;
		this.assignFloatingIp = assignFloatingIp;
		validate();
	}

	/**
	 * Returns a description of how to authenticate with the OpenStack identity
	 * service (Keystone).
	 *
	 * @return
	 */
	public AuthConfig getAuth() {
		return this.auth;
	}

	/**
	 * Returns the particular OpenStack region (out of the ones available in
	 * Keystone's service catalog) to connect to. For example, {@code RegionOne}
	 * .
	 *
	 * @return
	 */
	public String getRegion() {
		return this.region;
	}

	/**
	 * Returns <code>true</code> if a floating IP address should be allocated to
	 * launched servers.
	 *
	 * @return
	 */
	public Boolean isAssignFloatingIp() {
		return Optional.fromNullable(this.assignFloatingIp).or(true);
	}

	/**
	 * Performs basic validation of this configuration. Throws an
	 * {@link IllegalArgumentException} on failure to validate the
	 * configuration.
	 *
	 * @throws IllegalArgumentException
	 */
	public void validate() throws IllegalArgumentException {
		checkArgument(this.auth != null, "no auth method specified");
		checkArgument(this.region != null, "missing region");
		this.auth.validate();
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.auth, this.region,
				this.isAssignFloatingIp());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof OpenStackPoolDriverConfig) {
			OpenStackPoolDriverConfig that = (OpenStackPoolDriverConfig) obj;
			return equal(this.auth, that.auth)
					&& equal(this.region, that.region)
					&& equal(this.isAssignFloatingIp(),
							that.isAssignFloatingIp());
		}
		return false;
	}

	@Override
	public String toString() {
		return JsonUtils.toString(JsonUtils.toJson(this));
	}
}