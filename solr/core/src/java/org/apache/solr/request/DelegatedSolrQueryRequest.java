package org.apache.solr.request;

import io.opentracing.Span;
import io.opentracing.Tracer;
import org.apache.solr.cloud.CloudDescriptor;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.CommandOperation;
import org.apache.solr.common.util.ContentStream;
import org.apache.solr.core.CoreContainer;
import org.apache.solr.core.SolrCore;
import org.apache.solr.schema.IndexSchema;
import org.apache.solr.search.SolrIndexSearcher;
import org.apache.solr.servlet.HttpSolrCall;
import org.apache.solr.util.RTimerTree;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public class DelegatedSolrQueryRequest implements SolrQueryRequest{
    private final SolrQueryRequest delegate;

    public DelegatedSolrQueryRequest(SolrQueryRequest delegate) {
        this.delegate = delegate;
    }

    @Override
    public SolrParams getParams() {
        return delegate.getParams();
    }

    @Override
    public void setParams(SolrParams params) {
        delegate.setParams(params);

    }

    @Override
    public Iterable<ContentStream> getContentStreams() {
        return delegate.getContentStreams();
    }

    @Override
    public SolrParams getOriginalParams() {
        return delegate.getOriginalParams();
    }

    @Override
    public Map<Object, Object> getContext() {
        return delegate.getContext();
    }

    @Override
    public void close() {
        delegate.close();

    }

    @Override
    public long getStartTime() {
        return delegate.getStartTime();
    }

    @Override
    public RTimerTree getRequestTimer() {
        return delegate.getRequestTimer();
    }

    @Override
    public SolrIndexSearcher getSearcher() {
        return delegate.getSearcher();
    }

    @Override
    public SolrCore getCore() {
        return delegate.getCore();
    }

    @Override
    public IndexSchema getSchema() {
        return delegate.getSchema();
    }

    @Override
    public void updateSchemaToLatest() {
        delegate.updateSchemaToLatest();

    }

    @Override
    public String getParamString() {
        return delegate.getParamString();
    }

    @Override
    public Map<String, Object> getJSON() {
        return delegate.getJSON();
    }

    @Override
    public void setJSON(Map<String, Object> json) {
        delegate.setJSON(json);

    }

    @Override
    public Principal getUserPrincipal() {
        return delegate.getUserPrincipal();
    }

    @Override
    public String getPath() {
        return delegate.getPath();
    }

    @Override
    public Map<String, String> getPathTemplateValues() {
        return delegate.getPathTemplateValues();
    }

    @Override
    public List<CommandOperation> getCommands(boolean validateInput) {
        return delegate.getCommands(validateInput);
    }

    @Override
    public String getHttpMethod() {
        return delegate.getHttpMethod();
    }

    @Override
    public HttpSolrCall getHttpSolrCall() {
        return delegate.getHttpSolrCall();
    }

    @Override
    public Tracer getTracer() {
        return delegate.getTracer();
    }

    @Override
    public Span getSpan() {
        return delegate.getSpan();
    }

    @Override
    public CoreContainer getCoreContainer() {
        return delegate.getCoreContainer();
    }

    @Override
    public CloudDescriptor getCloudDescriptor() {
        return delegate.getCloudDescriptor();
    }
}
