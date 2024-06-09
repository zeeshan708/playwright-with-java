package com.company.automation.utils;


import com.company.automation.data.login.LoginData;
import com.company.automation.endpoints.ApiEndpoints;
import com.company.automation.helpers.AuthenticationHelper;

import java.util.HashMap;

import static com.company.automation.config.ConfigurationManager.configuration;

public class PdfService {
    public static String getPrintPreviewDocumentData(LoginData loginData, String entity, String DocName, String entityID, boolean isNewFlow) {
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("entity",entity);
        map.put("docName",DocName);
        map.put("entityID",getEntityIDFromShipmentID(loginData,entityID));
        map.put("isNewFlow",isNewFlow);
        String Response = new APIUtil().sendAPIRequest(configuration().baseUrl(), ApiEndpoints.DUMMY, "POST", AuthenticationHelper.getAuthHeaders(loginData), map).text();
        return Response;
    }

    private static String getEntityIDFromShipmentID(LoginData loginData,String ShipmentNo) {
        String result = CommonUtils.performQuery(loginData, "Select id from shipments where shipmentId = '" + ShipmentNo + "';").text();
        return result.substring(result.indexOf(":")+1,result.indexOf("}"));
    }
}
