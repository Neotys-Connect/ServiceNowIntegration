package com.neotys.servicenow.customActions;

import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.neotys.action.result.ResultFactory;
import com.neotys.ascode.api.v3.client.ApiClient;
import com.neotys.ascode.api.v3.client.ApiException;
import com.neotys.ascode.api.v3.client.api.ResultsApi;
import com.neotys.ascode.api.v3.client.model.TestResultUpdateRequest;
import com.neotys.extensions.action.ActionParameter;
import com.neotys.extensions.action.engine.ActionEngine;
import com.neotys.extensions.action.engine.Context;
import com.neotys.extensions.action.engine.Logger;
import com.neotys.extensions.action.engine.SampleResult;

import com.neotys.servicenow.datamodel.SnowContext;

import java.util.*;
import java.util.stream.Collectors;

import static com.neotys.action.argument.Arguments.getArgumentLogString;
import static com.neotys.action.argument.Arguments.parseArguments;
import static com.neotys.servicenow.common.SnowUtils.ARTIFACT;

public class SendSnowContextActionEngine implements ActionEngine {


    private static final String STATUS_CODE_INVALID_PARAMETER = "NL-SNOW-SENDCONTEXT_ACTION-01";
    private static final String STATUS_CODE_TECHNICAL_ERROR = "NL-SNOW-SENDCONTEXT_ACTION-02";
    private static final String STATUS_CODE_BAD_CONTEXT = "NL-SNOW-SENDCONTEXT_ACTION-03";
    private static final String NLWEB_VERSION ="v3" ;

    public SampleResult execute(Context context, List<ActionParameter> parameters) {
        final SampleResult sampleResult = new SampleResult();
        final StringBuilder requestBuilder = new StringBuilder();
        final StringBuilder responseBuilder = new StringBuilder();

        final Map<String, Optional<String>> parsedArgs;
        try {
            parsedArgs = parseArguments(parameters, SnowContextOption.values());

        } catch (final IllegalArgumentException iae) {
            return ResultFactory.newErrorResult(context, STATUS_CODE_INVALID_PARAMETER, "Could not parse arguments: ", iae);
        }


        final Optional<String> buildnumber = parsedArgs.get(SnowContextOption.BuildNumber.getName());
        final Optional<String> pipelineName=parsedArgs.get(SnowContextOption.PipelineName.getName());
        final String stagename=parsedArgs.get(SnowContextOption.StageName.getName()).get();
        final String packages=parsedArgs.get(SnowContextOption.PackageName.getName()).get();

        List<String> packageslist=Arrays.asList(packages.split(","));

        final Logger logger = context.getLogger();
        if (logger.isDebugEnabled()) {
            logger.debug("Executing " + this.getClass().getName() + " with parameters: "
                    + getArgumentLogString(parsedArgs, SnowContextOption.values()));
        }

        List<Map<String,String>> artifacts=getArtifactsParameters(parameters);

        try {
            Optional<HashMap<String,String>> customFields;
            Optional<List<String>> optinalListofTags;
            ApiClient client=new ApiClient();
            ResultsApi resultsApi=new ResultsApi(client);
            client.setBasePath(getBasePath(context));
            client.setApiKey(context.getAccountToken());
            Gson gson=new Gson();
            SnowContext snowContext=new SnowContext(packageslist,artifacts, buildnumber,stagename,pipelineName);
            String description=gson.toJson(snowContext);


            TestResultUpdateRequest testUpdateRequest =new TestResultUpdateRequest();





            testUpdateRequest.setDescription(description);
            resultsApi.updateTestResult(testUpdateRequest,context.getWorkspaceId(),context.getTestId());
            appendLineToStringBuilder(responseBuilder, description);

        }catch (ApiException e) {
            return ResultFactory.newErrorResult(context, STATUS_CODE_TECHNICAL_ERROR, "Snow Send context Api Error - API Exception "+e.getResponseBody(), e);
        }
        catch (Exception e)
        {
            return ResultFactory.newErrorResult(context, STATUS_CODE_TECHNICAL_ERROR, "Snow Send context technical Error  ", e);

        }


        sampleResult.setRequestContent(requestBuilder.toString());
        sampleResult.setResponseContent(responseBuilder.toString());


        return sampleResult;
    }

    private  List<Map<String,String>> getArtifactsParameters(List<ActionParameter> actionParameters)
    {
        List<Map<String,String>> artifacts=new ArrayList<>();
       actionParameters.stream().filter(actionParameter -> actionParameter.getName().toUpperCase().contains(ARTIFACT.toUpperCase())).collect(
               Collectors.groupingBy(e -> {
                               return e.getName().substring(e.getName().length()-1);
                       }
               )).forEach((s, actionParameters1) -> {
                if(actionParameters1.size()==3)
                {
                    String name = null;
                    String version = null;
                    String repo=null;
                    if(actionParameters1.get(0).getName().startsWith("ArtifactName"))
                        name=actionParameters1.get(0).getValue();

                    if(actionParameters1.get(1).getName().startsWith("ArtifactName"))
                        name=actionParameters1.get(1).getValue();

                    if(actionParameters1.get(2).getName().startsWith("ArtifactName"))
                        name=actionParameters1.get(2).getValue();

                    if(actionParameters1.get(0).getName().startsWith("ArtifactVersion"))
                        version=actionParameters1.get(0).getValue();

                    if(actionParameters1.get(1).getName().startsWith("ArtifactVersion"))
                        version=actionParameters1.get(1).getValue();

                    if(actionParameters1.get(2).getName().startsWith("ArtifactVersion"))
                        version=actionParameters1.get(2).getValue();

                    if(actionParameters1.get(0).getName().startsWith("ArtifactRepoName"))
                        repo=actionParameters1.get(0).getValue();

                    if(actionParameters1.get(1).getName().startsWith("ArtifactRepoName"))
                        repo=actionParameters1.get(1).getValue();


                    if(actionParameters1.get(2).getName().startsWith("ArtifactRepoName"))
                        repo=actionParameters1.get(2).getValue();

                    HashMap<String,String> map=new HashMap<>();

                    map.put(repo,name);
                    map.put("version",version);

                    artifacts.add(map);

                }

       });

       return artifacts;
    }

    private String getBasePath(final Context context) {
        final String webPlatformApiUrl = context.getWebPlatformApiUrl();
        final StringBuilder basePathBuilder = new StringBuilder(webPlatformApiUrl);
        if(!webPlatformApiUrl.endsWith("/")) {
            basePathBuilder.append("/");
        }
      //  basePathBuilder.append(NLWEB_VERSION + "/");
        return basePathBuilder.toString();
    }

    private void appendLineToStringBuilder(final StringBuilder sb, final String line) {
        sb.append(line).append("\n");
    }

    /**
     * This method allows to easily create an error result and log exception.
     */
    private static SampleResult getErrorResult(final Context context, final SampleResult result, final String errorMessage, final Exception exception) {
        result.setError(true);
        result.setStatusCode("NL-SNOW_ERROR");
        result.setResponseContent(errorMessage);
        if (exception != null) {
            context.getLogger().error(errorMessage, exception);
        } else {
            context.getLogger().error(errorMessage);
        }
        return result;
    }

    @Override
    public void stopExecute() {
        // TODO add code executed when the test have to stop.

    }
}