package com.company.automation.data;
import com.company.automation.data.login.LoginData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.univocity.parsers.annotations.Parsed;
import lombok.Data;
import lombok.ToString;


/**
 * This class provides common properties for all the test data.
 *
 * @author Zeeshan
 */
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseData {
    @Parsed(field = "testCaseId", defaultNullRead = "")
    public String testCaseId;

    @Parsed(field = "testCaseDescription", defaultNullRead = "")
    public String testCaseDescription;
    private LoginData loginData;

    private String testcaseId;

    public String getTestCaseId(){
        return testCaseId;
    }

    public void setTestCaseId(String testCaseId){
        this.testCaseId = testCaseId;
    }
}
