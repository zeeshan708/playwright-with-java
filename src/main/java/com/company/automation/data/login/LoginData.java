package com.company.automation.data.login;

import com.company.automation.data.BaseData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * This class captures all the test data properties required for the login page.
 *
 * @author Zeeshan
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginData extends BaseData {
    private String userName;
    private String password;
    private String errorMessage;
    private String captcha;
}
