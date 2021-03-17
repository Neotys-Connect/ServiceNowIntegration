# NeoLoad ServiceNow DevOps Integration
<p align="center"><img src="/screenshot/543-5435205_transparent-servicenow-logo-png-png-download.png" width="40%" alt="ServiceNow" /></p>

This project is aimed to integrate NeoLoad with ServiceNow DevOps. The plugin will add the project "context" to neoload Web.
A project context requires the following information
* `Artifact` :  Artifact Repo named ( defined in ServiceNow DevOps), version
* `Package` : name of the package (defined in ServiceNow DevOps)
* `StageName` : name of the stage
* `BuildNumer` : BuildNumber
* `PipelineName` : PipelineName

     
| Property | Value |
| -----| -------------- |
| Maturity | Experimental |
| Author   | Neotys Partner Team |
| License  | [BSD Simplified](https://www.neotys.com/documents/legal/bsd-neotys.txt) |
| NeoLoad  | 7.7 (Enterprise or Professional Edition w/ Integration & Advanced Usage and NeoLoad Web option required)|
| Requirements | NeoLoad Web |
| Bundled in NeoLoad | No
| Download Binaries | <ul><li>[latest release] is only compatible with NeoLoad from version 7.7</li></ul>|
                
### Installation

1. Download the [latest release]() for NeoLoad from version 7.7
1. Read the NeoLoad documentation to see [How to install a custom Advanced Action](https://www.neotys.com/documents/doc/neoload/latest/en/html/#25928.htm).

<p align="center"><img src="/screenshot/custom_action.png" alt="ServiceNow Advanced Action" /></p>

### ServiceNow Custom Actions
  
| Name             | Description |
| -----            | ----- |
| `ArtifactName1`      | Name of your Artifact number 1 |
| `ArtifactRepoName1`   |   Name of the repository for the artifact number 1|
| `ArtifactVersion1`   |  Version of the artifact number 1|
| `ArtifactNameX`      | Name of your Artifact number X |
| `ArtifactRepoNameX`   |   Name of the repository for the artifact number X|
| `ArtifactVersionX`   |  Version of the artifact number X|
| `PackagesName`   |  Name of the packages seperated by ,", "Name of the packages seperated by ,|
| `StageName`      | (Optional) Name of your Stage |
| `PipelineName`   |(Optional)   Name of the pipeline|
| `BuildNumber`   | (Optional) BuildNumber|
### NeoLoad Set-up

Once installed, how to use in a given NeoLoad project:

1. Create a `ServiceNow` User Path.
1. Insert one of the Gremlin custom Action in the `Action` block.
<p align="center"><img src="/screenshot/vu.png" alt="ServiceNow User Path" /></p>

1. Create a NeoLoad Population ServiceNow having only the userPath ServiceNow
<p align="center"><img src="/screenshot/population.png" alt="ServiceNow Population" /></p>
1. Create a NeoLoad Scenario Using your population and the ServiceNow Population
The ServiceNow Population would need to be added to your NeoLoad scenario with the following settings :
* Duration : iteration
* Load Policy : Constant : 1 user doing 1 iteration
<p align="center"><img src="/screenshot/scenario.png" alt="ServiceNow scenario" /></p>

### ServiceNow Set-up

