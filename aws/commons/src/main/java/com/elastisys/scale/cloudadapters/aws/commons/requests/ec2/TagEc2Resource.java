package com.elastisys.scale.cloudadapters.aws.commons.requests.ec2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.Tag;
import com.google.common.collect.Lists;

/**
 * * A {@link Callable} task that, when executed, applies a {@link Tag} to an
 * EC2 resource (such as an instance) in a given region.
 *
 * 
 *
 */
public class TagEc2Resource extends AmazonEc2Request<Void> {
	static Logger LOG = LoggerFactory.getLogger(TagEc2Resource.class);

	/** The identifier of the resource to tag. For example, an instance id. */
	private final String resourceId;

	/** The tags to apply to the resource. */
	private List<Tag> tags = Lists.newArrayList();

	public TagEc2Resource(AWSCredentials awsCredentials, String region,
			String resourceId, Tag... tags) {
		this(awsCredentials, region, resourceId,
				(tags == null ? new ArrayList<Tag>() : Arrays.asList(tags)));
	}

	public TagEc2Resource(AWSCredentials awsCredentials, String region,
			String resourceId, List<Tag> tags) {
		super(awsCredentials, region);
		this.resourceId = resourceId;
		this.tags = Lists.newArrayList(tags);
	}

	@Override
	public Void call() {
		for (Tag tag : this.tags) {
			LOG.debug("setting {}={} tag on instance {}", tag.getKey(),
					tag.getValue(), this.resourceId);
		}

		CreateTagsRequest request = new CreateTagsRequest(
				Arrays.asList(this.resourceId), this.tags);
		getClient().getApi().createTags(request);
		return null;
	}
}
