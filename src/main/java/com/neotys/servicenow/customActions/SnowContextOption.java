package com.neotys.servicenow.customActions;

import com.neotys.action.argument.ArgumentValidator;
import com.neotys.action.argument.Option;
import com.neotys.extensions.action.ActionParameter;

import static com.neotys.action.argument.DefaultArgumentValidator.INTEGER_VALIDATOR;
import static com.neotys.action.argument.DefaultArgumentValidator.NON_EMPTY;
import static com.neotys.action.argument.Option.AppearsByDefault.True;
import static com.neotys.action.argument.Option.OptionalRequired.Optional;
import static com.neotys.action.argument.Option.OptionalRequired.Required;
import static com.neotys.extensions.action.ActionParameter.Type.TEXT;

enum SnowContextOption implements Option {

    ArtifactName1("ArtifactName1", Required, True, TEXT,
            "Name of the Artifact 1",
                    "Name of the Artifact 1",
          NON_EMPTY),
    ArtifactRepoName1("ArtifactRepoName1", Required, True, TEXT,
            "Name of the Artifact 1",
            "Name of the Artifact 1",
            NON_EMPTY),
    ArtifactVersion1("ArtifactVersion1",Required,True,TEXT,"Version of the Artifact 1","Version of the Artifact 1",NON_EMPTY),
    PackageName("PackagesName",Required,True,TEXT,"Name of the packages seperated by ,", "Name of the packages seperated by ,",NON_EMPTY),
    StageName("StageName",Required,True,TEXT,"Name of the Stage", "Name of the Stage",NON_EMPTY),
    PipelineName("PipelineName",Optional,True,TEXT,"Name of the Pipeline", "Name of the Pipeline",NON_EMPTY),
    BuildNumber("BuildNumber",Optional,True,TEXT,"Build Number", "Build Number",INTEGER_VALIDATOR);
    private final String name;
    private final OptionalRequired optionalRequired;
    private final AppearsByDefault appearsByDefault;
    private final ActionParameter.Type type;
    private final String defaultValue;
    private final String description;
    private final ArgumentValidator argumentValidator;

    SnowContextOption(final String name, final OptionalRequired optionalRequired,
                      final AppearsByDefault appearsByDefault,
                      final ActionParameter.Type type, final String defaultValue, final String description,
                      final ArgumentValidator argumentValidator) {
        this.name = name;
        this.optionalRequired = optionalRequired;
        this.appearsByDefault = appearsByDefault;
        this.type = type;
        this.defaultValue = defaultValue;
        this.description = description;
        this.argumentValidator = argumentValidator;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public OptionalRequired getOptionalRequired() {
        return optionalRequired;
    }

    @Override
    public AppearsByDefault getAppearsByDefault() {
        return appearsByDefault;
    }

    @Override
    public ActionParameter.Type getType() {
        return type;
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public ArgumentValidator getArgumentValidator() {
        return argumentValidator;
    }

}