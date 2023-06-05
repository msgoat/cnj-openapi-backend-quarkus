package group.msg.at.cloud.cloudtrain.core.entity;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * Enumeration representing the various categories of a {@link Task}.
 *
 * @author Michael Theis (mtheis@msg.group)
 * @version 1.0
 * @since release 1.0 31.10.2012 13:17:48
 */
@Schema(description = "enumeration representing the various categories of a task")
public enum TaskCategory {

    UNDEFINED,
    BUGFIX,
    REFACTORING,
    NEW_FEATURE,
    PERFORMANCE_IMPROVEMENT,
    RELEASE_MANAGEMENT,
    QUALITY_ASSURANCE,
    BUILD_FAILURE,
    COMMUNICATION
}
