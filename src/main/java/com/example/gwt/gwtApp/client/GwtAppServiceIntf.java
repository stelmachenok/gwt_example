package com.example.gwt.gwtApp.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("gwtAppService")
public interface GwtAppServiceIntf extends RemoteService {
    String gwtAppCallServer(String data) throws IllegalArgumentException;
}