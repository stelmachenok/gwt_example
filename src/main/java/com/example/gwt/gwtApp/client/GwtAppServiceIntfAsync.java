package com.example.gwt.gwtApp.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GwtAppServiceIntf</code>
 */
public interface GwtAppServiceIntfAsync {
    void gwtAppCallServer(String data, AsyncCallback<String> callback) throws IllegalArgumentException;
}