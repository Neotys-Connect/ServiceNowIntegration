package com.neotys.servicenow.datamodel;

import com.google.common.base.Optional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SnowContext {
    List<Package> packages;
    List<Map<String,String>> artifacts;
    String buildNumber;
    String stageName;
    String pipelineName;

    public SnowContext(List<Package> packages, List<Map<String,String>> artifacts, String buildNumber, String stageName, String pipelineName) {
        this.packages = packages;
        this.artifacts = artifacts;
        this.buildNumber = buildNumber;
        this.stageName = stageName;
        this.pipelineName = pipelineName;
    }


    public SnowContext(List<String> packages, List<Map<String,String>> artifacts, Optional<String> buildNumber, String stageName, Optional<String> pipelineName) {
        this.packages = packages.stream().map(s -> {return new Package(s);}).collect(Collectors.toList());
        this.artifacts = artifacts;
        if(buildNumber.isPresent())
            this.buildNumber = buildNumber.get();
        else
            this.buildNumber=null;

        this.stageName = stageName;
        if(pipelineName.isPresent())
            this.pipelineName = pipelineName.get();
        else
            this.pipelineName=null;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    public List<Map<String,String>> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<Map<String,String>> artifacts) {
        this.artifacts = artifacts;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getPipelineName() {
        return pipelineName;
    }

    public void setPipelineName(String pipelineName) {
        this.pipelineName = pipelineName;
    }
}
