package com.elastisys.scale.cloudadapters.aws.ec2.scalinggroup;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

import com.elastisys.scale.cloudadapers.api.CloudAdapterException;
import com.elastisys.scale.commons.json.JsonUtils;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;

/**
 * Configuration object for an {@link Ec2ScalingGroup}.
 * 
 * 
 */
public class Ec2ScalingGroupConfig {

	/** The access key id of the AWS account. */
	private final String awsAccessKeyId;
	/** The secret access key of the AWS account. */
	private final String awsSecretAccessKey;
	/** The particular AWS region to connect to. For example, {@code us-east-1}. */
	private final String region;

	/**
	 * Creates a new {@link Ec2ScalingGroupConfig}.
	 * 
	 * @param awsAccessKeyId
	 *            The access key id of the AWS account.
	 * @param awsSecretAccessKey
	 *            The secret access key of the AWS account.
	 * @param region
	 *            The particular AWS region to connect to. For example,
	 *            {@code us-east-1}.
	 */
	public Ec2ScalingGroupConfig(String awsAccessKeyId,
			String awsSecretAccessKey, String region) {
		this.awsAccessKeyId = awsAccessKeyId;
		this.awsSecretAccessKey = awsSecretAccessKey;
		this.region = region;
	}

	/**
	 * Returns the access key id of the AWS account.
	 * 
	 * @return
	 */
	public String getAwsAccessKeyId() {
		return this.awsAccessKeyId;
	}

	/**
	 * Returns the secret access key of the AWS account.
	 * 
	 * @return
	 */
	public String getAwsSecretAccessKey() {
		return this.awsSecretAccessKey;
	}

	/**
	 * Returns the particular AWS region to connect to.
	 * 
	 * @return
	 */
	public String getRegion() {
		return this.region;
	}

	/**
	 * Performs basic validation of this configuration.
	 * 
	 * @throws CloudAdapterException
	 */
	public void validate() throws CloudAdapterException {
		try {
			checkNotNull(this.awsAccessKeyId, "missing awsAccessKeyId");
			checkNotNull(this.awsSecretAccessKey, "missing awsSecretAccessKey");
			checkNotNull(this.region, "missing region");
		} catch (Exception e) {
			// no need to wrap further if already a config exception
			Throwables.propagateIfInstanceOf(e, CloudAdapterException.class);
			throw new CloudAdapterException(format(
					"failed to validate cloud client configuration: %s",
					e.getMessage()), e);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.awsAccessKeyId, this.awsSecretAccessKey,
				this.region);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Ec2ScalingGroupConfig) {
			Ec2ScalingGroupConfig that = (Ec2ScalingGroupConfig) obj;
			return equal(this.awsAccessKeyId, that.awsAccessKeyId)
					&& equal(this.awsSecretAccessKey, that.awsSecretAccessKey)
					&& equal(this.region, that.region);
		}
		return false;
	}

	@Override
	public String toString() {
		return JsonUtils.toString(JsonUtils.toJson(this));
	}
}
