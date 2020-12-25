package com.lhc;

import lombok.Data;

@Data
public class Configuration {
    private String jdbcDriver;
    private String jdbcUrl;
    private String jdbcUserName;
    private String jdbcUserPassword;
}