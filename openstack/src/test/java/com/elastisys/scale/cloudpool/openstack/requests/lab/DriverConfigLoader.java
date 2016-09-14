package com.elastisys.scale.cloudpool.openstack.requests.lab;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.elastisys.scale.cloudpool.openstack.driver.config.OpenStackPoolDriverConfig;
import com.elastisys.scale.commons.json.JsonUtils;

/**
 * Utility class that can be used by OpenStack clients wishing to load
 * {@link OpenStackPoolDriverConfig} from a JSON file to initialize a client
 * before submitting a request to the OpenStack API.
 */
public class DriverConfigLoader {

    /**
     * TODO: set to a path holding the {@link OpenStackPoolDriverConfig} to use.
     */
    private static final Path OPENSTACK_DRIVER_CONFIG = Paths.get(System.getenv("HOME"), ".elastisys",
            "openstack-driver-config.json");

    /**
     * Loads an {@link OpenStackPoolDriverConfig} from
     * {@link #OPENSTACK_DRIVER_CONFIG}.
     *
     * @return
     */
    public static OpenStackPoolDriverConfig loadDefault() {
        return load(OPENSTACK_DRIVER_CONFIG.toFile());
    }

    /**
     * Loads an {@link OpenStackPoolDriverConfig} from a given file system path.
     *
     * @return
     */
    public static OpenStackPoolDriverConfig load(File configFile) {
        checkArgument(configFile.isFile(), "openstack driver config file %s does not exist",
                configFile.getAbsolutePath());
        return JsonUtils.toObject(JsonUtils.parseJsonFile(configFile), OpenStackPoolDriverConfig.class);
    }
}
