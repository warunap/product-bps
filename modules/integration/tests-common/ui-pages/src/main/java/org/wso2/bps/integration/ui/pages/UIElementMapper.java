/*
*Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*WSO2 Inc. licenses this file to you under the Apache License,
*Version 2.0 (the "License"); you may not use this file except
*in compliance with the License.
*You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing,
*software distributed under the License is distributed on an
*"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*KIND, either express or implied.  See the License for the
*specific language governing permissions and limitations
*under the License.
*/

package org.wso2.bps.integration.ui.pages;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Read mapper.properties file and load it's uiElements into Properties object.
 */
public class UIElementMapper {
    private static final Properties uiProperties = new Properties();

    private static final Log log = LogFactory.getLog(UIElementMapper.class);
    private static UIElementMapper instance;

    static {
        try {
            setStream();
            instance = new UIElementMapper();
        } catch (IOException ioe) {
            throw new ExceptionInInitializerError("mapper stream not set. Failed to read file");
        }
    }

    private UIElementMapper() {
    }

    public static UIElementMapper getInstance() {
        return instance;
    }

    private static Properties setStream() throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = UIElementMapper.class.getResourceAsStream("/mapper.properties");
            if (inputStream.available() > 0) {
                uiProperties.load(inputStream);
                return uiProperties;
            }
        } catch (IOException e) {
            String errMsg = "Cannot read the steam";
            log.error(errMsg, e);
            throw new IOException(errMsg, e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.error("Couldn't close the input steam", e);
            }
        }
        return null;
    }

    public String getElement(String key) {
        if (uiProperties != null) {
            return uiProperties.getProperty(key);
        }
        return null;
    }
}
